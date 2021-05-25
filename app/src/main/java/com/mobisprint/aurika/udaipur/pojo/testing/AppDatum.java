
package com.mobisprint.aurika.udaipur.pojo.testing;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppDatum {


    @SerializedName("propertyName")
    @Expose
    private String propertyName;
    @SerializedName("dashboardItems")
    @Expose
    private List<String> dashboardItems = null;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public List<String> getDashboardItems() {
        return dashboardItems;
    }

    public void setDashboardItems(List<String> dashboardItems) {
        this.dashboardItems = dashboardItems;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
