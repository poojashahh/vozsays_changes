package com.vozsays.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.SharedElementCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vozsays.R;
import com.vozsays.activity.BaseFragment;
import com.vozsays.activity.GetStartedActivity;
import com.vozsays.activity.JoinTheCommunity;
import com.vozsays.databinding.FragAccountBinding;

/**
 * Created by yudizsolutionspvt.ltd. on 22/09/17.
 */

public class AccountFragment extends BaseFragment {



    FragAccountBinding mBinding;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_account, container, false);
        mBinding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPrefs.setLogin(false);
                mPrefs.setInterest(false);
                startActivity(new Intent(getActivity(), GetStartedActivity.class));
            }
        });
        return mBinding.getRoot();
    }
}
