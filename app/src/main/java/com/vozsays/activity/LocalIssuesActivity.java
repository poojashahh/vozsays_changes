package com.vozsays.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import com.vozsays.R;
import com.vozsays.databinding.ActivityLocalIssuesBinding;
import com.vozsays.model.UpdateCategoryModel;
import com.vozsays.utility.IntentExtra;
import com.vozsays.utility.Validator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocalIssuesActivity extends BaseFragmentActivity {
    private String vCategories;
    private String Vauthtoken;
    private ActivityLocalIssuesBinding mBinding;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_local_issues);
        if (getIntent() != null && getIntent().hasExtra(IntentExtra.ACTIVITY.CATEGORY_EXTRA))
            vCategories = getIntent().getStringExtra(IntentExtra.ACTIVITY.CATEGORY_EXTRA);
        context=this;
              mBinding.actLocalissuesEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    mBinding.actLocalIssueRl.performClick();
                    return true;
                }
                return false;
            }
        });
    }
    public void onZipCodeClick(View view) {
        mBinding.actLocalissuesZipLl.setVisibility(View.VISIBLE);
        mBinding.actLocalissuesEt.setVisibility(View.VISIBLE);
        mBinding.actLocalIssueRl.setVisibility(View.VISIBLE);
    }
    public void onReadyClick(View view) {
        if (mBinding.actLocalissuesEt.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(mBinding.localissues,  getResources().getString(R.string.insertion_fail), Snackbar.LENGTH_LONG)
                    .setAction("Ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    });
            snackbar.show();
        } else {
            Vauthtoken = mPrefs.getAccessToekn();
            if (validator.hasConnection(LocalIssuesActivity.this)) {
                showProgressDialog(this);
                apiTask.updateCategoryData(Vauthtoken, mBinding.actLocalissuesEt.getText().toString(), vCategories, callback);
            } else {
                Snackbar snackbar = Snackbar.make(mBinding.localissues, getResources().getString(R.string.connection_fail_message), Snackbar.LENGTH_LONG)
                        .setAction("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });
                snackbar.show();
            }
        }
    }
    Callback<UpdateCategoryModel> callback = new Callback<UpdateCategoryModel>() {
        @Override
        public void onResponse(Call<UpdateCategoryModel> call, Response<UpdateCategoryModel> response) {
            dismissProgressDialog();
            if (response != null && response.body() != null && response.body().getStatus() != null && response.body().getStatus().equals("200")) {
                Intent intent = new Intent(getApplicationContext(), ChooseYourCategoryActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mPrefs.setInterest(true);
                startActivity(intent);
                finish();
            }
            else {
                if (response != null && response.body() != null && response.body().getStatus() != null && !response.body().getStatus().equals("200") && response.body().getMessage() != null && response.body().getMessage().getError() != null && !response.body().getMessage().getError().equals("")) {
                    onFailure(null, null);
                }
               else{
                    Snackbar snackbar = Snackbar.make(mBinding.localissues, "Invalid Zip Code..Try again!!", Snackbar.LENGTH_LONG)
                            .setAction("Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    mBinding.actLocalissuesZipLl.setVisibility(View.VISIBLE);;
                                    mBinding.actLocalIssueRl.setVisibility(View.VISIBLE);


                                }
                            });
                    snackbar.show();
                }
            }
        }
        @Override
        public void onFailure(Call<UpdateCategoryModel> call, Throwable t) {
            dismissProgressDialog();
            validator.alert(context,getResources().getString(R.string.login_fail_alert));
            Intent in=new Intent(getApplicationContext(),JoinTheCommunity.class);
            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(in);
        }
    };


}
