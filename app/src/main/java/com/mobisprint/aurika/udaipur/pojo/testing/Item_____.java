
package com.mobisprint.aurika.udaipur.pojo.testing;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobisprint.aurika.helper.MenuListner;

public class Item_____ implements MenuListner {

    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("itemDescription")
    @Expose
    private String itemDescription;
    @SerializedName("priceList")
    @Expose
    private List<PriceList_> priceList = null;
    @SerializedName("itemPrice")
    @Expose
    private String itemPrice;

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

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

    public List<PriceList_> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<PriceList_> priceList) {
        this.priceList = priceList;
    }

    @Override
    public int getType() {
        return MenuListner.TYPE_ITEM;
    }
}
