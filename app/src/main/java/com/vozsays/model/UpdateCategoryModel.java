package com.vozsays.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yudizsolutionspvt.ltd. on 15/09/17.
 */

public class   UpdateCategoryModel {
    @SerializedName("status")
    String status;
    @SerializedName("message")
    Message message;
    @SerializedName("data")
    UpdatedList updatedList;

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

    public UpdatedList getUpdatedList() {
        return updatedList;
    }

    public void setUpdatedList(UpdatedList updatedList) {
        this.updatedList = updatedList;
    }
}
