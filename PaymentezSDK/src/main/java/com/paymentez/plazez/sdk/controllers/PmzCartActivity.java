package com.paymentez.plazez.sdk.controllers;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.paymentez.plazez.sdk.R;
import com.paymentez.plazez.sdk.adapters.PmzCartAdapter;
import com.paymentez.plazez.sdk.adapters.SwiperAdapter;
import com.paymentez.plazez.sdk.models.PmzErrorMessage;
import com.paymentez.plazez.sdk.models.PmzItem;
import com.paymentez.plazez.sdk.models.PmzOrder;
import com.paymentez.plazez.sdk.models.PmzStore;
import com.paymentez.plazez.sdk.services.API;
import com.paymentez.plazez.sdk.styles.PmzStyle;
import com.paymentez.plazez.sdk.utils.ColorHelper;
import com.paymentez.plazez.sdk.utils.DialogUtils;
import com.paymentez.plazez.sdk.utils.ImageUtils;
import com.paymentez.plazez.sdk.utils.PmzCurrencyUtils;

public class PmzCartActivity extends AbstractSwiperContainerActivity<PmzItem, PmzCartAdapter.PmzCartHolder> {

    public static final String SHOW_CART = "show cart";
    public static final String ORDER_MODIFIED = "order modified";
    public static final String FROM_REOPEN = "from reopen";
    private static final int EDIT_ITEM = 3001;

    private boolean orderModified = false;
    private boolean justCart = false;
    private boolean fromReopen = false;

    private PmzOrder order;
    private PmzStore store;

    private RecyclerView recycler;
    private PmzCartAdapter adapter;

