package com.vozsays.activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import com.vozsays.R;
import com.vozsays.adapter.CategoryAdapter;
import com.vozsays.databinding.ActivityChooseYourCategoryBinding;
import com.vozsays.model.CategoryDataList;
import com.vozsays.model.CategoryDataModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class ChooseYourCategoryActivity extends BaseFragmentActivity {
    private CategoryDataModel categoryDataModel;
    private List<CategoryDataList> categoryDataList;
    private CategoryAdapter categoryAdapter;
    private ActivityChooseYourCategoryBinding mBinding;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_choose_your_category);
        context=this;
        if (validator.hasConnection(ChooseYourCategoryActivity.this)) {
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
                categoryDataList= categoryDataModel.getCategoryDataList();
                feelData();
            } else onFailure(null, null);
        }
        @Override
        public void onFailure(Call<CategoryDataModel> call, Throwable t) {
            dismissProgressDialog();
            validator.alert(context,getResources().getString(R.string.login_fail_alert));
            Intent in=new Intent(getApplicationContext(),JoinTheCommunity.class);
            startActivity(in);
        }
    };
    private void feelData() {
        categoryAdapter=new CategoryAdapter(getApplicationContext(),categoryDataList);
        mBinding.categoriesRecylerview.setLayoutManager(new LinearLayoutManager(ChooseYourCategoryActivity.this));
        mBinding.categoriesRecylerview.setAdapter(categoryAdapter);

    }
}


