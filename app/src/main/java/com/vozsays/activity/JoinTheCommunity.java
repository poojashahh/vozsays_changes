package com.vozsays.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.vozsays.R;
import com.vozsays.databinding.ActivityJoinTheCommunityBinding;
import com.vozsays.model.LoginDataModel;
import com.vozsays.model.Message;
import com.vozsays.retrofit.AlertPermissionDialog;
import com.vozsays.utility.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinTheCommunity extends BaseFragmentActivity {

    private AlertPermissionDialog alerpermissionDialog;
    private ActivityJoinTheCommunityBinding mBinding;
    CallbackManager callbackManager;
    private Context context;
    String vUserName, vUDID, vFacebookId, eDeviceType, profilImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_join_the_community);
        context = this;
        initUI();
        printHashKey(this);
        mBinding.joinCommunityRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validator.hasConnection(JoinTheCommunity.this)) {
                    mBinding.loginBtn.performClick();
                } else {
                    Snackbar snackbar = Snackbar.make(mBinding.joinCommunityRl
                            , getResources().getString(R.string.connection_fail_message), Snackbar.LENGTH_LONG)
                            .setAction("Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            });
                    snackbar.show();
                }
            }
        });

        mBinding.loginBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                RequestData();
            }

            @Override
            public void onCancel() {
             Snackbar snackbar = Snackbar.make(mBinding.joinCommunityRl
                        ,getResources().getString(R.string.login_fail_alert), Snackbar.LENGTH_LONG)
                        .setAction("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });
                snackbar.show();

            }
            @Override
            public void onError(FacebookException exception) {
                Snackbar snackbar = Snackbar.make(mBinding.joinCommunityRl
                        , getResources().getString(R.string.login_fail_alert), Snackbar.LENGTH_LONG)
                        .setAction("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });
                snackbar.show();
            }
        });
    }

    public void RequestData() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                JSONObject json = response.getJSONObject();
                getProfileData(json);
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,gender");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        super.onActivityResult(requestCode, responseCode, data);
        callbackManager.onActivityResult(requestCode, responseCode, data);
    }

    private void initUI() {
        callbackManager = CallbackManager.Factory.create();
        alerpermissionDialog = new AlertPermissionDialog(this);
        mBinding.loginBtn.setReadPermissions("public_profile email");
    }

    public void getProfileData(JSONObject json) {
        try {
            if (json != null) {
                eDeviceType = "Android";
                vFacebookId = getResources().getString(R.string.facebook_app_id);
                vUserName = json.getString("email");
                vUDID = json.getString("id");
                webServiceCall(vFacebookId, vUserName, vUDID, eDeviceType);
            } else {

                Snackbar snackbar = Snackbar.make(mBinding.joinCommunityRl
                        , getResources().getString(R.string.login_fail_alert), Snackbar.LENGTH_LONG)
                        .setAction("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });
                snackbar.show();

                Intent in = new Intent(getApplicationContext(), JoinTheCommunity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void webServiceCall(String vFacebookId, String vUserName, String vUDID, String eDeviceType) {
        if (validator.hasConnection(JoinTheCommunity.this)) {
            showProgressDialog(this);
            apiTask.loginUser(vFacebookId, vUserName, vUDID, eDeviceType, callback);
        }
    }

    Callback<LoginDataModel> callback = new Callback<LoginDataModel>() {
        @Override
        public void onResponse(Call<LoginDataModel> call, Response<LoginDataModel> response) {
            dismissProgressDialog();

            if (response != null && response.body() != null && response.body().getStatus() != null && response.body().getStatus().equals("200")) {
                if (response.body().getData() != null && response.body().getData().getvAuthToken() != null)
                    mPrefs.setAccessToekn(response.body().getData().getvAuthToken());
                System.out.println("join_community" + response.body().getData().getvAuthToken());
                mPrefs.setLogin(true);
                Intent intent = new Intent(getApplicationContext(), LocalIssuesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else onFailure(null, null);
        }

        @Override
        public void onFailure(Call<LoginDataModel> call, Throwable t) {
            dismissProgressDialog();
        }
    };

    public static void printHashKey(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String keyHash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.d("Tag", "keyHash: " + keyHash);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}


