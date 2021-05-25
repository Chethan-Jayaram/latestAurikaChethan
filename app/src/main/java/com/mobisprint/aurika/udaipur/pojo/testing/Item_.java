
package com.mobisprint.aurika.udaipur.pojo.testing;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item_  {
    @SerializedName("Expandcontrast")
    @Expose
    private Boolean Expandcontrast;
    @SerializedName("serviceTitle")
    @Expose
    private String serviceTitle;
    @SerializedName("serviceDescription")
    @Expose
    private String serviceDescription;
    @SerializedName("categoryTitle")
    @Expose
    private String categoryTitle;
    @SerializedName("categoryDescription")
    @Expose
    private String categoryDescription;
    @SerializedName("items")
    @Expose
    private List<Item__> items = null;
    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("itemDescription")
    @Expose
    private String itemDescription;
    @SerializedName("assistance")
    @Expose
    private String assistance;
    @SerializedName("timing")
    @Expose
    private String timing;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("menuItem")
    @Expose
    private String menuItem;
    @SerializedName("headerTitle")
    @Expose
    private String headerTitle;
    @SerializedName("menuDescription")
    @Expose
    private String menuDescription;
    @SerializedName("isVegNonveg")
    @Expose
    private Boolean isVegNonveg;
    @SerializedName("menu")
    @Expose
    private List<Menu> menu = null;

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

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public List<Item__> getItems() {
        return items;
    }

    public void setItems(List<Item__> items) {
        this.items = items;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(String menuItem) {
        this.menuItem = menuItem;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getMenuDescription() {
        return menuDescription;
    }

    public void setMenuDescription(String menuDescription) {
        this.menuDescription = menuDescription;
    }

    public Boolean getIsVegNonveg() {
        return isVegNonveg;
    }

    public void setIsVegNonveg(Boolean isVegNonveg) {
        this.isVegNonveg = isVegNonveg;
    }

    public List<Menu> getMenu() {
        return menu;
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }
    public Boolean getExpandcontrast() {
        return Expandcontrast;
    }

    public void setExpandcontrast(Boolean expandcontrast) {
        Expandcontrast = expandcontrast;
    }

    public Boolean getVegNonveg() {
        return isVegNonveg;
    }

    public void setVegNonveg(Boolean vegNonveg) {
        isVegNonveg = vegNonveg;
    }


}
