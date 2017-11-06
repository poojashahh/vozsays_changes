package com.vozsays.utility;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import com.vozsays.R;

/**
 * Created by yudizsolutionspvt.ltd. on 22/09/17.
 */


public class CustomBroadcastDialog extends Dialog {
    //
    public Activity c;
    public CustomBroadcastDialog(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_broadcast);
//    yes.setOnClickListener(this);

    }
}