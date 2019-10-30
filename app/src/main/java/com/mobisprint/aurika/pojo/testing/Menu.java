
package com.mobisprint.aurika.pojo.testing;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobisprint.aurika.helper.MenuListner;

public class Menu implements MenuListner {

    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("items")
    @Expose
    private List<Item_____> items = null;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Item_____> getItems() {
        return items;
    }

    public void setItems(List<Item_____> items) {
        this.items = items;
    }

    @Override
    public int getType() {
        return MenuListner.TYPE_CATEGORY;
    }


}
