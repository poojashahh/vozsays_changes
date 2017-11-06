package com.vozsays.adapter;
/**
 * Created by yudizsolutionspvt.ltd. on 30/10/17.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.vozsays.R;
import com.vozsays.activity.HomeNavigationActivity;
import com.vozsays.databinding.LayoutCategoriesBinding;
import com.vozsays.model.CategoryDataList;
import com.vozsays.utility.Utility;
import java.util.List;
/**
 * Created by yudizsolutionspvt.ltd. on 22/09/17.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private View v;
    private Context context;
    private List<CategoryDataList> categoryDataList;
    private LayoutCategoriesBinding mBinding;
    public CategoryAdapter(Context context, List<CategoryDataList> categoryDataList) {
        this.categoryDataList = categoryDataList;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.getBinding(parent);
        if (mBinding == null) {
            mBinding = DataBindingUtil.inflate(((Activity) parent.getContext()).getLayoutInflater(), R.layout.layout_categories, parent, false);
        }
        ViewHolder viewHolder = new ViewHolder(mBinding);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        mBinding.category.setText(categoryDataList.get(position).getvCategory());
        switch ((position) % 4) {
            case 0:
                mBinding.categoriesLl.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.first_category_cust_bg));
                break;
            case 1:
                mBinding.categoriesLl.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.second_category_cust_bg));
                break;
            case 2:
                mBinding.categoriesLl.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.third_category_cuts_bg));
                break;
            case 3:
                mBinding.categoriesLl.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.fourth_category_cust_bg));
                break;
            case 4:
                mBinding.categoriesLl.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.fifth_category_cust_bg));
                break;
            default:
                break;
        }
    }
    @Override
    public int getItemCount() {
        return categoryDataList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private LayoutCategoriesBinding mBinding;

        public ViewHolder(LayoutCategoriesBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
            mBinding.categoriesLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(context, HomeNavigationActivity.class);
                    in.putExtra("categoryid", categoryDataList.get(getPosition()).getiCatId());
                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    Utility.toast(context,""+getPosition());
                    context.startActivity(in);
                }
            });
        }

    }
}