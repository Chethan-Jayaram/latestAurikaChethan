
package com.mobisprint.aurika.pojo.notification;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobisprint.aurika.pojo.GeneralPojo;

public class PushNotificationResponse  extends GeneralPojo {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

}
