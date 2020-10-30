package ar.com.fennoma.paymentezsdk.services;

import android.os.Build;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import ar.com.fennoma.paymentezsdk.controllers.PaymentezSDK;
import ar.com.fennoma.paymentezsdk.exceptions.PmzException;
import ar.com.fennoma.paymentezsdk.models.PmzCapacity;
import ar.com.fennoma.paymentezsdk.models.PmzMenu;
import ar.com.fennoma.paymentezsdk.models.PmzItem;
import ar.com.fennoma.paymentezsdk.models.PmzOrder;
import ar.com.fennoma.paymentezsdk.models.PmzSession;
import ar.com.fennoma.paymentezsdk.models.PmzStore;

public class Services {

    //Internal handling
    private static final int SUCCESS_CODE = 200;
    private static final String MESSAGE_OK = "OK";
    private static final String STATUS_OK = "00";

    private static final String BASE_URL = "https://middleware-stg.paymentez.com/";

    //GETs
    private static final String GET_STORES = "store/list";
    private static final String GET_CAPACITIES = "store/capacity";
    private static final String GET_MENU = "store/get-menu";
    private static final String GET_ORDER = "order/get-order";

    //POSTs
    private static final String TOKEN = "start-session";
    private static final String PAY_ORDER = "payment/pay-order";
    private static final String PLACE_ORDER = "payment/pay-order";
    private static final String ADD_ITEM_WITH_CONFIGURATIONS = "order/add-item-w-configuration";
    private static final String START_ORDER = "order/start-order";


