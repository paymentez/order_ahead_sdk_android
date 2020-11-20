package ar.com.fennoma.paymentezsdk.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class PmzAddress extends PmzModel implements Parcelable, IJsonParsingModel {

    private String city;
    private String country;
    private Double latitude;
    private Double longitude;
    private String addressLine1;
    private String addressLine2;
    private String state;
    private String zipCode;

    public static PmzAddress fromJSONObject(JSONObject json) {
        PmzAddress address = new PmzAddress();
        if(json != null) {
            try {
                if(json.has("address_city")) {
                    address.setCity(decode(json.getString("address_city")));
                }
                if(json.has("address_country")) {
                    address.setCountry(decode(json.getString("address_country")));
                }
                if(json.has("address_line1")) {
                    address.setAddressLine1(decode(json.getString("address_line1")));
                }
                if(json.has("address_line2")) {
                    address.setAddressLine2(decode(json.getString("address_line2")));
                }
                if(json.has("address_state")) {
                    address.setState(decode(json.getString("address_state")));
                }
                if(json.has("address_zip")) {
                    address.setZipCode(decode(json.getString("address_zip")));
                }
                if(json.has("address_latitude")) {
                    address.setLatitude(json.getDouble("address_latitude"));
                }
                if(json.has("address_longitude")) {
                    address.setLongitude(json.getDouble("address_longitude"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return address;
    }

    @Override
    public JSONObject getJSON() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("address_city", encode(city));
        params.put("address_country", encode(country));
        params.put("address_latitude", latitude);
        params.put("address_longitude", longitude);
        params.put("address_line1", encode(addressLine1));
        params.put("address_line2", encode(addressLine2));
        params.put("address_state", encode(state));
        params.put("address_zip", zipCode);
        return params;
    }

    public JSONObject addToJSON(JSONObject params) throws JSONException {
        params.put("address_city", encode(city));
        params.put("address_country", encode(country));
        params.put("address_latitude", latitude);
        params.put("address_longitude", longitude);
        params.put("address_line1", encode(addressLine1));
        params.put("address_line2", encode(addressLine2));
        params.put("address_state", encode(state));
        params.put("address_zip", zipCode);
        return params;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.city);
        dest.writeString(this.country);
        dest.writeValue(this.latitude);
        dest.writeValue(this.longitude);
        dest.writeString(this.addressLine1);
        dest.writeString(this.addressLine2);
        dest.writeString(this.state);
        dest.writeString(this.zipCode);
    }

    public PmzAddress() {
    }

    protected PmzAddress(Parcel in) {
        this.city = in.readString();
        this.country = in.readString();
        this.latitude = (Double) in.readValue(Double.class.getClassLoader());
        this.longitude = (Double) in.readValue(Double.class.getClassLoader());
        this.addressLine1 = in.readString();
        this.addressLine2 = in.readString();
        this.state = in.readString();
        this.zipCode = in.readString();
    }

    public static final Creator<PmzAddress> CREATOR = new Creator<PmzAddress>() {
        @Override
        public PmzAddress createFromParcel(Parcel source) {
            return new PmzAddress(source);
        }

        @Override
        public PmzAddress[] newArray(int size) {
            return new PmzAddress[size];
        }
    };
}
