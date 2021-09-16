
package com.mobisprint.aurika.coorg.pojo.guestfoilos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Taxes__1 {

    @SerializedName("@xmlns")
    @Expose
    private String xmlns;
    @SerializedName("Tax")
    @Expose
    private Tax__1 tax;

    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    public Tax__1 getTax() {
        return tax;
    }

    public void setTax(Tax__1 tax) {
        this.tax = tax;
    }

}
