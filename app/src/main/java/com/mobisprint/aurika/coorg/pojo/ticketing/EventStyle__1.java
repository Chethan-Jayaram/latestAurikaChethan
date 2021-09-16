
package com.mobisprint.aurika.coorg.pojo.ticketing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventStyle__1 {

    @SerializedName("ticketStatusPills")
    @Expose
    private TicketStatusPills__1 ticketStatusPills;
    @SerializedName("actionStyles")
    @Expose
    private ActionStyles__1 actionStyles;

    public TicketStatusPills__1 getTicketStatusPills() {
        return ticketStatusPills;
    }

    public void setTicketStatusPills(TicketStatusPills__1 ticketStatusPills) {
        this.ticketStatusPills = ticketStatusPills;
    }

    public ActionStyles__1 getActionStyles() {
        return actionStyles;
    }

    public void setActionStyles(ActionStyles__1 actionStyles) {
        this.actionStyles = actionStyles;
    }

}
