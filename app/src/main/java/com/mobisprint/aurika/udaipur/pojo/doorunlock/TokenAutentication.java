
package com.mobisprint.aurika.udaipur.pojo.doorunlock;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobisprint.aurika.udaipur.pojo.GeneralPojo;

public class TokenAutentication extends GeneralPojo {

    @SerializedName("result")
    @Expose
    private Validation result;

    @SerializedName("errorCode")
    @Expose
    private String errorCode;




    public Validation getResult() {
        return result;
    }

    public void setResult(Validation result) {
        this.result = result;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }


}
