package com.vozsays.activity;


import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import com.vozsays.R;
import com.vozsays.databinding.ActivityChooseYourInterestBinding;
import com.vozsays.model.CategoryDataList;
import com.vozsays.model.CategoryDataModel;
import com.vozsays.model.Message;
import com.vozsays.retrofit.AlertPermissionDialog;
import com.vozsays.retrofit.WebAPI;
import com.vozsays.utility.CircleImageView;
import com.vozsays.utility.IntentExtra;
import com.vozsays.utility.Utility;
import com.vozsays.utility.Validator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChooseYourInterestActivity extends BaseFragmentActivity {

    private CategoryDataModel categoryDataModel;
    private ActivityChooseYourInterestBinding mBinding;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_choose_your_interest);
        context=this;
        mBinding.ivInterest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                if (v.isSelected()) {
                    mBinding.tvInterest1.setTextColor(ContextCompat.getColor(ChooseYourInterestActivity.this, R.color.colorAccent));
                    mBinding.tvSelectInterest.setBackgroundResource(R.drawable.pink_round_rect);
                    mBinding.tvSelectInterest.setTextColor(ContextCompat.getColor(ChooseYourInterestActivity.this, android.R.color.white));
                } else
                    mBinding.tvInterest1.setTextColor(ContextCompat.getColor(ChooseYourInterestActivity.this, android.R.color.black));

                if (!mBinding.ivInterest1.isSelected() && !mBinding.ivInterest2.isSelected() && !mBinding.ivInterest3.isSelected() &&
                        !mBinding.ivInterest4.isSelected() && !mBinding.ivInterest5.isSelected() && !mBinding.ivInterest6.isSelected()) {
                    mBinding.tvSelectInterest.setBackgroundResource(R.drawable.white_round_rect_pink_line);
                    mBinding.tvSelectInterest.setTextColor(ContextCompat.getColor(ChooseYourInterestActivity.this, R.color.text_grey));
                }
            }
        });
        mBinding.ivInterest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                if (v.isSelected()) {
                    mBinding.tvInterest2.setTextColor(ContextCompat.getColor(ChooseYourInterestActivity.this, R.color.colorAccent));
                    mBinding.tvSelectInterest.setBackgroundResource(R.drawable.pink_round_rect);
                    mBinding.tvSelectInterest.setTextColor(ContextCompat.getColor(ChooseYourInterestActivity.this, android.R.color.white));
                } else
                    mBinding.tvInterest2.setTextColor(ContextCompat.getColor(ChooseYourInterestActivity.this, android.R.color.black));
                if (!mBinding.ivInterest1.isSelected() && !mBinding.ivInterest2.isSelected() && !mBinding.ivInterest3.isSelected() &&
                        !mBinding.ivInterest4.isSelected() && !mBinding.ivInterest5.isSelected() && !mBinding.ivInterest6.isSelected()) {
                    mBinding.tvSelectInterest.setBackgroundResource(R.drawable.white_round_rect_pink_line);
                    mBinding.tvSelectInterest.setTextColor(ContextCompat.getColor(ChooseYourInterestActivity.this, R.color.text_grey));
                }
            }
        });
        mBinding.ivInterest3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                if (v.isSelected()) {
                    mBinding.tvInterest3.setTextColor(ContextCompat.getColor(ChooseYourInterestActivity.this, R.color.colorAccent));
                    mBinding.tvSelectInterest.setBackgroundResource(R.drawable.pink_round_rect);
                    mBinding.tvSelectInterest.setTextColor(ContextCompat.getColor(ChooseYourInterestActivity.this, android.R.color.white));
                } else
                    mBinding.tvInterest3.setTextColor(ContextCompat.getColor(ChooseYourInterestActivity.this, android.R.color.black));
                if (!mBinding.ivInterest1.isSelected() && !mBinding.ivInterest2.isSelected() && !mBinding.ivInterest3.isSelected() &&
                        !mBinding.ivInterest4.isSelected() && !mBinding.ivInterest5.isSelected() && !mBinding.ivInterest6.isSelected()) {
                    mBinding.tvSelectInterest.setBackgroundResource(R.drawable.white_round_rect_pink_line);
                    mBinding.tvSelectInterest.setTextColor(ContextCompat.getColor(ChooseYourInterestActivity.this, R.color.text_grey));
                }
            }
        });
        mBinding.ivInterest4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                if (v.isSelected()) {
                    mBinding.tvInterest4.setTextColor(ContextCompat.getColor(ChooseYourInterestActivity.this, R.color.colorAccent));
                    mBinding.tvSelectInterest.setBackgroundResource(R.drawable.pink_round_rect);
                    mBinding.tvSelectInterest.setTextColor(ContextCompat.getColor(ChooseYourInterestActivity.this, android.R.color.white));
                } else
                    mBinding.tvInterest4.setTextColor(ContextCompat.getColor(ChooseYourInterestActivity.this, android.R.color.black));
                if (!mBinding.ivInterest1.isSelected() && !mBinding.ivInterest2.isSelected() && !mBinding.ivInterest3.isSelected() &&
                        !mBinding.ivInterest4.isSelected() && !mBinding.ivInterest5.isSelected() && !mBinding.ivInterest6.isSelected()) {
                    mBinding.tvSelectInterest.setBackgroundResource(R.drawable.white_round_rect_pink_line);
                    mBinding.tvSelectInterest.setTextColor(ContextCompat.getColor(ChooseYourInterestActivity.this, R.color.text_grey));
                }
            }
        });
        mBinding.ivInterest5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                if (v.isSelected()) {
                    mBinding.tvInterest5.setTextColor(ContextCompat.getColor(ChooseYourInterestActivity.this, R.color.colorAccent));
                    mBinding.tvSelectInterest.setBackgroundResource(R.drawable.pink_round_rect);
                    mBinding.tvSelectInterest.setTextColor(ContextCompat.getColor(ChooseYourInterestActivity.this, android.R.color.white));
                } else
                    mBinding.tvInterest5.setTextColor(ContextCompat.getColor(ChooseYourInterestActivity.this, android.R.color.black));
                if (!mBinding.ivInterest1.isSelected() && !mBinding.ivInterest2.isSelected() && !mBinding.ivInterest3.isSelected() &&
                        !mBinding.ivInterest4.isSelected() && !mBinding.ivInterest5.isSelected() && !mBinding.ivInterest6.isSelected()) {
                    mBinding.tvSelectInterest.setBackgroundResource(R.drawable.white_round_rect_pink_line);
                    mBinding.tvSelectInterest.setTextColor(ContextCompat.getColor(ChooseYourInterestActivity.this, R.color.text_grey));
                }

            }
        });
        mBinding.ivInterest6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                if (v.isSelected()) {
                    mBinding.tvInterest6.setTextColor(ContextCompat.getColor(ChooseYourInterestActivity.this, R.color.colorAccent));
                    mBinding.tvSelectInterest.setBackgroundResource(R.drawable.pink_round_rect);
                    mBinding.tvSelectInterest.setTextColor(ContextCompat.getColor(ChooseYourInterestActivity.this, android.R.color.white));
                } else
                    mBinding.tvInterest6.setTextColor(ContextCompat.getColor(ChooseYourInterestActivity.this, android.R.color.black));
                if (!mBinding.ivInterest1.isSelected() && !mBinding.ivInterest2.isSelected() && !mBinding.ivInterest3.isSelected() &&
                        !mBinding.ivInterest4.isSelected() && !mBinding.ivInterest5.isSelected() && !mBinding.ivInterest6.isSelected()) {
                    mBinding.tvSelectInterest.setBackgroundResource(R.drawable.white_round_rect_pink_line);
                    mBinding.tvSelectInterest.setTextColor(ContextCompat.getColor(ChooseYourInterestActivity.this, R.color.text_grey));
                }

            }
        });

        mBinding.tvSelectInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBinding.ivInterest1.isSelected() || mBinding.ivInterest2.isSelected() || mBinding.ivInterest3.isSelected() ||
                        mBinding.ivInterest4.isSelected() || mBinding.ivInterest5.isSelected() || mBinding.ivInterest6.isSelected()) {
                    String selectedCatIds = "";
                    if (mBinding.ivInterest1.isSelected())
                        selectedCatIds = selectedCatIds + categoryDataModel.getCategoryDataList().get(0).getiCatId() + ",";
                    if (mBinding.ivInterest2.isSelected())
                        selectedCatIds = selectedCatIds + categoryDataModel.getCategoryDataList().get(1).getiCatId() + ",";
                    if (mBinding.ivInterest3.isSelected())
                        selectedCatIds = selectedCatIds + categoryDataModel.getCategoryDataList().get(2).getiCatId() + ",";
                    if (mBinding.ivInterest4.isSelected())
                        selectedCatIds = selectedCatIds + categoryDataModel.getCategoryDataList().get(3).getiCatId() + ",";
                    if (mBinding.ivInterest5.isSelected())
                        selectedCatIds = selectedCatIds + categoryDataModel.getCategoryDataList().get(4).getiCatId() + ",";
                    if (mBinding.ivInterest6.isSelected())
                        selectedCatIds = selectedCatIds + categoryDataModel.getCategoryDataList().get(5).getiCatId() + ",";
                    if (selectedCatIds.endsWith(","))
                        selectedCatIds = selectedCatIds.substring(0, selectedCatIds.length() - 1);
                    Intent intent = new Intent(getApplicationContext(), LocalIssuesActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra(IntentExtra.ACTIVITY.CATEGORY_EXTRA, selectedCatIds);
                    startActivity(intent);
                    finish();
                }
            }
        });

        if (validator.hasConnection(ChooseYourInterestActivity.this)) {
            showProgressDialog(this);
            apiTask.categoryData(mPrefs.getAccessToekn(), callback);
        }
    }

    Callback<CategoryDataModel> callback = new Callback<CategoryDataModel>() {
        @Override
        public void onResponse(Call<CategoryDataModel> call, Response<CategoryDataModel> response) {
            dismissProgressDialog();

            if (response != null && response.body() != null & response.body().getStatus() != null && response.body().getStatus().equals("200")) {
                categoryDataModel = response.body();
                feelData();
            } else onFailure(null, null);
        }

        @Override
        public void onFailure(Call<CategoryDataModel> call, Throwable t) {
            dismissProgressDialog();
            validator.alert(context,getResources().getString(R.string.login_fail_alert));
            Intent in=new Intent(getApplicationContext(),JoinTheCommunity.class);
            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(in);
            finish();
        }
    };

    private void feelData() {
        if (categoryDataModel.getCategoryDataList().size() > 0) {
            mBinding.rlInterest1.setVisibility(View.VISIBLE);
            Utility.load(ChooseYourInterestActivity.this, WebAPI.BASE_IMAGE_URL + categoryDataModel.getCategoryDataList().get(0).getvImage(), mBinding.ivInterest1);
            mBinding.tvInterest1.setText(categoryDataModel.getCategoryDataList().get(0).getvCategory());
        }
        if (categoryDataModel.getCategoryDataList().size() > 1) {
            mBinding.rlInterest2.setVisibility(View.VISIBLE);
            Utility.load(ChooseYourInterestActivity.this, WebAPI.BASE_IMAGE_URL + categoryDataModel.getCategoryDataList().get(1).getvImage(), mBinding.ivInterest2);
            mBinding.tvInterest2.setText(categoryDataModel.getCategoryDataList().get(1).getvCategory());
        }
        if (categoryDataModel.getCategoryDataList().size() > 2) {
            mBinding.rlInterest3.setVisibility(View.VISIBLE);
            Utility.load(ChooseYourInterestActivity.this, WebAPI.BASE_IMAGE_URL + categoryDataModel.getCategoryDataList().get(2).getvImage(), mBinding.ivInterest3);
            mBinding.tvInterest3.setText(categoryDataModel.getCategoryDataList().get(2).getvCategory());
        }
        if (categoryDataModel.getCategoryDataList().size() > 3) {
            mBinding.rlInterest4.setVisibility(View.VISIBLE);
            Utility.load(ChooseYourInterestActivity.this, WebAPI.BASE_IMAGE_URL + categoryDataModel.getCategoryDataList().get(3).getvImage(), mBinding.ivInterest4);
            mBinding.tvInterest4.setText(categoryDataModel.getCategoryDataList().get(3).getvCategory());
        }
        if (categoryDataModel.getCategoryDataList().size() > 4) {
            mBinding.rlInterest5.setVisibility(View.VISIBLE);
            Utility.load(ChooseYourInterestActivity.this, WebAPI.BASE_IMAGE_URL + categoryDataModel.getCategoryDataList().get(4).getvImage(), mBinding.ivInterest5);
            mBinding.tvInterest5.setText(categoryDataModel.getCategoryDataList().get(4).getvCategory());
        }
        if (categoryDataModel.getCategoryDataList().size() > 5) {
            mBinding.rlInterest6.setVisibility(View.VISIBLE);
            Utility.load(ChooseYourInterestActivity.this, WebAPI.BASE_IMAGE_URL + categoryDataModel.getCategoryDataList().get(5).getvImage(), mBinding.ivInterest6);
            mBinding.tvInterest6.setText(categoryDataModel.getCategoryDataList().get(5).getvCategory());
        }

    }

}

