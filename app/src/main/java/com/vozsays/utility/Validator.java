package com.vozsays.utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.vozsays.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yudizsolutions on 12/04/16.
 */
public class Validator {
    private static Validator ourInstance = new Validator();

    public static Validator getInstance() {
        return ourInstance;
    }

    public Validator() {
    }

    public static boolean isEmpty(TextView editText) {
        if (editText.getText().toString().trim().equals(""))
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
        if (mobile == null || mobile.length() > 3 || mobile.length() < 14)
            return false;
        return true;
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

    public static void alert(Context context, String msg) {
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


    /**
     * method to convert date format
     *
     * @param dateString        : date to be converted
     * @param formatSource      : source date format
     * @param formatDestination : destination date format
     **/
    @SuppressLint("SimpleDateFormat")
    public static String ConvertTimeInUTC(String dateString, String formatSource, String formatDestination) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatSource);// "yyyy-MM-dd'T'HH:mm:ssz"
        try {
            Date date = sdf.parse(dateString);
            SimpleDateFormat sf = new SimpleDateFormat(formatDestination);
            sf.setTimeZone(TimeZone.getTimeZone("UTC"));

            String formated = sf.format(date);
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
     **/
    @SuppressLint("SimpleDateFormat")
    public static String getFormatedDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// "yyyy-MM-dd'T'HH:mm:ssz"
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date = sdf.parse(dateString);
            String formated = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(date);
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
     *                     //     * @param formatDestination : destination date format
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
            return String.format("%02d", mCalendar.get(Calendar.DAY_OF_MONTH)) + "-" + String.format("%02d", mCalendar.get(Calendar.MONTH) + 1) + "-" + mCalendar.get(Calendar.YEAR);
        } catch (Exception e) {
            return "";
        }
    }

    public static String getDate_DD_MM_YYYY(Calendar mCalendar) {
        try {
            String monthName = "";
            switch ((mCalendar.get(Calendar.MONTH) + 1)) {
                case 1:
                    monthName = "Jan";
                    break;
                case 2:
                    monthName = "Feb";
                    break;
                case 3:
                    monthName = "Mar";
                    break;
                case 4:
                    monthName = "Apr";
                    break;
                case 5:
                    monthName = "May";
                    break;
                case 6:
                    monthName = "Jun";
                    break;
                case 7:
                    monthName = "Jul";
                    break;
                case 8:
                    monthName = "Aug";
                    break;
                case 9:
                    monthName = "Sep";
                    break;
                case 10:
                    monthName = "Oct";
                    break;
                case 11:
                    monthName = "Nov";
                    break;
                case 12:
                    monthName = "Dec";
                    break;
            }

            return mCalendar.get(Calendar.YEAR) + "-" + monthName + "-" + String.format("%02d", mCalendar.get(Calendar.DAY_OF_MONTH));
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean isPastDate(Calendar mCalendar) {
        try {
            Calendar cal = Calendar.getInstance();
            if (mCalendar.get(Calendar.YEAR) == cal.get(Calendar.YEAR) && mCalendar.get(Calendar.DATE) == cal.get(Calendar.DATE) && mCalendar.get(Calendar.MONTH) == cal.get(Calendar.MONTH))
                return false;
            else
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




    /**
     * Checks if is GPS enable.
     *
     * @param context the context
     * @return true, if is GPS enable
     */
    public static boolean isGPSEnable(Context context) {
        @SuppressWarnings("static-access")
        LocationManager manager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            return false;
        else
            return true;
    }

    /**
     * Show settings alert.
     *
     * @param context  the context
     * @param provider the provider
     */
    public static void showSettingsAlert(final Context context, String provider) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setTitle(provider + " SETTINGS");

        alertDialog.setMessage(provider + " is not enabled! Want to go to settings menu?");

        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }


}
