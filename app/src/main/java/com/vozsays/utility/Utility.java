package com.vozsays.utility;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Attendees;
import android.provider.CalendarContract.Events;
import android.provider.Settings;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.vozsays.R;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.os.Environment.DIRECTORY_PICTURES;

/**
 * Created by yudizsolutions on 12/04/16.
 */
public class Utility {
    private static Utility ourInstance = new Utility();
    public static int ageLimitForSignUp = 16 * -1;
    private static DisplayImageOptions profileOptions;

    public static Utility getInstance() {
        return ourInstance;
    }

    private Utility() {
    }

    public static boolean isEmpty(TextView editText) {
        return isEmpty(editText.getText().toString());
    }
    public static void toast(Context context,String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean isEmpty(String text) {
        if (text != null && text.trim().equals(""))
            return true;
        return false;
    }

    public static boolean isValidEmail(EditText edtEmail) {
        return isEmailValid(edtEmail.getText().toString().trim());
    }

    public static boolean isValidMobile(EditText edtMobile) {
        String mobile = edtMobile.getText().toString().trim();
        if (mobile == null || mobile.length() != 10)
            return false;
        return true;
    }

    public static boolean isValidPhone(EditText edtMobile) {
        String mobile = edtMobile.getText().toString().trim();
        if (mobile == null || mobile.length() < 10 || mobile.length() > 11)
            return false;
        return true;
    }

    public static boolean isAppInstalled(Context context, String uri) {
        PackageManager pm = context.getPackageManager();
        boolean isAppInstalled = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            isAppInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return isAppInstalled;
    }

    public static boolean hasConnection(Context context) {
        ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo network = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (network != null && network.isConnected()) {
            return true;
        }

        network = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (network != null && network.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = cManager.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }
        return false;
    }

    public static void showKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(view.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void alert(Context context, String msg) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(Html.fromHtml(msg)).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    public static void showExitDialog(final Activity context) {
        AlertDialog.Builder alertDialogBuilderExit = new AlertDialog.Builder(context);
        alertDialogBuilderExit.setTitle(context.getResources().getString(R.string.app_name)).setMessage("Do you want to Exit?").setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                context.finish();
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilderExit.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public static boolean isEmailValid(String email) {
        String EMAILADDRESS_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAILADDRESS_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public static String getHashKey(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return Base64.encodeToString(md.digest(), Base64.DEFAULT);
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        return null;
    }

    public static String bundle2string(Bundle bundle) {
        String string = "Bundle{";
        for (String key : bundle.keySet()) {
            string += " " + key + " => " + bundle.get(key) + ";";
        }
        string += " }Bundle";
        return string;
    }

    /**
     * method to convert date format
     *
     * @param dateString        : date to be converted
     * @param formatSource      : source date format
     * @param formatDestination : destination date format
     **/
    @SuppressLint("SimpleDateFormat")
    public static String getFormatedDate(String dateString, String formatSource, String formatDestination) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatSource);// "yyyy-MM-dd'T'HH:mm:ssz"
//        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date = sdf.parse(dateString);
            String formated = new SimpleDateFormat(formatDestination).format(date);
            return formated;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * method to convert date format
     *
     * @param dateString : date to be converted
     *                   //     * @param formatSource      : source date format
     *                   //     * @param formatDestination : destination date format
     **/
    @SuppressLint("SimpleDateFormat")
    public static String utcToLocalTimezone(String dateString) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = df.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        df.setTimeZone(TimeZone.getDefault());

        String formattedDate = df.format(date);
        return formattedDate;

    }


    /**
     * method to convert date format
     *
     * @param dateString   : date to be converted
     * @param formatSource : source date format
     **/
    @SuppressLint("SimpleDateFormat")
    public static long getMillisFromDate(String dateString, String formatSource) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatSource);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date = sdf.parse(dateString);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * method to convert milliseconds into date String
     *
     * @param dateStringMillis : milliseconds be converted into date String
     **/
    @SuppressLint("SimpleDateFormat")
    public static String getFormatedImageDate(String dateStringMillis) {
        try {
            Calendar mCalendar = Calendar.getInstance();
            mCalendar.setTimeInMillis(Long.parseLong(dateStringMillis));
            return mCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US) + ", " + mCalendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US) + " " + mCalendar.get(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            return "";
        }
    }

