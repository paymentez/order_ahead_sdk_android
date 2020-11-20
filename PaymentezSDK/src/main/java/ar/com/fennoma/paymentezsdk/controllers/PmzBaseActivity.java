package ar.com.fennoma.paymentezsdk.controllers;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.models.PmzError;
import ar.com.fennoma.paymentezsdk.styles.PmzFont;
import ar.com.fennoma.paymentezsdk.utils.TypefaceUtils;

public class PmzBaseActivity extends AppCompatActivity {

    protected static final int MAIN_FLOW_KEY = 1001;
    public static final String SESSION_EXPIRED_KEY = "session expired key";
    public static final String PMZ_STORE = "store Id";
    public static final String PMZ_ORDER_ID = "order id key";
    public static final String PMZ_ORDER = "order key";
    private Toolbar toolbar;
    private Dialog loadingDialog;

    protected void setFont() {
        PmzFont font = PmzData.getInstance().getStyle().getFont();
        if(font != PmzFont.SERIF) {
            TypefaceUtils.overrideFont(getApplicationContext(), "SERIF", PmzFont.getFont(font));
        }
    }

    protected void setFullTitleWithBack(String text) {
        setToolbar();
        hideTitle();
        setToolbarTitle(text);
        setBackButton();
    }

    protected void setFullTitleWOBack(String text) {
        setToolbar();
        hideTitle();
        setToolbarTitle(text);
    }

    protected void setToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        hideTitle();
    }

    protected void hideTitle() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    protected void changeToolbarBackground(Integer color) {
        if(toolbar != null && color != null) {
            toolbar.setBackgroundColor(color);
        }
        if (color != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

    protected void changeToolbarTextColor(Integer color) {
        TextView title = findViewById(R.id.toolbar_title);
        if(title != null && color != null) {
            title.setTextColor(color);
        }
        if(color != null && toolbar != null && toolbar.getNavigationIcon() != null) {
            Drawable navigationIcon = toolbar.getNavigationIcon();
            navigationIcon.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }

    protected void setToolbarTitle(String text) {
        TextView title = findViewById(R.id.toolbar_title);
        title.setText(text);
    }

    protected void setBackButton() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    protected void animActivityRightToLeft() {
        overridePendingTransition(R.anim.enter_right_to_left, R.anim.exit_right_to_left);
    }

    protected void animActivityLeftToRight() {
        overridePendingTransition(R.anim.enter_left_to_right, R.anim.exit_left_to_right);
    }

    protected void onSessionExpired() {
        Intent intent = new Intent().putExtra(SESSION_EXPIRED_KEY, true);
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    public void showLoading() {
        if(isActivityAlive()) {
            hideLoading();
            loadingDialog = new Dialog(PmzBaseActivity.this, R.style.CustomAlertDialog);
            loadingDialog.setContentView(R.layout.dialog_loading);
            loadingDialog.setCancelable(false);
            loadingDialog.show();
        }
    }

    @Override
    protected void onDestroy() {
        hideLoading();
        super.onDestroy();
    }

    public void hideLoading() {
        if (isActivityAlive()) {
            if (loadingDialog != null) {
                loadingDialog.dismiss();
                loadingDialog = null;
            }
        }
    }

    private boolean isActivityAlive() {
        return !isFinishing();
    }
}
