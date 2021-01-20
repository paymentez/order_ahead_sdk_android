package com.paymentez.plazez.sdk.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.paymentez.plazez.sdk.R;
import com.paymentez.plazez.sdk.adapters.PmzProductAdapter;
import com.paymentez.plazez.sdk.controls.QuantitySelector;
import com.paymentez.plazez.sdk.models.PmzCategory;
import com.paymentez.plazez.sdk.models.PmzErrorMessage;
import com.paymentez.plazez.sdk.models.PmzItem;
import com.paymentez.plazez.sdk.models.PmzMenu;
import com.paymentez.plazez.sdk.models.PmzOrder;
import com.paymentez.plazez.sdk.models.PmzProduct;
import com.paymentez.plazez.sdk.models.PmzStore;
import com.paymentez.plazez.sdk.services.API;
import com.paymentez.plazez.sdk.utils.ColorHelper;
import com.paymentez.plazez.sdk.utils.DialogUtils;
import com.paymentez.plazez.sdk.utils.ImageUtils;
import com.paymentez.plazez.sdk.utils.PmzCurrencyUtils;

public class PmzProductActivity extends PmzBaseActivity {

    public static final String PRODUCT_KEY = "product key";
    public static final String PMZ_ITEM = "item key";

    private PmzProductAdapter adapter;
    private QuantitySelector quantitySelector;

    private PmzProduct product;
    private Long orderId;
    private PmzItem item;
    private PmzOrder order;

    private ImageView image;
    private TextView title;
    private TextView description;
    private TextView price;
    private TextView quantityTitle;
    private TextView totalTitle;
    private TextView totalPrice;

