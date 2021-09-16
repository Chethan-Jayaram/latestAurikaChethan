
package com.mobisprint.aurika.coorg.pojo.guestfoilos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Taxes {

    @SerializedName("@xmlns")
    @Expose
    private String xmlns;
    @SerializedName("Tax")
    @Expose
    private Tax tax;

    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

}
