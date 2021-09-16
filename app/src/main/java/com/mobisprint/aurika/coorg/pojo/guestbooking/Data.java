
package com.mobisprint.aurika.coorg.pojo.guestbooking;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("activeBooking")
    @Expose
    private List<ActiveBooking> activeBooking = null;
    @SerializedName("upcomingBookingSerializer")
    @Expose
    private List<ActiveBooking> upcomingBookingSerializer = null;
    @SerializedName("BookingHistory")
    @Expose
    private List<ActiveBooking> bookingHistory = null;

    public List<ActiveBooking> getActiveBooking() {
        return activeBooking;
    }

    public void setActiveBooking(List<ActiveBooking> activeBooking) {
        this.activeBooking = activeBooking;
    }

    public List<ActiveBooking> getUpcomingBookingSerializer() {
        return upcomingBookingSerializer;
    }

    public void setUpcomingBookingSerializer(List<ActiveBooking> upcomingBookingSerializer) {
        this.upcomingBookingSerializer = upcomingBookingSerializer;
    }

    public List<ActiveBooking> getBookingHistory() {
        return bookingHistory;
    }

    public void setBookingHistory(List<ActiveBooking> bookingHistory) {
        this.bookingHistory = bookingHistory;
    }
}
