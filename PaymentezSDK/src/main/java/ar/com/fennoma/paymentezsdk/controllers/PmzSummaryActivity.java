package ar.com.fennoma.paymentezsdk.controllers;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.adapters.PmzSummaryAdapter;
import ar.com.fennoma.paymentezsdk.models.PmzOrder;
import ar.com.fennoma.paymentezsdk.models.PmzStore;
import ar.com.fennoma.paymentezsdk.styles.PmzStyle;
import ar.com.fennoma.paymentezsdk.utils.ColorHelper;
import ar.com.fennoma.paymentezsdk.utils.ImageUtils;
import ar.com.fennoma.paymentezsdk.utils.PmzCurrencyUtils;

public class PmzSummaryActivity extends PmzBaseActivity {

    public static final String JUST_SUMMARY = "just summary";
    public static final String MULTIPLE_PAYMENT = "multiple payment";

    private PmzSummaryAdapter adapter;

    private boolean justCart;
    private PmzOrder order;
    private PmzStore store;
    private boolean multiplePayment;

    private TextView price;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmz_summary);
        setFont();
        setFullTitleWithBack(getString(R.string.activity_pmz_summary_title));
        setViews();
        handleIntent();
    }

    private void handleIntent() {
        if(getIntent() != null) {
            justCart = getIntent().getBooleanExtra(JUST_SUMMARY, false);
            multiplePayment = getIntent().getBooleanExtra(MULTIPLE_PAYMENT, false);
            order = getIntent().getParcelableExtra(PMZ_ORDER);
            if(order != null) {
                store = order.getStore();
            }

            if(order == null) {
                order = new PmzOrder();
            }
            setDataIntoViews();
        }
    }

    private void setDataIntoViews() {
        setStoreData();
        calculatePrice();
        setDataIntoRecycler();
    }

    private void setStoreData() {
        ImageView image = findViewById(R.id.image);
        ImageView icon = findViewById(R.id.icon);
        TextView title = findViewById(R.id.title);
        TextView description = findViewById(R.id.description);

        if(store != null) {
            ImageUtils.loadStoreImage(this, image, store.getImageUrl());
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
                    ColorHelper.replaceButtonBackground(findViewById(R.id.next),
                            style.getButtonBackgroundColor());
                }
                changeToolbarBackground(style.getButtonBackgroundColor());
            }
            if (style.getButtonTextColor() != null) {
                TextView next = findViewById(R.id.next);
                next.setTextColor(style.getButtonTextColor());
                changeToolbarTextColor(style.getButtonTextColor());
            }
        }
        setRecycler();
        setButtons();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(justCart) {
            PmzData.getInstance().onSearchCancel();
        } else {
            if(multiplePayment) {
                PmzData.getInstance().onMultiplePaymentCheckingSuccess(order);
            } else {
                PmzData.getInstance().onPaymentCheckingSuccess(order);
            }
        }
    }

    private void calculatePrice() {
        if(order != null) {
            price.setText(PmzCurrencyUtils.formatPrice(order.getFullPrice()));
        } else {
            price.setText(PmzCurrencyUtils.formatPrice(0L));
        }
    }

    private void setRecycler() {
        RecyclerView recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PmzSummaryAdapter(this);
        recycler.setAdapter(adapter);
        recycler.setNestedScrollingEnabled(false);
    }

    private void setDataIntoRecycler() {
        adapter.setItems(order.getItems());
    }

    private void setButtons() {
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(justCart) {
                    PmzData.getInstance().onSearchSuccess();
                } else if(multiplePayment) {
                    PmzData.getInstance().onMultiplePaymentCheckingSuccess(order);
                } else {
                    PmzData.getInstance().onPaymentCheckingSuccess(order);
                }
                finish();

            }
        });
    }
}
