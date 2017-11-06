package com.vozsays.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.vozsays.VozsaysApp;
import com.vozsays.retrofit.task.ApiTask;
import com.vozsays.utility.MyPrefs;
import com.vozsays.utility.Validator;


public class BaseFragment extends Fragment {

    private VozsaysApp app;
    public MyPrefs mPrefs;
    public ApiTask apiTask;
    public Validator validator;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (VozsaysApp) getActivity().getApplicationContext();
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

    public static class Log {
        public static void d(int logText) {
            android.util.Log.d("Tag", logText + "");
        }

        public static void d(String logText) {
            android.util.Log.d("Tag", logText);
        }

        public static void d(String tag, String logText) {
            android.util.Log.d(tag, logText);
        }

        public static void e(String logText) {
            android.util.Log.e("Tag", logText);
        }

        public static void i(String logText) {
            android.util.Log.i("Tag", logText);
        }
    }

}
