
package com.mobisprint.aurika.coorg.pojo.Services;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServicesList {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("route_name")
    @Expose
    private RouteName routeName;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("location_id")
    @Expose
    private Integer locationId;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("last_activity_on")
    @Expose
    private String lastActivityOn;
    @SerializedName("services_id")
    @Expose
    private Integer servicesId;
    @SerializedName("last_activity_by")
    @Expose
    private Integer lastActivityBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RouteName getRouteName() {
        return routeName;
    }

    public void setRouteName(RouteName routeName) {
        this.routeName = routeName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLastActivityOn() {
        return lastActivityOn;
    }

    public void setLastActivityOn(String lastActivityOn) {
        this.lastActivityOn = lastActivityOn;
    }

    public Integer getServicesId() {
        return servicesId;
    }

    public void setServicesId(Integer servicesId) {
        this.servicesId = servicesId;
    }

    public Integer getLastActivityBy() {
        return lastActivityBy;
    }

    public void setLastActivityBy(Integer lastActivityBy) {
        this.lastActivityBy = lastActivityBy;
    }

}
