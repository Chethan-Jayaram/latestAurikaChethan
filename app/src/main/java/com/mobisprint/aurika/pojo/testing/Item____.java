
package com.mobisprint.aurika.pojo.testing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobisprint.aurika.helper.MenuListner;

public class Item____ implements MenuListner {

    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("itemPrice")
    @Expose
    private String itemPrice;
    @SerializedName("displayIcon")
    @Expose
    private Boolean displayIcon;
    @SerializedName("itemDescription")
    @Expose
    private String itemDescription;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Boolean getDisplayIcon() {
        return displayIcon;
    }

    public void setDisplayIcon(Boolean displayIcon) {
        this.displayIcon = displayIcon;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
    @Override
    public int getType() {
        return MenuListner.TYPE_ITEM;
    }
}
