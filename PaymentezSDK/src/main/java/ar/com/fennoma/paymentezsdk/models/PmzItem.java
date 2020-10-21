package ar.com.fennoma.paymentezsdk.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PmzItem implements Parcelable {

    private Long id;
    private Double tax;
    private String annotation;
    private Integer status;
    private Long totalAmount;
    private Long unitAmount;
    private Integer quantity;
    private Long productId;
    private String productName;
    private Double discount;
    private List<PmzConfiguration> configurations;

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

    private static PmzItem fromJSONObject(JSONObject json) {
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
                    item.setAnnotation(json.getString("annotations"));
                }
                if(json.has("total_amount")) {
                    item.setTotalAmount(json.getLong("total_amount"));
                }
                if(json.has("unit_amount")) {
                    item.setUnitAmount(json.getLong("unit_amount"));
                }
                if(json.has("product_id")) {
                    item.setProductId(json.getLong("product_id"));
                }
                if(json.has("quantity")) {
                    item.setQuantity(json.getInt("quantity"));
                }
                if(json.has("product_name")) {
                    item.setProductName(json.getString("product_name"));
                }
                if(json.has("discount") && !json.isNull("discount")) {
                    item.setDiscount(json.getDouble("discount"));
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

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.tax);
        dest.writeString(this.annotation);
        dest.writeValue(this.status);
        dest.writeValue(this.totalAmount);
        dest.writeValue(this.unitAmount);
        dest.writeValue(this.quantity);
        dest.writeValue(this.productId);
        dest.writeString(this.productName);
        dest.writeValue(this.discount);
        dest.writeTypedList(this.configurations);
    }

    public PmzItem() {
    }

    protected PmzItem(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.tax = (Double) in.readValue(Double.class.getClassLoader());
        this.annotation = in.readString();
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalAmount = (Long) in.readValue(Long.class.getClassLoader());
        this.unitAmount = (Long) in.readValue(Long.class.getClassLoader());
        this.quantity = (Integer) in.readValue(Integer.class.getClassLoader());
        this.productId = (Long) in.readValue(Long.class.getClassLoader());
        this.productName = in.readString();
        this.discount = (Double) in.readValue(Double.class.getClassLoader());
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
