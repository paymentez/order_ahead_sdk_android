package com.paymentez.plazez.sdk.controllers;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import com.paymentez.plazez.sdk.models.PmzBuyer;
import com.paymentez.plazez.sdk.models.PmzError;
import com.paymentez.plazez.sdk.models.PmzErrorMessage;
import com.paymentez.plazez.sdk.models.PmzOrder;
import com.paymentez.plazez.sdk.models.PmzPaymentData;
import com.paymentez.plazez.sdk.models.PmzSession;
import com.paymentez.plazez.sdk.models.PmzStore;
import com.paymentez.plazez.sdk.services.API;
import com.paymentez.plazez.sdk.styles.PmzStyle;

class PmzData {

    private static final String STG_BASE_URL = "https://middleware-stg.paymentez.com/";
    private static final String PROD_BASE_URL = "https://middleware.paymentez.com/";

    private static PmzData instance;
    private PmzSession session;

    private PaymentezSDK.PmzSearchListener searchListener;
    private PaymentezSDK.PmzPayAndPlaceListener paymentChecker;
    private PaymentezSDK.MultiPaymentOrderListener multiplePaymentOrderChecker;

    private PmzStyle style;

    private PmzOrder order;
    private String token;
    private PmzBuyer buyer;
    private String appOrderReference;
    private boolean production = false;

    public static PmzData getInstance() {
        if(instance == null) {
            instance = new PmzData();
            instance.setStyle(new PmzStyle());
        }
        return instance;
    }

    private PmzData() {}

    public void startSearchWithStoreId(Context context, PmzBuyer buyer, String appOrderReference, Long storeId, PaymentezSDK.PmzSearchListener listener) {
        this.searchListener = listener;
        this.buyer = buyer;
        this.appOrderReference = appOrderReference;
        Intent intent;
        if(storeId != null) {
            intent = new Intent(context, PmzMenuActivity.class);
            intent.putExtra(PmzMenuActivity.PMZ_STORE, storeId);
            intent.putExtra(PmzMenuActivity.FORCED_ID, true);
        } else {
            intent = new Intent(context, PmzStoresActivity.class);
        }
        context.startActivity(intent);
    }

    public void startSearch(Context context, PmzBuyer buyer, String appOrderReference, String searchStoresFilter, PaymentezSDK.PmzSearchListener listener) {
        this.searchListener = listener;
        this.buyer = buyer;
        this.appOrderReference = appOrderReference;
        Intent intent = new Intent(context, PmzStoresActivity.class);
        if(!TextUtils.isEmpty(searchStoresFilter)) {
            intent.putExtra(PmzStoresActivity.SEARCH_STORES_FILTER, searchStoresFilter);
        }
        context.startActivity(intent);
    }

    public void reopenOrder(Context context, PmzOrder order, PmzBuyer buyer, String appOrderReference, PaymentezSDK.PmzSearchListener listener) {
        this.searchListener = listener;
        this.buyer = buyer;
        this.order = order;
        this.appOrderReference = appOrderReference;
        Intent intent = new Intent(context, PmzCartActivity.class);
        intent.putExtra(PmzCartActivity.FROM_REOPEN, true);
        intent.putExtra(PmzCartActivity.PMZ_ORDER, order);
        intent.putExtra(PmzCartActivity.PMZ_STORE, order.getStore());
        context.startActivity(intent);
    }

    public void showSummary(Context context, String appOrderReference, PmzOrder order, PaymentezSDK.PmzSearchListener listener) {
        this.searchListener = listener;
        this.appOrderReference = appOrderReference;
        Intent intent = new Intent(context, PmzSummaryActivity.class);
        intent.putExtra(PmzSummaryActivity.JUST_SUMMARY, true);
        intent.putExtra(PmzCartActivity.PMZ_ORDER, order);
        context.startActivity(intent);
    }

    public void startPayAndPlace(Context context, PmzOrder order, PmzPaymentData paymentData, boolean skipSummary, PaymentezSDK.PmzPayAndPlaceListener listener) {
        this.paymentChecker = listener;
        Intent intent = new Intent(context, PmzPayAndPlaceActivity.class);
        intent.putExtra(PmzPayAndPlaceActivity.PMZ_ORDER, order);
        intent.putExtra(PmzPayAndPlaceActivity.SKIP_SUMMARY, skipSummary);
        intent.putExtra(PmzPayAndPlaceActivity.PMZ_PAYMENT_DATA, paymentData);
        context.startActivity(intent);
    }

