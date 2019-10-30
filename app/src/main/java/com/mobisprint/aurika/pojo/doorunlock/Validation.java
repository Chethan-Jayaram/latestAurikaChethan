
package com.mobisprint.aurika.pojo.doorunlock;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Validation {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("status")
    @Expose
    private String status;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
