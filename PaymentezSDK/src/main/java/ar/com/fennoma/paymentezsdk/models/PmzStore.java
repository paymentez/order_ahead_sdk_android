package ar.com.fennoma.paymentezsdk.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PmzStore implements Parcelable {

    private Long id;
    private Long commerceId;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private String country;
    private String city;
    private String website;
    private String phone;
    private String imageUrl;
    private Boolean enablePayAtTheTableOrders;
    private Integer type;
    private String commerceImage;
    private Integer commerceFiscalNumber;
    private Long subsidiaryId;
    private String zipCode;
    private String companyName;
    private Integer status;
    private String commercePaymentezAppCode;
    private String internalName;
    private String commerceName;
    private Boolean showStock;
    private String print;
    private Long menuId;
    private Double deliveryPrice;
    private Integer maxDeliveryDistance;
    private Boolean acceptsDelivery;
    private Integer timePreparing;
    private List<String> extraPaymentCodes;
    private String serverAppCode;
    private String clientAppCode;
    private PmzSponsor sponsor;

    public static List<PmzStore> fromJSONArray(JSONArray json) {
        List<PmzStore> store = new ArrayList<>();
        if(json != null) {
            for(int i = 0; i < json.length(); i++) {
                try {
                    store.add(fromJSONObject(json.getJSONObject(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return store;
    }

    private static PmzStore fromJSONObject(JSONObject json) {
        PmzStore store = new PmzStore();
        if(json != null) {
            try {
                if(json.has("id")) {
                    store.setId(json.getLong("id"));
                }
                if(json.has("name")) {
                    store.setName(json.getString("name"));
                }
                if(json.has("address")) {
                    store.setAddress(json.getString("address"));
                }
                if(json.has("lat") && !json.isNull("lat")) {
                    store.setLatitude(json.getDouble("lat"));
                }
                if(json.has("lon") && !json.isNull("lon")) {
                    store.setLongitude(json.getDouble("lon"));
                }
                if(json.has("country")) {
                    store.setCountry(json.getString("country"));
                }
                if(json.has("city")) {
                    store.setCity(json.getString("city"));
                }
                if(json.has("website") && !json.isNull("website")) {
                    store.setWebsite(json.getString("website"));
                }
                if(json.has("phone") && !json.isNull("phone")) {
                    store.setPhone(json.getString("phone"));
                }
                if(json.has("image") && !json.isNull("image")) {
                    store.setImageUrl(json.getString("image"));
                }
                if(json.has("enable_pay_at_the_table_orders") && !json.isNull("enable_pay_at_the_table_orders")) {
                    store.setEnablePayAtTheTableOrders(json.getBoolean("enable_pay_at_the_table_orders"));
                } else {
                    store.setEnablePayAtTheTableOrders(false);
                }
                if(json.has("type") && !json.isNull("type")) {
                    store.setType(json.getInt("type"));
                }
                if(json.has("commerce_image") && !json.isNull("commerce_image")) {
                    store.setCommerceImage(json.getString("commerce_image"));
                }
                if(json.has("commerce_id") && !json.isNull("commerce_id")) {
                    store.setCommerceId(json.getLong("commerce_id"));
                }
                if(json.has("commerce_fiscal_number") && !json.isNull("commerce_fiscal_number")) {
                    store.setCommerceFiscalNumber(json.getInt("commerce_fiscal_number"));
                }
                if(json.has("subsidiary_id") && !json.isNull("subsidiary_id")) {
                    store.setSubsidiaryId(json.getLong("subsidiary_id"));
                }
                if(json.has("zip_code") && !json.isNull("zip_code")) {
                    store.setZipCode(json.getString("zip_code"));
                }
                if(json.has("company_name") && !json.isNull("company_name")) {
                    store.setCompanyName(json.getString("company_name"));
                }
                if(json.has("status") && !json.isNull("status")) {
                    store.setStatus(json.getInt("status"));
                }
                if(json.has("commerce_paymentez_app_code") && !json.isNull("commerce_paymentez_app_code")) {
                    store.setCommercePaymentezAppCode(json.getString("commerce_paymentez_app_code"));
                }
                if(json.has("internal_name") && !json.isNull("internal_name")) {
                    store.setInternalName(json.getString("internal_name"));
                }
                if(json.has("commerce_name") && !json.isNull("commerce_name")) {
                    store.setCommerceName(json.getString("commerce_name"));
                }
                if(json.has("show_stock") && !json.isNull("show_stock")) {
                    store.setShowStock(json.getBoolean("show_stock"));
                }
                if(json.has("print") && !json.isNull("print")) {
                    store.setPrint(json.getString("print"));
                }
                if(json.has("menu_id") && !json.isNull("menu_id")) {
                    store.setMenuId(json.getLong("menu_id"));
                }
                if(json.has("delivery_price") && !json.isNull("delivery_price")) {
                    store.setDeliveryPrice(json.getDouble("delivery_price"));
                }
                if(json.has("max_delivery_distance") && !json.isNull("max_delivery_distance")) {
                    store.setMaxDeliveryDistance(json.getInt("max_delivery_distance"));
                }
                if(json.has("time_preparing") && !json.isNull("time_preparing")) {
                    store.setTimePreparing(json.getInt("time_preparing"));
                }
                if(json.has("accept_delivery") && !json.isNull("accept_delivery")) {
                    store.setAcceptsDelivery(json.getBoolean("accept_delivery"));
                } else {
                    store.setAcceptsDelivery(false);
                }
                /*if(json.has("extra_payments_codes") && !json.isNull("extra_payments_codes")) {
                    store.setExtraPaymentCodes(new ArrayList<String>());
                }*/
                if(json.has("server_app_code") && !json.isNull("server_app_code")) {
                    store.setServerAppCode(json.getString("server_app_code"));
                }
                if(json.has("clien_app_code") && !json.isNull("clien_app_code")) {
                    store.setClientAppCode(json.getString("clien_app_code"));
                }
                if(json.has("sponsor") && !json.isNull("sponsor")) {
                    store.setSponsor(PmzSponsor.fromJSONObject(json.getJSONObject("sponsor")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return store;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommerceId() {
        return commerceId;
    }

    public void setCommerceId(Long commerceId) {
        this.commerceId = commerceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getEnablePayAtTheTableOrders() {
        return enablePayAtTheTableOrders;
    }

    public void setEnablePayAtTheTableOrders(Boolean enablePayAtTheTableOrders) {
        this.enablePayAtTheTableOrders = enablePayAtTheTableOrders;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCommerceImage() {
        return commerceImage;
    }

    public void setCommerceImage(String commerceImage) {
        this.commerceImage = commerceImage;
    }

    public Integer getCommerceFiscalNumber() {
        return commerceFiscalNumber;
    }

    public void setCommerceFiscalNumber(Integer commerceFiscalNumber) {
        this.commerceFiscalNumber = commerceFiscalNumber;
    }

    public Long getSubsidiaryId() {
        return subsidiaryId;
    }

    public void setSubsidiaryId(Long subsidiaryId) {
        this.subsidiaryId = subsidiaryId;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCommercePaymentezAppCode() {
        return commercePaymentezAppCode;
    }

    public void setCommercePaymentezAppCode(String commercePaymentezAppCode) {
        this.commercePaymentezAppCode = commercePaymentezAppCode;
    }

    public String getInternalName() {
        return internalName;
    }

    public void setInternalName(String internalName) {
        this.internalName = internalName;
    }

    public String getCommerceName() {
        return commerceName;
    }

    public void setCommerceName(String commerceName) {
        this.commerceName = commerceName;
    }

    public Boolean getShowStock() {
        return showStock;
    }

    public void setShowStock(Boolean showStock) {
        this.showStock = showStock;
    }

    public String getPrint() {
        return print;
    }

    public void setPrint(String print) {
        this.print = print;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(Double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public Integer getMaxDeliveryDistance() {
        return maxDeliveryDistance;
    }

    public void setMaxDeliveryDistance(Integer maxDeliveryDistance) {
        this.maxDeliveryDistance = maxDeliveryDistance;
    }

    public Boolean getAcceptsDelivery() {
        return acceptsDelivery;
    }

    public void setAcceptsDelivery(Boolean acceptsDelivery) {
        this.acceptsDelivery = acceptsDelivery;
    }

    public Integer getTimePreparing() {
        return timePreparing;
    }

    public void setTimePreparing(Integer timePreparing) {
        this.timePreparing = timePreparing;
    }

    public List<String> getExtraPaymentCodes() {
        return extraPaymentCodes;
    }

    public void setExtraPaymentCodes(List<String> extraPaymentCodes) {
        this.extraPaymentCodes = extraPaymentCodes;
    }

    public String getServerAppCode() {
        return serverAppCode;
    }

    public void setServerAppCode(String serverAppCode) {
        this.serverAppCode = serverAppCode;
    }

    public String getClientAppCode() {
        return clientAppCode;
    }

    public void setClientAppCode(String clientAppCode) {
        this.clientAppCode = clientAppCode;
    }

    public PmzStore() {
    }

    public PmzSponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(PmzSponsor sponsor) {
        this.sponsor = sponsor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.commerceId);
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeValue(this.latitude);
        dest.writeValue(this.longitude);
        dest.writeString(this.country);
        dest.writeString(this.city);
        dest.writeString(this.website);
        dest.writeString(this.phone);
        dest.writeString(this.imageUrl);
        dest.writeValue(this.enablePayAtTheTableOrders);
        dest.writeValue(this.type);
        dest.writeString(this.commerceImage);
        dest.writeValue(this.commerceFiscalNumber);
        dest.writeValue(this.subsidiaryId);
        dest.writeString(this.zipCode);
        dest.writeString(this.companyName);
        dest.writeValue(this.status);
        dest.writeString(this.commercePaymentezAppCode);
        dest.writeString(this.internalName);
        dest.writeString(this.commerceName);
        dest.writeValue(this.showStock);
        dest.writeString(this.print);
        dest.writeValue(this.menuId);
        dest.writeValue(this.deliveryPrice);
        dest.writeValue(this.maxDeliveryDistance);
        dest.writeValue(this.acceptsDelivery);
        dest.writeValue(this.timePreparing);
        dest.writeStringList(this.extraPaymentCodes);
        dest.writeString(this.serverAppCode);
        dest.writeString(this.clientAppCode);
        dest.writeParcelable(this.sponsor, flags);
    }

    protected PmzStore(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.commerceId = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.address = in.readString();
        this.latitude = (Double) in.readValue(Double.class.getClassLoader());
        this.longitude = (Double) in.readValue(Double.class.getClassLoader());
        this.country = in.readString();
        this.city = in.readString();
        this.website = in.readString();
        this.phone = in.readString();
        this.imageUrl = in.readString();
        this.enablePayAtTheTableOrders = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.type = (Integer) in.readValue(Integer.class.getClassLoader());
        this.commerceImage = in.readString();
        this.commerceFiscalNumber = (Integer) in.readValue(Integer.class.getClassLoader());
        this.subsidiaryId = (Long) in.readValue(Long.class.getClassLoader());
        this.zipCode = in.readString();
        this.companyName = in.readString();
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.commercePaymentezAppCode = in.readString();
        this.internalName = in.readString();
        this.commerceName = in.readString();
        this.showStock = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.print = in.readString();
        this.menuId = (Long) in.readValue(Long.class.getClassLoader());
        this.deliveryPrice = (Double) in.readValue(Double.class.getClassLoader());
        this.maxDeliveryDistance = (Integer) in.readValue(Integer.class.getClassLoader());
        this.acceptsDelivery = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.timePreparing = (Integer) in.readValue(Integer.class.getClassLoader());
        this.extraPaymentCodes = in.createStringArrayList();
        this.serverAppCode = in.readString();
        this.clientAppCode = in.readString();
        this.sponsor = in.readParcelable(PmzSponsor.class.getClassLoader());
    }

    public static final Creator<PmzStore> CREATOR = new Creator<PmzStore>() {
        @Override
        public PmzStore createFromParcel(Parcel source) {
            return new PmzStore(source);
        }

        @Override
        public PmzStore[] newArray(int size) {
            return new PmzStore[size];
        }
    };
}
