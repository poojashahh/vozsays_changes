package com.vozsays.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yudizsolutionspvt.ltd. on 12/09/17.
 */

public class Message {

    @SerializedName("success")
    String success;
    @SerializedName("error")
    String error;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
