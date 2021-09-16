
package com.mobisprint.aurika.coorg.pojo.reservation;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("active_bookings")
    @Expose
    private List<ActiveBooking> activeBookings = null;

    public List<ActiveBooking> getActiveBookings() {
        return activeBookings;
    }

    public void setActiveBookings(List<ActiveBooking> activeBookings) {
        this.activeBookings = activeBookings;
    }

}
