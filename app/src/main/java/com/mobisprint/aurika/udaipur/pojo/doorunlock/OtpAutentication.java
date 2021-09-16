
package com.mobisprint.aurika.udaipur.pojo.doorunlock;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobisprint.aurika.udaipur.pojo.GeneralPojo;

public class OtpAutentication extends GeneralPojo {




    @SerializedName("result")
    @Expose
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

}