    private TextView price;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmz_cart);
        setFullTitleWithBack(getString(R.string.activity_pmz_cart_title));
        setViews();
        handleIntent();
        if(fromReopen) {
            getToken();
        }
    }

    private void handleIntent() {
        if(getIntent() != null) {
            justCart = getIntent().getBooleanExtra(SHOW_CART, false);
            order = getIntent().getParcelableExtra(PMZ_ORDER);
            store = getIntent().getParcelableExtra(PMZ_STORE);
            fromReopen = getIntent().getBooleanExtra(FROM_REOPEN, false);

            if(order == null) {
                order = new PmzOrder();
            }
            setDataIntoViews();
        }
    }

    private void setDataIntoViews() {
        setStoreData();
        recalculatePrice();
        setDataIntoRecycler();
    }

    private void setDataIntoRecycler() {
        if(order == null || order.getItems() == null || order.getItems().size() == 0) {
            recycler.setVisibility(View.GONE);
        } else {
            recycler.setVisibility(View.VISIBLE);
            adapter.setItems(order.getItems());
        }
    }

    private void setStoreData() {
        ImageView icon = findViewById(R.id.icon);
        TextView title = findViewById(R.id.title);
        TextView description = findViewById(R.id.description);

        if(store != null) {
            ImageUtils.loadStoreImage(this, icon, store.getImageUrl());

            title.setText(store.getName());
            description.setText(store.getCommerceName());
        }

        if(PaymentezSDK.getInstance().getStyle().getTextColor() != null) {
            title.setTextColor(PaymentezSDK.getInstance().getStyle().getTextColor());
            description.setTextColor(PaymentezSDK.getInstance().getStyle().getTextColor());
        }
    }

    private void setViews() {
        PmzStyle style = PaymentezSDK.getInstance().getStyle();
        if(style != null) {
            if (style.getTextColor() != null) {
                price = findViewById(R.id.price);
                price.setTextColor(style.getTextColor());
            }
            if (style.getBackgroundColor() != null) {
                View background = findViewById(R.id.background);
                background.setBackgroundColor(style.getBackgroundColor());
            }
            if (style.getButtonBackgroundColor() != null) {
                changeToolbarBackground(style.getButtonBackgroundColor());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ColorHelper.replaceButtonBackground(findViewById(R.id.next), style.getButtonBackgroundColor());
                    ColorHelper.replaceButtonBackground(findViewById(R.id.keep_buying), style.getButtonBackgroundColor());
                }
                changeToolbarBackground(style.getButtonBackgroundColor());
            }
            if (style.getButtonTextColor() != null) {
                TextView next = findViewById(R.id.next);
                TextView keepBuying = findViewById(R.id.keep_buying);
                next.setTextColor(style.getButtonTextColor());
                keepBuying.setTextColor(style.getButtonTextColor());
                changeToolbarTextColor(style.getButtonTextColor());
            }
        }
        setRecycler();
        setButtons();
    }

    private void setRecycler() {
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PmzCartAdapter(this, new PmzCartAdapter.IPmzCartAdapterListener() {
            @Override
            public void onItemRemoved(PmzItem item, int position) {
                deleteItem(item);
                recalculatePrice();
            }

            @Override
            public void onItemRestored(PmzItem item) {
                recalculatePrice();
            }

            @Override
            public void onEditItem(PmzItem item) {
                Intent intent = new Intent(PmzCartActivity.this, PmzProductActivity.class);
                item.setOrderId(order.getId());
                intent.putExtra(PmzProductActivity.PMZ_ITEM, item);
                intent.putExtra(PMZ_STORE, store);
                startActivityForResult(intent, EDIT_ITEM);
                animActivityRightToLeft();
            }
        });
        recycler.setAdapter(adapter);
        recycler.setNestedScrollingEnabled(false);
        setRecyclerItemTouchHelper(recycler);
    }

    private void deleteItem(PmzItem item) {
        showLoading();
        API.deleteItem(item, new API.ServiceCallback<PmzOrder>() {
            @Override
            public void onSuccess(PmzOrder response) {
                hideLoading();
                orderModified = true;
                response.mergeData(order);
                PmzCartActivity.this.order = response;
                recalculatePrice();
                if(order.getItems() == null || order.getItems().size() == 0) {
                    DialogUtils.toast(PmzCartActivity.this, getString(R.string.cart_no_items_to_show));
                    onBackPressed();
                }
            }

            @Override
            public void onError(PmzErrorMessage error) {
                hideLoading();
                DialogUtils.toast(PmzCartActivity.this, error.getErrorMessage());
            }

            @Override
            public void onFailure() {
                hideLoading();
                DialogUtils.genericError(PmzCartActivity.this);
            }

            @Override
            public void sessionExpired() {
                hideLoading();
                onSessionExpired();
                PmzData.getInstance().onSearchSessionExpired();
            }
        });
    }

    private void recalculatePrice() {
        if(order != null) {
            price.setText(PmzCurrencyUtils.formatPrice(order.getFullPrice()));
        } else {
            price.setText(PmzCurrencyUtils.formatPrice(0L));
        }
    }

    private void setButtons() {
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(justCart) {
                    if(order != null) {
                        PaymentezSDK.getInstance().setOrderResult(order);
                    }
                    PmzData.getInstance().onSearchSuccess();
                } else {
                    if(order != null && store != null) {
                        order.setStore(store);
                    }
                    PaymentezSDK.getInstance().setOrderResult(order);
                    setResult(RESULT_OK);
                }
                if(fromReopen) {
                    PmzData.getInstance().onSearchSuccess();
                }
                finish();
                animActivityRightToLeft();
            }
        });
        findViewById(R.id.keep_buying).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EDIT_ITEM && resultCode == RESULT_OK && data != null && data.getParcelableExtra(PMZ_ORDER) != null) {
            orderModified = true;
            this.order = data.getParcelableExtra(PMZ_ORDER);
            setDataIntoViews();
        }
    }

    @Override
    public void onBackPressed() {
        if(fromReopen) {
            goBackToMenuFromReopen();
        } else if(justCart) {
            super.onBackPressed();
            PmzData.getInstance().onSearchCancel();
        } else if(orderModified) {
            sendBackOrder();
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
        animActivityLeftToRight();
    }

    private void goBackToMenuFromReopen() {
        Intent intent = new Intent(this, PmzMenuActivity.class);
        intent.putExtra(PMZ_ORDER, order);
        intent.putExtra(PMZ_STORE, store);
        intent.putExtra(FROM_REOPEN, true);
        startActivity(intent);
        finish();
    }

    private void sendBackOrder() {
        Intent intent = new Intent();
        intent.putExtra(ORDER_MODIFIED, true);
        intent.putExtra(PMZ_ORDER, order);
        setResult(RESULT_OK, intent);
    }

    @Override
    protected String getDeletedLabelWarning() {
        return getString(R.string.swipe_to_delete_favorite_warn);
    }

    @Override
    protected CoordinatorLayout getCoordinatorLayout() {
        return findViewById(R.id.coordinator);
    }

    @Override
    protected SwiperAdapter<PmzItem, PmzCartAdapter.PmzCartHolder> getAdapter() {
        return adapter;
    }

    @Override
    protected List<PmzItem> getItems() {
        return order.getItems();
    }

    private void getToken() {
        showLoading();
        API.getSession(PmzData.getInstance().getSession(), new API.ServiceCallback<String>() {
            @Override
            public void onSuccess(String response) {
                hideLoading();
                PmzData.getInstance().setToken(response);
            }

            @Override
            public void onError(PmzErrorMessage error) {
                hideLoading();
                DialogUtils.toast(PmzCartActivity.this, error.getErrorMessage());
                finish();
                animActivityLeftToRight();
            }

            @Override
            public void onFailure() {
                hideLoading();
                DialogUtils.genericError(PmzCartActivity.this);
                finish();
                animActivityLeftToRight();
            }

            @Override
            public void sessionExpired() {
                hideLoading();
                onSessionExpired();
                PmzData.getInstance().onSearchSessionExpired();
            }
        });
    }
}
