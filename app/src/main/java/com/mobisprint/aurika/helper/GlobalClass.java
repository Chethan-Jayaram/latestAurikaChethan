package com.mobisprint.aurika.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.coorg.pojo.petservices.K9Data;
import com.mobisprint.aurika.coorg.services.APIMethods;
import com.mobisprint.aurika.retrofit.ClientServiceGenerator;
import com.mobisprint.aurika.unlock.Aurika;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class GlobalClass {

    public static final String Laundry_count = "Laundry_count";
    public static final String Laundry_price = "Laundry_price";

    public static final String Dining_count = "Dining_count";
    public static final String Dining_price = "Dining_price";

    public static final String SleepWell_count = "SleepWell_count";
    public static final String SleepWell_price = "SleepWell_price";


    public static final String Amenities_count = "Amenities_count";
    public static final String Amenities_price = "Amenities_price";


    public static final String K9Amenities_count = "K9Amenities_count";
    public static final String K9Amenities_price = "K9Amenities_price";

    public static final String UDAIPUR_DOOR_UNLOCK = "UDAIPUR_DOOR_UNLOCK";
    public static final String UDAIPUR = "UDAIPUR";
    public static final String COORG = "COORG";

    public static Boolean Forgot_Mpin = true;

    public static final APIMethods API_COORG = ClientServiceGenerator.getUrlClient(GlobalClass.COORG).create(APIMethods.class);


    public static final String android_id = Settings.Secure.getString(Aurika.getAppContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    public static final SharedPreferences sharedPreferences = Aurika.getAppContext().getSharedPreferences("AURIKA", Context.MODE_PRIVATE);
    public static final SharedPreferences.Editor editor = sharedPreferences.edit();


    public static String ORGANISATION_ID = "";
    public static String LOCATION_NAME = "";

    public static String PH_NO = "";
    public static String ROOM_NO = "";
    public static boolean END_POINT_REGISTORED = false;
    public static android.content.SharedPreferences SharedPreferences;
    public static String user_token = "";
    public static String request_id = "";
    public static String USER_NAME = "";
    public static boolean flow = false;
    public static int previous = 100;
    ;
    public static String APPDATA = "";
    public static String[] homeNames;
    public static DateFormat inputdateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    public static DateFormat outputdateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    public static String prefix = "494820";
    public static String suffix = "20434c";


    public interface AdapterListener {
        void onItemClicked(int Position);

    }

    public interface AmenitiesAdapterListener {
        void onItemClicked(List<Data> data);
    }


    public interface K9AdapterListener {
        void onItemClicked(List<K9Data> data);
    }

    public interface ExpandableAdapterListener {
        void onItemClicked(Data data);
    }


    public interface ExpandableAdapterListenerIRD {
        void onItemClicked(com.mobisprint.aurika.coorg.pojo.dining.Data data);

    }

    public static Boolean timeComparator(String fromTime, String toTime) {
        try {
            /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Calendar c = Calendar.getInstance();
                String time = new SimpleDateFormat("HH:mm:ss").format(c.getTime());


                LocalTime target = LocalTime.parse(time);
                if (target.isAfter(LocalTime.parse(fromTime))
                        &&
                        target.isBefore(LocalTime.parse(toTime))) {
                    return true;
                }

            }*/

            Calendar c = Calendar.getInstance();
            String time = new SimpleDateFormat("HH:mm").format(c.getTime());
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
            Date Current=_24HourSDF.parse(time);
            Date from = _24HourSDF.parse(fromTime);
            Date to = _24HourSDF.parse(toTime);
            int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
            if (Current.after (from)&&Current.before(to)) {

                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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

   /* public static Date TimeFormat(Date date){
        SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
        try {
            Date _24HourDt1 = _24HourSDF.parse(String.valueOf(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
*/


}
