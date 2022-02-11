
package com.mobisprint.aurika.coorg.pojo.Services;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobisprint.aurika.udaipur.pojo.GeneralPojo;

public class Data implements Parcelable {

    @SerializedName("ishourbounded")
    @Expose
    private Boolean ishourbounded;

    public Boolean getTimePop() {
        return IsTimePop;
    }

    public void setTimePop(Boolean timePop) {
        IsTimePop = timePop;
    }

    @SerializedName("IsTimePop")
    @Expose
    private Boolean IsTimePop;

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

    public boolean isItemSelected() {
        return isItemSelected;
    }

    public void setItemSelected(boolean itemSelected) {
        isItemSelected = itemSelected;
    }

    @SerializedName("isItemSelected")
    @Expose
    private boolean isItemSelected=false;

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
    @SerializedName("item_id")
    @Expose
    private Integer item_id;

    public Integer getItem_id() {
        return item_id;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }

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
    private String price="0.0";
    @SerializedName("quantity")
    @Expose
    private Integer quantity=1;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
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
    @SerializedName("itemcode")
    @Expose
    private String itemCode;

    @SerializedName("ItemType")
    @Expose
    private String Item_Type;

    @SerializedName("SubMenuCode")
    @Expose
    private String SubMenuCode;

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItem_Type() {
        return Item_Type;
    }

    public void setItem_Type(String item_Type) {
        Item_Type = item_Type;
    }

    public String getSubMenuCode() {
        return SubMenuCode;
    }

    public void setSubMenuCode(String subMenuCode) {
        SubMenuCode = subMenuCode;
    }

    @SerializedName("details")
    @Expose
    private List<Data> details = null;

    public List<Data> getDetails() {
        return details;
    }

    public void setDetails(List<Data> details) {
        this.details = details;
    }
}
