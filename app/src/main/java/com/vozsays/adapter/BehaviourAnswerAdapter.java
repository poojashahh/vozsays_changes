package com.vozsays.adapter;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.vozsays.R;
import com.vozsays.databinding.LayoutAnswerBinding;
import com.vozsays.model.QuestionOption;
import com.vozsays.retrofit.WebAPI;

import java.util.List;
/**
 * Created by yudizsolutionspvt.ltd. on 22/09/17.
 */
public class BehaviourAnswerAdapter extends RecyclerView.Adapter<BehaviourAnswerAdapter.ViewHolder> {
    private View v;
    private Context context;
    private List<QuestionOption> questionOptions;
    private LayoutAnswerBinding mBinding;
    public BehaviourAnswerAdapter(Context context, List<QuestionOption> questionOptions) {
        this.questionOptions = questionOptions;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_answer, parent, false);
        mBinding = DataBindingUtil.getBinding(parent);
        if (mBinding == null) {
            mBinding = DataBindingUtil.inflate(((Activity) parent.getContext()).getLayoutInflater(), R.layout.layout_answer, parent, false);
        }
        ViewHolder viewHolder = new ViewHolder(mBinding);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        mBinding.firstAnswerNoofuser.setText(questionOptions.get(position).getPerc());
        mBinding.progressBar.getIndeterminateDrawable().setColorFilter(context.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
        Glide.with(context).load(WebAPI.BASE_IMAGE_URL + questionOptions.get(position).getvProfilePicture()).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                mBinding.progressBar.setVisibility(View.VISIBLE);
                mBinding.optionImage.setVisibility(View.INVISIBLE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                mBinding.progressBar.setVisibility(View.GONE);
                mBinding.optionImage.setVisibility(View.VISIBLE);
                return false;
            }
        }).fitCenter().into(mBinding.optionImage);
        mBinding.progressBar.setVisibility(View.GONE);
        System.out.println(WebAPI.BASE_IMAGE_URL + questionOptions.get(position).getvProfilePicture());
//        mBinding.seekbar.getThumb().mutate().setAlpha(0);
        mBinding.firstAnswerNoofuser.setText(questionOptions.get(position).getiPoll());
        mBinding.answerTv.setText(questionOptions.get(position).getvOption());
        mBinding.seekbar.setProgress((int) Double.parseDouble(questionOptions.get(position).getPerc()));
        mBinding.perc.setText(questionOptions.get(position).getPerc() + " %");
        mBinding.seekbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        System.out.println((int) Double.parseDouble(questionOptions.get(position).getPerc()));
    }

    @Override
    public int getItemCount() {
        return questionOptions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LayoutAnswerBinding mBinding;

        public ViewHolder(LayoutAnswerBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }
    }
}
