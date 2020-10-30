package ar.com.fennoma.paymentezsdk.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class PmzMenu implements Parcelable {

    private Long menuId;
    private List<PmzCategory> categories;

    public static PmzMenu fromJSONObject(JSONObject json) {
        PmzMenu menu = new PmzMenu();
        if(json != null) {
            try {
                if(json.has("menu_id")) {
                    menu.setMenuId(json.getLong("menu_id"));
                }
                if(json.has("categories")) {
                    menu.setCategories(PmzCategory.fromJSONArray(json.getJSONArray("categories")));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return menu;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public List<PmzCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<PmzCategory> categories) {
        this.categories = categories;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.menuId);
        dest.writeTypedList(this.categories);
    }

    public PmzMenu() {
    }

    protected PmzMenu(Parcel in) {
        this.menuId = (Long) in.readValue(Long.class.getClassLoader());
        this.categories = in.createTypedArrayList(PmzCategory.CREATOR);
    }

    public static final Creator<PmzMenu> CREATOR = new Creator<PmzMenu>() {
        @Override
        public PmzMenu createFromParcel(Parcel source) {
            return new PmzMenu(source);
        }

        @Override
        public PmzMenu[] newArray(int size) {
            return new PmzMenu[size];
        }
    };
}
