package ar.com.fennoma.paymentezsdk.controllers;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.List;

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.models.PmzError;
import ar.com.fennoma.paymentezsdk.models.PmzOrder;
import ar.com.fennoma.paymentezsdk.models.PmzPaymentData;
import ar.com.fennoma.paymentezsdk.utils.ColorHelper;

public class PmzPayAndPlaceActivity extends PmzBaseActivity {

    public static final String PMZ_ORDER = "pmz order";
    public static final String PMZ_PAYMENT_DATA = "pmz payment data";
    public static final String SKIP_SUMMARY = "skip summary";
    public static final String PMZ_ORDERS = "pmz order array";

    private PmzOrder order;
    private List<PmzOrder> orderList;
    private PmzPaymentData paymentData;
    private boolean skipSummary;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmz_pay_and_place_activity);
        setFullTitleWOBack(getString(R.string.activity_pmz_pay_and_place_title));
        setViews();
        handleIntent();
    }

    private void handleIntent() {
        if(getIntent() != null && getIntent().getParcelableExtra(PMZ_ORDER) != null
                && getIntent().getParcelableExtra(PMZ_PAYMENT_DATA) != null) {
            this.order = getIntent().getParcelableExtra(PMZ_ORDER);
            this.orderList = getIntent().getParcelableExtra(PMZ_ORDERS);
            this.paymentData = getIntent().getParcelableExtra(PMZ_PAYMENT_DATA);
            this.skipSummary = getIntent().getBooleanExtra(SKIP_SUMMARY, false);
            setButtons();
        } else {
            finish();
            animActivityLeftToRight();
            PmzData.getInstance().onPaymentCheckingError(null, new PmzError(PmzError.NO_ORDER_SET_ERROR));
        }
    }

    private void setViews() {
        if(PaymentezSDK.getInstance().getStyle().getBackgroundColor() != null) {
            View background = findViewById(R.id.background);
            background.setBackgroundColor(PaymentezSDK.getInstance().getStyle().getBackgroundColor());
        }
        if(PaymentezSDK.getInstance().getStyle().getTextColor() != null) {
            TextView text = findViewById(R.id.text);
            text.setTextColor(PaymentezSDK.getInstance().getStyle().getTextColor());
            ProgressBar progress = findViewById(R.id.progress);
            progress.getIndeterminateDrawable().setColorFilter(PaymentezSDK.getInstance().getStyle().getTextColor(), PorterDuff.Mode.SRC_IN);
        }
        if(PaymentezSDK.getInstance().getStyle().getButtonBackgroundColor() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ColorHelper.replaceButtonBackground(findViewById(R.id.payment_error),
                        PaymentezSDK.getInstance().getStyle().getButtonBackgroundColor());
                ColorHelper.replaceButtonBackground(findViewById(R.id.place_error),
                        PaymentezSDK.getInstance().getStyle().getButtonBackgroundColor());
                ColorHelper.replaceButtonBackground(findViewById(R.id.success),
                        PaymentezSDK.getInstance().getStyle().getButtonBackgroundColor());
            }
            changeToolbarBackground(PaymentezSDK.getInstance().getStyle().getButtonBackgroundColor());
        }
        if(PaymentezSDK.getInstance().getStyle().getButtonTextColor() != null) {
            TextView paymentError = findViewById(R.id.payment_error);
            paymentError.setTextColor(PaymentezSDK.getInstance().getStyle().getButtonTextColor());
            TextView placeError = findViewById(R.id.place_error);
            placeError.setTextColor(PaymentezSDK.getInstance().getStyle().getButtonTextColor());
            TextView success = findViewById(R.id.success);
            success.setTextColor(PaymentezSDK.getInstance().getStyle().getButtonTextColor());
            changeToolbarTextColor(PaymentezSDK.getInstance().getStyle().getButtonTextColor());
        }
    }

    private void setButtons() {
        findViewById(R.id.success).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(skipSummary) {
                    finish();
                    if(order != null) {
                        PmzData.getInstance().onPaymentCheckingSuccess(order);
                    } else if(orderList != null) {
                        PmzData.getInstance().onPaymentCheckingSuccess(orderList);
                    }
                } else {
                    Intent intent = new Intent(PmzPayAndPlaceActivity.this, PmzResultActivity.class);
                    intent.putExtra(PmzResultActivity.PMZ_ORDER, order);
                    startActivity(intent);
                    animActivityRightToLeft();
                    finish();
                }
            }
        });
        findViewById(R.id.place_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                if(order != null) {
                    PmzData.getInstance().onPaymentCheckingError(order, new PmzError(PmzError.PLACE_ERROR));
                } else if(orderList != null) {
                    PmzData.getInstance().onPaymentMultipleOrdersCheckingError(orderList, new PmzError(PmzError.PLACE_ERROR));
                }
            }
        });
        findViewById(R.id.payment_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                if(order != null) {
                    PmzData.getInstance().onPaymentCheckingError(order, new PmzError(PmzError.PAYMENT_ERROR));
                } else if(orderList != null) {
                    PmzData.getInstance().onPaymentMultipleOrdersCheckingError(orderList, new PmzError(PmzError.PAYMENT_ERROR));
                }
            }
        });
    }

    @Override
    public void onBackPressed() {}
}