    private long extras = 0;
    private boolean itemEdit = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmz_product);
        setFullTitleWithBack(getString(R.string.activity_pmz_product_title));
        findViews();
        setViews();
        setRecycler();
        handleIntent();
    }

    private void setRecycler() {
        RecyclerView recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PmzProductAdapter(this, new PmzProductAdapter.IExtrasListener() {
            @Override
            public void onExtrasUpdated(long extras) {
                PmzProductActivity.this.extras = extras;
                refreshPrice(item.getQuantity());
            }
        });
        recycler.setAdapter(adapter);
    }

    private void findViews() {
        image = findViewById(R.id.image);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);
        quantityTitle = findViewById(R.id.quantity_title);
        totalTitle = findViewById(R.id.total_title);
        quantitySelector = findViewById(R.id.quantity_selector);
        totalPrice = findViewById(R.id.total_price);
        quantitySelector.setListener(new QuantitySelector.PmzIQuantitySelectorListener() {
            @Override
            public void onQuantityChanged(int quantity) {
                item.setQuantity(quantity);
                refreshPrice(quantity);
            }
        });
    }

    private void refreshPrice(int quantity) {
        if(product != null && product.getListPrice() != null) {
            Long listPrice = product.getListPrice();
            if(adapter != null) {
                listPrice += adapter.getExtras();
            }
            totalPrice.setText(PmzCurrencyUtils.formatPrice(listPrice * quantity));
        }
    }

    private void handleIntent() {
        if(getIntent() != null && getIntent().getParcelableExtra(PMZ_ITEM) != null) {
            itemEdit = true;
            item = getIntent().getParcelableExtra(PMZ_ITEM);
            PmzStore store = getIntent().getParcelableExtra(PMZ_STORE);
            changeButtonToEditMode();
            if(store != null && store.getId() != null && item != null && item.getProductId() != null) {
                quantitySelector.setQuantity(item.getQuantity());
                getProduct(store.getId());
            } else {
                DialogUtils.genericError(this);
                onBackPressed();
            }
        } else if(getIntent() != null && getIntent().getParcelableExtra(PRODUCT_KEY) != null) {
            orderId = getIntent().getLongExtra(PMZ_ORDER_ID, 0L);
            product = getIntent().getParcelableExtra(PRODUCT_KEY);
            order = getIntent().getParcelableExtra(PMZ_ORDER);
            setDataIntoViews();
            item = new PmzItem(product, orderId);
        } else {
            DialogUtils.genericError(this);
            onBackPressed();
        }
    }

    private void setDataIntoViews() {
        ImageUtils.loadProductImage(this, image, product.getImageUrl());
        title.setText(product.getName());
        description.setText(product.getDescription());
        price.setText("$".concat(String.valueOf(product.getCurrentPrice())));
        refreshPrice(1);
        if(itemEdit) {
            adapter.setProductForEdit(product, item);
            refreshPrice(quantitySelector.getCounter());
        } else {
            adapter.setProduct(product);
        }
    }

    private void setViews() {
        if(PaymentezSDK.getInstance().getStyle().getBackgroundColor() != null) {
            View background = findViewById(R.id.background);
            background.setBackgroundColor(PaymentezSDK.getInstance().getStyle().getBackgroundColor());
        }
        if(PaymentezSDK.getInstance().getStyle().getTextColor() != null) {
            Integer textColor = PaymentezSDK.getInstance().getStyle().getTextColor();
            title.setTextColor(textColor);
            description.setTextColor(textColor);
            price.setTextColor(textColor);
            quantityTitle.setTextColor(textColor);
            totalTitle.setTextColor(textColor);
            totalPrice.setTextColor(textColor);
        }
        if(PaymentezSDK.getInstance().getStyle().getButtonBackgroundColor() != null) {
            ColorHelper.replaceButtonBackground(findViewById(R.id.next),
                    PaymentezSDK.getInstance().getStyle().getButtonBackgroundColor());
            changeToolbarBackground(PaymentezSDK.getInstance().getStyle().getButtonBackgroundColor());
        }
        if(PaymentezSDK.getInstance().getStyle().getButtonTextColor() != null) {
            TextView next = findViewById(R.id.next);
            next.setTextColor(PaymentezSDK.getInstance().getStyle().getButtonTextColor());
            changeToolbarTextColor(PaymentezSDK.getInstance().getStyle().getButtonTextColor());
        }
        setButtons();
    }

    private void setButtons() {
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading();
                item.setConfigurations(adapter.getOrganizer());
                if(itemEdit) {
                    deleteItemWConfigurations();
                } else {
                    addItemWConfigurations();
                }
            }
        });
    }

    private void changeButtonToEditMode() {
        TextView next = findViewById(R.id.next);
        next.setText(getString(R.string.pmz_product_edit_button));
    }

    private void deleteItemWConfigurations() {
        API.deleteItem(item, new API.ServiceCallback<PmzOrder>() {
            @Override
            public void onSuccess(PmzOrder response) {
                addItemWConfigurations();
            }

            @Override
            public void onError(PmzErrorMessage error) {
                hideLoading();
                DialogUtils.toast(PmzProductActivity.this, error.getErrorMessage());
            }

            @Override
            public void onFailure() {
                hideLoading();
                DialogUtils.genericError(PmzProductActivity.this);
            }

            @Override
            public void sessionExpired() {
                hideLoading();
                onSessionExpired();
                PmzData.getInstance().onSearchSessionExpired();
            }
        });
    }

    private void addItemWConfigurations() {
        API.addItemWithConfigurations(item, new API.ServiceCallback<PmzOrder>() {
            @Override
            public void onSuccess(PmzOrder response) {
                hideLoading();
                sendBackOrder(mergeData(response));
            }

            @Override
            public void onError(PmzErrorMessage error) {
                hideLoading();
                DialogUtils.toast(PmzProductActivity.this, error.getErrorMessage());
            }

            @Override
            public void onFailure() {
                hideLoading();
                DialogUtils.genericError(PmzProductActivity.this);
            }

            @Override
            public void sessionExpired() {
                hideLoading();
                onSessionExpired();
                PmzData.getInstance().onSearchSessionExpired();
            }
        });
    }

    private void sendBackOrder(PmzOrder order) {
        Intent intent = new Intent();
        intent.putExtra(PMZ_ORDER, order);
        setResult(RESULT_OK, intent);
        finish();
        animActivityLeftToRight();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        animActivityLeftToRight();
    }

    private PmzOrder mergeData(PmzOrder response) {
        if(response != null && response.getItems() != null) {
            for(PmzItem item: response.getItems()) {
                if(item.getProductId().equals(PmzProductActivity.this.product.getId())) {
                    item.setImageUrl(PmzProductActivity.this.product.getImageUrl());
                }
            }
            if(order != null && order.getItems() != null) {
                for(PmzItem oldItem: order.getItems()) {
                    for(PmzItem newItem: response.getItems()) {
                        if(newItem.getProductId().equals(oldItem.getProductId()) && !TextUtils.isEmpty(oldItem.getImageUrl())) {
                            newItem.setImageUrl(oldItem.getImageUrl());
                        }
                    }
                }
            }
        }
        return response;
    }

    private void getProduct(final Long storeId) {
        showLoading();
        API.getMenu(storeId, new API.ServiceCallback<PmzMenu>() {
            @Override
            public void onSuccess(PmzMenu response) {
                findProduct(response);
                hideLoading();
                if(product != null) {
                    setDataIntoViews();
                }
            }

            @Override
            public void onError(PmzErrorMessage error) {
                DialogUtils.genericError(PmzProductActivity.this);
                onBackPressed();
            }

            @Override
            public void onFailure() {
                DialogUtils.genericError(PmzProductActivity.this);
                onBackPressed();
            }

            @Override
            public void sessionExpired() {
                onSessionExpired();
            }
        });
    }

    private void findProduct(PmzMenu menu) {
        if(menu != null) {
            for(PmzCategory category: menu.getCategories()) {
                if (category.getProducts() != null) {
                    for (PmzProduct product : category.getProducts()) {
                        if(product != null && product.getId() != null && product.getId().equals(item.getProductId())) {
                            this.product = product;
                        }
                    }
                }
            }
        }
    }
}
