
package com.mobisprint.aurika.udaipur.pojo.testing;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobisprint.aurika.helper.MenuListner;

public class Item___ implements MenuListner {

    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("itemDescription")
    @Expose
    private String itemDescription;
    @SerializedName("isVeg")
    @Expose
    private Boolean isVeg;
    @SerializedName("itemPrice")
    @Expose
    private String itemPrice;
    @SerializedName("priceList")
    @Expose
    private List<PriceList> priceList = null;
    @SerializedName("subCategoryTitle")
    @Expose
    private String subCategoryTitle;
    @SerializedName("items")
    @Expose
    private List<Item____> items = null;
    @SerializedName("displayIcon")
    @Expose
    private Boolean displayIcon;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Boolean getIsVeg() {
        return isVeg;
    }

    public void setIsVeg(Boolean isVeg) {
        this.isVeg = isVeg;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public List<PriceList> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<PriceList> priceList) {
        this.priceList = priceList;
    }

    public String getSubCategoryTitle() {
        return subCategoryTitle;
    }

    public void setSubCategoryTitle(String subCategoryTitle) {
        this.subCategoryTitle = subCategoryTitle;
    }

    public List<Item____> getItems() {
        return items;
    }

    public void setItems(List<Item____> items) {
        this.items = items;
    }

    public Boolean getDisplayIcon() {
        return displayIcon;
    }

    public void setDisplayIcon(Boolean displayIcon) {
        this.displayIcon = displayIcon;
    }
    @Override
    public int getType() {
        return MenuListner.TYPE_ITEM;
    }

}