    public static String getToken(PmzSession session) throws PmzException {
        HttpURLConnection urlConnection = null;
        InputStream in = null;
        String response = null;
        DataOutputStream os = null;
        BufferedWriter writer = null;
        try {
            urlConnection = getHttpURLConnection(TOKEN);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            os = new DataOutputStream(urlConnection.getOutputStream());
            writer = getBufferedWriter(os);

            os.writeBytes(session.getJSON().toString());
            writer.flush();
            if (isValidStatusLineCode(urlConnection.getResponseCode())) {
                in = new BufferedInputStream(urlConnection.getInputStream());
                JSONObject json = getJsonFromResponse(in);
                if (isResultValid(json)) {
                    response = PmzSession.getToken(json);
                }
            } else {
                throw new PmzException();
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            throw new PmzException();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            closeWriterAndStream(os, writer);
            closeInputStream(in);
        }
        return response;
    }

    public static PmzOrder startOrder(PmzOrder starter) throws PmzException {
        HttpURLConnection urlConnection = null;
        InputStream in = null;
        PmzOrder response = null;
        DataOutputStream os = null;
        BufferedWriter writer = null;
        try {
            urlConnection = getHttpURLConnection(START_ORDER);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            os = new DataOutputStream(urlConnection.getOutputStream());
            writer = getBufferedWriter(os);

            os.writeBytes(starter.getJSONForOrderStart().toString());
            writer.flush();
            if (isValidStatusLineCode(urlConnection.getResponseCode())) {
                in = new BufferedInputStream(urlConnection.getInputStream());
                JSONObject json = getJsonFromResponse(in);
                if (isResultValid(json)) {
                    response = PmzOrder.fromJSONObject(genericJSONObjectParser(json));
                }
            } else {
                throw new PmzException();
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            throw new PmzException();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            closeWriterAndStream(os, writer);
            closeInputStream(in);
        }
        return response;
    }

    public static PmzOrder payOrder(PmzOrder order) throws PmzException {
        HttpURLConnection urlConnection = null;
        InputStream in = null;
        PmzOrder response = null;
        DataOutputStream os = null;
        BufferedWriter writer = null;
        try {
            urlConnection = getHttpURLConnection(PAY_ORDER);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            os = new DataOutputStream(urlConnection.getOutputStream());
            writer = getBufferedWriter(os);

            os.writeBytes(order.getJSONForPayment().toString());
            writer.flush();
            if (isValidStatusLineCode(urlConnection.getResponseCode())) {
                in = new BufferedInputStream(urlConnection.getInputStream());
                JSONObject json = getJsonFromResponse(in);
                if (isResultValid(json)) {
                    response = PmzOrder.fromJSONObject(genericJSONObjectParser(json));
                }
            } else {
                throw new PmzException();
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            throw new PmzException();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            closeWriterAndStream(os, writer);
            closeInputStream(in);
        }
        return response;
    }

    public static PmzItem placeOrder(PmzOrder order) throws PmzException {
        HttpURLConnection urlConnection = null;
        InputStream in = null;
        PmzItem response = null;
        DataOutputStream os = null;
        BufferedWriter writer = null;
        try {
            urlConnection = getHttpURLConnection(PLACE_ORDER);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            os = new DataOutputStream(urlConnection.getOutputStream());
            writer = getBufferedWriter(os);

            os.writeBytes(order.getJSONForPlacement().toString());
            writer.flush();
            if (isValidStatusLineCode(urlConnection.getResponseCode())) {
                in = new BufferedInputStream(urlConnection.getInputStream());
                JSONObject json = getJsonFromResponse(in);
                if (isResultValid(json)) {
                    response = PmzItem.fromJSONObject(genericJSONObjectParser(json));
                }
            } else {
                throw new PmzException();
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            throw new PmzException();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            closeWriterAndStream(os, writer);
            closeInputStream(in);
        }
        return response;
    }

    public static PmzOrder addItemWithConfig(PmzItem item) throws PmzException {
        HttpURLConnection urlConnection = null;
        InputStream in = null;
        PmzOrder response = null;
        DataOutputStream os = null;
        BufferedWriter writer = null;
        try {
            urlConnection = getHttpURLConnection(ADD_ITEM_WITH_CONFIGURATIONS);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            os = new DataOutputStream(urlConnection.getOutputStream());
            writer = getBufferedWriter(os);

            os.writeBytes(item.getJSONWithConfigurations().toString());
            writer.flush();
            if (isValidStatusLineCode(urlConnection.getResponseCode())) {
                in = new BufferedInputStream(urlConnection.getInputStream());
                JSONObject json = getJsonFromResponse(in);
                if (isResultValid(json)) {
                    response = PmzOrder.fromJSONObject(genericJSONObjectParser(json));
                }
            } else {
                throw new PmzException();
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            throw new PmzException();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            closeWriterAndStream(os, writer);
            closeInputStream(in);
        }
        return response;
    }

    public static List<PmzStore> getStores() throws PmzException {
        HttpURLConnection urlConnection = null;
        List<PmzStore> response = null;
        InputStream in = null;
        try {
            String url = GET_STORES;
            url += "?session=" + PaymentezSDK.getInstance().getToken();
            urlConnection = getHttpURLConnection(url);
            urlConnection.connect();
            if (isValidStatusLineCode(urlConnection.getResponseCode())) {
                in = new BufferedInputStream(urlConnection.getInputStream());
                JSONObject json = getJsonFromResponse(in);
                if(isResultValid(json)) {
                    response = PmzStore.fromJSONArray(genericJSONArrayParser(json));
                }
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            throw new PmzException();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            closeInputStream(in);
        }
        return response;
    }

    public static List<PmzCapacity> getCapacities() throws PmzException {
        HttpURLConnection urlConnection = null;
        List<PmzCapacity> response = null;
        InputStream in = null;
        try {
            String url = GET_CAPACITIES;
            url += "?session=" + PaymentezSDK.getInstance().getToken();
            urlConnection = getHttpURLConnection(url);
            urlConnection.connect();
            if (isValidStatusLineCode(urlConnection.getResponseCode())) {
                in = new BufferedInputStream(urlConnection.getInputStream());
                JSONObject json = getJsonFromResponse(in);
                if(isResultValid(json)) {
                    response = PmzCapacity.fromJSONArray(genericJSONArrayParser(json));
                }
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            throw new PmzException();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            closeInputStream(in);
        }
        return response;
    }

    public static PmzMenu getMenu(@NonNull Long storeId) throws PmzException {
        HttpURLConnection urlConnection = null;
        PmzMenu response = null;
        InputStream in = null;
        try {
            String url = GET_MENU;
            url += "?session=" + PaymentezSDK.getInstance().getToken()
                    + "&id_store=" + storeId;
            urlConnection = getHttpURLConnection(url);
            urlConnection.connect();
            if (isValidStatusLineCode(urlConnection.getResponseCode())) {
                in = new BufferedInputStream(urlConnection.getInputStream());
                JSONObject json = getJsonFromResponse(in);
                if(isResultValid(json)) {
                    response = PmzMenu.fromJSONObject(genericJSONObjectParser(json));
                }
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            throw new PmzException();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            closeInputStream(in);
        }
        return response;
    }

    public static PmzOrder getOrder(@NonNull Long orderId) throws PmzException {
        HttpURLConnection urlConnection = null;
        PmzOrder response = null;
        InputStream in = null;
        try {
            String url = GET_ORDER;
            url += "?session=" + PaymentezSDK.getInstance().getToken()
                    + "&id_order=" + orderId;
            urlConnection = getHttpURLConnection(url);
            urlConnection.connect();
            if (isValidStatusLineCode(urlConnection.getResponseCode())) {
                in = new BufferedInputStream(urlConnection.getInputStream());
                JSONObject json = getJsonFromResponse(in);
                if(isResultValid(json)) {
                    response = PmzOrder.fromJSONObject(genericJSONObjectParser(json));
                }
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            throw new PmzException();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            closeInputStream(in);
        }
        return response;
    }

    private static JSONObject genericJSONObjectParser(JSONObject json) throws JSONException {
        if(json.has("data")) {
            return json.getJSONObject("data");
        } else {
            return null;
        }
    }

    private static boolean isResultValid(JSONObject json) throws PmzException, JSONException {
        if(json != null) {
            if(json.has("error")) {
                if(json.has("message")) {
                    throw new PmzException(json.getString("error"), json.getString("message"));
                } else {
                    throw new PmzException(json.getString("error"));
                }
            } else if(json.has("status") && json.has("status_msg")) {
                if(json.getString("status").equals(STATUS_OK) && json.getString("status_msg").equals(MESSAGE_OK)) {
                    return true;
                } else {
                    throw new PmzException();
                }
            } else {
                throw new PmzException();
            }
        } else {
            throw new PmzException();
        }
    }

    private static JSONArray genericJSONArrayParser(JSONObject json) throws JSONException {
        if(json.has("data")) {
            return json.getJSONArray("data");
        } else {
            return null;
        }
    }

    @NonNull
    private static HttpURLConnection getHttpURLConnection(String method) throws IOException {
        URL url = new URL(BASE_URL + method);
        return (HttpURLConnection) url.openConnection();
    }

    public static boolean isValidStatusLineCode(int statusCode) {
        return statusCode == SUCCESS_CODE;
    }

    public static void closeWriterAndStream(OutputStream os, BufferedWriter writer) {
        closeWriter(writer);
        closeOutputStream(os);
    }

    private static void closeOutputStream(OutputStream os) {
        if(os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void closeWriter(BufferedWriter writer) {
        if(writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeInputStream(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @NonNull
    public static BufferedWriter getBufferedWriter(OutputStream os) throws UnsupportedEncodingException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
        } else {
            return new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        }
    }

    public static OutputStream getOutputStream(HttpURLConnection urlConnection) throws IOException {
        return urlConnection.getOutputStream();
    }

    protected static JSONObject getError(HttpURLConnection urlConnection) throws IOException, JSONException {
        return getJsonFromResponse(new BufferedInputStream(urlConnection.getErrorStream()));
    }

    public static JSONObject getJsonFromResponse(InputStream in) throws IOException, JSONException {
        StringBuilder strBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String line;
        while ((line = reader.readLine()) != null) {
            strBuilder.append(line);
        }
        String value = strBuilder.toString();
        return new JSONObject(value);
    }

    public static JSONArray getJsonArrayFromResponse(InputStream in) throws IOException, JSONException {
        StringBuilder strBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String line;
        while ((line = reader.readLine()) != null) {
            strBuilder.append(line);
        }
        String value = strBuilder.toString();
        return new JSONArray(value);
    }
}
