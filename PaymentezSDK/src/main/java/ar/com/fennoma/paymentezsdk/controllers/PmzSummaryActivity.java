package ar.com.fennoma.paymentezsdk.controllers;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.List;

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.models.PmzOrder;

public class PmzSummaryActivity extends PmzBaseActivity {

    public static final String SHOW_SUMMARY = "show summary";
    public static final String PMZ_ORDER = "order key";

    private PmzOrder order;
    private List<PmzOrder> orderList;
    private boolean justSummary = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmz_summary);
        setFullTitleWithBack(getString(R.string.activity_pmz_summary_title));
        setViews();
        handleIntent();
    }

    private void handleIntent() {
        if(getIntent() != null) {
            justSummary = getIntent().getBooleanExtra(SHOW_SUMMARY, false);
            order = getIntent().getParcelableExtra(PMZ_ORDER);
            orderList = getIntent().getParcelableArrayListExtra(PMZ_ORDER);
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
            TextView back = findViewById(R.id.back);
            back.setTextColor(PmzData.getInstance().getTextColor());
        }
        if(PmzData.getInstance().getButtonBackgroundColor() != null) {
            changeToolbarBackground(PmzData.getInstance().getButtonBackgroundColor());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                replaceRippleBackgroundColor(findViewById(R.id.next));
            }
            changeToolbarBackground(PmzData.getInstance().getButtonBackgroundColor());
        }
        if(PmzData.getInstance().getButtonTextColor() != null) {
            TextView next = findViewById(R.id.next);
            next.setTextColor(PmzData.getInstance().getButtonTextColor());
            changeToolbarTextColor(PmzData.getInstance().getButtonTextColor());
        }
        setButtons();
    }

    private void setButtons() {
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(justSummary) {
                    if(order != null) {
                        PaymentezSDK.getInstance().setOrderResult(order);
                    } else if(orderList != null) {
                        PaymentezSDK.getInstance().setOrderResult(orderList);
                    }
                    PmzData.getInstance().onSearchSuccess();
                } else {
                    PaymentezSDK.getInstance().setOrderResult(PmzOrder.hardcoded());
                    setResult(RESULT_OK);
                }
                finish();
            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(justSummary) {
            PmzData.getInstance().onSearchCancel();
        }
        animActivityLeftToRight();
    }
}
