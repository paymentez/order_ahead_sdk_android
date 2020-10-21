package ar.com.fennoma.paymentezsdk.controllers;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.models.PmzOrder;

public class PmzProductActivity extends PmzBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmz_product);
        setFullTitleWithBack(getString(R.string.activity_pmz_product_title));
        setViews();
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
                Intent intent = new Intent(PmzProductActivity.this, PmzSummaryActivity.class);
                intent.putExtra(PmzSummaryActivity.SHOW_SUMMARY, PmzOrder.hardcoded());
                startActivityForResult(intent, MAIN_FLOW_KEY);
                animActivityRightToLeft();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MAIN_FLOW_KEY && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        animActivityLeftToRight();
    }
}
