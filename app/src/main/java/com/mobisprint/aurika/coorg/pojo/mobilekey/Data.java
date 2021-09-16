
package com.mobisprint.aurika.coorg.pojo.mobilekey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("token")
    @Expose
    private String token;
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
