package ar.com.fennoma.paymentezsdk.controllers;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.adapters.MenuPagerAdapter;
import ar.com.fennoma.paymentezsdk.models.PmzErrorMessage;
import ar.com.fennoma.paymentezsdk.models.PmzMenu;
import ar.com.fennoma.paymentezsdk.models.PmzOrder;
import ar.com.fennoma.paymentezsdk.models.PmzProduct;
import ar.com.fennoma.paymentezsdk.models.PmzStore;
import ar.com.fennoma.paymentezsdk.services.API;
import ar.com.fennoma.paymentezsdk.utils.ColorHelper;
import ar.com.fennoma.paymentezsdk.utils.DialogUtils;
import ar.com.fennoma.paymentezsdk.utils.ImageUtils;

public class PmzMenuActivity extends PmzBaseActivity {

    public static final String FORCED_ID = "forced Id";
    private static final int ADD_PRODUCT_REQUEST = 1002;

    private PmzStore store;
    private boolean forcedId = false;
    private MenuPagerAdapter adapter;
    private TabLayout tabLayout;
    private PmzOrder order;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmz_menu);
        setFullTitleWithBack(getString(R.string.activity_pmz_menu_title));
        setViews();
        handleIntent();
    }

    private void handleIntent() {
        if(getIntent() != null && getIntent().getParcelableExtra(PMZ_STORE) != null) {
            store = getIntent().getParcelableExtra(PMZ_STORE);
            forcedId = getIntent().getBooleanExtra(FORCED_ID, false);
            setStoreData();
            if(forcedId) {
                getToken();
            } else {
                getData(true);
            }
        } else {
            DialogUtils.genericError(this);
            onBackPressed();
        }
    }

    private void setStoreData() {
        ImageView image = findViewById(R.id.image);
        ImageView icon = findViewById(R.id.icon);
        TextView title = findViewById(R.id.title);
        TextView description = findViewById(R.id.description);
        TextView distance = findViewById(R.id.distance);

        ImageUtils.loadStoreImage(this, image, store.getImageUrl());
        ImageUtils.loadStoreImage(this, icon, store.getImageUrl());

        title.setText(store.getName());
        description.setText(store.getCommerceName());

        if(PaymentezSDK.getInstance().getStyle().getTextColor() != null) {
            title.setTextColor(PaymentezSDK.getInstance().getStyle().getTextColor());
            description.setTextColor(PaymentezSDK.getInstance().getStyle().getTextColor());
        }
    }

    private void getToken() {
        showLoading();
        API.getSession(PmzData.getInstance().getSession(), new API.ServiceCallback<String>() {
            @Override
            public void onSuccess(String response) {
                PmzData.getInstance().setToken(response);
                getData(false);
            }

            @Override
            public void onError(PmzErrorMessage error) {
                hideLoading();
                DialogUtils.toast(PmzMenuActivity.this, error.getErrorMessage());
                finish();
                animActivityLeftToRight();
            }

            @Override
            public void onFailure() {
                hideLoading();
                DialogUtils.genericError(PmzMenuActivity.this);
                finish();
                animActivityLeftToRight();
            }

            @Override
            public void sessionExpired() {
                hideLoading();
                onSessionExpired();
            }
        });
    }

    private void getData(boolean showLoading) {
        if(showLoading) {
            showLoading();
        }
        API.getMenu(store.getId(), new API.ServiceCallback<PmzMenu>() {
            @Override
            public void onSuccess(PmzMenu response) {
                setDataIntoViews(response);
                startOrder();
            }

            @Override
            public void onError(PmzErrorMessage error) {
                hideLoading();
                DialogUtils.toast(PmzMenuActivity.this, error.getErrorMessage());
            }

            @Override
            public void onFailure() {
                hideLoading();
                DialogUtils.genericError(PmzMenuActivity.this);
            }

            @Override
            public void sessionExpired() {
                hideLoading();
                onSessionExpired();
            }
        });
    }

    private void startOrder() {
        API.startOrder(PmzOrder.hardcodedForOrderStart(), new API.ServiceCallback<PmzOrder>() {
            @Override
            public void onSuccess(PmzOrder response) {
                hideLoading();
                order = response;
            }

            @Override
            public void onError(PmzErrorMessage error) {
                hideLoading();
                DialogUtils.toast(PmzMenuActivity.this, error.getErrorMessage());
            }

            @Override
            public void onFailure() {
                hideLoading();
                DialogUtils.genericError(PmzMenuActivity.this);
            }

            @Override
            public void sessionExpired() {
                hideLoading();
                onSessionExpired();
            }
        });
    }

    private void setDataIntoViews(PmzMenu menu) {
        adapter.setMenu(menu);
    }

    private void setViews() {
        if(PaymentezSDK.getInstance().getStyle().getBackgroundColor() != null) {
            View background = findViewById(R.id.background);
            background.setBackgroundColor(PaymentezSDK.getInstance().getStyle().getBackgroundColor());
        }
        if(PaymentezSDK.getInstance().getStyle().getTextColor() != null) {
        }
        if(PaymentezSDK.getInstance().getStyle().getButtonBackgroundColor() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ColorHelper.replaceButtonBackground(findViewById(R.id.chart_button), PaymentezSDK.getInstance().getStyle().getButtonBackgroundColor());
            }
            changeToolbarBackground(PaymentezSDK.getInstance().getStyle().getButtonBackgroundColor());
        }
        if(PaymentezSDK.getInstance().getStyle().getButtonTextColor() != null) {
            TextView next = findViewById(R.id.chart_button);
            next.setTextColor(PaymentezSDK.getInstance().getStyle().getButtonTextColor());
            changeToolbarTextColor(PaymentezSDK.getInstance().getStyle().getButtonTextColor());
        }
        setButtons();
        setPager();
    }

    private void setPager() {
        ViewPager pager = findViewById(R.id.pager);
        adapter = new MenuPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

        setTabLayout(pager);
    }

    private void setTabLayout(ViewPager pager) {
        final Typeface robotoCondensedRegular = Typeface.create("font_roboto_condensed_regular", Typeface.NORMAL);
        final Typeface robotoCondensedBold = Typeface.create("font_roboto_condensed_bold", Typeface.NORMAL);
        tabLayout = findViewById(R.id.tab);
        tabLayout.setupWithViewPager(pager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LinearLayout container = (LinearLayout)((ViewGroup) tabLayout.getChildAt(0)).getChildAt(tab.getPosition());
                TextView tabTextView = (TextView) container.getChildAt(1);
                tabTextView.setTypeface(robotoCondensedBold, Typeface.BOLD);
                tabTextView.setAllCaps(false);
                if(PaymentezSDK.getInstance().getStyle().getTextColor() != null) {
                    tabTextView.setTextColor(PaymentezSDK.getInstance().getStyle().getTextColor());
                } else {
                    tabTextView.setTextColor(getResources().getColor(android.R.color.black));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                LinearLayout container = (LinearLayout)((ViewGroup) tabLayout.getChildAt(0)).getChildAt(tab.getPosition());
                TextView tabTextView = (TextView) container.getChildAt(1);
                tabTextView.setAllCaps(false);
                tabTextView.setTypeface(robotoCondensedRegular, Typeface.NORMAL);
                if(PaymentezSDK.getInstance().getStyle().getTextColor() != null) {
                    tabTextView.setTextColor(PaymentezSDK.getInstance().getStyle().getTextColor());
                } else {
                    tabTextView.setTextColor(getResources().getColor(android.R.color.black));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    private void setButtons() {
        findViewById(R.id.chart_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PmzMenuActivity.this, PmzSummaryActivity.class);
                if(order != null) {
                    intent.putExtra(PMZ_ORDER, order);
                }
                if(store != null) {
                    intent.putExtra(PMZ_STORE, store);
                }
                startActivityForResult(intent, MAIN_FLOW_KEY);
                animActivityRightToLeft();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_PRODUCT_REQUEST && resultCode == RESULT_OK && data != null
                && data.getParcelableExtra(PMZ_ORDER) != null) {
            this.order = data.getParcelableExtra(PMZ_ORDER);
        }
        if(requestCode == MAIN_FLOW_KEY && resultCode == RESULT_OK) {
            if(forcedId) {
                PmzData.getInstance().onSearchSuccess();
            } else {
                setResult(RESULT_OK);
            }
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        animActivityLeftToRight();
    }

    public void addProduct(PmzProduct product) {
        Intent intent = new Intent(this, PmzProductActivity.class);
        intent.putExtra(PmzProductActivity.PRODUCT_KEY, product);
        intent.putExtra(PMZ_ORDER_ID, order.getId());
        startActivityForResult(intent, ADD_PRODUCT_REQUEST);
        animActivityRightToLeft();
    }
}
