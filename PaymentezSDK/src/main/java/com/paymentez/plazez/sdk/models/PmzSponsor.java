package com.paymentez.plazez.sdk.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class PmzSponsor implements Parcelable {

    private String name;
    private String logoUrl;
    private String iconUrl;
    private String loaderUrl;
    private String headerEmailUrl;
    private String colorHex;
    private String splashUrl;
    private String transactionSuccessUrl;
    private String transactionErrorUrl;

    public static PmzSponsor fromJSONObject(JSONObject json) {
        PmzSponsor sponsor = new PmzSponsor();
        if(json != null) {
            try {
                if(json.has("name")) {
                    sponsor.setName(json.getString("name"));
                }
                if(json.has("logo")) {
                    sponsor.setLogoUrl(json.getString("logo"));
                }
                if(json.has("icon")) {
                    sponsor.setIconUrl(json.getString("icon"));
                }
                if(json.has("loader")) {
                    sponsor.setLoaderUrl(json.getString("loader"));
                }
                if(json.has("header_email")) {
                    sponsor.setHeaderEmailUrl(json.getString("header_email"));
                }
                if(json.has("color")) {
                    sponsor.setColorHex(json.getString("color"));
                }
                if(json.has("splash")) {
                    sponsor.setSplashUrl(json.getString("splash"));
                }
                if(json.has("transaction_success")) {
                    sponsor.setTransactionSuccessUrl(json.getString("transaction_success"));
                }
                if(json.has("transaction_error")) {
                    sponsor.setTransactionErrorUrl(json.getString("transaction_error"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return sponsor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getLoaderUrl() {
        return loaderUrl;
    }

    public void setLoaderUrl(String loaderUrl) {
        this.loaderUrl = loaderUrl;
    }

    public String getHeaderEmailUrl() {
        return headerEmailUrl;
    }

    public void setHeaderEmailUrl(String headerEmailUrl) {
        this.headerEmailUrl = headerEmailUrl;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    public String getSplashUrl() {
        return splashUrl;
    }

    public void setSplashUrl(String splashUrl) {
        this.splashUrl = splashUrl;
    }

    public String getTransactionSuccessUrl() {
        return transactionSuccessUrl;
    }

    public void setTransactionSuccessUrl(String transactionSuccessUrl) {
        this.transactionSuccessUrl = transactionSuccessUrl;
    }

    public String getTransactionErrorUrl() {
        return transactionErrorUrl;
    }

    public void setTransactionErrorUrl(String transactionErrorUrl) {
        this.transactionErrorUrl = transactionErrorUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.logoUrl);
        dest.writeString(this.iconUrl);
        dest.writeString(this.loaderUrl);
        dest.writeString(this.headerEmailUrl);
        dest.writeString(this.colorHex);
        dest.writeString(this.splashUrl);
        dest.writeString(this.transactionSuccessUrl);
        dest.writeString(this.transactionErrorUrl);
    }

    public PmzSponsor() {
    }

    protected PmzSponsor(Parcel in) {
        this.name = in.readString();
        this.logoUrl = in.readString();
        this.iconUrl = in.readString();
        this.loaderUrl = in.readString();
        this.headerEmailUrl = in.readString();
        this.colorHex = in.readString();
        this.splashUrl = in.readString();
        this.transactionSuccessUrl = in.readString();
        this.transactionErrorUrl = in.readString();
    }

    public static final Creator<PmzSponsor> CREATOR = new Creator<PmzSponsor>() {
        @Override
        public PmzSponsor createFromParcel(Parcel source) {
            return new PmzSponsor(source);
        }

        @Override
        public PmzSponsor[] newArray(int size) {
            return new PmzSponsor[size];
        }
    };
}
