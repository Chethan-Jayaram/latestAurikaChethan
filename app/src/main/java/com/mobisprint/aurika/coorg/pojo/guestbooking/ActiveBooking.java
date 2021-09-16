
package com.mobisprint.aurika.coorg.pojo.guestbooking;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActiveBooking {

    public String getBalanaceAmount() {
        return balanaceAmount;
    }

    public void setBalanaceAmount(String balanaceAmount) {
        this.balanaceAmount = balanaceAmount;
    }

    @SerializedName("balance_amount")
    @Expose
    private String balanaceAmount;


    @SerializedName("grossAmount")
    @Expose
    private String grossAmount;

    public String getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(String grossAmount) {
        this.grossAmount = grossAmount;
    }

    public String getGuestFolioId() {
        return guestFolioId;
    }

    public void setGuestFolioId(String guestFolioId) {
        this.guestFolioId = guestFolioId;
    }

    @SerializedName("guestFolioId")
    @Expose
    private String guestFolioId;


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("booking_number")
    @Expose
    private String bookingNumber;
    @SerializedName("guest_count")
    @Expose
    private Object guestCount;
    @SerializedName("checkin_date_time")
    @Expose
    private String checkinDateTime;
    @SerializedName("checkout_date_time")
    @Expose
    private String checkoutDateTime;
    @SerializedName("guest_document")
    @Expose
    private String guestDocument;
    @SerializedName("guest_document_verification_status")
    @Expose
    private String guestDocumentVerificationStatus;
    @SerializedName("last_activity_on")
    @Expose
    private String lastActivityOn;
    @SerializedName("location_id")
    @Expose
    private Integer locationId;
    @SerializedName("booking_status")
    @Expose
    private Boolean bookingStatus;
    @SerializedName("guest")
    @Expose
    private Guest guest;
    @SerializedName("last_activity_by")
    @Expose
    private Integer lastActivityBy;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("room")
    @Expose
    private List<Room> room = null;
    @SerializedName("reservation_status")
    @Expose
    private String reservationStatus;
    @SerializedName("reservationId")
    @Expose
    private Object reservationId;
    @SerializedName("booking_tickets")
    @Expose
    private List<BookingTicket> bookingTickets = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public Object getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(Object guestCount) {
        this.guestCount = guestCount;
    }

    public String getCheckinDateTime() {
        return checkinDateTime;
    }

    public void setCheckinDateTime(String checkinDateTime) {
        this.checkinDateTime = checkinDateTime;
    }

    public String getCheckoutDateTime() {
        return checkoutDateTime;
    }

    public void setCheckoutDateTime(String checkoutDateTime) {
        this.checkoutDateTime = checkoutDateTime;
    }

    public String getGuestDocument() {
        return guestDocument;
    }

    public void setGuestDocument(String guestDocument) {
        this.guestDocument = guestDocument;
    }

    public String getGuestDocumentVerificationStatus() {
        return guestDocumentVerificationStatus;
    }

    public void setGuestDocumentVerificationStatus(String guestDocumentVerificationStatus) {
        this.guestDocumentVerificationStatus = guestDocumentVerificationStatus;
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

    public Boolean getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(Boolean bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Integer getLastActivityBy() {
        return lastActivityBy;
    }

    public void setLastActivityBy(Integer lastActivityBy) {
        this.lastActivityBy = lastActivityBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Room> getRoom() {
        return room;
    }

    public void setRoom(List<Room> room) {
        this.room = room;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public Object getReservationId() {
        return reservationId;
    }

    public void setReservationId(Object reservationId) {
        this.reservationId = reservationId;
    }

    public List<BookingTicket> getBookingTickets() {
        return bookingTickets;
    }

    public void setBookingTickets(List<BookingTicket> bookingTickets) {
        this.bookingTickets = bookingTickets;
    }

}
