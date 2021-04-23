package com.paymentez.plazez.sdk.controllers;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.material.appbar.AppBarLayout;
import com.paymentez.plazez.sdk.R;
import com.paymentez.plazez.sdk.styles.PmzFont;
import com.paymentez.plazez.sdk.utils.TypefaceUtils;

import java.util.HashMap;
import java.util.Map;

public class PmzBaseActivity extends AppCompatActivity {

    protected static final int MAIN_FLOW_KEY = 1001;
    protected static final int LOCATION_PERMISSION_REQUEST = 113;
    public static final String SESSION_EXPIRED_KEY = "session expired key";
    public static final String PMZ_STORE = "store Id";
    public static final String PMZ_ORDER_ID = "order id key";
    public static final String PMZ_ORDER = "order key";
    private Toolbar toolbar;
    private Dialog loadingDialog;

    protected interface IPermissionsListener {
        void onPermissionAccepted();
        void onPermissionDenied();
    }

    private IPermissionsListener permissionsListener;

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

    protected void changeCollapseIconColor(final Integer color) {
        if(toolbar != null) {
            Drawable drawable = AppCompatResources.getDrawable(this, R.drawable.searchview_back);
            if(drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                toolbar.setCollapseIcon(drawable);
            }
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

    private boolean requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
            if (!hasPermissions(getApplicationContext(), PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, LOCATION_PERMISSION_REQUEST);
                return false;
            } else {
                if(permissionsListener != null) {
                    permissionsListener.onPermissionAccepted();
                }
                return true;
            }
        } else {
            return true;
        }
    }

    protected boolean checkLocationPermission(IPermissionsListener permissionsListener) {
        this.permissionsListener = permissionsListener;
        return requestLocationPermission();
    }

    protected boolean checkLocationPermission() {
        return requestLocationPermission();
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(permissionsListener != null ) {
            Map<String, Integer> expectedPermissions = new HashMap<>();
            if(requestCode == LOCATION_PERMISSION_REQUEST) {
                expectedPermissions.put(Manifest.permission.ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);
                expectedPermissions.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                for (int i = 0; i < permissions.length; i++) {
                    expectedPermissions.put(permissions[i], grantResults[i]);
                }
                if (arePermissionsGranted(expectedPermissions)) {
                    permissionsListener.onPermissionAccepted();
                    return;
                }
                if (!shouldShowRequestPermissionRationale(expectedPermissions)) {
                    permissionsListener.onPermissionDenied();
                    return;
                }
                permissionsListener.onPermissionDenied();
            } else {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public boolean shouldShowRequestPermissionRationale(Map<String, Integer> expectedPermissions) {
        for (String permission : expectedPermissions.keySet()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                return true;
            }
        }
        return false;
    }

    protected boolean arePermissionsGranted(Map<String, Integer> expectedPermissions) {
        for (Integer result : expectedPermissions.values()) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
