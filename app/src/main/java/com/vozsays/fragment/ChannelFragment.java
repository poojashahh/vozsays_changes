package com.vozsays.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vozsays.R;
import com.vozsays.activity.BaseFragment;
import com.vozsays.activity.GetStartedActivity;
import com.vozsays.databinding.FragChannelBinding;

/**
 * Created by yudizsolutionspvt.ltd. on 22/09/17.
 */

public class ChannelFragment extends BaseFragment {
   FragChannelBinding mBinding;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding= DataBindingUtil.inflate(inflater,R.layout.frag_channel,container,false);
        return mBinding.getRoot();
    }
}
