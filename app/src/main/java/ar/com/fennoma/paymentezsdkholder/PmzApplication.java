package ar.com.fennoma.paymentezsdkholder;

import android.app.Application;

import ar.com.fennoma.paymentezsdk.controllers.PaymentezSDK;

public class PmzApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PaymentezSDK.initialize("api_key", "secret");
    }
}
