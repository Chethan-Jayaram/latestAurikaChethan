
package com.mobisprint.aurika.coorg.pojo.guestfoilos;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RevenueDetails {

    @SerializedName("RevenueDetail")
    @Expose
    private List<RevenueDetail> revenueDetail = null;

    public List<RevenueDetail> getRevenueDetail() {
        return revenueDetail;
    }

    public void setRevenueDetail(List<RevenueDetail> revenueDetail) {
        this.revenueDetail = revenueDetail;
    }

}
