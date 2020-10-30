package ar.com.fennoma.paymentezsdkholder;

import android.app.Application;

import ar.com.fennoma.paymentezsdk.controllers.PaymentezSDK;

public class PmzApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PaymentezSDK.initialize("PMTZ-SDK-LM-CO-SERVER", "S72CVybhzWRTMFJHnyLLMJS0cXXRpQ4");
    }
}
