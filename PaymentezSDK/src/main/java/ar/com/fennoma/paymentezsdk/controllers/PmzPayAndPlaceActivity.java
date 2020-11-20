package ar.com.fennoma.paymentezsdk.controllers;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.List;

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.models.PmzError;
import ar.com.fennoma.paymentezsdk.models.PmzErrorMessage;
import ar.com.fennoma.paymentezsdk.models.PmzOrder;
import ar.com.fennoma.paymentezsdk.models.PmzPaymentData;
import ar.com.fennoma.paymentezsdk.services.API;
import ar.com.fennoma.paymentezsdk.utils.DialogUtils;

public class PmzPayAndPlaceActivity extends PmzBaseActivity {

    public static final String PMZ_PAYMENT_DATA = "pmz payment data";
    public static final String PMZ_PAYMENTS_DATA = "pmz payments data";
    public static final String SKIP_SUMMARY = "skip summary";

    private PmzOrder order;
    private PmzPaymentData paymentData;
    private List<PmzPaymentData> payments;

    private boolean skipSummary;
    private boolean multiPayment = false;

    private PmzPaymentData currentPayment;
    private int currentIndex = 0;
    private PmzOrder orderResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmz_pay_and_place_activity);
        setFont();
        setFullTitleWOBack(getString(R.string.activity_pmz_pay_and_place_title));
        setViews();
        if(TextUtils.isEmpty(PmzData.getInstance().getToken())) {
            getToken();
        } else {
            handleIntent();
        }
    }

    private void handleIntent() {
        if(getIntent() != null && getIntent().getParcelableExtra(PMZ_ORDER) != null
                && (getIntent().getParcelableExtra(PMZ_PAYMENT_DATA) != null ||
                getIntent().getParcelableArrayListExtra(PMZ_PAYMENTS_DATA) != null)) {
            this.order = getIntent().getParcelableExtra(PMZ_ORDER);
            this.paymentData = getIntent().getParcelableExtra(PMZ_PAYMENT_DATA);
            this.payments = getIntent().getParcelableArrayListExtra(PMZ_PAYMENTS_DATA);
            this.skipSummary = getIntent().getBooleanExtra(SKIP_SUMMARY, false);
            if(payments != null) {
                multiPayment = true;
            }
            handlePayment();
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
        }
        if(PaymentezSDK.getInstance().getStyle().getButtonBackgroundColor() != null) {
            changeToolbarBackground(PaymentezSDK.getInstance().getStyle().getButtonBackgroundColor());
            ProgressBar progress = findViewById(R.id.progress);
            progress.getIndeterminateDrawable().setColorFilter(PaymentezSDK.getInstance().getStyle().getButtonBackgroundColor(),
                    PorterDuff.Mode.SRC_IN);
        }
        if(PaymentezSDK.getInstance().getStyle().getButtonTextColor() != null) {
            changeToolbarTextColor(PaymentezSDK.getInstance().getStyle().getButtonTextColor());
        }
    }

    private void handlePayment() {
        if(currentIndex == 0) {
            if(payments != null && payments.size() > 0) {
                currentPayment = payments.get(0);
                currentIndex = 1;
            } else {
                currentPayment = paymentData;
                currentIndex = -1;
            }
            doPayment();
        } else if(currentIndex == -1 || payments.size() <= currentIndex) {
            doPlace();
        } else {
            currentPayment = payments.get(currentIndex);
            currentIndex++;
            doPayment();
        }
    }

    private void doPayment() {
        if(currentPayment != null && order != null && order.getId() != null) {
            API.pay(currentPayment, order.getId(), new API.ServiceCallback<PmzOrder>() {
                @Override
                public void onSuccess(PmzOrder response) {
                    orderResult = response.mergeData(order);;
                    handlePayment();
                }

                @Override
                public void onError(PmzErrorMessage error) {
                    showErrorAndBack(new PmzError(PmzError.PAYMENT_ERROR));
                }

                @Override
                public void onFailure() {
                    showErrorAndBack();
                }

                @Override
                public void sessionExpired() {
                    resolveSessionExpired();
                }
            });
        } else {
            showErrorAndBack();
        }
    }

    private void resolveSessionExpired() {
        finish();
        if(multiPayment) {
            PmzData.getInstance().onMultiplePaymentSessionExpired(order);
        } else {
            PmzData.getInstance().onPaymentSessionExpired(order);
        }
    }

    private void showErrorAndBack(PmzError error) {
        if(multiPayment) {
            PmzData.getInstance().onMultiplePaymentOrderCheckingError(order, error);
        } else {
            PmzData.getInstance().onPaymentCheckingError(order, error);
        }
        finish();
    }

    private void showErrorAndBack() {
        if(multiPayment) {
            PmzData.getInstance().onMultiplePaymentOrderCheckingError(order, new PmzError(PmzError.GENERIC_SERVICE_ERROR));
        } else {
            PmzData.getInstance().onPaymentCheckingError(order, new PmzError(PmzError.GENERIC_SERVICE_ERROR));
        }
        finish();
    }

    private void doPlace() {
        API.placeOrder(orderResult, new API.ServiceCallback<PmzOrder>() {
            @Override
            public void onSuccess(PmzOrder response) {
                orderResult = response.mergeData(order);
                showSummary();
            }

            @Override
            public void onError(PmzErrorMessage error) {
                showErrorAndBack(new PmzError(PmzError.PLACE_ERROR));
            }

            @Override
            public void onFailure() {
                showErrorAndBack();
            }

            @Override
            public void sessionExpired() {
                resolveSessionExpired();
            }
        });
    }

    private void showSummary() {
        if(!skipSummary) {
            Intent intent = new Intent(this, PmzSummaryActivity.class);
            intent.putExtra(PMZ_ORDER, orderResult);
            intent.putExtra(PmzSummaryActivity.MULTIPLE_PAYMENT, multiPayment);
            startActivity(intent);
            animActivityRightToLeft();
        } else {
            if(multiPayment) {
                PmzData.getInstance().onMultiplePaymentCheckingSuccess(orderResult);
            } else {
                PmzData.getInstance().onPaymentCheckingSuccess(orderResult);
            }
        }
        finish();
    }

    @Override
    public void onBackPressed() {}

    private void getToken() {
        API.getSession(PmzData.getInstance().getSession(), new API.ServiceCallback<String>() {
            @Override
            public void onSuccess(String response) {
                PmzData.getInstance().setToken(response);
                handleIntent();
            }

            @Override
            public void onError(PmzErrorMessage error) {
                DialogUtils.toast(PmzPayAndPlaceActivity.this, error.getErrorMessage());
                finish();
            }

            @Override
            public void onFailure() {
                DialogUtils.genericError(PmzPayAndPlaceActivity.this);
                finish();
            }

            @Override
            public void sessionExpired() {
                resolveSessionExpired();
            }
        });
    }
}
