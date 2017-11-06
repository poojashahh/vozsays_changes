package com.vozsays.retrofit;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vozsays.R;


public class AlertPermissionDialog {
    private static Activity mContext;
    private String mTitle = "", mMessage = "", mOkButton = "", mCancleButton = "";
    private boolean isOkBold, isCancleBold;
    private View.OnClickListener mOkClickListener;
    private View.OnClickListener mCancleClickListener;
    private DialogInterface.OnDismissListener mOnDismissClickListener;
    private Dialog dialog;
    private boolean isCancelable = true;

    public AlertPermissionDialog(Activity mContext) {
        this.mContext = mContext;
    }

    public AlertPermissionDialog() {

    }

    public static void set(Activity mContext) {
        AlertPermissionDialog.mContext = mContext;
    }

    public static boolean show(String message) {
        return message != null && message.length() == 0 || show("Vozsays", message);
    }

    public static boolean show(String title, String message) {
        if (message == null) {
            message = "";
        }
        if (mContext != null) {
            final boolean isExpire = message.equals("Authorization has been denied for this request.") || message.equals("Invalid Access. Please relogin.");
            final AlertPermissionDialog alert = new AlertPermissionDialog(mContext);
            alert.setTitle(title)
                    .setMessage(!isExpire ? message : "Your Session has Expired! Please re-login to continue.")
                    .setOkClickListener("Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alert.dismiss();
                                    if (isExpire) {
                                        resetUserData();
                                    }
                                }
                            }
                    );
            alert.show();
        }
        return mContext != null;
    }

    private static void resetUserData() {
//        mContext.startActivity(new Intent(mContext, ActLogin.class));
    }


    public static Activity get() {
        return mContext;
    }

    public void show() {
        dialog = getDialog(mTitle, mMessage, mOkButton, mCancleButton, mOkClickListener, mCancleClickListener);
        dialog.show();
        if (mOnDismissClickListener != null)
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mOnDismissClickListener != null)
                        mOnDismissClickListener.onDismiss(dialog);
                }
            }, 2500);
    }

    public void dismiss() {
        if (dialog.isShowing())
            dialog.dismiss();
    }

    public AlertPermissionDialog setOkClickListener(String mOkButton, View.OnClickListener mOkClickListener) {
        this.mOkButton = mOkButton;
        this.mOkClickListener = mOkClickListener;
        return this;
    }

    public AlertPermissionDialog setOkClickListener(String mOkButton, boolean isOkBold, View.OnClickListener mOkClickListener) {
        this.mOkButton = mOkButton;
        this.isOkBold = isOkBold;
        this.mOkClickListener = mOkClickListener;
        return this;
    }

    public AlertPermissionDialog setOnAutoDismissListener(DialogInterface.OnDismissListener mOnDismissClickListener) {
        this.mOnDismissClickListener = mOnDismissClickListener;
        return this;
    }

    public AlertPermissionDialog setCancleClickListener(String mCancleButton, View.OnClickListener mCancleClickListener) {
        this.mCancleButton = mCancleButton;
        this.mCancleClickListener = mCancleClickListener;
        return this;
    }

    public AlertPermissionDialog setCancleClickListener(String mCancleButton, boolean isCancleBold, View.OnClickListener mCancleClickListener) {
        this.mCancleButton = mCancleButton;
        this.isCancleBold = isCancleBold;
        this.mCancleClickListener = mCancleClickListener;
        return this;
    }

    public AlertPermissionDialog setTitle(String mTitle) {
        this.mTitle = mTitle;
        return this;
    }

    public AlertPermissionDialog setMessage(String mMessage) {
        this.mMessage = mMessage;
        return this;
    }

    public AlertPermissionDialog setCancelable(boolean isCancelable) {
        this.isCancelable = isCancelable;
        return this;
    }

    public AlertPermissionDialog setTitleMessage(String mTitle, String mMessage) {
        this.mTitle = mTitle;
        this.mMessage = mMessage;
        return this;
    }

    private Dialog getDialog(String title, String msg, String okButton, String cancleButton, View.OnClickListener mOkClickListener, View.OnClickListener mCancleClickListener) {
        final Dialog myDialog = new Dialog(mContext);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.dialog_permission);
        myDialog.setCancelable(isCancelable);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));

        TextView tvTitle = (TextView) myDialog.findViewById(R.id.dialog_permission_title);
        TextView tvText = (TextView) myDialog.findViewById(R.id.dialog_permission_text);
        if (title.length() > 0) {
            tvTitle.setText(title);
        } else
            tvTitle.setVisibility(View.GONE);

        if (msg.length() > 0)
            tvText.setText(msg);
        else
            tvText.setVisibility(View.GONE);

        LinearLayout llOk = (LinearLayout) myDialog.findViewById(R.id.dialog_permission_ll_ok);
        if (okButton.length() > 0) {
            ((TextView) llOk.getChildAt(0)).setText(okButton);
            if (isOkBold)
                ((TextView) llOk.getChildAt(0)).setTypeface(((TextView) llOk.getChildAt(0)).getTypeface(), Typeface.BOLD);
            if (mOkClickListener != null)
                llOk.setOnClickListener(mOkClickListener);
        } else
            llOk.setVisibility(View.GONE);

        LinearLayout llCancel = (LinearLayout) myDialog.findViewById(R.id.dialog_permission_ll_cancel);
        if (cancleButton.length() > 0) {
            ((TextView) llCancel.getChildAt(0)).setText(cancleButton);
            if (isCancleBold)
                ((TextView) llCancel.getChildAt(0)).setTypeface(((TextView) llCancel.getChildAt(0)).getTypeface(), Typeface.BOLD);
            if (mCancleClickListener != null)
                llCancel.setOnClickListener(mCancleClickListener);
        } else
            llCancel.setVisibility(View.GONE);

        return myDialog;

    }
}