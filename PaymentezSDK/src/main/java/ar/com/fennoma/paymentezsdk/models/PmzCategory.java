package ar.com.fennoma.paymentezsdk.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PmzCategory implements Parcelable {

    private Long id;
    private String name;
    private String imageUrl;
    private Integer status;
    private List<PmzProduct> products;
    private Integer displayOrder;

    public static List<PmzCategory> fromJSONArray(JSONArray json) {
        List<PmzCategory> categories = new ArrayList<>();
        if(json != null) {
            for(int i = 0; i < json.length(); i++) {
                try {
                    categories.add(fromJSONObject(json.getJSONObject(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return categories;
    }

    public static PmzCategory fromJSONObject(JSONObject json) {
        PmzCategory category = new PmzCategory();
        if(json != null) {
            try {
                if(json.has("id")) {
                    category.setId(json.getLong("id"));
                }
                if(json.has("display_order")) {
                    category.setDisplayOrder(json.getInt("display_order"));
                }
                if(json.has("status")) {
                    category.setStatus(json.getInt("status"));
                }
                if(json.has("name")) {
                    category.setName(json.getString("name"));
                }
                if(json.has("image")) {
                    category.setImageUrl(json.getString("image"));
                }
                if(json.has("products")) {
                    category.setProducts(PmzProduct.fromJSONArray(json.getJSONArray("products")));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return category;
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

    public List<PmzProduct> getProducts() {
        return products;
    }

    public void setProducts(List<PmzProduct> products) {
        this.products = products;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.imageUrl);
        dest.writeValue(this.status);
        dest.writeTypedList(this.products);
        dest.writeValue(this.displayOrder);
    }

    public PmzCategory() {
    }

    protected PmzCategory(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.imageUrl = in.readString();
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.products = in.createTypedArrayList(PmzProduct.CREATOR);
        this.displayOrder = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<PmzCategory> CREATOR = new Creator<PmzCategory>() {
        @Override
        public PmzCategory createFromParcel(Parcel source) {
            return new PmzCategory(source);
        }

        @Override
        public PmzCategory[] newArray(int size) {
            return new PmzCategory[size];
        }
    };
}
