
package com.mobisprint.aurika.coorg.pojo.Services;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobisprint.aurika.udaipur.pojo.GeneralPojo;

public class Data implements Parcelable {

    @SerializedName("minibar_list")
    @Expose
    private List<Minibar__1> minibarList = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @SerializedName("count")
    @Expose
    private Integer count=0;

    public List<Minibar__1> getMinibarList() {
        return minibarList;
    }

    public void setMinibarList(List<Minibar__1> minibarList) {
        this.minibarList = minibarList;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("availability")
    @Expose
    private String availability;

    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("quantity")
    @Expose
    private String quantity;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

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
    private List<ServicesList> servicesList = null;
    @SerializedName("route_name")
    @Expose
    private RouteName routeName;

    public RouteName getRouteName() {
        return routeName;
    }

    public void setRouteName(RouteName routeName) {
        this.routeName = routeName;
    }

    @SerializedName("sleepwell_list")
    @Expose
    private List<SleepwellList> sleepwellList = null;


    public List<Category_item> getCategory_item() {
        return category_item;
    }

    public void setCategory_item(List<Category_item> category_item) {
        this.category_item = category_item;
    }

    @SerializedName("category_item")
    @Expose
    private List<Category_item> category_item = null;

    public List<SleepwellList> getSleepwellList() {
        return sleepwellList;
    }

    public void setSleepwellList(List<SleepwellList> sleepwellList) {
        this.sleepwellList = sleepwellList;
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

    public List<ServicesList> getServicesList() {
        return servicesList;
    }

    public void setServicesList(List<ServicesList> servicesList) {
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
