package com.vozsays.model;

import com.google.gson.annotations.SerializedName;
import com.vozsays.model.LoginDataList;
import com.vozsays.model.Message;

/**
 * Created by yudizsolutionspvt.ltd. on 13/09/17.
 */

public class LoginDataModel {
    @SerializedName("status")
    String status;
    @SerializedName("message")
    Message message;
    @SerializedName("data")
    LoginDataList data;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public LoginDataList getData() {
        return data;
    }

    public void setData(LoginDataList data) {
        this.data = data;
    }
}