    public static String getDate_YYYY_MM_DD(Calendar mCalendar) {
        try {
            return mCalendar.get(Calendar.YEAR) + "/" + String.format("%02d", mCalendar.get(Calendar.MONTH)) + "/" + String.format("%02d", mCalendar.get(Calendar.DAY_OF_MONTH));
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean isPastDate(Calendar mCalendar) {
        try {
            return mCalendar.before(Calendar.getInstance());
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean hasRequestPermissionsResultMethodInActivity(Activity mActivity) {
        try {
            Class c = mActivity.getClass();
            // method Long
            Class[] cArg = new Class[3];
            cArg[0] = int.class;//int requestCode
            cArg[1] = String[].class;//String[] permissions
            cArg[2] = int[].class;//int[] grantResults
            c.getDeclaredMethod("onRequestPermissionsResult", cArg);
            return true;
        } catch (NoSuchMethodException e) {
            System.out.println("Please Override \"onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)\" in " + mActivity.getClass().getSimpleName() + " file.");
            return false;
        }
    }

    public static void startInstalledAppDetailsActivity(final Context context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    public static void load(Context mContext, boolean hasAvatar, String avatarUrl, final ImageView userAvatar) {
        if (hasAvatar) {
            load(mContext, avatarUrl, userAvatar);
        } else {
            userAvatar.setImageResource(R.drawable.logo);
        }
    }

    public static String SaveIamge(Bitmap finalBitmap, String name, String directoryName) {

        File publicFile = Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES);
//        String root = Environment.getExternalStorageDirectory().toString();
        File file = new File(publicFile, name);
//        myDir.mkdirs();
//        String fname = "/" + name;
//        File file = new File(myDir, fname);

        try {
            file.createNewFile();
            if (file.exists())
                file.delete();
            FileOutputStream out = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(out);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bos);
            out.flush();
            out.close();
//            return root + "/" + directoryName + fname;
            return file.getAbsolutePath();

        } catch (FileNotFoundException e) {
            Log.d("Tag", "Error saving image file: " + e.getMessage());
            return "";
        } catch (IOException e) {
            Log.d("Tag", "Error saving image file: " + e.getMessage());
            return "";
        }
    }

    public static void load(final Context mContext, final String mAvatarUrl, final Target target) {
        String avatarUrl = mAvatarUrl;
//        if (target.getWidth() > 0 && target.getHeight() > 0)
//            avatarUrl = cropImageUrl(avatarUrl, target.getWidth(), target.getHeight());
        try {
            DisplayImageOptions options = getOption();
            ImageLoader.getInstance().loadImage(avatarUrl, options, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    target.onFail(new Exception(failReason.getCause()));
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    target.onSuccess(loadedImage);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                }
            });
        } catch (Exception e) {
            Glide.with(mContext)
                    .load(avatarUrl)
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            target.onSuccess(resource);
                        }

                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            super.onLoadFailed(e, errorDrawable);
                            target.onFail(e);
                        }
                    });
        }
    }

//    public static void sendFeedback(BaseFragmentActivity activity) {
//        Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "dan.carney@me.com", null));
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        i.putExtra(Intent.EXTRA_SUBJECT, "Eye On | Customer Feedback");
//        i.putExtra(Intent.EXTRA_TEXT, "Hi! Good Day!");
//        try {
//            activity.startActivity(Intent.createChooser(i, "Send mail..."));
//        } catch (android.content.ActivityNotFoundException ex) {
//            activity.showMessageDialog(activity.getString(R.string.EMAIL_SERVICE_ERROR), activity.getString(R.string.EMAIL_SERVICE_ERROR_MSG), false, null);
//        }
//    }

    public interface Target {
        int getHeight();

        int getWidth();

        void onSuccess(Bitmap bitmap);

        void onFail(Exception e);
    }

