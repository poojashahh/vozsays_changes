package com.vozsays.activity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewManager;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;
import com.vozsays.R;
import com.vozsays.fragment.ChannelFragment;
import com.vozsays.fragment.AccountFragment;
import com.vozsays.fragment.QuestionFragment;
import com.vozsays.model.QuestionDataList;
import com.vozsays.model.QuestionDataModel;
import com.vozsays.utility.CustomBroadcastDialog;
import com.vozsays.utility.CustomInviteDialogClass;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class HomeNavigationActivity extends BaseFragmentActivity {
    private String Vauthtoken;
    private List<QuestionDataList> questionDataList;
    private Bundle b = new Bundle();
    private Dialog d;
    private CustomInviteDialogClass cdd;
    private CustomBroadcastDialog cbd;
    private String fileName = "";
    private Bitmap mbitmap;
    private String TAG = "SNAP";
    private String dirPath;
    private File file;
    private String iCatId;
    private Context context;
    FrameLayout fl_container;
    private RelativeLayout root;
    private Call<QuestionDataModel> call;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_questions:
                    getQuestionThroughApi();
                    return true;
                case R.id.navigation_channels:
                    if(call!=null){
                        call.cancel();
                    }
                    replaceFragment(new ChannelFragment(), false);
                    return true;
                case R.id.navigation_account:
                    replaceFragment(new AccountFragment(), false);
                    if(call!=null) {
                        call.cancel();
                    }
                    return true;
            }
            return false;
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_navigation);
        root = (RelativeLayout) findViewById(R.id.container);
        fl_container = (FrameLayout) findViewById(R.id.fl_container);
        context = this;
        if (getIntent().getStringExtra("categoryid") != null) {
            iCatId = getIntent().getStringExtra("categoryid");
        }
        getQuestionThroughApi();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void getQuestionThroughApi() {
        Vauthtoken = mPrefs.getAccessToekn();
        System.out.println(Vauthtoken);
        if (validator.hasConnection(context)) {
          call = apiTask.getQuestionData(Vauthtoken, iCatId, callback);

        } else {
            Snackbar snackbar = Snackbar.make(root, getResources().getString(R.string.connection_fail_message), Snackbar.LENGTH_LONG)
                    .setAction("Ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    });
            snackbar.show();
        }
    }

    Callback<QuestionDataModel> callback = new Callback<QuestionDataModel>() {
        @Override
        public void onResponse(Call<QuestionDataModel> call, Response<QuestionDataModel> response) {
            dismissProgressDialog();
            if (response != null && response.body() != null && response.body().getStatus() != null && response.body().getStatus().equals("200")) {
                questionDataList = response.body().getData();
                fillQuestionFragment();
            } else {
                if (response != null && response.body() != null && response.body().getStatus() != null && !response.body().getStatus().equals("200")
                        && response.body().getMessage() != null && response.body().getMessage().getError() != null && !response.body().getMessage().getError().equals("")) {
                    Snackbar snackbar = Snackbar.make(root, "Something went wrong", Snackbar.LENGTH_LONG)
                            .setAction("Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            });
                    snackbar.show();

                    onFailure(null, null);
                } else {
                    onFailure(null, null);
                }
            }
        }

        @Override
        public void onFailure(Call<QuestionDataModel> call, Throwable t) {
            dismissProgressDialog();
        }
    };

    private void fillQuestionFragment() {
        QuestionImpl();
    }

    private void QuestionImpl() {
        if (questionDataList.size() != 0) {
            Fragment f = new QuestionFragment();
            b.putParcelableArrayList("questionDataList", (ArrayList<? extends Parcelable>) questionDataList);
            f.setArguments(b);
            replaceFragment(f, false);
        } else {
            Fragment f = new QuestionFragment();
            b.putParcelableArrayList("questionDataList", (ArrayList<? extends Parcelable>) questionDataList);
            f.setArguments(b);
            replaceFragment(f, false);
        }
    }

    private void replaceFragment(Fragment f, Boolean isAddToBackStack) {
        if (!isAddToBackStack) {
//            getFragmentManager().beginTransaction().remove()\
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, f)
                    .commit();
        } else {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, f)
                    .addToBackStack(null)
                    .commit();

        }
    }

    //invite interface
    public void onInvitetool(View view) {
        cdd = new CustomInviteDialogClass(HomeNavigationActivity.this);
        cdd.show();
    }

    public void onInviteYesBtn(View view) {
        cdd.cancel();
        d = new Dialog(HomeNavigationActivity.this, android.R.style.Theme_DeviceDefault_Light_NoActionBar);
        Window window = d.getWindow();
        window.setLayout(AppBarLayout.LayoutParams.FILL_PARENT, AppBarLayout.LayoutParams.FILL_PARENT);
        d.setContentView(R.layout.dialog_invite);
        d.show();
    }

    public void onInviteCloseBtn(View view) {
        cdd.cancel();
    }

    public void onDownArrow(View view) {
        d.cancel();
    }

    public void onInvite(View view) {
        d.cancel();
        String appLinkUrl, previewImageUrl;

        appLinkUrl = "https://www.mydomain.com/myapplink";
        previewImageUrl = "https://www.mydomain.com/my_invite_image.jpg";
        if (AppInviteDialog.canShow()) {
            AppInviteContent content = new AppInviteContent.Builder()
                    .setApplinkUrl(appLinkUrl)
                    .setPreviewImageUrl(previewImageUrl)
                    .build();
            AppInviteDialog.show(this, content);
        }
    }

    //Broadcast interface
    public void onBroadcasttool(View view) {
        // broadcast tool onclick
        cbd = new CustomBroadcastDialog(HomeNavigationActivity.this);
        cbd.show();
    }

    public void onBroadcastYesBtn(View view) {
        cbd.cancel();
        store(getScreenShot(root));
        shareImage(file);
    }

    public void onBroadcastCloseBtn(View view) {
        cbd.cancel();
    }

    public void store(Bitmap bm) {
        Calendar calendar = Calendar.getInstance();
        fileName = "SNAP_" + String.valueOf(calendar.getTimeInMillis()) + ".png";
        dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ZZZ";
        File dir = new File(dirPath);
        if (!dir.exists())
            dir.mkdirs();
        file = new File(dirPath, fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "store: " + e.toString());
        }
    }

    private void shareImage(File file) {
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            startActivity(Intent.createChooser(intent, "Share Screenshot"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No App Available", Toast.LENGTH_SHORT).show();
        }
    }
    public Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        System.out.println("count" + count + "mPref.isLogin" + mPrefs.isLogin());
        if (count == 0 && mPrefs.isLogin()) {
            super.onBackPressed();
        } else if (count != 0 && !mPrefs.isLogin()) {
            getFragmentManager().popBackStack();
        }

    }


}
