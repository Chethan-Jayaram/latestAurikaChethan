
package com.mobisprint.aurika.coorg.pojo.guestfoilos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FolioIDs {

    @SerializedName("@xmlns")
    @Expose
    private String xmlns;
    @SerializedName("FolioID")
    @Expose
    private String folioID;

    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    public String getFolioID() {
        return folioID;
    }

    public void setFolioID(String folioID) {
        this.folioID = folioID;
    }

}
