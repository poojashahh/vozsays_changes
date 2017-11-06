package com.vozsays.utility;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.vozsays.R;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.vozsays.R;

public class CustomInviteDialogClass extends Dialog {
    //
    public Activity c;
    public Dialog d;
    public Button close;
    public Button yes;

    public CustomInviteDialogClass(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
      setContentView(R.layout.dialog_before_invite);
//    yes.setOnClickListener(this);

    }

//  @Override
//  public void onClick(View v) {
//    switch (v.getId()) {
//    case R.id.closebtn:
//          d.cancel();
//      break;
//    default:
//      break;
//    }
//    dismiss();
//  }

}