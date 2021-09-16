
package com.mobisprint.aurika.coorg.pojo.guestfoilos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UnitPrice {

    @SerializedName("@AmountBeforeTax")
    @Expose
    private String amountBeforeTax;
    @SerializedName("@AmountAfterTax")
    @Expose
    private String amountAfterTax;
    @SerializedName("@CurrencyCode")
    @Expose
    private String currencyCode;
    @SerializedName("@Type")
    @Expose
    private String type;
    @SerializedName("Taxes")
    @Expose
    private Taxes taxes;

    public String getAmountBeforeTax() {
        return amountBeforeTax;
    }

    public void setAmountBeforeTax(String amountBeforeTax) {
        this.amountBeforeTax = amountBeforeTax;
    }

    public String getAmountAfterTax() {
        return amountAfterTax;
    }

    public void setAmountAfterTax(String amountAfterTax) {
        this.amountAfterTax = amountAfterTax;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Taxes getTaxes() {
        return taxes;
    }

    public void setTaxes(Taxes taxes) {
        this.taxes = taxes;
    }

}
