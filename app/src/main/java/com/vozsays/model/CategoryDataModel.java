package com.vozsays.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yudizsolutionspvt.ltd. on 13/09/17.
 */

public class CategoryDataModel {
    @SerializedName("status")
    String status;
    @SerializedName("message")
    Message message;
    @SerializedName("data")
    List<CategoryDataList> categoryDataList;

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

    public List<CategoryDataList> getCategoryDataList() {
        return categoryDataList;
    }

    public void setCategoryDataList(List<CategoryDataList> categoryDataList) {
        this.categoryDataList = categoryDataList;
    }
}
