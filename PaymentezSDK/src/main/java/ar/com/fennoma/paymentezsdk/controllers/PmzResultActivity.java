package ar.com.fennoma.paymentezsdk.controllers;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.models.PmzError;
import ar.com.fennoma.paymentezsdk.models.PmzOrder;
import ar.com.fennoma.paymentezsdk.utils.ColorHelper;

public class PmzResultActivity extends PmzBaseActivity {

    public static final String PMZ_ORDER = "pmz order";

    private PmzOrder order;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmz_result_detail);
        setFullTitleWithBack(getString(R.string.activity_pmz_result_title));
        setViews();
        handleIntent();
    }

    private void handleIntent() {
        if(getIntent() != null && getIntent().getParcelableExtra(PMZ_ORDER) != null) {
            this.order = getIntent().getParcelableExtra(PMZ_ORDER);
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
        }
        if(PaymentezSDK.getInstance().getStyle().getButtonBackgroundColor() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ColorHelper.replaceButtonBackground(findViewById(R.id.back),
                        PaymentezSDK.getInstance().getStyle().getButtonBackgroundColor());
            }
            changeToolbarBackground(PaymentezSDK.getInstance().getStyle().getButtonBackgroundColor());
        }
        if(PaymentezSDK.getInstance().getStyle().getButtonTextColor() != null) {
            TextView back = findViewById(R.id.back);
            back.setTextColor(PaymentezSDK.getInstance().getStyle().getButtonTextColor());
            changeToolbarTextColor(PaymentezSDK.getInstance().getStyle().getButtonTextColor());
        }
        setButtons();
    }

    private void setButtons() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MAIN_FLOW_KEY && resultCode == RESULT_OK) {
            PmzData.getInstance().onSearchSuccess();
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        PmzData.getInstance().onPaymentCheckingSuccess(order);
    }

}
