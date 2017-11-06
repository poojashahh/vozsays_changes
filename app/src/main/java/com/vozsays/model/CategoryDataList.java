package com.vozsays.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yudizsolutionspvt.ltd. on 13/09/17.
 */

public class CategoryDataList {

    @SerializedName("iCatId")
    String iCatId;
    @SerializedName("vCategory")
    String vCategory;
    @SerializedName("vImage")
    String vImage;

    public String getiCatId() {
        return iCatId;
    }

    public void setiCatId(String iCatId) {
        this.iCatId = iCatId;
    }

    public String getvCategory() {
        return vCategory;
    }

    public void setvCategory(String vCategory) {
        this.vCategory = vCategory;
    }

    public String getvImage() {
        return vImage;
    }

    public void setvImage(String vImage) {
        this.vImage = vImage;
    }
}