//    public static void load(final Context mContext, final String mAvatarUrl, final ImageView userAvatar, final int loadingRes) {
//        load(mContext, mAvatarUrl, userAvatar, loadingRes, true);
//    }

    public static void load(final Context mContext, final String mAvatarUrl, final ImageView userAvatar, final int loadingRes) {
        userAvatar.post(new Runnable() {
            @Override
            public void run() {
                String avatarUrl = mAvatarUrl;
//                if (isCrop)
//                    avatarUrl = cropImageUrl(mAvatarUrl, userAvatar);
                try {
                    DisplayImageOptions options;
                    if (loadingRes == -1)
                        options = getOption();
                    else
                        options = getOption(loadingRes);
                    ImageLoader.getInstance().displayImage(avatarUrl, userAvatar, options);
                } catch (Exception e1) {
                    final Object tag = userAvatar.getTag();
                    userAvatar.setTag(null);
                    try {
                        DrawableRequestBuilder<String> glideLoader = Glide.with(mContext)
                                .load(avatarUrl)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .listener(new RequestListener<String, GlideDrawable>() {
                                    @Override
                                    public boolean onException(Exception e, String model, com.bumptech.glide.request.target.Target<GlideDrawable> target, boolean isFirstResource) {
                                        userAvatar.setTag(tag);
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(GlideDrawable resource, String model, com.bumptech.glide.request.target.Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                        userAvatar.setImageDrawable(resource);
                                        userAvatar.setTag(tag);
                                        userAvatar.setAlpha(1.0f);
                                        return false;
                                    }
                                });
                        //.placeholder(R.drawable.ic_progress_loading)
                        //.error(R.drawable.avatar_tutorials)
                        if (loadingRes != -1)
                            glideLoader.placeholder(loadingRes);
                        glideLoader.into(userAvatar);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

//    private static String cropImageUrl(String avatarUrl, int width, int height) {
//        if (avatarUrl == null || !avatarUrl.startsWith("http") || avatarUrl.contains("facebook.com") || avatarUrl.contains("twitter.com") || avatarUrl.contains("twimg.com"))
//            return avatarUrl;
//        return WebAPI.IMAGE_CROP_URL.replace("IMAGE_SRC", avatarUrl).replace("HEIGHT", height + "").replace("WIDTH", width + "");
//    }

//    private static String cropImageUrl(String avatarUrl, ImageView userAvatar) {
//        return cropImageUrl(avatarUrl, userAvatar.getMeasuredWidth(), userAvatar.getMeasuredHeight());
//    }

    public static void load(Context mContext, String avatarUrl, final ImageView userAvatar) {
        load(mContext, avatarUrl, userAvatar, -1);
    }


    public static DisplayImageOptions getOption() {
        if (profileOptions == null) {
            profileOptions = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true)
//                    .resetViewBeforeLoading(true)
//                    .showImageForEmptyUri(R.drawable.avatar_tutorials)
//                    .showImageOnFail(R.drawable.avatar_tutorials)
//                    .showImageOnLoading(R.drawable.ic_progress_loading)
                    .build();
        }
        return profileOptions;
    }

    public static DisplayImageOptions getOption(int loadingRes) {
        if (profileOptions == null) {
            profileOptions = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true)
//                    .resetViewBeforeLoading(true)
//                    .showImageForEmptyUri(R.drawable.avatar_tutorials)
//                    .showImageOnFail(R.drawable.avatar_tutorials)
                    .showImageOnLoading(loadingRes)
                    .build();
        }
        return profileOptions;
    }

    /**
     * Set selected state for the given views<br>
     * Purpose to create this method to start marque effect
     *
     * @param views
     */
    public static void setSelected(View... views) {
        for (View view : views) {
            view.setSelected(true);
        }
    }

    /**
     * method to convert date format
     *
     * @param dateString        : date to be converted
     * @param formatSource      : source date format
     * @param formatDestination : destination date format
     **/
    @SuppressLint("SimpleDateFormat")
    public static String getFormatedDateInUTC(String dateString, String formatSource, String formatDestination) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatSource);// "yyyy-MM-dd'T'HH:mm:ssz"
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date = sdf.parse(dateString);
            String formated = new SimpleDateFormat(formatDestination).format(date);
            return formated;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public static boolean isToday(Calendar mCalendar) {
        try {
            Calendar todayCalendar = Calendar.getInstance();
            return (mCalendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR) && mCalendar.get(Calendar.MONTH) == todayCalendar.get(Calendar.MONTH) && mCalendar.get(Calendar.DATE) == todayCalendar.get(Calendar.DATE));
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isToday(String dateString, String formatSource) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(getMillisFromDate(dateString, formatSource));
        return isToday(mCalendar);
    }

    public static boolean isYesterday(Calendar mCalendar) {
        try {
            Calendar yesterdayCalendar = Calendar.getInstance();
            yesterdayCalendar.roll(Calendar.DATE, false);
            return (mCalendar.get(Calendar.YEAR) == yesterdayCalendar.get(Calendar.YEAR) && mCalendar.get(Calendar.MONTH) == yesterdayCalendar.get(Calendar.MONTH) && mCalendar.get(Calendar.DATE) == yesterdayCalendar.get(Calendar.DATE));
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isYesterday(String dateString, String formatSource) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(getMillisFromDate(dateString, formatSource));
        return isYesterday(mCalendar);
    }


    /**
     * convert list of string to comma separated string with double quote. <br>eg. ["10","20","30"]
     *
     * @param list list of string to convert
     * @return comma separate string with double quote
     */
    public static String toCommaSeparatedArrayString(List<String> list) {
        if (list == null)
            return "[]";
        String strArray = Arrays.toString(list.toArray());
        if (strArray.length() > 2) {
            strArray = strArray.replace("[", "[\"");
            strArray = strArray.replace("]", "\"]");
            strArray = strArray.replace(", ", "\",\"");
        }
        return strArray;
    }

    public static byte[] encodeImage(String path) {
        File imagefile = new File(path);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(imagefile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bm = BitmapFactory.decodeStream(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] b = baos.toByteArray();
        return b;
    }

}
