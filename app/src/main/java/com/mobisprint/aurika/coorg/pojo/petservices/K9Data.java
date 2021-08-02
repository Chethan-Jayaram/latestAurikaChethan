
package com.mobisprint.aurika.coorg.pojo.petservices;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class K9Data implements Parcelable {

    @SerializedName("ishourbounded")
    @Expose
    private Boolean ishourbounded;

    public Boolean getIshourbounded() {
        return ishourbounded;
    }

    public void setIshourbounded(Boolean ishourbounded) {
        this.ishourbounded = ishourbounded;
    }

    @SerializedName("max_count")
    @Expose
    private Integer maxCount;

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    @SerializedName("ItemselectorType")
    @Expose
    private String itemselectorType;

    public String getItemselectorType() {
        return itemselectorType;
    }

    public void setItemselectorType(String itemselectorType) {
        this.itemselectorType = itemselectorType;
    }

    @SerializedName("isItemSelected")
    @Expose
    private boolean isItemSelected=false;

    public boolean isItemSelected() {
        return isItemSelected;
    }

    public void setItemSelected(boolean itemSelected) {
        isItemSelected = itemSelected;
    }

    @SerializedName("count")
    @Expose
    private Integer count=0;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("availability")
    @Expose
    private String availability;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("location_id")
    @Expose
    private Integer locationId;
    @SerializedName("last_activity_on")
    @Expose
    private String lastActivityOn;
    @SerializedName("last_activity_by")
    @Expose
    private String lastActivityBy;
    @SerializedName("services_list")
    @Expose
    private List<Services> servicesList = null;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("quantity")
    @Expose
    private Integer quantity;

    public RouteName getRouteName() {
        return routeName;
    }

    public void setRouteName(RouteName routeName) {
        this.routeName = routeName;
    }

    @SerializedName("route_name")
    @Expose
    private RouteName routeName;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getLastActivityOn() {
        return lastActivityOn;
    }

    public void setLastActivityOn(String lastActivityOn) {
        this.lastActivityOn = lastActivityOn;
    }

    public String getLastActivityBy() {
        return lastActivityBy;
    }

    public void setLastActivityBy(String lastActivityBy) {
        this.lastActivityBy = lastActivityBy;
    }

    public List<Services> getServicesList() {
        return servicesList;
    }

    public void setServicesList(List<Services> servicesList) {
        this.servicesList = servicesList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
