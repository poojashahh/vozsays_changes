package com.vozsays.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.vozsays.retrofit.WebAPI;


/**
 * Created by yudizsolutions on 11/04/16.
 */
public class MyPrefs {

    SharedPreferences myPrefs;
    SharedPreferences.Editor prefEditor;
    Context context;
    private static MyPrefs mPrefs;
    public static MyPrefs getInstance(Context context) {
        if (mPrefs == null)
            mPrefs = new MyPrefs(context);
        return mPrefs;
    }
    // ------------------------------------------------------------------costructor
    private MyPrefs(Context context) {
        this.context = context;
        myPrefs = context.getSharedPreferences("Vozsays", 0);
        prefEditor = myPrefs.edit();
    }

    public void setAccessToekn(String accessToken) {
        WebAPI.HEADER_VALUE = "Bearer " + accessToken;
        prefEditor.putString("accessToken", "Bearer " + accessToken);
        prefEditor.commit();
    }
    public void setInterest(Boolean isInterest){
        prefEditor.putBoolean("isInterest",isInterest);
        prefEditor.commit();
    }




    public String getAccessToekn() {
        return myPrefs.getString("accessToken", "");
    }

    public void setLogin(boolean isLogin) {
        prefEditor.putBoolean("isLogin", isLogin);
        prefEditor.commit();
    }

    public boolean isLogin() {
        return myPrefs.getBoolean("isLogin", false);
    }


    public boolean isInterest(){
        return myPrefs.getBoolean("isInterest",false);
    }

}
