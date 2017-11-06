package com.vozsays.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yudizsolutionspvt.ltd. on 12/09/17.
 */
public class LoginDataList {

    @SerializedName("iUserId")
    String iUserId;
    @SerializedName("vUserName")
    String vUserName;
    @SerializedName("vEmail")
    String vEmail;
    @SerializedName("vFacebookId")
    String vFacebookId;
    @SerializedName("vProfilePicture")
    String vProfilePicture;
    @SerializedName("eStatus")
    String eStatus;
    @SerializedName("vCategories")
    String vCategories;
    @SerializedName("vPostalCode")
    String vPostalCode;
    @SerializedName("vAuthToken")
    String vAuthToken;



    public String getiUserId() {
        return iUserId;
    }

    public void setiUserId(String iUserId) {
        this.iUserId = iUserId;
    }

    public String getvUserName() {
        return vUserName;
    }

    public void setvUserName(String vUserName) {
        this.vUserName = vUserName;
    }

    public String getvEmail() {
        return vEmail;
    }

    public void setvEmail(String vEmail) {
        this.vEmail = vEmail;
    }

    public String getvFacebookId() {
        return vFacebookId;
    }

    public void setvFacebookId(String vFacebookId) {
        this.vFacebookId = vFacebookId;
    }

    public String getvProfilePicture() {
        return vProfilePicture;
    }

    public void setvProfilePicture(String vProfilePicture) {
        this.vProfilePicture = vProfilePicture;
    }

    public String geteStatus() {
        return eStatus;
    }

    public void seteStatus(String eStatus) {
        this.eStatus = eStatus;
    }

    public String getvCategories() {
        return vCategories;
    }

    public void setvCategories(String vCategories) {
        this.vCategories = vCategories;
    }

    public String getvPostalCode() {
        return vPostalCode;
    }

    public void setvPostalCode(String vPostalCode) {
        this.vPostalCode = vPostalCode;
    }

    public String getvAuthToken() {
        return vAuthToken;
    }

    public void setvAuthToken(String vAuthToken) {
        this.vAuthToken = vAuthToken;
    }
}



