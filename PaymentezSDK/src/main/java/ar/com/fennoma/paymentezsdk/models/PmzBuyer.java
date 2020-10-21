package ar.com.fennoma.paymentezsdk.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class PmzBuyer implements Parcelable {

    private String name;
    private String phone;
    private String userReference;
    private String email;
    private String fiscalNumber;

    public static PmzBuyer fromJSONObject(JSONObject json) {
        PmzBuyer buyer = new PmzBuyer();
        if(json != null) {
            try {
                if(json.has("buyer_email")) {
                    buyer.setEmail(json.getString("buyer_email"));
                }
                if(json.has("buyer_fiscal_number")) {
                    buyer.setFiscalNumber(json.getString("buyer_fiscal_number"));
                }
                if(json.has("buyer_name")) {
                    buyer.setName(json.getString("buyer_name"));
                }
                if(json.has("buyer_phone")) {
                    buyer.setPhone(json.getString("buyer_phone"));
                }
                if(json.has("buyer_user_reference")) {
                    buyer.setUserReference(json.getString("buyer_user_reference"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return buyer;
    }

    public JSONObject getJSON() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("buyer_email", email);
        params.put("buyer_fiscal_number", fiscalNumber);
        params.put("buyer_name", fiscalNumber);
        params.put("buyer_phone", phone);
        params.put("buyer_user_reference", userReference);
        return params;
    }

    public JSONObject addToJSON(JSONObject params) throws JSONException {
        params.put("buyer_email", email);
        params.put("buyer_fiscal_number", fiscalNumber);
        params.put("buyer_name", fiscalNumber);
        params.put("buyer_phone", phone);
        params.put("buyer_user_reference", userReference);
        return params;
    }

    public String getName() {
        return name;
    }

    public PmzBuyer setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public PmzBuyer setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getUserReference() {
        return userReference;
    }

    public PmzBuyer setUserReference(String userReference) {
        this.userReference = userReference;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public PmzBuyer setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFiscalNumber() {
        return fiscalNumber;
    }

    public PmzBuyer setFiscalNumber(String fiscalNumber) {
        this.fiscalNumber = fiscalNumber;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.userReference);
        dest.writeString(this.email);
        dest.writeString(this.fiscalNumber);
    }

    public PmzBuyer() {
    }

    protected PmzBuyer(Parcel in) {
        this.name = in.readString();
        this.phone = in.readString();
        this.userReference = in.readString();
        this.email = in.readString();
        this.fiscalNumber = in.readString();
    }

    public static final Parcelable.Creator<PmzBuyer> CREATOR = new Parcelable.Creator<PmzBuyer>() {
        @Override
        public PmzBuyer createFromParcel(Parcel source) {
            return new PmzBuyer(source);
        }

        @Override
        public PmzBuyer[] newArray(int size) {
            return new PmzBuyer[size];
        }
    };
}