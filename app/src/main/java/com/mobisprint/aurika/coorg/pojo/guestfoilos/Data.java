
package com.mobisprint.aurika.coorg.pojo.guestfoilos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("reservationId")
    @Expose
    private String reservationId;
    @SerializedName("GrossAmount")
    @Expose
    private String grossAmount;
    @SerializedName("BalanceDueAmount")
    @Expose
    private String balanceDueAmount;
   /* @SerializedName("RevenueDetails")
    @Expose
    private RevenueDetails revenueDetails;*/
    @SerializedName("totalTax")
    @Expose
    private String totalTax;
    @SerializedName("AmountReceived")
    @Expose
    private String amountReceived;
    @SerializedName("no_of_nights")
    @Expose
    private Object noOfNights;
    @SerializedName("FolioID")
    @Expose
    private String folioID;
    @SerializedName("activity_on")
    @Expose
    private String activityOn;
    @SerializedName("booking")
    @Expose
    private Object booking;
    @SerializedName("activity_by")
    @Expose
    private Object activityBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(String grossAmount) {
        this.grossAmount = grossAmount;
    }

    public String getBalanceDueAmount() {
        return balanceDueAmount;
    }

    public void setBalanceDueAmount(String balanceDueAmount) {
        this.balanceDueAmount = balanceDueAmount;
    }

   /* public RevenueDetails getRevenueDetails() {
        return revenueDetails;
    }

    public void setRevenueDetails(RevenueDetails revenueDetails) {
        this.revenueDetails = revenueDetails;
    }*/

    public String getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(String totalTax) {
        this.totalTax = totalTax;
    }

    public String getAmountReceived() {
        return amountReceived;
    }

    public void setAmountReceived(String amountReceived) {
        this.amountReceived = amountReceived;
    }

    public Object getNoOfNights() {
        return noOfNights;
    }

    public void setNoOfNights(Object noOfNights) {
        this.noOfNights = noOfNights;
    }

    public String getFolioID() {
        return folioID;
    }

    public void setFolioID(String folioID) {
        this.folioID = folioID;
    }

    public String getActivityOn() {
        return activityOn;
    }

    public void setActivityOn(String activityOn) {
        this.activityOn = activityOn;
    }

    public Object getBooking() {
        return booking;
    }

    public void setBooking(Object booking) {
        this.booking = booking;
    }

    public Object getActivityBy() {
        return activityBy;
    }

    public void setActivityBy(Object activityBy) {
        this.activityBy = activityBy;
    }

}
