package com.vozsays;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.view.Window;
import android.widget.Toast;

import com.vozsays.retrofit.WebAPI;
import com.vozsays.retrofit.task.ApiTask;
import com.vozsays.utility.MyPrefs;
import com.vozsays.utility.Validator;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class
        VozsaysApp extends MultiDexApplication {

    private static Retrofit retrofit;
    private static VozsaysApp vozsaysApp;
    private MyPrefs mPrefs;
    private ApiTask apiTask;
    private Dialog dialog;
    private Validator validator;

    @Override
    public void onCreate() {
        super.onCreate();
        vozsaysApp = this;
        MultiDex.install(this);
        mPrefs = MyPrefs.getInstance(VozsaysApp.this);
        WebAPI.HEADER_VALUE = MyPrefs.getInstance(this).getAccessToekn();
        apiTask = new ApiTask();
         dialog = new Dialog(VozsaysApp.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loader);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
    }

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder builder = original.newBuilder();
                    if (WebAPI.HEADER_VALUE != null && !WebAPI.HEADER_VALUE.equals(""))
                        builder.header(WebAPI.HEADER_KEY, WebAPI.HEADER_VALUE);
                    builder.method(original.method(), original.body());

                    return chain.proceed(builder.build());
                }
            });
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(interceptor);
            OkHttpClient client = httpClient.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(WebAPI.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static void resetRetrofitInstance() {
        retrofit = null;
    }

    public MyPrefs getPrefs() {
        return mPrefs;
    }
    public Validator getValidator(){
        return validator;
    }

    public ApiTask getApiTask() {
        return apiTask;
    }

    public void toast(String message) {
        Toast.makeText(VozsaysApp.this, message, Toast.LENGTH_SHORT).show();
    }

    public void showProgressDialog(Context context) {

        if (dialog != null && dialog.isShowing())
            try {
                dialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        try {
            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.loader);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void dismissProgressDialog() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }
    public static VozsaysApp getInstance() {
        return vozsaysApp;
    }
}
