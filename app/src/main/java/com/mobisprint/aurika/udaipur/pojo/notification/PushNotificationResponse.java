
package com.mobisprint.aurika.udaipur.pojo.notification;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobisprint.aurika.udaipur.pojo.GeneralPojo;

public class PushNotificationResponse  extends GeneralPojo {


    @SerializedName("result")
    @Expose
    private List<Result> result = null;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

}