    public void startPayAndPlace(Context context, PmzOrder order, List<PmzPaymentData> payments, boolean skipSummary, PaymentezSDK.MultiPaymentOrderListener listener) {
        this.multiplePaymentOrderChecker = listener;
        Intent intent = new Intent(context, PmzPayAndPlaceActivity.class);
        intent.putExtra(PmzPayAndPlaceActivity.PMZ_ORDER, order);
        intent.putExtra(PmzPayAndPlaceActivity.SKIP_SUMMARY, skipSummary);
        intent.putExtra(PmzPayAndPlaceActivity.PMZ_PAYMENTS_DATA, new ArrayList<>(payments));
        context.startActivity(intent);
    }

    public void getStores(String filter, final PaymentezSDK.PmzStoresListener listener) {
        API.getStores(new API.ServiceCallback<List<PmzStore>>() {
            @Override
            public void onSuccess(List<PmzStore> response) {
                listener.onFinishedSuccessfully(response);
            }

            @Override
            public void onError(PmzErrorMessage error) {
                listener.onError(new PmzError(PmzError.GENERIC_SERVICE_ERROR));
            }

            @Override
            public void onFailure() {
                listener.onError(new PmzError(PmzError.GENERIC_SERVICE_ERROR));
            }

            @Override
            public void sessionExpired() {
                listener.onError(new PmzError(PmzError.GENERIC_SERVICE_ERROR));
            }
        });
    }

    public void onSearchCancel() {
        if(searchListener != null) {
            searchListener.onCancel();
        }
    }

    public void onSearchSuccess() {
        if(searchListener != null) {
            searchListener.onFinishedSuccessfully(order);
        }
    }

    public void onPaymentCheckingError(PmzOrder order, PmzError error) {
        if(paymentChecker != null) {
            paymentChecker.onError(order, error);
        }
    }

    public void onMultiplePaymentOrderCheckingError(PmzOrder order, PmzError error) {
        if(multiplePaymentOrderChecker != null) {
            multiplePaymentOrderChecker.onError(order, error);
        }
    }

    public void onPaymentCheckingSuccess(PmzOrder order) {
        if(paymentChecker != null) {
            paymentChecker.onFinishedSuccessfully(order);
        }
    }

    public void onMultiplePaymentCheckingSuccess(PmzOrder order) {
        if(multiplePaymentOrderChecker != null) {
            multiplePaymentOrderChecker.onFinishedSuccessfully(order);
        }
    }

    public String getToken() {
        return token;
    }

    public PmzSession getSession() {
        return session;
    }

    public void setSession(PmzSession session) {
        this.session = session;
    }

    public boolean isInitialized() {
        return session != null && session.isInitialized();
    }

    public void setToken(String token) {
        this.token = token;
    }

    public PmzStyle getStyle() {
        return style;
    }

    public void setStyle(PmzStyle style) {
        this.style = style;
    }

    public PmzBuyer getBuyer() {
        return buyer;
    }

    public void setBuyer(PmzBuyer buyer) {
        this.buyer = buyer;
    }

    public String getAppOrderReference() {
        return appOrderReference;
    }

    public void setAppOrderReference(String appOrderReference) {
        this.appOrderReference = appOrderReference;
    }

    public void setOrderResult(PmzOrder order) {
        this.order = order;
    }

    public void onSearchSessionExpired() {
        if(searchListener != null) {
            searchListener.onError(new PmzError(PmzError.SESSION_EXPIRED));
        }
    }

    public void onMultiplePaymentSessionExpired(PmzOrder order) {
        if(multiplePaymentOrderChecker != null) {
            multiplePaymentOrderChecker.onError(order, new PmzError(PmzError.SESSION_EXPIRED));
        }
    }

    public void onPaymentSessionExpired(PmzOrder order) {
        if(paymentChecker != null) {
            paymentChecker.onError(order, new PmzError(PmzError.SESSION_EXPIRED));
        }
    }

    public String getBaseUrl() {
        if(production) {
            return PROD_BASE_URL;
        }
        return STG_BASE_URL;
    }

    public void setProduction(boolean isProd) {
        this.production = isProd;
    }

    public boolean isProduction() {
        return production;
    }
}
