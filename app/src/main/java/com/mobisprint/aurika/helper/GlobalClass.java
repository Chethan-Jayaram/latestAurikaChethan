package com.mobisprint.aurika.helper;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class GlobalClass {
    public static  String PH_NO="";
    public static  String ROOM_NO="";
    public static  boolean END_POINT_REGISTORED=false;
    public static android.content.SharedPreferences SharedPreferences;
    public static String user_token="";
    public static String USER_NAME="";
    public static boolean flow=false;
    public static String APPDATA="";
    public static DateFormat inputdateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    public static DateFormat outputdateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

}
