
package com.mobisprint.aurika.pojo.doorunlock;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("guest")
    @Expose
    private Guest guest;
    @SerializedName("guest_info")
    @Expose
    private GuestInfo guestInfo;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("isGuest")
    @Expose
    private Boolean isGuest;
    @SerializedName("activeBooking")
    @Expose
    private List<ActiveBooking> activeBooking = null;
    @SerializedName("organizationDetails")
    @Expose
    private OrganizationDetails organizationDetails;
    @SerializedName("invitationCode")
    @Expose
    private String invitationCode;
    @SerializedName("endpointId")
    @Expose
    private String endpointId;
    @SerializedName("AutoKey")
    @Expose
    private AutoKey autoKey;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public GuestInfo getGuestInfo() {
        return guestInfo;
    }

    public void setGuestInfo(GuestInfo guestInfo) {
        this.guestInfo = guestInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getIsGuest() {
        return isGuest;
    }

    public void setIsGuest(Boolean isGuest) {
        this.isGuest = isGuest;
    }

    public List<ActiveBooking> getActiveBooking() {
        return activeBooking;
    }

    public void setActiveBooking(List<ActiveBooking> activeBooking) {
        this.activeBooking = activeBooking;
    }

    public OrganizationDetails getOrganizationDetails() {
        return organizationDetails;
    }

    public void setOrganizationDetails(OrganizationDetails organizationDetails) {
        this.organizationDetails = organizationDetails;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public String getEndpointId() {
        return endpointId;
    }

    public void setEndpointId(String endpointId) {
        this.endpointId = endpointId;
    }

    public AutoKey getAutoKey() {
        return autoKey;
    }

    public void setAutoKey(AutoKey autoKey) {
        this.autoKey = autoKey;
    }

}
