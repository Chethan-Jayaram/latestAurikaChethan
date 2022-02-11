
package com.mobisprint.aurika.coorg.pojo.dining;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubcategoryItem {

    @SerializedName("isCheckBoxSelected")
    @Expose
    private Boolean isCheckBoxSelected=false;
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
    @SerializedName("location_id")
    @Expose
    private Integer locationId;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("last_activity_on")
    @Expose
    private String lastActivityOn;
    @SerializedName("subitemcategory_id")
    @Expose
    private Integer subitemcategoryId;
    @SerializedName("last_activity_by")
    @Expose
    private Integer lastActivityBy;

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

    public Integer getSubitemcategoryId() {
        return subitemcategoryId;
    }

    public void setSubitemcategoryId(Integer subitemcategoryId) {
        this.subitemcategoryId = subitemcategoryId;
    }

    public Integer getLastActivityBy() {
        return lastActivityBy;
    }

    public void setLastActivityBy(Integer lastActivityBy) {
        this.lastActivityBy = lastActivityBy;
    }

}
