
package com.mobisprint.aurika.udaipur.pojo.testing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobisprint.aurika.helper.MenuListner;

public class PriceList_ implements MenuListner {

    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("itemPrice")
    @Expose
    private String itemPrice;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    @Override
    public int getType() {
        return MenuListner.TYPE_PRICELIST;
    }
}
