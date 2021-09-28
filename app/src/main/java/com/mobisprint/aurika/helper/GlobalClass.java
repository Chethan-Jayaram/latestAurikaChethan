package com.mobisprint.aurika.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.provider.Settings;
import android.util.Base64;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.modle.TicketModle;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.coorg.pojo.dining.DiningSubcategory;
import com.mobisprint.aurika.coorg.pojo.petservices.K9Data;
import com.mobisprint.aurika.coorg.services.APIMethods;
import com.mobisprint.aurika.retrofit.ClientServiceGenerator;
import com.mobisprint.aurika.unlock.Aurika;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class GlobalClass {

    public static final String BOTTOM_VIEW = "BOTTOM_VIEW";

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

    public static final String K9Menu_count = "K9Menu_count";
    public static final String K9Menu_price = "K9Menu_price";

    public static final String HouseKeeping_count = "HouseKeeping_count";
    public static final String HouseKeeping_price = "HouseKeeping_price";

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
    public static String check_in = "";
    public static String check_out = "";
    public static Integer number_of_guest ;
    public static String number_of_rooms = "";
    public static Integer guest_id;
    public static Integer Guest_Id;
    public static boolean user_active_booking = false;
    public static boolean logged_in = false;

    ;
    public static String APPDATA = "";
    public static String[] homeNames;
    public static DateFormat inputdateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    public static DateFormat outputdateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    public static String prefix = "494820";
    public static String suffix = "20434c";


    public static String encodeTobase64(Bitmap image) {
        String imageEncoded = "";
        try {
            Bitmap immagex = image;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            immagex.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            byte[] b = byteArrayOutputStream.toByteArray();
            imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageEncoded;
    }


    public interface AdapterListener {
        void onItemClicked(int Position);

    }


    public interface MystayListener {
        void onItemClicked(String  Amount, String FolioId);

    }

    public interface RoomNumberListener{
        void onItemClicked(String RoomNumber);
    }

    public interface NavigationListener {
        void onItemClicked(int GroupPosition,int ChildPosition, boolean decider);

    }

    public interface AmenitiesAdapterListener {
        void onItemClicked(int Position);
    }


    public interface K9AdapterListener {
        void onItemClicked(List<K9Data> data);
    }


    public interface HouseKeepingListener {
        void onItemClicked(List<Data> data);
    }

    public interface ExpandableAdapterListener {
        void onItemClicked(Data data);
    }


    public interface ExpandableAdapterListenerIRD {
        void onItemClicked(com.mobisprint.aurika.coorg.pojo.dining.Data data);

    }


    public interface CustomExpandableAdapterListenerIRD {
        void onItemClicked(int groupPos, int childPos, List<DiningSubcategory> diningSubcategory);

    }



    public interface FragmentCallback {
        void onDataSent(TicketModle ticketModle);
    }

    public interface IRDFragmentCallback {
        void onCustomizationAdded(List<Data> data );
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
            Date current=_24HourSDF.parse(time);
            Date from = _24HourSDF.parse(fromTime);
            Date to = _24HourSDF.parse(toTime);

           if(from.getTime()>to.getTime())  {
                if(current.getTime()<=to.getTime()) {
                   return true;
                }else if (current.getTime()>from.getTime()) {
                   return  true;
                }else{
                    return  false;
                }

            }else{
               if(from.getTime()<=current.getTime() &&( current.getTime()<=to.getTime()|| from.getTime()>to.getTime())){
                   return true;
                }else{
                    return  false;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void ShowAlert(Context context, String title, String message){
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Oops!");
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


    public static String round(double value, int places) {
  /*  if (places < 0) throw new IllegalArgumentException();
    long factor = (long) Math.pow(10, places);
    value = value * factor;
    long tmp = Math.round(value);
    return (double) tmp / factor;*/
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);

        return String.format("%.2f",bd.doubleValue());

    }


    public interface OnBiometricAuthSucess { // create an interface
        void onSucessfullBiometricAuth(); // create callback function
    }


}
