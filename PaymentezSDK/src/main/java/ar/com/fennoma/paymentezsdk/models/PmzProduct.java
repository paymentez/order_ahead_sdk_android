package ar.com.fennoma.paymentezsdk.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PmzProduct implements Parcelable {

    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Integer status;
    private Long listPrice;
    private Long currentPrice;
    private String appDisplayName;
    private Long displayOrder;
    private String coverImageUrl;
    private Boolean storeDisabled;
    private List<PmzProductConfiguration> configurations;

    public static List<PmzProduct> fromJSONArray(JSONArray json) {
        List<PmzProduct> products = new ArrayList<>();
        if(json != null) {
            for(int i = 0; i < json.length(); i++) {
                try {
                    products.add(fromJSONObject(json.getJSONObject(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return products;
    }

    private static PmzProduct fromJSONObject(JSONObject json) {
        PmzProduct product = new PmzProduct();
        if(json != null) {
            try {
                if(json.has("id")) {
                    product.setId(json.getLong("id"));
                }
                if(json.has("name")) {
                    product.setName(json.getString("name"));
                }
                if(json.has("description")) {
                    product.setDescription(json.getString("description"));
                }
                if(json.has("image")) {
                    product.setImageUrl(json.getString("image"));
                }
                if(json.has("app_display_name")) {
                    product.setAppDisplayName(json.getString("app_display_name"));
                }
                if(json.has("status")) {
                    product.setStatus(json.getInt("status"));
                }
                if(json.has("list_price")) {
                    product.setListPrice(json.getLong("list_price"));
                }
                if(json.has("current_price")) {
                    product.setCurrentPrice(json.getLong("current_price"));
                }
                if(json.has("display_order")) {
                    product.setDisplayOrder(json.getLong("display_order"));
                }
                if(json.has("store_disabled") && !json.isNull("store_disabled")) {
                    product.setStoreDisabled(json.getBoolean("store_disabled"));
                }
                if(json.has("configurations")) {
                    product.setConfigurations(PmzProductConfiguration.fromJSONArray(json.getJSONArray("configurations")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<PmzProductConfiguration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<PmzProductConfiguration> configurations) {
        this.configurations = configurations;
    }

    public Long getListPrice() {
        return listPrice;
    }

    public void setListPrice(Long listPrice) {
        this.listPrice = listPrice;
    }

    public String getAppDisplayName() {
        return appDisplayName;
    }

    public void setAppDisplayName(String appDisplayName) {
        this.appDisplayName = appDisplayName;
    }

    public Long getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public Boolean getStoreDisabled() {
        return storeDisabled;
    }

    public void setStoreDisabled(Boolean storeDisabled) {
        this.storeDisabled = storeDisabled;
    }

    public Long getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Long currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.imageUrl);
        dest.writeValue(this.status);
        dest.writeTypedList(this.configurations);
        dest.writeValue(this.listPrice);
        dest.writeValue(this.currentPrice);
        dest.writeString(this.appDisplayName);
        dest.writeValue(this.displayOrder);
        dest.writeString(this.coverImageUrl);
        dest.writeValue(this.storeDisabled);
    }

    public PmzProduct() {
    }

    protected PmzProduct(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.description = in.readString();
        this.imageUrl = in.readString();
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.configurations = in.createTypedArrayList(PmzProductConfiguration.CREATOR);
        this.listPrice = (Long) in.readValue(Long.class.getClassLoader());
        this.currentPrice = (Long) in.readValue(Long.class.getClassLoader());
        this.appDisplayName = in.readString();
        this.displayOrder = (Long) in.readValue(Long.class.getClassLoader());
        this.coverImageUrl = in.readString();
        this.storeDisabled = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Creator<PmzProduct> CREATOR = new Creator<PmzProduct>() {
        @Override
        public PmzProduct createFromParcel(Parcel source) {
            return new PmzProduct(source);
        }

        @Override
        public PmzProduct[] newArray(int size) {
            return new PmzProduct[size];
        }
    };
}
