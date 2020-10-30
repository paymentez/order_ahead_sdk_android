package ar.com.fennoma.paymentezsdk.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.adapters.PmzStoresAdapter;
import ar.com.fennoma.paymentezsdk.models.PmzErrorMessage;
import ar.com.fennoma.paymentezsdk.models.PmzStore;
import ar.com.fennoma.paymentezsdk.services.API;
import ar.com.fennoma.paymentezsdk.utils.DialogUtils;

public class PmzStoresActivity extends PmzBaseActivity {

    public static final String SEARCH_STORES_FILTER = "search stores filter";

    private String storesFilter;
    private PmzStoresAdapter adapter;

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
        getToken();
    }

    private void getToken() {
        showLoading();
        API.getSession(PmzData.getInstance().getSession(), new API.ServiceCallback<String>() {
            @Override
            public void onSuccess(String response) {
                PmzData.getInstance().setToken(response);
                getData();
            }

            @Override
            public void onError(PmzErrorMessage error) {
                hideLoading();
                DialogUtils.toast(PmzStoresActivity.this, error.getErrorMessage());
            }

            @Override
            public void onFailure() {
                hideLoading();
                DialogUtils.genericError(PmzStoresActivity.this);
            }

            @Override
            public void sessionExpired() {
                hideLoading();
                onSessionExpired();
            }
        });
    }

    private void getData() {
        API.getStores(new API.ServiceCallback<List<PmzStore>>() {
            @Override
            public void onSuccess(List<PmzStore> response) {
                hideLoading();
                adapter.setStores(response);
            }

            @Override
            public void onError(PmzErrorMessage error) {
                hideLoading();
                DialogUtils.toast(PmzStoresActivity.this, error.getErrorMessage());
            }

            @Override
            public void onFailure() {
                hideLoading();
                DialogUtils.genericError(PmzStoresActivity.this);
            }

            @Override
            public void sessionExpired() {
                hideLoading();
                onSessionExpired();
            }
        });
    }

    private void setViews() {
        if(PaymentezSDK.getInstance().getStyle().getBackgroundColor() != null) {
            View background = findViewById(R.id.background);
            background.setBackgroundColor(PaymentezSDK.getInstance().getStyle().getBackgroundColor());
        }
        if(PaymentezSDK.getInstance().getStyle().getButtonBackgroundColor() != null) {
            changeToolbarBackground(PaymentezSDK.getInstance().getStyle().getButtonBackgroundColor());
        }
        if(PaymentezSDK.getInstance().getStyle().getButtonTextColor() != null) {
            changeToolbarTextColor(PaymentezSDK.getInstance().getStyle().getButtonTextColor());
        }
        setRecyclerView();
    }

    private void setRecyclerView() {
        RecyclerView recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PmzStoresAdapter(this, new PmzStoresAdapter.PmzStoreItemListener() {
            @Override
            public void onStoreClicked(PmzStore store) {
                Intent intent = new Intent(PmzStoresActivity.this, PmzMenuActivity.class);
                intent.putExtra(PmzMenuActivity.PMZ_STORE, store);
                startActivity(intent);
                animActivityRightToLeft();
            }
        });
        recycler.setAdapter(adapter);
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
