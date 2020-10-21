package ar.com.fennoma.paymentezsdk.controllers;

import android.content.Context;
import android.text.TextUtils;

import java.util.List;

import ar.com.fennoma.paymentezsdk.models.PmzBuyer;
import ar.com.fennoma.paymentezsdk.models.PmzError;
import ar.com.fennoma.paymentezsdk.models.PmzOrder;
import ar.com.fennoma.paymentezsdk.models.PmzPaymentData;
import ar.com.fennoma.paymentezsdk.models.PmzStore;

public class PaymentezSDK {

    private static PaymentezSDK instance;

    public static void initialize(String apiKey, String secret) {
        PaymentezSDK instance = getInstance();
        instance.setApiKey(apiKey);
        instance.setSecret(secret);
    }

    public interface PmzSearchListener {
        void onFinishedSuccessfully(PmzOrder order);
        void onCancel();
    }

    public interface PmzPayAndPlaceListener {
        void onFinishedSuccessfully(PmzOrder order);
        void onError(PmzOrder order, PmzError error);
    }

    public interface PmzPayAndPlaceMultipleOrderListener {
        void onFinishedSuccessfully(List<PmzOrder> orders);
        void onError(List<PmzOrder> orders, PmzError error);
    }

    public interface PmzStoresListener {
        void onFinishedSuccessfully(List<PmzStore> stores);
        void onError(PmzError error);
    }

    public static PaymentezSDK getInstance() {
        if(instance == null) {
            instance = new PaymentezSDK();
        }
        return instance;
    }

    public void startSearch(Context context, PmzBuyer buyer, String appOrderReference, Long storeId, PmzSearchListener listener) {
        if(isInitialized() && isBuyerWellInitialized(buyer) && isAppOrderReferenceUsable(appOrderReference)) {
            checkContext(context);
            PmzData.getInstance().startSearchWithStoreId(context, buyer, appOrderReference, storeId, listener);
        }
    }

    public void startSearch(Context context, PmzBuyer buyer, String appOrderReference, String searchStoresFilter, PmzSearchListener listener) {
        if(isInitialized() && isBuyerWellInitialized(buyer) && isAppOrderReferenceUsable(appOrderReference)) {
            checkContext(context);
            PmzData.getInstance().startSearch(context, buyer, appOrderReference, searchStoresFilter, listener);
        }
    }

    public void startSearch(Context context, PmzBuyer buyer, String appOrderReference, PmzSearchListener listener) {
        if(isInitialized() && isBuyerWellInitialized(buyer) && isAppOrderReferenceUsable(appOrderReference)) {
            checkContext(context);
            PmzData.getInstance().startSearch(context, buyer, appOrderReference, null, listener);
        }
    }

    public void showSummary(Context context, String appOrderReference, PmzOrder order, PmzSearchListener listener) {
        if(isInitialized() && isAppOrderReferenceUsable(appOrderReference)) {
            checkContext(context);
            PmzData.getInstance().showSummary(context, appOrderReference, order, listener);
        }
    }

    private boolean isAppOrderReferenceUsable(String appOrderReference) {
        if(!TextUtils.isEmpty(appOrderReference)) {
            return true;
        }
        throw new RuntimeException("PaymentezSDK: appOrderReference is empty");
    }

    private boolean isBuyerWellInitialized(PmzBuyer buyer) {
        if(buyer != null && !TextUtils.isEmpty(buyer.getEmail())
                && !TextUtils.isEmpty(buyer.getFiscalNumber())
                && !TextUtils.isEmpty(buyer.getName())
                && !TextUtils.isEmpty(buyer.getPhone())
                && !TextUtils.isEmpty(buyer.getUserReference())) {
            return true;
        }
        throw new RuntimeException("PaymentezSDK: PmzBuyer malformed");
    }

    private boolean isInitialized() {
        if (TextUtils.isEmpty(PmzData.getInstance().getApiKey()) || TextUtils.isEmpty(PmzData.getInstance().getSecret())) {
            throw new RuntimeException("PaymentezSDK not initialized");
        } else {
            return true;
        }
    }

    public void startPayAndPlace(Context context, PmzOrder order, PmzPaymentData paymentData, PmzPayAndPlaceListener listener) {
        if(isInitialized() && isPaymentDataUsable(paymentData)) {
            checkContext(context);
            PmzData.getInstance().startPayAndPlace(context, order, paymentData, false, listener);
        }
    }

    public void startPayAndPlace(Context context, PmzOrder order, PmzPaymentData paymentData, boolean skipSummary, PmzPayAndPlaceListener listener) {
        if(isInitialized() && isPaymentDataUsable(paymentData)) {
            checkContext(context);
            PmzData.getInstance().startPayAndPlace(context, order, paymentData, skipSummary, listener);
        }
    }

    public void startPayAndPlace(Context context, List<PmzOrder> orders, PmzPaymentData paymentData, PmzPayAndPlaceListener listener) {
        if(isInitialized() && isPaymentDataUsable(paymentData)) {
            checkContext(context);
            PmzData.getInstance().startPayAndPlace(context, orders, paymentData, false, listener);
        }
    }

    public void startPayAndPlace(Context context, List<PmzOrder> orders, PmzPaymentData paymentData, boolean skipSummary, PmzPayAndPlaceListener listener) {
        if(isInitialized() && isPaymentDataUsable(paymentData)) {
            checkContext(context);
            PmzData.getInstance().startPayAndPlace(context, orders, paymentData, skipSummary, listener);
        }
    }

    public void getStores(PmzStoresListener listener) {
        if(isInitialized()) {
            PmzData.getInstance().getStores(null, listener);
        }
    }

    public void getStores(String searchStoresFilter, PmzStoresListener listener) {
        if(isInitialized()) {
            PmzData.getInstance().getStores(searchStoresFilter, listener);
        }
    }

    private boolean isPaymentDataUsable(PmzPaymentData paymentData) {
        if(paymentData != null && !TextUtils.isEmpty(paymentData.getPaymentMethodReference())
                && !TextUtils.isEmpty(paymentData.getPaymentReference())
                && paymentData.getAmount() != null
                && paymentData.getService() != null) {
            return true;
        }
        throw new RuntimeException("PaymentezSDK: PmzPaymentData malformed");
    }

    private boolean checkContext(Context context) {
        if(context == null) {
            throw new RuntimeException("PaymentezSDK has no context provided");
        } else {
            return true;
        }
    }

    public PaymentezSDK setButtonBackgroundColor(Integer buttonBackgroundColor) {
        PmzData.getInstance().setButtonBackgroundColor(buttonBackgroundColor);
        return this;
    }

    public PaymentezSDK setButtonTextColor(Integer buttonTextColor) {
        PmzData.getInstance().setButtonTextColor(buttonTextColor);
        return this;
    }

    public PaymentezSDK setTextColor(Integer textColor) {
        PmzData.getInstance().setTextColor(textColor);
        return this;
    }

    public PaymentezSDK setBackgroundColor(int color) {
        PmzData.getInstance().setBackgroundColor(color);
        return this;
    }

    public void setOrderResult(PmzOrder order) {
        PmzData.getInstance().setOrderResult(order);
    }

    public void setOrderResult(List<PmzOrder> orders) {
        PmzData.getInstance().setOrderResult(orders);
    }

    public void setSecret(String secret) {
        PmzData.getInstance().setSecret(secret);
    }

    public void setApiKey(String apiKey) {
        PmzData.getInstance().setApiKey(apiKey);
    }
}
