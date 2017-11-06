package com.vozsays.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yudizsolutionspvt.ltd. on 18/09/17.
 */

public class QuestionDataModel {
    @SerializedName("status")
    String status;
    @SerializedName("message")
    Message message;
    @SerializedName("data")
    List<QuestionDataList> data;

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

    public List<QuestionDataList> getData() {
        return data;
    }

    public void setData(List<QuestionDataList> data) {
        this.data = data;
    }
}
