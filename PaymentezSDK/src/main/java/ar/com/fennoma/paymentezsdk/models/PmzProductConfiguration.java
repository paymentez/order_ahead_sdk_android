package ar.com.fennoma.paymentezsdk.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PmzProductConfiguration implements Parcelable {

    private Long id;
    private String name;
    private Boolean defaultValue;
    private String annotation;
    private Long extraPrice;
    private String typeName;
    private String subtypeName;
    private String subTypeInternalName;
    private Integer minConfiguration;
    private Integer maxConfiguration;
    private Long displayOrder;
    private Long subtypeDisplayOrder;
    private Boolean isDefault;

    private boolean checked = false;

    public static List<PmzProductConfiguration> fromJSONArray(JSONArray array) {
        List<PmzProductConfiguration> result = new ArrayList<>();
        if(array != null) {
            for(int i = 0; i < array.length(); i++) {
                try {
                    result.add(fromJSONObject(array.getJSONObject(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private static PmzProductConfiguration fromJSONObject(JSONObject json) {
        PmzProductConfiguration config = new PmzProductConfiguration();
        if(json != null) {
            try {
                if(json.has("id")) {
                    config.setId(json.getLong("id"));
                }
                if(json.has("default")) {
                    config.setDefaultValue(json.getBoolean("default"));
                }
                if(json.has("is_default")) {
                    config.setDefault(json.getBoolean("is_default"));
                }
                if(json.has("name")) {
                    config.setName(json.getString("name"));
                }
                if(json.has("annotations")) {
                    config.setAnnotation(json.getString("annotations"));
                }
                if(json.has("type_name")) {
                    config.setTypeName(json.getString("type_name"));
                }
                if(json.has("sub_type_name")) {
                    config.setSubtypeName(json.getString("sub_type_name"));
                }
                if(json.has("sub_type_internal_name")) {
                    config.setSubTypeInternalName(json.getString("sub_type_internal_name"));
                }
                if(json.has("extra_price")) {
                    config.setExtraPrice(json.getLong("extra_price"));
                }
                if(json.has("min_configuration")) {
                    config.setMinConfiguration(json.getInt("min_configuration"));
                }
                if(json.has("max_configuration")) {
                    config.setMaxConfiguration(json.getInt("max_configuration"));
                }
                if(json.has("display_order")) {
                    config.setDisplayOrder(json.getLong("display_order"));
                }
                if(json.has("subtype_display_order")) {
                    config.setSubtypeDisplayOrder(json.getLong("subtype_display_order"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return config;
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

    public Boolean getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Boolean defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public Long getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(Long extraPrice) {
        this.extraPrice = extraPrice;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSubtypeName() {
        return subtypeName;
    }

    public void setSubtypeName(String subtypeName) {
        this.subtypeName = subtypeName;
    }

    public String getSubTypeInternalName() {
        return subTypeInternalName;
    }

    public void setSubTypeInternalName(String subTypeInternalName) {
        this.subTypeInternalName = subTypeInternalName;
    }

    public Integer getMinConfiguration() {
        return minConfiguration;
    }

    public void setMinConfiguration(Integer minConfiguration) {
        this.minConfiguration = minConfiguration;
    }

    public Integer getMaxConfiguration() {
        return maxConfiguration;
    }

    public void setMaxConfiguration(Integer maxConfiguration) {
        this.maxConfiguration = maxConfiguration;
    }

    public Long getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Long getSubtypeDisplayOrder() {
        return subtypeDisplayOrder;
    }

    public void setSubtypeDisplayOrder(Long subtypeDisplayOrder) {
        this.subtypeDisplayOrder = subtypeDisplayOrder;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public boolean isDefault() {
        return isDefault == null ? false : isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeValue(this.defaultValue);
        dest.writeString(this.annotation);
        dest.writeValue(this.extraPrice);
        dest.writeString(this.typeName);
        dest.writeString(this.subtypeName);
        dest.writeString(this.subTypeInternalName);
        dest.writeValue(this.minConfiguration);
        dest.writeValue(this.maxConfiguration);
        dest.writeValue(this.displayOrder);
        dest.writeValue(this.subtypeDisplayOrder);
        dest.writeValue(this.isDefault);
    }

    public PmzProductConfiguration() {
    }

    protected PmzProductConfiguration(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.defaultValue = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.annotation = in.readString();
        this.extraPrice = (Long) in.readValue(Long.class.getClassLoader());
        this.typeName = in.readString();
        this.subtypeName = in.readString();
        this.subTypeInternalName = in.readString();
        this.minConfiguration = (Integer) in.readValue(Integer.class.getClassLoader());
        this.maxConfiguration = (Integer) in.readValue(Integer.class.getClassLoader());
        this.displayOrder = (Long) in.readValue(Long.class.getClassLoader());
        this.subtypeDisplayOrder = (Long) in.readValue(Long.class.getClassLoader());
        this.isDefault = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Creator<PmzProductConfiguration> CREATOR = new Creator<PmzProductConfiguration>() {
        @Override
        public PmzProductConfiguration createFromParcel(Parcel source) {
            return new PmzProductConfiguration(source);
        }

        @Override
        public PmzProductConfiguration[] newArray(int size) {
            return new PmzProductConfiguration[size];
        }
    };

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
