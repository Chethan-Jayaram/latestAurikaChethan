
package com.mobisprint.aurika.coorg.pojo.dining;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import kotlin.random.AbstractPlatformRandom;

public class DiningSubcategory implements Parcelable {


    @SerializedName("isCheckBoxSelected")
    @Expose
    private Boolean isCheckBoxSelected=false;


    @SerializedName("isRadioSelected")
    @Expose
    private Boolean isRadioSelected=false;


    public Boolean getCheckBoxSelected() {
        return isCheckBoxSelected;
    }

    public void setCheckBoxSelected(Boolean checkBoxSelected) {
        isCheckBoxSelected = checkBoxSelected;
    }

    public Boolean getRadioSelected() {
        return isRadioSelected;
    }

    public void setRadioSelected(Boolean radioSelected) {
        isRadioSelected = radioSelected;
    }

    public Boolean getItemDisabled() {
        return isItemDisabled;
    }

    public void setItemDisabled(Boolean itemDisabled) {
        isItemDisabled = itemDisabled;
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
    @SerializedName("max_no_items")
    @Expose
    private Integer maxNoItems;
    @SerializedName("ItemOption")
    @Expose
    private String itemOption;
    @SerializedName("item_type")
    @Expose
    private String itemType;
    @SerializedName("IsItemDisabled")
    @Expose
    private Boolean isItemDisabled;
    @SerializedName("itemcategory_id")
    @Expose
    private Integer itemcategoryId;
    @SerializedName("location_id")
    @Expose
    private Integer locationId;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("last_activity_on")
    @Expose
    private String lastActivityOn;
    @SerializedName("subcategory_items")
    @Expose
    private List<SubcategoryItem> subcategoryItems = null;

    private List<SubcategoryItem> customisedSubCategoryItems = new ArrayList<>();

    public List<SubcategoryItem> getCustomisedSubCategoryItems() {
        return customisedSubCategoryItems;
    }

    public void setCustomisedSubCategoryItems(List<SubcategoryItem> customisedSubCategoryItems) {
        this.customisedSubCategoryItems = customisedSubCategoryItems;
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

    public Integer getMaxNoItems() {
        return maxNoItems;
    }

    public void setMaxNoItems(Integer maxNoItems) {
        this.maxNoItems = maxNoItems;
    }

    public String getItemOption() {
        return itemOption;
    }

    public void setItemOption(String itemOption) {
        this.itemOption = itemOption;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Boolean getIsItemDisabled() {
        return isItemDisabled;
    }

    public void setIsItemDisabled(Boolean isItemDisabled) {
        this.isItemDisabled = isItemDisabled;
    }

    public Integer getItemcategoryId() {
        return itemcategoryId;
    }

    public void setItemcategoryId(Integer itemcategoryId) {
        this.itemcategoryId = itemcategoryId;
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

    public List<SubcategoryItem> getSubcategoryItems() {
        return subcategoryItems;
    }

    public void setSubcategoryItems(List<SubcategoryItem> subcategoryItems) {
        this.subcategoryItems = subcategoryItems;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
