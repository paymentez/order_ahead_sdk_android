# order_ahead_sdk_android

## Como incluir el SDK en el proyecto

[![](https://jitpack.io/v/paymentez/order_ahead_sdk_android.svg)](https://jitpack.io/#paymentez/order_ahead_sdk_android)

#Add it in your root build.gradle at the end of repositories:

<img width="698" alt="Captura de Pantalla 2022-03-09 a la(s) 1 30 56 p m" src="https://user-images.githubusercontent.com/67014146/157517438-dda10c14-e54e-4001-9e8c-a66963264fb2.png">

#Step 2. Add the dependency

dependencies {

	        implementation 'com.github.paymentez:order_ahead_sdk_android:0.4.1'
	
  }

###Integration

<img width="655" alt="Captura de Pantalla 2022-03-09 a la(s) 1 35 37 p m" src="https://user-images.githubusercontent.com/67014146/157518964-570b3b84-8419-42a6-a5fe-a269308f2992.png">

### Available actions


    public static void initialize(String appCode, String appKey) {
        getInstance();
        PmzData instance = PmzData.getInstance();
        instance.setSession(new PmzSession(appCode, appKey));
    }

    public static void initialize(String appCode, String appKey, boolean isProd) {
        getInstance();
        PmzData instance = PmzData.getInstance();
        instance.setProduction(isProd);
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

    public void reopenOrder(Context context, PmzOrder order, PmzBuyer buyer, String appOrderReference, PmzSearchListener listener) {
        if(isInitialized() && isBuyerWellInitialized(buyer) && isAppOrderReferenceUsable(appOrderReference) && isOrderUsable(order)) {
            checkContext(context);
            PmzData.getInstance().reopenOrder(context, order, buyer, appOrderReference, listener);
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

    private boolean isOrderUsable(PmzOrder order) {
        if(order != null && order.getId() != null && order.getStore() != null) {
            return true;
        }
        throw new RuntimeException("PaymentezSDK: PmzOrder malformed");
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

