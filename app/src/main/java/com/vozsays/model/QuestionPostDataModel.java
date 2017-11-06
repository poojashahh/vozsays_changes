package com.vozsays.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yudizsolutionspvt.ltd. on 18/09/17.
 */

public class QuestionPostDataModel {

    @SerializedName("status")
    String status;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    AnswerDataList data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getMessage() {
        return message;
    }

    public AnswerDataList getData() {
        return data;
    }

    public void setData(AnswerDataList data) {
        this.data = data;
    }
}
