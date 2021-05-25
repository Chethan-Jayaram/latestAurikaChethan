package com.mobisprint.aurika.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.services.APIMethods;
import com.mobisprint.aurika.retrofit.ClientServiceGenerator;
import com.mobisprint.aurika.unlock.Aurika;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class GlobalClass {

    public static final String UDAIPUR_DOOR_UNLOCK="UDAIPUR_DOOR_UNLOCK";
    public static final String UDAIPUR="UDAIPUR";
    public static final String COORG="COORG";

    public static final APIMethods API_COORG = ClientServiceGenerator.getUrlClient(GlobalClass.COORG).create(APIMethods.class);


    public static final String android_id= Settings.Secure.getString(Aurika.getAppContext().getContentResolver(),Settings.Secure.ANDROID_ID);
    public static final SharedPreferences sharedPreferences= Aurika.getAppContext().getSharedPreferences("AURIKA", Context.MODE_PRIVATE);
    public static final SharedPreferences.Editor editor= sharedPreferences.edit();


    public static String ORGANISATION_ID = "";
    public static String LOCATION_NAME = "";

    public static  String PH_NO="";
    public static  String ROOM_NO="";
    public static  boolean END_POINT_REGISTORED=false;
    public static android.content.SharedPreferences SharedPreferences;
    public static String user_token="";
    public static String request_id = "";
    public static String USER_NAME="";
    public static boolean flow=false;
    public static int previous=100;;
    public static String APPDATA="";
    public  static String[] homeNames;
    public static DateFormat inputdateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    public static DateFormat outputdateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    public static String prefix = "494820";
    public static String suffix = "20434c";


    public interface AdapterListener {
        void onItemClicked(int Position);

    }

    public interface ExpandableAdapterListener {
        void onItemClicked(int ParentPosition, int ChildPosition);

    }



    public static void ShowAlert(Context context, String title, String message) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title);
            builder.setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("OK", (dialog, id) -> {
                        dialog.dismiss();
                    });
            AlertDialog alert = builder.create();
            alert.show();
            Button positiveButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
            Button negativeButton = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
            positiveButton.setTextColor(context.getResources().getColor(R.color.black));
            negativeButton.setTextColor(context.getResources().getColor(R.color.black));
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

}
