package ar.com.fennoma.paymentezsdk.models;

import android.os.Parcel;
import android.os.Parcelable;

public class PmzPaymentData implements Parcelable {

    private Long amount;
    private String paymentMethodReference;
    private String paymentReference;
    private Long service;

    public Long getAmount() {
        return amount;
    }

    public PmzPaymentData setAmount(Long amount) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.amount);
        dest.writeString(this.paymentMethodReference);
        dest.writeString(this.paymentReference);
        dest.writeValue(this.service);
    }

    public PmzPaymentData() {
    }

    protected PmzPaymentData(Parcel in) {
        this.amount = (Long) in.readValue(Long.class.getClassLoader());
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
