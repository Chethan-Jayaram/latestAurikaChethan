
package com.mobisprint.aurika.pojo.testing;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("subTitle")
    @Expose
    private String subTitle;
    @SerializedName("items")
    @Expose
    private List<Item_> items = null;
    @SerializedName("assistance")
    @Expose
    private String assistance;
    @SerializedName("timing")
    @Expose
    private String timing;
    @SerializedName("location")
    @Expose
    private String location;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public List<Item_> getItems() {
        return items;
    }

    public void setItems(List<Item_> items) {
        this.items = items;
    }

    public String getAssistance() {
        return assistance;
    }

    public void setAssistance(String assistance) {
        this.assistance = assistance;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
