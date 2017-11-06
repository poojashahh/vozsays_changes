package com.vozsays.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yudizsolutionspvt.ltd. on 18/09/17.
 */

public class QuestionDataList implements Parcelable {

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
    @SerializedName("vCategory")
    String vCategory;
    @SerializedName("iPollId")
    String iPollId;
    @SerializedName("options")
   List<QuestionOption> options;

    protected QuestionDataList(Parcel in) {
        iQuestionId = in.readString();
        vQuestion = in.readString();
        vType = in.readString();
        vImage = in.readString();
        iTotalVote = in.readString();
        vCategory = in.readString();
        iPollId = in.readString();
    }

    public static final Creator<QuestionDataList> CREATOR = new Creator<QuestionDataList>() {
        @Override
        public QuestionDataList createFromParcel(Parcel in) {
            return new QuestionDataList(in);
        }

        @Override
        public QuestionDataList[] newArray(int size) {
            return new QuestionDataList[size];
        }
    };

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

    public String getiPollId() {
        return iPollId;
    }

    public void setiPollId(String iPollId) {
        this.iPollId = iPollId;
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

    public String getvCategory() {
        return vCategory;
    }

    public void setvCategory(String vCategory) {
        this.vCategory = vCategory;
    }

    public List<QuestionOption> getOptions() {
        return options;
    }

    public void setOptions(List<QuestionOption> options) {
        this.options = options;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(iQuestionId);
        dest.writeString(vQuestion);
        dest.writeString(vType);
        dest.writeString(vImage);
        dest.writeString(iTotalVote);
        dest.writeString(vCategory);
        dest.writeString(iPollId);
    }
}
