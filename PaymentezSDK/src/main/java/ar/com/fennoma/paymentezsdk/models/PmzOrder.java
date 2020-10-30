package ar.com.fennoma.paymentezsdk.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ar.com.fennoma.paymentezsdk.controllers.PaymentezSDK;

public class PmzOrder implements Parcelable {

    private Long id;
    private Integer status;
    private Double tax;
    private String orderAppReference;
    private String confirmationCode;
    private String tableReference;
    private String deliveryDate;
    private String reserveCode;
    private String datePlaced;
    private String dateStarted;
    private Integer orderType;
    private Long totalAmount;
    private String deliveryInstructions;
    private Double deliveryPrice;
    private String statusDescription;
    private String annotations;
    private String appOrderReference;
    private Long storeId;
    private Integer typeOrder;
    private List<PmzItem> items;
    private PmzBuyer buyer;
    private PmzAddress address;

    private String currency;
    private String paymentMethodReference;
    private String paymentReference;
    private Integer service;

    public static PmzOrder hardcoded() {
        try {
            return fromJSONObject(new JSONObject(json));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new PmzOrder();
    }

    public static PmzOrder hardcodedForOrderStart() {
        try {
            return fromJSONObject(new JSONObject(orderStarterJson));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new PmzOrder();
    }

    public static PmzOrder fromJSONObject(JSONObject json) {
        PmzOrder order = new PmzOrder();
        if(json != null) {
            try {
                if(json.has("id")) {
                    order.setId(json.getLong("id"));
                }
                if(json.has("status")) {
                    order.setStatus(json.getInt("status"));
                }
                if(json.has("tax")) {
                    order.setTax(json.getDouble("tax"));
                }
                if(json.has("items") && !json.isNull("items")) {
                    order.setItems(PmzItem.fromJSONArray(json.getJSONArray("items")));
                }
                if(json.has("order_app_reference")) {
                    order.setOrderAppReference(json.getString("order_app_reference"));
                }
                if(json.has("confirmation_code") && !json.isNull("confirmation_code")) {
                    order.setConfirmationCode(json.getString("confirmation_code"));
                }
                if(json.has("table_reference") && !json.isNull("table_reference")) {
                    order.setTableReference(json.getString("table_reference"));
                }
                if(json.has("delivery_date") && !json.isNull("delivery_date")) {
                    order.setDeliveryDate(json.getString("delivery_date"));
                }
                if(json.has("reserve_code") && !json.isNull("reserve_code")) {
                    order.setReserveCode(json.getString("reserve_code"));
                }
                if(json.has("date_placed") && !json.isNull("date_placed")) {
                    order.setDatePlaced(json.getString("date_placed"));
                }
                if(json.has("date_started") && !json.isNull("date_started")) {
                    order.setDateStarted(json.getString("date_started"));
                }
                if(json.has("order_type")) {
                    order.setOrderType(json.getInt("order_type"));
                }
                if(json.has("total_amount")) {
                    order.setTotalAmount(json.getLong("total_amount"));
                }
                if(json.has("delivery_instructions")) {
                    order.setDeliveryInstructions(json.getString("delivery_instructions"));
                }
                if(json.has("delivery_price")) {
                    order.setDeliveryPrice(json.getDouble("delivery_price"));
                }
                if(json.has("status_description") && !json.isNull("status_description")) {
                    order.setStatusDescription(json.getString("status_description"));
                }
                if(json.has("delivery_instructions")) {
                    order.setDeliveryInstructions(json.getString("delivery_instructions"));
                }
                if(json.has("Annotations")) {
                    order.setAnnotations(json.getString("Annotations"));
                }
                if(json.has("app_order_reference")) {
                    order.setAppOrderReference(json.getString("app_order_reference"));
                }
                if(json.has("id_store")) {
                    order.setStoreId(json.getLong("id_store"));
                }
                if(json.has("type_order")) {
                    order.setTypeOrder(json.getInt("type_order"));
                }
                order.setBuyer(PmzBuyer.fromJSONObject(json));
                order.setAddress(PmzAddress.fromJSONObject(json));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return order;
    }

    public JSONObject getJSONForPayment() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("amount", totalAmount);
        params.put("currency", currency);
        params.put("id_order", id);
        params.put("payment_method_reference", paymentMethodReference);
        params.put("payment_reference", paymentReference);
        params.put("service", service);
        params.put("session", PaymentezSDK.getInstance().getToken());
        return params;
    }

    public JSONObject getJSONForPlacement() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("id_order", id);
        params.put("session", PaymentezSDK.getInstance().getToken());
        return params;
    }

    public JSONObject getJSONForOrderStart() throws JSONException {
        JSONObject params = new JSONObject();
        if(buyer != null) {
            buyer.addToJSON(params);
        }
        if(address != null) {
            address.addToJSON(params);
        }
        params.put("delivery_instructions", Uri.encode(deliveryInstructions, "UTF-8"));
        params.put("Annotations", Uri.encode(annotations, "UTF-8"));
        params.put("app_order_reference", Uri.encode(appOrderReference, "UTF-8"));
        params.put("id_store", storeId);
        params.put("type_order", typeOrder);
        params.put("session", PaymentezSDK.getInstance().getToken());
        return params;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public String getOrderAppReference() {
        return orderAppReference;
    }

    public void setOrderAppReference(String orderAppReference) {
        this.orderAppReference = orderAppReference;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public String getTableReference() {
        return tableReference;
    }

    public void setTableReference(String tableReference) {
        this.tableReference = tableReference;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getReserveCode() {
        return reserveCode;
    }

    public void setReserveCode(String reserveCode) {
        this.reserveCode = reserveCode;
    }

    public String getDatePlaced() {
        return datePlaced;
    }

    public void setDatePlaced(String datePlaced) {
        this.datePlaced = datePlaced;
    }

    public String getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(String dateStarted) {
        this.dateStarted = dateStarted;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDeliveryInstructions() {
        return deliveryInstructions;
    }

    public void setDeliveryInstructions(String deliveryInstructions) {
        this.deliveryInstructions = deliveryInstructions;
    }

    public Double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(Double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public List<PmzItem> getItems() {
        return items;
    }

    public void setItems(List<PmzItem> items) {
        this.items = items;
    }

    public PmzOrder() {
    }

    private static String json = "{\n" +
            "    \"id\": 8493,\n" +
            "    \"status\": 0,\n" +
            "    \"tax\": 0,\n" +
            "    \"items\": [\n" +
            "      {\n" +
            "        \"id\": 15053,\n" +
            "        \"tax\": 0,\n" +
            "        \"annotations\": \"sin servilletas\",\n" +
            "        \"status\": 0,\n" +
            "        \"configurations\": [\n" +
            "          {\n" +
            "            \"id\": 13015,\n" +
            "            \"annotations\": null,\n" +
            "            \"description\": \"Ajonjolí\",\n" +
            "            \"type\": 2,\n" +
            "            \"cost\": 1000,\n" +
            "            \"configuration_id\": 11520,\n" +
            "            \"discount\": null\n" +
            "          },\n" +
            "          {\n" +
            "            \"id\": 13016,\n" +
            "            \"annotations\": null,\n" +
            "            \"description\": \"Cubiertos\",\n" +
            "            \"type\": 2,\n" +
            "            \"cost\": 0,\n" +
            "            \"configuration_id\": 11494,\n" +
            "            \"discount\": null\n" +
            "          }\n" +
            "        ],\n" +
            "        \"total_amount\": 17000,\n" +
            "        \"unit_amount\": 16000,\n" +
            "        \"quantity\": 1,\n" +
            "        \"product_id\": 7351,\n" +
            "        \"product_name\": \"Ceviche\",\n" +
            "        \"discount\": null\n" +
            "      }\n" +
            "    ],\n" +
            "    \"buyer_email\": \"breyes@paymentez.com\",\n" +
            "    \"buyer_name\": \"Bruno Reyes\",\n" +
            "    \"buyer_phone\": \"3212000915\",\n" +
            "    \"buyer_fiscal_number\": \"1054092666\",\n" +
            "    \"user_reference\": \"f6dc275d-5e64-4127-bf5c-dbbfac02aacd\",\n" +
            "    \"order_app_reference\": \"test-1744\",\n" +
            "    \"confirmation_code\": null,\n" +
            "    \"table_reference\": null,\n" +
            "    \"delivery_date\": null,\n" +
            "    \"reserve_code\": null,\n" +
            "    \"date_placed\": null,\n" +
            "    \"date_started\": \"2020-09-17T10:48:24.000Z\",\n" +
            "    \"order_type\": 0,\n" +
            "    \"total_amount\": 17000,\n" +
            "    \"address_line1\": \"Calle 75 20C-81\",\n" +
            "    \"address_line2\": \"Calle 75 - 20C-81\",\n" +
            "    \"address_city\": \"Bogotá\",\n" +
            "    \"address_state\": \"DC\",\n" +
            "    \"address_zip\": \"00000\",\n" +
            "    \"address_country\": \"Colombia\",\n" +
            "    \"address_latitude\": 4.6568103,\n" +
            "    \"address_longitude\": -74.0561968,\n" +
            "    \"delivery_instructions\": \"Apto 206\",\n" +
            "    \"delivery_price\": 0,\n" +
            "    \"status_description\": null\n" +
            "  }";

    private static String orderStarterJson = "{\n" +
            "    \"address_city\": \"Bogotá\",\n" +
            "    \"address_country\": \"Colombia\",\n" +
            "    \"address_latitude\": 4.6568103,\n" +
            "    \"address_line1\": \"Calle 75 20C-81\",\n" +
            "    \"address_line2\": \"Calle 75 - 20C-81\",\n" +
            "    \"address_longitude\": -74.0561968,\n" +
            "    \"address_state\": \"DC\",\n" +
            "    \"address_zip\": \"\",\n" +
            "    \"delivery_instructions\": \"Apto 206\",\n" +
            "    \"Annotations\": \"\",\n" +
            "    \"buyer_email\": \"breyes@paymentez.com\",\n" +
            "    \"buyer_fiscal_number\": \"1054092666\",\n" +
            "    \"buyer_name\": \"Bruno Reyes\",\n" +
            "    \"buyer_phone\": \"3212000915\",\n" +
            "    \"buyer_user_reference\": \"f6dc275d-5e64-4127-bf5c-dbbfac02aacd\",\n" +
            "    \"app_order_reference\": \"test-1744\",\n" +
            "    \"id_store\":120,\n" +
            "    \"session\": \"{{token}}\",\n" +
            "    \"type_order\": 0\n" +
            "}";

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPaymentMethodReference() {
        return paymentMethodReference;
    }

    public void setPaymentMethodReference(String paymentMethodReference) {
        this.paymentMethodReference = paymentMethodReference;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public Integer getService() {
        return service;
    }

    public void setService(Integer service) {
        this.service = service;
    }

    public PmzBuyer getBuyer() {
        return buyer;
    }

    public void setBuyer(PmzBuyer buyer) {
        this.buyer = buyer;
    }

    public PmzAddress getAddress() {
        return address;
    }

    public void setAddress(PmzAddress address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.status);
        dest.writeValue(this.tax);
        dest.writeString(this.orderAppReference);
        dest.writeString(this.confirmationCode);
        dest.writeString(this.tableReference);
        dest.writeString(this.deliveryDate);
        dest.writeString(this.reserveCode);
        dest.writeString(this.datePlaced);
        dest.writeString(this.dateStarted);
        dest.writeValue(this.orderType);
        dest.writeValue(this.totalAmount);
        dest.writeString(this.deliveryInstructions);
        dest.writeValue(this.deliveryPrice);
        dest.writeString(this.statusDescription);
        dest.writeTypedList(this.items);
        dest.writeParcelable(this.buyer, flags);
        dest.writeParcelable(this.address, flags);
        dest.writeString(this.currency);
        dest.writeString(this.paymentMethodReference);
        dest.writeString(this.paymentReference);
        dest.writeValue(this.service);
    }

    protected PmzOrder(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.tax = (Double) in.readValue(Double.class.getClassLoader());
        this.orderAppReference = in.readString();
        this.confirmationCode = in.readString();
        this.tableReference = in.readString();
        this.deliveryDate = in.readString();
        this.reserveCode = in.readString();
        this.datePlaced = in.readString();
        this.dateStarted = in.readString();
        this.orderType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalAmount = (Long) in.readValue(Long.class.getClassLoader());
        this.deliveryInstructions = in.readString();
        this.deliveryPrice = (Double) in.readValue(Double.class.getClassLoader());
        this.statusDescription = in.readString();
        this.items = in.createTypedArrayList(PmzItem.CREATOR);
        this.buyer = in.readParcelable(PmzBuyer.class.getClassLoader());
        this.address = in.readParcelable(PmzAddress.class.getClassLoader());
        this.currency = in.readString();
        this.paymentMethodReference = in.readString();
        this.paymentReference = in.readString();
        this.service = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<PmzOrder> CREATOR = new Creator<PmzOrder>() {
        @Override
        public PmzOrder createFromParcel(Parcel source) {
            return new PmzOrder(source);
        }

        @Override
        public PmzOrder[] newArray(int size) {
            return new PmzOrder[size];
        }
    };

    public String getAnnotations() {
        return annotations;
    }

    public void setAnnotations(String annotations) {
        this.annotations = annotations;
    }

    public String getAppOrderReference() {
        return appOrderReference;
    }

    public void setAppOrderReference(String appOrderReference) {
        this.appOrderReference = appOrderReference;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Integer getTypeOrder() {
        return typeOrder;
    }

    public void setTypeOrder(Integer typeOrder) {
        this.typeOrder = typeOrder;
    }
}
