package ar.com.fennoma.paymentezsdk.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ar.com.fennoma.paymentezsdk.controllers.PaymentezSDK;

public class PmzPaymentData implements Parcelable {

    private String currency = "COP";
    private Double amount;
    private String paymentMethodReference;
    private String paymentReference;
    private Long service;

    public Double getAmount() {
        return amount;
    }

    public PmzPaymentData setAmount(Double amount) {
        this.amount = amount;
        return this;
    }

    public String getPaymentMethodReference() {
        return paymentMethodReference;
    }

    public PmzPaymentData setPaymentMethodReference(String paymentMethodReference) {
        this.paymentMethodReference = paymentMethodReference;
        return this;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public PmzPaymentData setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
        return this;
    }

    public Long getService() {
        return service;
    }

    public PmzPaymentData setService(Long service) {
        this.service = service;
        return this;
    }

    public PmzPaymentData() {
    }

    public String getCurrency() {
        return currency;
    }

    public PmzPaymentData setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public JSONObject getJSONForPayment(Long orderId) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("amount", amount);
        params.put("currency", currency);
        params.put("id_order", orderId);
        params.put("payment_method_reference", paymentMethodReference);
        params.put("payment_reference", paymentReference);
        params.put("service", service);
        params.put("session", PaymentezSDK.getInstance().getToken());
        return params;
    }

    public static List<PmzPaymentData> hardcodedList() {
        List<PmzPaymentData> payments = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            PmzPaymentData paymentData = new PmzPaymentData();
            paymentData.setAmount(17000D);
            paymentData.setPaymentMethodReference("PAYMENTEZ");
            paymentData.setPaymentReference("VN-100");
            paymentData.setService(0L);
            payments.add(paymentData);
        }
        return payments;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.currency);
        dest.writeValue(this.amount);
        dest.writeString(this.paymentMethodReference);
        dest.writeString(this.paymentReference);
        dest.writeValue(this.service);
    }

    protected PmzPaymentData(Parcel in) {
        this.currency = in.readString();
        this.amount = (Double) in.readValue(Double.class.getClassLoader());
        this.paymentMethodReference = in.readString();
        this.paymentReference = in.readString();
        this.service = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Creator<PmzPaymentData> CREATOR = new Creator<PmzPaymentData>() {
        @Override
        public PmzPaymentData createFromParcel(Parcel source) {
            return new PmzPaymentData(source);
        }

        @Override
        public PmzPaymentData[] newArray(int size) {
            return new PmzPaymentData[size];
        }
    };
}
