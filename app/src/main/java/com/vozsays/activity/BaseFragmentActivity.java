    package com.vozsays.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.vozsays.VozsaysApp;
import com.vozsays.retrofit.task.ApiTask;
import com.vozsays.utility.MyPrefs;
import com.vozsays.utility.Validator;
/**
 * Created by yudizsolutions on 11/04/16.
 */
public class BaseFragmentActivity extends AppCompatActivity {
    public static boolean isRunning;
    private VozsaysApp app;
    public MyPrefs mPrefs;
    public ApiTask apiTask;
    public Validator validator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (VozsaysApp) getApplicationContext();
        mPrefs = app.getPrefs();
        apiTask = app.getApiTask();
        validator=app.getValidator();
    }
    public void showProgressDialog(Context context) {
        app.showProgressDialog(context);
    }

    public void dismissProgressDialog() {
        app.dismissProgressDialog();
    }

    public void toast(String message) {
        app.toast(message);
    }

    public static final String LOGIN_USER = "login";

    @Override
    protected void onPause() {
        super.onPause();
        isRunning = false;
    }
}
