package ar.com.fennoma.paymentezsdkholder;

import android.app.Application;

import com.paymentez.plazez.sdk.controllers.PaymentezSDK;

public class PmzApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PaymentezSDK.initialize("LIFEMILES", "53aae0fb1b2441058f0", true);
        //PaymentezSDK.initialize("PMTZ-SDK-LM-CO-SERVER", "S72CVybhzWRTMFJHnyLLMJS0cXXRpQ4", false);
    }
}
