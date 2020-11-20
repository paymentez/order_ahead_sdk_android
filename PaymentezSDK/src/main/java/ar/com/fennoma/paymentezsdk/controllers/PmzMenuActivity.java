package ar.com.fennoma.paymentezsdk.controllers;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

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
    private long storeId;

    private SearchView searchView;
    private ViewPager pager;

    private View chartButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmz_menu);
        setFont();
        setFullTitleWithBack(getString(R.string.activity_pmz_menu_title));
        setViews();
        handleIntent();
    }

    private void handleIntent() {
        if(getIntent() != null) {
            if(getIntent().getBooleanExtra(FORCED_ID, false)) {
                storeId = getIntent().getLongExtra(PMZ_STORE, 0L);
                forcedId = getIntent().getBooleanExtra(FORCED_ID, false);
                getToken();
            } else if(getIntent().getParcelableExtra(PMZ_STORE) != null) {
                store = getIntent().getParcelableExtra(PMZ_STORE);
                if(store != null && store.getId() != null) {
                    storeId = store.getId();
                }
                setStoreData();
                getData(true);
            } else {
                DialogUtils.genericError(this);
                onBackPressed();
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
                getStore();
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
                PmzData.getInstance().onSearchSessionExpired();
            }
        });
    }

    private void getStore() {
        API.getStores(new API.ServiceCallback<List<PmzStore>>() {
            @Override
            public void onSuccess(List<PmzStore> response) {
                findStore(response);
                setStoreData();
                getData(false);
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
                PmzData.getInstance().onSearchSessionExpired();
            }
        });
    }

    private void findStore(List<PmzStore> stores) {
        if(stores != null) {
            for(PmzStore store: stores) {
                if(store != null && store.getId() != null && store.getId().equals(storeId)) {
                    PmzMenuActivity.this.store = store;
                }
            }
        }
    }

    private void getData(boolean showLoading) {
        if(showLoading) {
            showLoading();
        }
        API.getMenu(storeId, new API.ServiceCallback<PmzMenu>() {
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
                PmzData.getInstance().onSearchSessionExpired();
            }
        });
    }

    private void startOrder() {
        PmzOrder pmzOrder = PmzOrder.buildForOrderStart(PmzData.getInstance().getBuyer(), PmzData.getInstance().getAppOrderReference(),
                storeId);
        API.startOrder(pmzOrder, new API.ServiceCallback<PmzOrder>() {
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
                PmzData.getInstance().onSearchSessionExpired();
            }
        });
    }

    private void setDataIntoViews(PmzMenu menu) {
        adapter.setMenu(menu);
    }

    private void setViews() {
        if(PaymentezSDK.getInstance().getStyle().getBackgroundColor() != null) {
            View background = findViewById(R.id.main_content);
            background.setBackgroundColor(PaymentezSDK.getInstance().getStyle().getBackgroundColor());
            findViewById(R.id.collapsing_toolbar).setBackgroundColor(PaymentezSDK.getInstance().getStyle().getBackgroundColor());
        }
        if(PaymentezSDK.getInstance().getStyle().getTextColor() != null) {
            TabLayout tab = findViewById(R.id.tab);
            tab.setTabTextColors(PaymentezSDK.getInstance().getStyle().getTextColor(), PaymentezSDK.getInstance().getStyle().getTextColor());
        }
        Integer buttonBackgroundColor = PaymentezSDK.getInstance().getStyle().getButtonBackgroundColor();
        if(buttonBackgroundColor != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ColorHelper.replaceButtonBackground(findViewById(R.id.chart_button), buttonBackgroundColor);
            }
            changeToolbarBackground(buttonBackgroundColor);
            CollapsingToolbarLayout collapsing = findViewById(R.id.collapsing_toolbar);
            collapsing.setContentScrimColor(buttonBackgroundColor);
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
        pager = findViewById(R.id.pager);
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
        chartButton = findViewById(R.id.chart_button);
        disableCartButton();
    }

    private void goToCart() {
        if(order != null && order.getItems() != null && order.getItems().size() > 0) {
            Intent intent = new Intent(PmzMenuActivity.this, PmzCartActivity.class);
            if(order != null) {
                intent.putExtra(PMZ_ORDER, order);
            }
            if(store != null) {
                intent.putExtra(PMZ_STORE, store);
            }
            startActivityForResult(intent, MAIN_FLOW_KEY);
            animActivityRightToLeft();
        } else {
            DialogUtils.toast(this, getString(R.string.no_items_on_cart));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED && data != null && data.getBooleanExtra(SESSION_EXPIRED_KEY, false)) {
            onSessionExpired();
        } else if(requestCode == ADD_PRODUCT_REQUEST && resultCode == RESULT_OK && data != null
                && data.getParcelableExtra(PMZ_ORDER) != null) {
            this.order = data.getParcelableExtra(PMZ_ORDER);
            enableCartButton();
        }
        if(requestCode == MAIN_FLOW_KEY && resultCode == RESULT_OK) {
            if(data != null && data.getBooleanExtra(PmzCartActivity.ORDER_MODIFIED, false) &&
                    data.getParcelableExtra(PMZ_ORDER) != null) {
                order = data.getParcelableExtra(PMZ_ORDER);
                if(order != null && (order.getItems() == null || order.getItems().size() == 0)) {
                    disableCartButton();
                }
            } else {
                if (forcedId) {
                    PmzData.getInstance().onSearchSuccess();
                } else {
                    setResult(RESULT_OK);
                }
                finish();
            }
        }
    }

    private void disableCartButton() {
        chartButton.setOnClickListener(null);
        chartButton.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.disabled_button_rounded_background, getTheme()));
    }

    private void enableCartButton() {
        chartButton.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.orange_rounded_button_selector, getTheme()));
        Integer buttonBackgroundColor = PaymentezSDK.getInstance().getStyle().getButtonBackgroundColor();
        if (buttonBackgroundColor != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ColorHelper.replaceButtonBackground(findViewById(R.id.chart_button), buttonBackgroundColor);
        }
        chartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCart();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(forcedId) {
            PmzData.getInstance().onSearchCancel();
        }
        animActivityLeftToRight();
    }

    public void addProduct(PmzProduct product) {
        Intent intent = new Intent(this, PmzProductActivity.class);
        intent.putExtra(PmzProductActivity.PRODUCT_KEY, product);
        intent.putExtra(PMZ_ORDER_ID, order.getId());
        if(order != null) {
            intent.putExtra(PMZ_ORDER, order);
        }
        startActivityForResult(intent, ADD_PRODUCT_REQUEST);
        animActivityRightToLeft();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu, menu);

        final MenuItem myActionMenuItem = menu.findItem( R.id.search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                myActionMenuItem.collapseActionView();
                adapter.setFilter(query);
                //pager.setCurrentItem(0);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                adapter.setFilter(s);
                //pager.setCurrentItem(0);
                return false;
            }
        });
        EditText editText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        editText.setTextColor(Color.WHITE);
        editText.setHintTextColor(Color.WHITE);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.cart) {
            goToCart();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
