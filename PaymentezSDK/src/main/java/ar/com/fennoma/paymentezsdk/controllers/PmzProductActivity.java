package ar.com.fennoma.paymentezsdk.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.adapters.PmzProductAdapter;
import ar.com.fennoma.paymentezsdk.controls.QuantitySelector;
import ar.com.fennoma.paymentezsdk.models.PmzErrorMessage;
import ar.com.fennoma.paymentezsdk.models.PmzItem;
import ar.com.fennoma.paymentezsdk.models.PmzOrder;
import ar.com.fennoma.paymentezsdk.models.PmzProduct;
import ar.com.fennoma.paymentezsdk.services.API;
import ar.com.fennoma.paymentezsdk.utils.ColorHelper;
import ar.com.fennoma.paymentezsdk.utils.DialogUtils;
import ar.com.fennoma.paymentezsdk.utils.ImageUtils;

public class PmzProductActivity extends PmzBaseActivity {

    public static final String PRODUCT_KEY = "product key";

    private PmzProductAdapter adapter;

    private PmzProduct product;
    private Long orderId;
    private PmzItem item;

    private ImageView image;
    private TextView title;
    private TextView description;
    private TextView price;
    private TextView quantityTitle;
    private TextView totalTitle;
    private TextView totalPrice;
    private QuantitySelector quantitySelector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmz_product);
        setFullTitleWithBack(getString(R.string.activity_pmz_product_title));
        findViews();
        setViews();
        setRecycler();
        handleIntent();
        item = new PmzItem(product, orderId);
    }

    private void setRecycler() {
        RecyclerView recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PmzProductAdapter(this);
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
            totalPrice.setText("$".concat(String.valueOf(listPrice * quantity)));

        }
    }

    private void handleIntent() {
        if(getIntent() != null && getIntent().getParcelableExtra(PRODUCT_KEY) != null) {
            orderId = getIntent().getLongExtra(PMZ_ORDER_ID, 0L);
            product = getIntent().getParcelableExtra(PRODUCT_KEY);
            setDataIntoViews();
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
        adapter.setProduct(product);
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
                //API.addItemWithConfigurations(item, new API.ServiceCallback<PmzOrder>() {
                API.addItemWithConfigurations(PmzItem.hardcoded(), new API.ServiceCallback<PmzOrder>() {
                    @Override
                    public void onSuccess(PmzOrder response) {
                        hideLoading();
                        sendBackOrder(response);
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
                    }
                });
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
}
