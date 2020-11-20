package ar.com.fennoma.paymentezsdk.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ar.com.fennoma.paymentezsdk.controllers.PaymentezSDK;

public class PmzItem extends PmzModel implements Parcelable {

    private Long id;
    private Long orderId;
    private Double tax;
    private String annotation;
    private Integer status;
    private Double totalAmount;
    private Long unitAmount;
    private Integer quantity;
    private Long productId;
    private String productName;
    private Double discount;
    private String imageUrl;
    private List<PmzConfiguration> configurations;

    public PmzItem(PmzProduct product, Long orderId) {
        if(product != null) {
            this.orderId = orderId;
            this.productId = product.getId();
            this.annotation = product.getDescription();
            this.productName = product.getName();
            this.quantity = 1;
        }
    }

    public PmzItem(){}

    public static List<PmzItem> fromJSONArray(JSONArray json) {
        List<PmzItem> items = new ArrayList<>();
        if(json != null) {
            for(int i = 0; i < json.length(); i++) {
                try {
                    items.add(fromJSONObject(json.getJSONObject(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return items;
    }

    public static PmzItem fromJSONObject(JSONObject json) {
        PmzItem item = new PmzItem();
        if(json != null) {
            try {
                if(json.has("id")) {
                    item.setId(json.getLong("id"));
                }
                if(json.has("tax")) {
                    item.setTax(json.getDouble("tax"));
                }
                if(json.has("status")) {
                    item.setStatus(json.getInt("status"));
                }
                if(json.has("annotations")) {
                    item.setAnnotation(decode(json.getString("annotations")));
                }
                if(json.has("total_amount")) {
                    item.setTotalAmount(json.getDouble("total_amount"));
                }
                if(json.has("unit_amount")) {
                    item.setUnitAmount(json.getLong("unit_amount"));
                }
                if(json.has("product_id")) {
                    item.setProductId(json.getLong("product_id"));
                } else if (json.has("id_product")) {
                    item.setProductId(json.getLong("id_product"));
                }
                if(json.has("id_order")) {
                    item.setOrderId(json.getLong("id_order"));
                }
                if(json.has("quantity")) {
                    item.setQuantity(json.getInt("quantity"));
                }
                if(json.has("product_name")) {
                    item.setProductName(decode(json.getString("product_name")));
                }
                if(json.has("discount") && !json.isNull("discount")) {
                    item.setDiscount(json.getDouble("discount"));
                }
                if(json.has("image")) {
                    item.setImageUrl(json.getString("image"));
                }
                if(json.has("configurations") && !json.isNull("configurations")) {
                    item.setConfigurations(PmzConfiguration.fromJSONArray(json.getJSONArray("configurations")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return item;
    }

    public static PmzItem hardcoded() {
        try {
            return PmzItem.fromJSONObject(new JSONObject(json));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(Long unitAmount) {
        this.unitAmount = unitAmount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public List<PmzConfiguration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<PmzConfiguration> configurations) {
        this.configurations = configurations;
    }

    public JSONObject getJSONWithConfigurations() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("id_order", orderId);
        params.put("id_product", productId);
        params.put("quantity", quantity);
        params.put("annotations", encode(annotation));
        if(configurations != null) {
            params.put("configurations", PmzConfiguration.getJSONFor(configurations));
        }
        params.put("session", PaymentezSDK.getInstance().getToken());
        return params;
    }

    public JSONObject getJSONForDelete() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("id_order", orderId);
        params.put("id_order_item", id);
        params.put("session", PaymentezSDK.getInstance().getToken());
        return params;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setWith(PmzProduct product) {

    }

    private static String json = "{\n" +
            "  \"annotations\": \"sin servilletas\",\n" +
            "  \"configurations\": [\n" +
            "    {\n" +
            "      \"id_configuration\": 11520\n" +
            "    },\n" +
            "    {\n" +
            "      \"id_configuration\": 11494\n" +
            "    }\n" +
            "  ],\n" +
            "  \"id_order\": 8493,\n" +
            "  \"id_product\": 7351,\n" +
            "  \"quantity\": 1,\n" +
            "  \"session\": \"{{token}}\"\n" +
            "}\n";

    public Double getPrice() {
        return totalAmount;
    }

    public void setConfigurations(PmzProductOrganizer organizer) {
        this.configurations = organizer.getConfigurations();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.orderId);
        dest.writeValue(this.tax);
        dest.writeString(this.annotation);
        dest.writeValue(this.status);
        dest.writeValue(this.totalAmount);
        dest.writeValue(this.unitAmount);
        dest.writeValue(this.quantity);
        dest.writeValue(this.productId);
        dest.writeString(this.productName);
        dest.writeValue(this.discount);
        dest.writeString(this.imageUrl);
        dest.writeTypedList(this.configurations);
    }

    protected PmzItem(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.orderId = (Long) in.readValue(Long.class.getClassLoader());
        this.tax = (Double) in.readValue(Double.class.getClassLoader());
        this.annotation = in.readString();
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalAmount = (Double) in.readValue(Double.class.getClassLoader());
        this.unitAmount = (Long) in.readValue(Long.class.getClassLoader());
        this.quantity = (Integer) in.readValue(Integer.class.getClassLoader());
        this.productId = (Long) in.readValue(Long.class.getClassLoader());
        this.productName = in.readString();
        this.discount = (Double) in.readValue(Double.class.getClassLoader());
        this.imageUrl = in.readString();
        this.configurations = in.createTypedArrayList(PmzConfiguration.CREATOR);
    }

    public static final Creator<PmzItem> CREATOR = new Creator<PmzItem>() {
        @Override
        public PmzItem createFromParcel(Parcel source) {
            return new PmzItem(source);
        }

        @Override
        public PmzItem[] newArray(int size) {
            return new PmzItem[size];
        }
    };
}
