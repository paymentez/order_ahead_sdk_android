package ar.com.fennoma.paymentezsdkholder;

import android.app.Application;

import com.paymentez.plazez.sdk.controllers.PaymentezSDK;

public class PmzApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PaymentezSDK.initialize("PMTZ-SDK-LM-CO-SERVER", "S72CVybhzWRTMFJHnyLLMJS0cXXRpQ4");
    }
}
