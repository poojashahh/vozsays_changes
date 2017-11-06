package com.vozsays.retrofit.call;

import com.vozsays.model.CategoryDataModel;
import com.vozsays.model.LoginDataModel;
import com.vozsays.model.QuestionDataModel;
import com.vozsays.model.QuestionPostDataModel;
import com.vozsays.model.UpdateCategoryModel;
import com.vozsays.retrofit.WebAPI;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiCall {

    @POST(WebAPI.Fb_LOGIN)
    @FormUrlEncoded
    Call<LoginDataModel> getLoginData(@Field("vFacebookId") String vFacebookId, @Field("vUserName") String vUserName, @Field("vUDID") String vUDID, @Field("eDeviceType") String eDeviceType);

    @GET(WebAPI.CATEGORIES)
    Call<CategoryDataModel> getCategoryData(@Header("Vauthtoken") String Vauthtoken);

    @POST(WebAPI.UPDATE_CATEGORIES)
    @FormUrlEncoded
    Call<UpdateCategoryModel> updateCategoryData(@Header("Vauthtoken") String Vauthtoken, @Field("vPostalCode") String vPostalCode);

    @POST(WebAPI.QUESTIONS)
    @FormUrlEncoded
    Call<QuestionDataModel> getQuestionData(@Header("Vauthtoken") String Vauthtoken,@Field("iCatId") String iCatId);



    @POST(WebAPI.POST_QUESTIONS)
    @FormUrlEncoded
    Call<QuestionPostDataModel> postQuestion(@Header("Vauthtoken") String Vauthtoken, @Field("iPollId") String iPollId, @Field("iOptionId") String iOptionId);
  /*  @FormUrlEncoded
    @POST(WebAPI.LOGIN)
    Call<SignUpDataModel> loginUser(@Field("vEmail") String vEmail,
                                    @Field("vPassword") String vPassword,
                                    @Field("vPushToken") String vPushToken);
*/


  /*  @Multipart
    @POST(WebAPI.EDIT_PROFILE)
    Call<SignUpDataModel> editProfile(
          ]  @Part("vFullName") RequestBody vFullName,
            @Part("vContactNumber") RequestBody vContactNumber,
            @Part("vAddress") RequestBody vAddress,
            @Part MultipartBody.Part vMedia0
    );*/


}
