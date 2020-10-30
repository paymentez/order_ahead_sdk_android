package ar.com.fennoma.paymentezsdk.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PmzCapacity implements Parcelable {

    private Long storeId;
    private Integer capacity;
    private Integer actual;
    private Boolean exceeded;
    private Long timeRemaining;

    public static List<PmzCapacity> fromJSONArray(JSONArray json) {
        List<PmzCapacity> capacities = new ArrayList<>();
        if(json != null) {
            for(int i = 0; i < json.length(); i++) {
                try {
                    capacities.add(fromJSONObject(json.getJSONObject(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return capacities;
    }

    private static PmzCapacity fromJSONObject(JSONObject json) {
        PmzCapacity capacity = new PmzCapacity();
        if(json != null) {
            try {
                if(json.has("storeId")) {
                    capacity.setStoreId(json.getLong("storeId"));
                }
                if(json.has("capacity")) {
                    capacity.setCapacity(json.getInt("capacity"));
                }
                if(json.has("actual")) {
                    capacity.setActual(json.getInt("actual"));
                }
                if(json.has("exceeded")) {
                    capacity.setExceeded(json.getBoolean("exceeded"));
                }
                if(json.has("time_remaining")) {
                    capacity.setTimeRemaining(json.getLong("time_remaining"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return capacity;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getActual() {
        return actual;
    }

    public void setActual(Integer actual) {
        this.actual = actual;
    }

    public Boolean getExceeded() {
        return exceeded;
    }

    public void setExceeded(Boolean exceeded) {
        this.exceeded = exceeded;
    }

    public Long getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(Long timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.storeId);
        dest.writeValue(this.capacity);
        dest.writeValue(this.actual);
        dest.writeValue(this.exceeded);
        dest.writeValue(this.timeRemaining);
    }

    public PmzCapacity() {
    }

    protected PmzCapacity(Parcel in) {
        this.storeId = (Long) in.readValue(Long.class.getClassLoader());
        this.capacity = (Integer) in.readValue(Integer.class.getClassLoader());
        this.actual = (Integer) in.readValue(Integer.class.getClassLoader());
        this.exceeded = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.timeRemaining = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Creator<PmzCapacity> CREATOR = new Creator<PmzCapacity>() {
        @Override
        public PmzCapacity createFromParcel(Parcel source) {
            return new PmzCapacity(source);
        }

        @Override
        public PmzCapacity[] newArray(int size) {
            return new PmzCapacity[size];
        }
    };
}
