package ar.com.fennoma.paymentezsdk.controllers;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.utils.DialogUtils;

public class PmzStoresActivity extends PmzBaseActivity {

    public static final String SEARCH_STORES_FILTER = "search stores filter";

    private String storesFilter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmz_stores);
        setFullTitleWithBack(getString(R.string.activity_pmz_stores_title));
        setViews();
        handleIntent();
    }

    private void handleIntent() {
        if(getIntent() != null) {
            storesFilter = getIntent().getStringExtra(SEARCH_STORES_FILTER);
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
                Intent intent = new Intent(PmzStoresActivity.this, PmzMenuActivity.class);
                startActivityForResult(intent, MAIN_FLOW_KEY);
                animActivityRightToLeft();
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
        DialogUtils.showDialog(this, getString(R.string.first_activity_cancel_title),
                getString(R.string.first_activity_cancel_message),
                new DialogUtils.IDialogListener() {
                    @Override
                    public void onAccept() {
                        PmzData.getInstance().onSearchCancel();
                        PmzStoresActivity.super.onBackPressed();
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }
}
