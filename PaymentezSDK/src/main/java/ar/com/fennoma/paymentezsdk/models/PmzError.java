package ar.com.fennoma.paymentezsdk.models;

import android.os.Parcel;
import android.os.Parcelable;

public class PmzError implements Parcelable {

    public static final String PLACE_ERROR = "Place error";
    public static final String PAYMENT_ERROR = "Payment error";
    public static final String NO_ORDER_SET_ERROR = "No order set error";

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PmzError(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
    }

    public PmzError() {
    }

    protected PmzError(Parcel in) {
        this.type = in.readString();
    }

    public static final Creator<PmzError> CREATOR = new Creator<PmzError>() {
        @Override
        public PmzError createFromParcel(Parcel source) {
            return new PmzError(source);
        }

        @Override
        public PmzError[] newArray(int size) {
            return new PmzError[size];
        }
    };
}
