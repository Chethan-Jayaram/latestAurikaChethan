
package com.mobisprint.aurika.coorg.pojo.guestbooking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TicketItem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("curency_symbol")
    @Expose
    private String curencySymbol;
    @SerializedName("append_currency")
    @Expose
    private String appendCurrency;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("destination")
    @Expose
    private Object destination;
    @SerializedName("last_activity_on")
    @Expose
    private String lastActivityOn;
    @SerializedName("location_id")
    @Expose
    private Integer locationId;
    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("number_of_guest")
    @Expose
    private String numberOfGuest;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("ticket")
    @Expose
    private Integer ticket;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCurencySymbol() {
        return curencySymbol;
    }

    public void setCurencySymbol(String curencySymbol) {
        this.curencySymbol = curencySymbol;
    }

    public String getAppendCurrency() {
        return appendCurrency;
    }

    public void setAppendCurrency(String appendCurrency) {
        this.appendCurrency = appendCurrency;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Object getDestination() {
        return destination;
    }

    public void setDestination(Object destination) {
        this.destination = destination;
    }

    public String getLastActivityOn() {
        return lastActivityOn;
    }

    public void setLastActivityOn(String lastActivityOn) {
        this.lastActivityOn = lastActivityOn;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getNumberOfGuest() {
        return numberOfGuest;
    }

    public void setNumberOfGuest(String numberOfGuest) {
        this.numberOfGuest = numberOfGuest;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTicket() {
        return ticket;
    }

    public void setTicket(Integer ticket) {
        this.ticket = ticket;
    }

}
