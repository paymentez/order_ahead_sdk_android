package ar.com.fennoma.paymentezsdk.models;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class PmzSession implements IJsonParsingModel {

    private String appCode;
    private String appKey;

    public PmzSession(){}

    public PmzSession(String appCode, String appKey) {
        this.appCode = appCode;
        this.appKey = appKey;
    }

    public static String getToken(JSONObject json) {
        String token = null;
        if(json != null) {
             try {
                 if(json.has("data") && !TextUtils.isEmpty(json.getString("data"))) {
                     token = json.getString("data");
                 }
             } catch (JSONException e) {
                 e.printStackTrace();
             }
        }
        return token;
    }

    @Override
    public JSONObject getJSON() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("app_code", appCode);
        params.put("app_key", appKey);
        return params;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public boolean isInitialized() {
        return !TextUtils.isEmpty(getAppCode()) && !TextUtils.isEmpty(getAppKey());
    }
}
