package com.vozsays.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yudizsolutionspvt.ltd. on 18/09/17.
 */

public class QuestionOption {

    @SerializedName("iOptionId")
    String iOptionId;
    @SerializedName("vOption")
    String vOption;
    @SerializedName("vProfilePicture")
    String vProfilePicture;
    @SerializedName("vImage")
    String vImage;
    @SerializedName("iPoll")
    String iPoll;
    @SerializedName("perc")
    String perc;

    public String getiOptionId() {
        return iOptionId;
    }

    public void setiOptionId(String iOptionId) {
        this.iOptionId = iOptionId;
    }

    public String getvOption() {
        return vOption;
    }

    public void setvOption(String vOption) {
        this.vOption = vOption;
    }

    public String getvProfilePicture() {
        return vProfilePicture;
    }

    public void setvProfilePicture(String vProfilePicture) {
        this.vProfilePicture = vProfilePicture;
    }

    public String getiPoll() {
        return iPoll;
    }

    public void setiPoll(String iPoll) {
        this.iPoll = iPoll;
    }

    public String getPerc() {
        return perc;
    }

    public void setPerc(String perc) {
        this.perc = perc;
    }

    public String getvImage() {
        return vImage;
    }

    public void setvImage(String vImage) {
        this.vImage = vImage;
    }
}