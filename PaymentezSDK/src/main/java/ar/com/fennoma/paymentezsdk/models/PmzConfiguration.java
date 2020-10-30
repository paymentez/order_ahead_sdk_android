package ar.com.fennoma.paymentezsdk.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PmzConfiguration implements Parcelable {

    private Long id;
    private String annotations;
    private String description;
    private Integer type;
    private Double cost;
    private Long configurationId;
    private Double discount;

    public static List<PmzConfiguration> fromJSONArray(JSONArray json) {
        List<PmzConfiguration> configurations = new ArrayList<>();
        if(json != null) {
            for(int i = 0; i < json.length(); i++) {
                try {
                    configurations.add(fromJSONObject(json.getJSONObject(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return configurations;
    }

    private static PmzConfiguration fromJSONObject(JSONObject json) {
        PmzConfiguration configuration = new PmzConfiguration();
        if(json != null) {
            try {
                if(json.has("id")) {
                    configuration.setId(json.getLong("id"));
                }
                if(json.has("type")) {
                    configuration.setType(json.getInt("type"));
                }
                if(json.has("cost")) {
                    configuration.setCost(json.getDouble("cost"));
                }
                if(json.has("discount") && !json.isNull("discount")) {
                    configuration.setDiscount(json.getDouble("discount"));
                }
                if(json.has("configuration_id")) {
                    configuration.setConfigurationId(json.getLong("configuration_id"));
                } else if(json.has("id_configuration")) {
                    configuration.setConfigurationId(json.getLong("id_configuration"));
                }
                if(json.has("annotations")) {
                    configuration.setAnnotations(json.getString("annotations"));
                }
                if(json.has("description")) {
                    configuration.setDescription(json.getString("description"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return configuration;
    }

    public static JSONArray getJSONFor(List<PmzConfiguration> configurations) throws JSONException {
        JSONArray array = new JSONArray();
        if(configurations != null) {
            for(int i = 0; i < configurations.size(); i++) {
                JSONObject json = new JSONObject();
                json.put("id_configuration", configurations.get(i).getConfigurationId());
                array.put(json);
            }
        }
        return array;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnnotations() {
        return annotations;
    }

    public void setAnnotations(String annotations) {
        this.annotations = annotations;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Long getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(Long configurationId) {
        this.configurationId = configurationId;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.annotations);
        dest.writeString(this.description);
        dest.writeValue(this.type);
        dest.writeValue(this.cost);
        dest.writeValue(this.configurationId);
        dest.writeValue(this.discount);
    }

    public PmzConfiguration() {
    }

    protected PmzConfiguration(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.annotations = in.readString();
        this.description = in.readString();
        this.type = (Integer) in.readValue(Integer.class.getClassLoader());
        this.cost = (Double) in.readValue(Double.class.getClassLoader());
        this.configurationId = (Long) in.readValue(Long.class.getClassLoader());
        this.discount = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Creator<PmzConfiguration> CREATOR = new Creator<PmzConfiguration>() {
        @Override
        public PmzConfiguration createFromParcel(Parcel source) {
            return new PmzConfiguration(source);
        }

        @Override
        public PmzConfiguration[] newArray(int size) {
            return new PmzConfiguration[size];
        }
    };
}
