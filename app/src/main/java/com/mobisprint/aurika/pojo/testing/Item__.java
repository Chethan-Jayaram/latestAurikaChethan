
package com.mobisprint.aurika.pojo.testing;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobisprint.aurika.helper.MenuListner;

public class Item__ implements MenuListner {

    @SerializedName("serviceTitle")
    @Expose
    private String serviceTitle;
    @SerializedName("serviceDescription")
    @Expose
    private String serviceDescription;
    @SerializedName("servicePrice")
    @Expose
    private String servicePrice;
    @SerializedName("categoryTitle")
    @Expose
    private String categoryTitle;
    @SerializedName("items")
    @Expose
    private List<Item___> items = null;

    public String getServiceTitle() {
        return serviceTitle;
    }

    public void setServiceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public List<Item___> getItems() {
        return items;
    }

    public void setItems(List<Item___> items) {
        this.items = items;
    }
    @Override
    public int getType() {
        return MenuListner.TYPE_CATEGORY;
    }

}
