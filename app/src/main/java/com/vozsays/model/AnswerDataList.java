package com.vozsays.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yudizsolutionspvt.ltd. on 18/09/17.
 */

public class AnswerDataList {


    @SerializedName("iQuestionId")
    String iQuestionId;
    @SerializedName("vQuestion")
    String vQuestion;
    @SerializedName("vType")
    String vType;
    @SerializedName("vImage")
    String vImage;
    @SerializedName("iTotalVote")
    String iTotalVote;
    @SerializedName("options")
    List<QuestionOption> options;

    public String getiQuestionId() {
        return iQuestionId;
    }

    public void setiQuestionId(String iQuestionId) {
        this.iQuestionId = iQuestionId;
    }

    public String getvQuestion() {
        return vQuestion;
    }

    public void setvQuestion(String vQuestion) {
        this.vQuestion = vQuestion;
    }

    public String getvType() {
        return vType;
    }

    public void setvType(String vType) {
        this.vType = vType;
    }

    public String getvImage() {
        return vImage;
    }

    public void setvImage(String vImage) {
        this.vImage = vImage;
    }

    public String getiTotalVote() {
        return iTotalVote;
    }

    public void setiTotalVote(String iTotalVote) {
        this.iTotalVote = iTotalVote;
    }

    public List<QuestionOption> getOptions() {
        return options;
    }

    public void setOptions(List<QuestionOption> options) {
        this.options = options;
    }
}
