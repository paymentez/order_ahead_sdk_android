package ar.com.fennoma.paymentezsdk.controllers;

import android.content.Context;
import android.text.TextUtils;

import java.util.List;

import ar.com.fennoma.paymentezsdk.models.PmzBuyer;
import ar.com.fennoma.paymentezsdk.models.PmzError;
import ar.com.fennoma.paymentezsdk.models.PmzOrder;
import ar.com.fennoma.paymentezsdk.models.PmzPaymentData;
import ar.com.fennoma.paymentezsdk.models.PmzSession;
import ar.com.fennoma.paymentezsdk.models.PmzStore;
import ar.com.fennoma.paymentezsdk.styles.PmzStyle;

public class PaymentezSDK {

    private static PaymentezSDK instance;

    public static void initialize(String appCode, String appKey) {
        getInstance();
        PmzData instance = PmzData.getInstance();
        instance.setSession(new PmzSession(appCode, appKey));
    }

    public String getToken() {
        return PmzData.getInstance().getToken();
    }

    public PmzStyle getStyle() {
        return PmzData.getInstance().getStyle();
    }

    public interface PmzSearchListener {
        void onFinishedSuccessfully(PmzOrder order);
        void onError(PmzError error);
        void onCancel();
    }

    public interface PmzPayAndPlaceListener {
        void onFinishedSuccessfully(PmzOrder order);
        void onError(PmzOrder order, PmzError error);
    }

    public interface MultiPaymentOrderListener {
        void onFinishedSuccessfully(PmzOrder order);
        void onError(PmzOrder order, PmzError error);
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
        if(!PmzData.getInstance().isInitialized()) {
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

    public void startPayAndPlace(Context context, PmzOrder order, List<PmzPaymentData> payments, MultiPaymentOrderListener listener) {
        if(isInitialized() && arePaymentsUsable(payments)) {
            checkContext(context);
            PmzData.getInstance().startPayAndPlace(context, order, payments, false, listener);
        }
    }

    public void startPayAndPlace(Context context, PmzOrder order, List<PmzPaymentData> payments, boolean skipSummary, MultiPaymentOrderListener listener) {
        if(isInitialized() && arePaymentsUsable(payments)) {
            checkContext(context);
            PmzData.getInstance().startPayAndPlace(context, order, payments, skipSummary, listener);
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

    private boolean arePaymentsUsable(List<PmzPaymentData> payments) {
        boolean result = true;
        if(payments != null && payments.size() > 0) {
            for (PmzPaymentData payment : payments) {
                if (payment == null || TextUtils.isEmpty(payment.getPaymentMethodReference())
                        || TextUtils.isEmpty(payment.getPaymentReference())
                        || payment.getAmount() == null
                        || payment.getService() == null) {
                    result = false;
                }
            }
        } else {
            result = false;
        }
        if(!result) {
            throw new RuntimeException("PaymentezSDK: PmzPaymentData malformed");
        } else {
            return true;
        }
    }

    private boolean checkContext(Context context) {
        if(context == null) {
            throw new RuntimeException("PaymentezSDK has no context provided");
        } else {
            return true;
        }
    }

    public PaymentezSDK setStyle(PmzStyle style) {
        PmzData.getInstance().setStyle(style);
        return this;
    }

    public void setOrderResult(PmzOrder order) {
        PmzData.getInstance().setOrderResult(order);
    }
}
