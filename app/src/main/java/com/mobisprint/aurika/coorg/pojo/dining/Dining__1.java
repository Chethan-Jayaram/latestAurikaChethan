package com.mobisprint.aurika.coorg.pojo.dining;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dining__1 implements Parcelable {

    @SerializedName("IsItemDisabled")
    @Expose
    private boolean IsItemDisabled;

    public boolean isItemDisabled() {
        return IsItemDisabled;
    }

    public void setItemDisabled(boolean itemDisabled) {
        IsItemDisabled = itemDisabled;
    }


    @SerializedName("details")
    @Expose
    public List<com.mobisprint.aurika.coorg.pojo.Services.Data> customisedList =new ArrayList<>();

    public List<com.mobisprint.aurika.coorg.pojo.Services.Data> getCustomisedList() {
        return customisedList;
    }

    public void setCustomisedList(List<com.mobisprint.aurika.coorg.pojo.Services.Data> customisedList) {
        this.customisedList = customisedList;
    }

    @SerializedName("item_id")
    @Expose
    private Integer item_id;

    public Integer getItem_id() {
        return item_id;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
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

    @SerializedName("isItemSelected")
    @Expose
    private boolean isItemSelected=false;

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

    @SerializedName("itemcode")
    @Expose
    private String itemCode;

    @SerializedName("ItemType")
    @Expose
    private String Item_Type;

    @SerializedName("SubMenuCode")
    @Expose
    private String SubMenuCode;


    public String getSubMenuCode() {
        return SubMenuCode;
    }


    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public void setSubMenuCode(String subMenuCode) {
        SubMenuCode = subMenuCode;
    }

    public String getItem_Type() {
        return Item_Type;
    }

    public void setItem_Type(String item_Type) {
        Item_Type = item_Type;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLastActivityOn() {
        return lastActivityOn;
    }

    public void setLastActivityOn(String lastActivityOn) {
        this.lastActivityOn = lastActivityOn;
    }

    public Integer getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(Integer subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String getLastActivityBy() {
        return lastActivityBy;
    }

    public void setLastActivityBy(String lastActivityBy) {
        this.lastActivityBy = lastActivityBy;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("item_type")
    @Expose
    private String itemType;
    @SerializedName("location_id")
    @Expose
    private Integer locationId;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("last_activity_on")
    @Expose
    private String lastActivityOn;
    @SerializedName("subcategory_id")
    @Expose
    private Integer subcategoryId;
    @SerializedName("last_activity_by")
    @Expose
    private String lastActivityBy;

    @SerializedName("dining_subcategory")
    @Expose
    private List<DiningSubcategory> diningSubcategory = null;

    public List<DiningSubcategory> getDiningSubcategory() {
        return diningSubcategory;
    }

    public void setDiningSubcategory(List<DiningSubcategory> diningSubcategory) {
        this.diningSubcategory = diningSubcategory;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }


}
