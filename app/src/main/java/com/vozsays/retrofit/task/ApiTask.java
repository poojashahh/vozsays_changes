package com.vozsays.retrofit.task;



import com.vozsays.VozsaysApp;
import com.vozsays.model.CategoryDataModel;
import com.vozsays.model.LoginDataModel;
import com.vozsays.model.QuestionDataModel;
import com.vozsays.model.QuestionPostDataModel;
import com.vozsays.model.UpdateCategoryModel;
import com.vozsays.retrofit.call.ApiCall;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class ApiTask {

    private final ApiCall apiCall;

    public ApiTask() {
        Retrofit retrofit = VozsaysApp.getRetrofitInstance();
        apiCall = retrofit.create(ApiCall.class);
//        return apiCall;
    }

    public Call<LoginDataModel> loginUser(String vFacebookId, String vUserName, String vUDID, String eDeviceType, Callback<LoginDataModel> callback) {
        Call<LoginDataModel> callLogin = apiCall.getLoginData(vFacebookId,vUserName,vUDID,eDeviceType);
        callLogin.enqueue(callback);
        return callLogin;
    }
    public Call<CategoryDataModel> categoryData(String Vauthtoken, Callback<CategoryDataModel> callback) {
        Call<CategoryDataModel> categoryData = apiCall.getCategoryData(Vauthtoken);
        categoryData.enqueue(callback);
        return categoryData;
    }

    public Call<UpdateCategoryModel> updateCategoryData(String Vauthtoken,String vPostalCode,String vCategories, Callback<UpdateCategoryModel> callback) {
        Call<UpdateCategoryModel> callLogin = apiCall.updateCategoryData(Vauthtoken,vPostalCode);
        callLogin.enqueue(callback);
        return callLogin;
    }
    public Call<QuestionDataModel> getQuestionData(String Vauthtoken, String iCatId,Callback<QuestionDataModel> callback) {
        Call<QuestionDataModel> callQuestion = apiCall.getQuestionData(Vauthtoken,iCatId);
        callQuestion.enqueue(callback);
        return callQuestion;
    }
    public Call<QuestionPostDataModel> postQuestion(String Vauthtoken, String iPollId, String iOptionId, Callback<QuestionPostDataModel> callback) {
        Call<QuestionPostDataModel> callQuestion = apiCall.postQuestion(Vauthtoken,iPollId,iOptionId);
        callQuestion.enqueue(callback);
        return callQuestion;
    }



    /*public void editProfile(String vFullName, String vContactNumber, String vAddress, MultipartBody.Part parts, Callback<SignUpDataModel> callback) {
        Call<SignUpDataModel> call = apiCall.editProfile(getBody(vFullName), getBody(vContactNumber), getBody(vAddress), parts);
        call.enqueue(callback);
    }

    private RequestBody getBody(String name) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), name);
    }

    public static MultipartBody.Part getFile(String fieldName, File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData(fieldName, file.getName(), requestFile);
        return body;
    }*/

}