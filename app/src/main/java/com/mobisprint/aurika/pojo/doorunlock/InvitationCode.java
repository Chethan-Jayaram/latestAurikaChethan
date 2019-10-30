
package com.mobisprint.aurika.pojo.doorunlock;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvitationCode {

    @SerializedName("endpointId")
    @Expose
    private String endpointId;
    @SerializedName("invitationCode")
    @Expose
    private String invitationCode;

    public String getEndpointId() {
        return endpointId;
    }

    public void setEndpointId(String endpointId) {
        this.endpointId = endpointId;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

}
