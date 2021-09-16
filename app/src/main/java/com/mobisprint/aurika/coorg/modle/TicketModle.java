package com.mobisprint.aurika.coorg.modle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.coorg.pojo.ticketing.Detail;

import java.util.List;

public class TicketModle {

    private String personalisedTime;

    public String getPersonalisedTime() {
        return personalisedTime;
    }

    public void setPersonalisedTime(String personalisedTime) {
        this.personalisedTime = personalisedTime;
    }

    @SerializedName("department")
    @Expose
    private String department;

    @SerializedName("special_instructions")
    @Expose
    private String special_instructions;

    public String getSpecial_instructions() {
        return special_instructions;
    }

    public void setSpecial_instructions(String special_instructions) {
        this.special_instructions = special_instructions;
    }

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("booking")
    @Expose
    private String booking;
    @SerializedName("hangertype")
    @Expose
    private String hangertype;
    @SerializedName("RequestDate")
    @Expose
    private String requestDate;
    @SerializedName("RequestHours")
    @Expose
    private String requestHours;
    @SerializedName("RequestTime")
    @Expose
    private String requestTime;
    @SerializedName("details")
    @Expose
    private List<Detail> details = null;
    @SerializedName("room_number")
    @Expose
    private String roomNumber;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }

    public String getHangertype() {
        return hangertype;
    }

    public void setHangertype(String hangertype) {
        this.hangertype = hangertype;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestHours() {
        return requestHours;
    }

    public void setRequestHours(String requestHours) {
        this.requestHours = requestHours;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
