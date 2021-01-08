package com.paymentez.plazez.sdk.models;

import org.json.JSONException;
import org.json.JSONObject;

public interface IJsonParsingModel {
    JSONObject getJSON() throws JSONException;
}
