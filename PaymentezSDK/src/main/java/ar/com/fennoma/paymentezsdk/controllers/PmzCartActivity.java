package ar.com.fennoma.paymentezsdk.controllers;

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

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.adapters.PmzCartAdapter;
import ar.com.fennoma.paymentezsdk.adapters.SwiperAdapter;
import ar.com.fennoma.paymentezsdk.models.PmzErrorMessage;
import ar.com.fennoma.paymentezsdk.models.PmzItem;
import ar.com.fennoma.paymentezsdk.models.PmzOrder;
import ar.com.fennoma.paymentezsdk.models.PmzStore;
import ar.com.fennoma.paymentezsdk.services.API;
import ar.com.fennoma.paymentezsdk.styles.PmzStyle;
import ar.com.fennoma.paymentezsdk.utils.ColorHelper;
import ar.com.fennoma.paymentezsdk.utils.DialogUtils;
import ar.com.fennoma.paymentezsdk.utils.ImageUtils;
import ar.com.fennoma.paymentezsdk.utils.PmzCurrencyUtils;

public class PmzCartActivity extends AbstractSwiperContainerActivity<PmzItem, PmzCartAdapter.PmzCartHolder> {

    public static final String SHOW_CART = "show cart";
    public static final String ORDER_MODIFIED = "order modified";

    private boolean orderModified = false;
    private boolean justCart = false;

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
    }

    private void handleIntent() {
        if(getIntent() != null) {
            justCart = getIntent().getBooleanExtra(SHOW_CART, false);
            order = getIntent().getParcelableExtra(PMZ_ORDER);
            store = getIntent().getParcelableExtra(PMZ_STORE);

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
                recalculatePrice();
                deleteItem(item);
            }

            @Override
            public void onItemRestored(PmzItem item) {
                /*if(itemRemoved != null) {
                    order.addItem(itemRemoved, positionRemoved);
                }*/
                recalculatePrice();
            }

            @Override
            public void onEditItem(PmzItem item) {

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
                finish();
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
    public void onBackPressed() {
        if(justCart) {
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
}
