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
        if(PmzData.getInstance().getBackgroundColor() != null) {
            View background = findViewById(R.id.background);
            background.setBackgroundColor(PmzData.getInstance().getBackgroundColor());
        }
        if(PmzData.getInstance().getTextColor() != null) {
            TextView text = findViewById(R.id.text);
            text.setTextColor(PmzData.getInstance().getTextColor());
        }
        if(PmzData.getInstance().getButtonBackgroundColor() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                replaceRippleBackgroundColor(findViewById(R.id.back));
            }
            changeToolbarBackground(PmzData.getInstance().getButtonBackgroundColor());
        }
        if(PmzData.getInstance().getButtonTextColor() != null) {
            TextView back = findViewById(R.id.back);
            back.setTextColor(PmzData.getInstance().getButtonTextColor());
            changeToolbarTextColor(PmzData.getInstance().getButtonTextColor());
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
