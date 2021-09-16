
package com.mobisprint.aurika.coorg.pojo.guestfoilos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RevenueDetail {

    @SerializedName("@ReferenceID")
    @Expose
    private String referenceID;
    @SerializedName("@TransactionDate")
    @Expose
    private String transactionDate;
    @SerializedName("@Description")
    @Expose
    private String description;
    @SerializedName("@PMSRevenueCode")
    @Expose
    private String pMSRevenueCode;
    @SerializedName("@CurrencyCode")
    @Expose
    private String currencyCode;
    @SerializedName("@Amount")
    @Expose
    private String amount;
    @SerializedName("@SubTypeID")
    @Expose
    private String subTypeID;
    @SerializedName("@RevenueCategoryCode")
    @Expose
    private String revenueCategoryCode;
    @SerializedName("FolioIDs")
    @Expose
    private FolioIDs folioIDs;
    @SerializedName("UnitPrice")
    @Expose
    private UnitPrice unitPrice;
    @SerializedName("ExtendedPrice")
    @Expose
    private ExtendedPrice extendedPrice;

    public String getReferenceID() {
        return referenceID;
    }

    public void setReferenceID(String referenceID) {
        this.referenceID = referenceID;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPMSRevenueCode() {
        return pMSRevenueCode;
    }

    public void setPMSRevenueCode(String pMSRevenueCode) {
        this.pMSRevenueCode = pMSRevenueCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSubTypeID() {
        return subTypeID;
    }

    public void setSubTypeID(String subTypeID) {
        this.subTypeID = subTypeID;
    }

    public String getRevenueCategoryCode() {
        return revenueCategoryCode;
    }

    public void setRevenueCategoryCode(String revenueCategoryCode) {
        this.revenueCategoryCode = revenueCategoryCode;
    }

    public FolioIDs getFolioIDs() {
        return folioIDs;
    }

    public void setFolioIDs(FolioIDs folioIDs) {
        this.folioIDs = folioIDs;
    }

    public UnitPrice getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(UnitPrice unitPrice) {
        this.unitPrice = unitPrice;
    }

    public ExtendedPrice getExtendedPrice() {
        return extendedPrice;
    }

    public void setExtendedPrice(ExtendedPrice extendedPrice) {
        this.extendedPrice = extendedPrice;
    }

}
