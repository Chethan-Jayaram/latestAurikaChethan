package com.mobisprint.aurika.coorg.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.controller.BottomDailogController;
import com.mobisprint.aurika.coorg.modle.DiningModle;
import com.mobisprint.aurika.coorg.modle.LaundryModle;
import com.mobisprint.aurika.coorg.modle.PetServicesModle;
import com.mobisprint.aurika.coorg.modle.ServiceModle;
import com.mobisprint.aurika.coorg.modle.SleepWellModle;
import com.mobisprint.aurika.coorg.modle.TicketModle;
import com.mobisprint.aurika.coorg.pojo.General;
import com.mobisprint.aurika.coorg.pojo.Services.Category_item;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.coorg.pojo.Services.SleepwellList;
import com.mobisprint.aurika.coorg.pojo.dining.Dining__1;
import com.mobisprint.aurika.coorg.pojo.petservices.K9Data;
import com.mobisprint.aurika.coorg.pojo.ticketing.Detail;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Response;


public class BottomDailogFragment extends BottomSheetDialogFragment implements ApiListner {

    private Button bt_today, bt_tomorrow, bt_back, bt_save, bt_save_order, next_order;
    private CardView select_date, cardview_today;
    private ImageView img_up_hr, img_down_hr, img_up_min, img_down_min, img_select_date_arrow, img_hour_add, img_hour_minus;
    private LinearLayout lyt_calendar, lyt_select_date, select_hours, lyt_folded_hanger,lyt_time_popup;
    private TextView tv_hr, tv_min, tv_select_date, tv_pm, tv_am, tv_hour, tv__bottomsheet_tittle,tv_sub_heading;
    private Calendar calendar,calendar1;
    private int hr, min,hr1,min1;
    private String selected_date = "", category, special_instruction  = "" ;
    private Boolean hours = false, save = false, today = false, tomorrow = false, date_selected = false,dogCake = false;
    private Integer count = 1, am;
    private CheckBox checkbox_hanger, checkbox_folded;
    private CalendarView calendar_view;
    private Date select;
    private List<Data> selectedItemList = new ArrayList<>();
    private BottomDailogController controller;
    private String title;
    private String booking, reqtime , pr_hr, pr_min,ch ;
    private String hangerType = "", requestDate,preferredRequestDate, am_or_pm;
    private ServiceModle serviceModle = new ServiceModle();
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private List<SleepwellList> sleepwellList = new ArrayList<>();
    private SleepWellModle sleepWellModle = new SleepWellModle();
    private LaundryModle laundryModle = new LaundryModle();
    private List<Category_item> laundryList = new ArrayList<>();
    private PetServicesModle petServicesModle = new PetServicesModle();
    private List<K9Data> k9SelectedItemList = new ArrayList<>();
    private DiningModle diningModle = new DiningModle();
    private List<Dining__1> diningSelectedItemList = new ArrayList<>();
    private List<Detail> list = new ArrayList<>();
    private TicketModle ticketModle = new TicketModle();
    private Date dt = new Date();
    private Date dtt = new Date();
    private Date dtt1 = new Date();
    private Date dtt2 = new Date();
    private Date dtt3 = new Date();
    private Date dtt4 = new Date(), date5;
    private Calendar c = Calendar.getInstance();
    private Calendar c22 = Calendar.getInstance();
    private Detail detail = new Detail();
    private GlobalClass.FragmentCallback fragmentCallback;
    private Date date,selected_time,current_time,date1;
    private DateFormat formatter = new SimpleDateFormat("hh:mm a");
    private DateTimeFormatter dtf1;
    private DateFormat fr = new SimpleDateFormat("hh:mm");
    private EditText et_special_instruction;

    private boolean trmin = false, twohr = false, frhr = false;

    private Calendar mindate1,mindate2, checkout_date;

    private String sel_date="",c_time,item_name, ticketType;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bottom_sheet_dailog, container, false);

        controller = new BottomDailogController(this);
        lyt_time_popup = view.findViewById(R.id.lyt_time_popup);


        booking = String.valueOf(GlobalClass.Guest_Id);

        try {
            checkout_date = Calendar.getInstance();
            Date checkoutDate =new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(GlobalClass.check_out);
            SimpleDateFormat dateFormatcheckout = new SimpleDateFormat("dd-MM-yyyy");
            ch = dateFormatcheckout.format(checkoutDate);
            Log.d("checkkkk",ch);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date dd = new Date();
        mindate1 = Calendar.getInstance();
        mindate2 = Calendar.getInstance();
        mindate1.setTime(dd);
        mindate2.setTime(dd);

        mindate1.add(Calendar.DATE,1);
        mindate2.add(Calendar.DATE,0);

        tv_sub_heading = view.findViewById(R.id.tv_sub_heading);
        tv_sub_heading.setVisibility(View.GONE);

        bt_today = view.findViewById(R.id.bt_today);
        bt_tomorrow = view.findViewById(R.id.bt_tomorrow);
        select_date = view.findViewById(R.id.select_date);
        img_up_hr = view.findViewById(R.id.img_up_hr);
        img_down_hr = view.findViewById(R.id.img_down_hr);
        select_hours = view.findViewById(R.id.select_hours);
        et_special_instruction= view.findViewById(R.id.et_special_instruction);


        next_order = view.findViewById(R.id.next_order);

        lyt_calendar = view.findViewById(R.id.lyt_calendar);
        lyt_select_date = view.findViewById(R.id.lyt_select_date);
        lyt_select_date.setVisibility(View.VISIBLE);

        cardview_today = view.findViewById(R.id.cardview_today);

        img_up_min = view.findViewById(R.id.img_up_min);
        img_down_min = view.findViewById(R.id.img_down_min);
        /*img_select_date_arrow = view.findViewById(R.id.img_select_date_arrow);*/

        checkbox_hanger = view.findViewById(R.id.checkbox_hanger);
        checkbox_folded = view.findViewById(R.id.checkbox_folded);

        lyt_folded_hanger = view.findViewById(R.id.folded_hanger);

        img_hour_add = view.findViewById(R.id.img_hour_add);
        img_hour_minus = view.findViewById(R.id.img_hour_minus);

        tv__bottomsheet_tittle = view.findViewById(R.id.tv__bottomsheet_tittle);

        tv_hr = view.findViewById(R.id.tv_hr);
        tv_min = view.findViewById(R.id.tv_min);

        tv_select_date = view.findViewById(R.id.tv_select_date);

        tv_hour = view.findViewById(R.id.tv_hour);

        tv_pm = view.findViewById(R.id.tv_pm);
        tv_am = view.findViewById(R.id.tv_am);

        bt_back = view.findViewById(R.id.bt_back);
        bt_save = view.findViewById(R.id.bt_save);
        bt_save_order = view.findViewById(R.id.bt_save_order);

        calendar_view = view.findViewById(R.id.calendar_view);

        Bundle bundle = getArguments();
        category = bundle.getString("Category");

        calendar = Calendar.getInstance();


        /*DateFormat dateFormat = new SimpleDateFormat("hh");
        String dateString = dateFormat.format(new Date()).toString();*/

        /*hr = Integer.parseInt(dateString);*/

        if (category.equalsIgnoreCase("housekeeping")){
             item_name = bundle.getString("item_name");
        }






        if (category.equalsIgnoreCase("laundry") || category.equalsIgnoreCase("wine-and-dine")){
            calendar.add(Calendar.HOUR,1);
            calendar.add(Calendar.MINUTE,1);
        }else if (category.equalsIgnoreCase("spa")){
            calendar.add(Calendar.HOUR,2);
            calendar.add(Calendar.MINUTE,1);
        }else if(category.equalsIgnoreCase("housekeeping")){

            if (item_name.equalsIgnoreCase("Clean My Room") || item_name.equalsIgnoreCase("Turn Down Service")){
                trmin = true;
                twohr = false;
                frhr = false;
                calendar.add(Calendar.MINUTE,31);

            }else if (item_name.equalsIgnoreCase("Baby Cot") || item_name.equalsIgnoreCase("Extra Bed")){
                trmin = false;
                twohr = true;
                frhr = false;
                calendar.add(Calendar.HOUR,2);
                calendar.add(Calendar.MINUTE,1);

            }if (item_name.equalsIgnoreCase("Baby Sitting Service (Per Hour)")){
                trmin = false;
                twohr = false;
                frhr = true;
                calendar.add(Calendar.HOUR,4);
                calendar.add(Calendar.MINUTE,1);
            }else{
                calendar.add(Calendar.MINUTE,31);
            }
        }else {
            calendar.add(Calendar.MINUTE,31);
        }

        if (calendar.get(Calendar.HOUR) == 0){
            hr=12;
        }else{
            hr= calendar.get(Calendar.HOUR);
        }


        min = calendar.get(Calendar.MINUTE);
        reqtime = hr + ":" + min;

        /*c_time = reqtime;*/


        am = calendar.get(Calendar.HOUR_OF_DAY);



        bt_today.setBackgroundColor(getResources().getColor(R.color.custom_purple));
        bt_today.setTextColor(Color.WHITE);



        dtf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm");
        LocalDateTime now = LocalDateTime.now();
        requestDate = dtf.format(now);
        /*preferredRequestDate = dtf1.format(now);*/
        title = category + " Ticket";

        preferredRequestDate = dtf1.format(now);

        sel_date = dtf1.format(now);

        checkbox_folded.setChecked(true);

        lyt_folded_hanger.setVisibility(View.GONE);

        switch (category) {
            case "housekeeping":
                selectedItemList.clear();
                save = false;
                today = true;

                tv_sub_heading.setVisibility(View.VISIBLE);

                if (item_name.equalsIgnoreCase("Clean My Room") || item_name.equalsIgnoreCase("Turn Down Service")){
                    tv_sub_heading.setText("Services available from 6 AM to 10 PM");
                }else if (item_name.equalsIgnoreCase("Baby Cot") || item_name.equalsIgnoreCase("Extra Bed")){
                    tv_sub_heading.setText("Services available from 10 AM to 8 PM");

                }if (item_name.equalsIgnoreCase("Baby Sitting Service (Per Hour)")){
                tv_sub_heading.setText("Services available from 6 AM to 10 PM");
            }

                if (bundle.getBoolean("timepop")){
                    lyt_time_popup.setVisibility(View.VISIBLE);
                }else{
                    lyt_time_popup.setVisibility(View.GONE);
                }

                if (bundle.getBoolean("hours")) {
                    select_hours.setVisibility(View.VISIBLE);
                    serviceModle.setRequestHours(tv_hour.getText().toString());
                } else {
                    select_hours.setVisibility(View.GONE);
                }
                selectedItemList = bundle.getParcelableArrayList("List");
                serviceModle.setDetails(selectedItemList);
                serviceModle.setDepartment(category);
                serviceModle.setTitle(title);
                serviceModle.setBooking(booking);
                serviceModle.setRoomNumber(GlobalClass.ROOM_NO);
                serviceModle.setRequestTime(reqtime);
                break;

            case "k9-services":
                tv_sub_heading.setVisibility(View.GONE);

                today = true;
                k9SelectedItemList.clear();
                if (bundle.getBoolean("hours")) {
                    select_hours.setVisibility(View.VISIBLE);
                    petServicesModle.setRequestHours(tv_hour.getText().toString());
                } else {
                    select_hours.setVisibility(View.GONE);
                }
                k9SelectedItemList = bundle.getParcelableArrayList("List");
                petServicesModle.setDetails(k9SelectedItemList);
                petServicesModle.setDepartment(category);
                petServicesModle.setTitle(title);
                petServicesModle.setBooking(booking);
                petServicesModle.setRoomNumber(GlobalClass.ROOM_NO);

                petServicesModle.setRequestTime(reqtime);
                break;
            case "laundry":
                tv_sub_heading.setVisibility(View.VISIBLE);
                today = true;
                laundryList.clear();
                save = true;
                lyt_select_date.setVisibility(View.GONE);
                lyt_folded_hanger.setVisibility(View.VISIBLE);
                tv_sub_heading.setText("Services available from 8 AM to 5 PM");
                laundryList = bundle.getParcelableArrayList("List");
                laundryModle.setDetails(laundryList);
                laundryModle.setDepartment(category);
                laundryModle.setTitle(title);
                laundryModle.setBooking(booking);
                laundryModle.setRoomNumber(GlobalClass.ROOM_NO);

                laundryModle.setRequestTime(reqtime);
                break;

            case "k9-menu":
                tv_sub_heading.setVisibility(View.GONE);

                k9SelectedItemList.clear();
                tomorrow = true;
                if (bundle.getBoolean("DogCake")){
                    lyt_time_popup.setVisibility(View.VISIBLE);
                    dogCake = true;
                    cardview_today.setVisibility(View.GONE);
                    bt_tomorrow.setBackgroundColor(getResources().getColor(R.color.custom_purple));
                    bt_tomorrow.setTextColor(Color.WHITE);
                    c.setTime(dt);
                    c.add(Calendar.DATE, 1);
                    dt = c.getTime();
                    requestDate = format.format(dt) + " " + reqtime;
                } else {
                    lyt_time_popup.setVisibility(View.GONE);
                    dogCake = false;
                    cardview_today.setVisibility(View.VISIBLE);
                }
                k9SelectedItemList.clear();
                k9SelectedItemList = bundle.getParcelableArrayList("List");
                petServicesModle.setDetails(k9SelectedItemList);
                petServicesModle.setDepartment(category);
                petServicesModle.setTitle(title);
                petServicesModle.setBooking(booking);
                petServicesModle.setRoomNumber(GlobalClass.ROOM_NO);
                petServicesModle.setRequestTime(reqtime);

                break;
            case "amenities":
                tv_sub_heading.setVisibility(View.GONE);

                lyt_time_popup.setVisibility(View.GONE);
                today = true;
                selectedItemList.clear();
                save = false;
                selectedItemList = bundle.getParcelableArrayList("List");
                serviceModle.setDetails(selectedItemList);
                serviceModle.setDepartment(category);
                serviceModle.setTitle(title);
                serviceModle.setBooking(booking);

                serviceModle.setRoomNumber(GlobalClass.ROOM_NO);

                serviceModle.setRequestTime(reqtime);

                break;

            case "sleepwell":
                tv_sub_heading.setVisibility(View.GONE);


                ticketType = bundle.getString("ticketType");

                if (ticketType.equalsIgnoreCase("housekeeping")){
                    sleepWellModle.setDepartment(ticketType);

                }else if (ticketType.equalsIgnoreCase("in-room-dining")){
                    sleepWellModle.setDepartment(ticketType);
                }
                title = ticketType + " Ticket";

                today = true;
                lyt_time_popup.setVisibility(View.GONE);
                sleepwellList.clear();
                save = false;
                sleepwellList = bundle.getParcelableArrayList("List");
                sleepWellModle.setDetails(sleepwellList);
                sleepWellModle.setTitle(title);
                sleepWellModle.setBooking(booking);

                sleepWellModle.setRoomNumber(GlobalClass.ROOM_NO);

                sleepWellModle.setRequestTime(reqtime);
                break;

            case "k9-amenities":
                tv_sub_heading.setVisibility(View.GONE);

                today = true;
                lyt_time_popup.setVisibility(View.GONE);
                k9SelectedItemList.clear();
                k9SelectedItemList = bundle.getParcelableArrayList("List");
                petServicesModle.setDetails(k9SelectedItemList);
                petServicesModle.setDepartment(category);
                petServicesModle.setTitle(title);
                petServicesModle.setBooking(booking);

                petServicesModle.setRoomNumber(GlobalClass.ROOM_NO);

                petServicesModle.setRequestTime(reqtime);
                break;


            case "in-room-dining":
                tv_sub_heading.setVisibility(View.GONE);

                today = true;
                lyt_time_popup.setVisibility(View.GONE);
                diningSelectedItemList.clear();
                diningSelectedItemList = bundle.getParcelableArrayList("List");
                diningModle.setDetails(diningSelectedItemList);
                diningModle.setDepartment(category);
                diningModle.setTitle(title);
                diningModle.setBooking(booking);
                diningModle.setRoomNumber(GlobalClass.ROOM_NO);
                diningModle.setRequestTime(reqtime);
                break;

            case "wine-and-dine":
                tv_sub_heading.setVisibility(View.GONE);
                today = true;
                detail.setTitle(bundle.getString("title"));
                detail.setNumberOfGuest(bundle.getInt("GuestCount"));
                detail.setItem_id(bundle.getInt("item_id"));
                list.clear();
                list.add(detail);
                ticketModle.setDetails(list);
                ticketModle.setDepartment(category);
                ticketModle.setTitle(title);
                ticketModle.setBooking(booking);

                ticketModle.setRoomNumber(GlobalClass.ROOM_NO);

                ticketModle.setRequestTime(reqtime);

                break;

            case "spa":
                today = true;
                tv_sub_heading.setVisibility(View.VISIBLE);
                tv_sub_heading.setText("Services available from 9 AM to 10 PM");
                detail.setTitle(bundle.getString("title"));
                detail.setItem_id(bundle.getInt("item_id"));
                double d= Double.parseDouble(bundle.getString("price"));
                /*Double lat=Double.parseDouble(bundle.getString("price"));*/
                detail.setPrice(d);
                list.clear();
                list.add(detail);
                ticketModle.setDetails(list);
                ticketModle.setDepartment(category);
                ticketModle.setTitle(title);
                ticketModle.setBooking(booking);
                ticketModle.setRoomNumber(GlobalClass.ROOM_NO);
                ticketModle.setRequestTime(reqtime);
        }


        bt_save_order.setOnClickListener(v -> {


           /* requestDate = sel_date + " " + reqtime;*/


            special_instruction = et_special_instruction.getText().toString();

            calendar1 = Calendar.getInstance();

            if (category.equalsIgnoreCase("laundry") || category.equalsIgnoreCase("wine-and-dine")){
                calendar1.add(Calendar.HOUR,1);
            }else if (category.equalsIgnoreCase("spa")){
                calendar1.add(Calendar.HOUR,2);
            }else if(category.equalsIgnoreCase("housekeeping")){

                if (item_name.equalsIgnoreCase("Clean My Room") || item_name.equalsIgnoreCase("Turn Down Service")){
                    calendar1.add(Calendar.MINUTE,30);

                }else if (item_name.equalsIgnoreCase("Baby Cot") || item_name.equalsIgnoreCase("Extra Bed")){
                    calendar1.add(Calendar.HOUR,2);

                }if (item_name.equalsIgnoreCase("Baby Sitting Service (Per Hour)")){
                    calendar1.add(Calendar.HOUR,4);
                }
            }else{
                calendar1.add(Calendar.MINUTE,30);
            }





            if (calendar1.get(Calendar.HOUR) == 0){
                hr1=12;
            }else{
                hr1= calendar1.get(Calendar.HOUR);
            }


            min1 = calendar1.get(Calendar.MINUTE);


            c_time = hr1 + ":" + min1;

            reqtime = hr + ":" + min;

            DateFormat sdf = new SimpleDateFormat("h:mm");
            try {
                date = sdf.parse(reqtime);
                date1 = sdf.parse(c_time);
                selected_time = sdf.parse(sdf.format(new Date()));
                current_time = sdf.parse(c_time);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            switch (category) {

                case "housekeeping":

                    if (bundle.getBoolean("timepop")){
                        requestDate = sel_date + " " + reqtime;
                    }else{
                        requestDate = null;
                    }

                    serviceModle.setSpecial_instructions(special_instruction);

                    if (today){
                        if (date.after(current_time)){
                            serviceModle.setRequestDate(requestDate);
                            controller.ticketingCreation(serviceModle);
                            break;
                        }else{
                            if (trmin){
                                GlobalClass.ShowAlert(getContext(),"Alert","Order time should be 30 minutes after the current time");
                            }else if (twohr) {
                                GlobalClass.ShowAlert(getContext(),"Alert","Order time should be 2 hours after the current time");
                            }else if (frhr){
                                GlobalClass.ShowAlert(getContext(),"Alert","Order time should be 4 hours after the current time");
                            }
                        }
                    }else if (date_selected) {
                        Calendar css4 = Calendar.getInstance();
                        dtt4 = css4.getTime();
                        sel_date = format.format(dtt4);

                        if (format.format(select).equals(format.format(dtt4))){
                            if (date.after(current_time)){
                                serviceModle.setRequestDate(requestDate);
                                controller.ticketingCreation(serviceModle);
                                break;
                            }else {
                                if (trmin){
                                    GlobalClass.ShowAlert(getContext(),"Alert","Order time should be 30 minutes after the current time");
                                }else if (twohr) {
                                    GlobalClass.ShowAlert(getContext(),"Alert","Order time should be 2 hours after the current time");
                                }else if (frhr){
                                    GlobalClass.ShowAlert(getContext(),"Alert","Order time should be 4 hours after the current time");
                                }
                            }
                        }else if (select.after(dtt4)){
                            serviceModle.setRequestDate(requestDate);
                            controller.ticketingCreation(serviceModle);
                            break;
                        }else{
                            if (trmin){
                                GlobalClass.ShowAlert(getContext(),"Alert","Order time should be 30 minutes after the current time");
                            }else if (twohr) {
                                GlobalClass.ShowAlert(getContext(),"Alert","Order time should be 2 hours after the current time");
                            }else if (frhr){
                                GlobalClass.ShowAlert(getContext(),"Alert","Order time should be 4 hours after the current time");
                            }
                        }
                    }else{
                        serviceModle.setRequestDate(requestDate);
                        controller.ticketingCreation(serviceModle);
                        break;
                    }
                    break;

                case "amenities":

                    requestDate = null;
                    serviceModle.setSpecial_instructions(special_instruction);
                    serviceModle.setRequestDate(requestDate);
                    controller.ticketingCreation(serviceModle);


                    /*if (today){
                        if (date.after(current_time)){
                            serviceModle.setRequestDate(requestDate);
                            controller.ticketingCreation(serviceModle);
                            break;
                        }else{
                            GlobalClass.ShowAlert(getContext(),"Alert","Order time should be 30m after the current time");
                        }
                    }else{
                        serviceModle.setRequestDate(requestDate);
                        controller.ticketingCreation(serviceModle);
                        break;
                    }*/
                    break;

                case "sleepwell":
                    requestDate = sel_date + " " + reqtime;

                    sleepWellModle.setSpecial_instructions(special_instruction);

                    if (today){
                        if (date.after(current_time)){
                            sleepWellModle.setRequestDate(null);
                            controller.ticketingCreation(sleepWellModle);
                            break;
                        }else{
                            GlobalClass.ShowAlert(getContext(),"Alert","Order time should be 30 minutes after the current time");
                        }
                    }else{
                        sleepWellModle.setRequestDate(requestDate);
                        controller.ticketingCreation(sleepWellModle);
                        break;
                    }


                    break;
                case "laundry":
                    requestDate = sel_date + " " + reqtime;
                    laundryModle.setSpecial_instructions(special_instruction);

                    if (today){
                        if (date.after(current_time)){
                            laundryModle.setRequestDate(requestDate);
                            controller.ticketingCreation(laundryModle);
                            break;
                        }else{
                            GlobalClass.ShowAlert(getContext(),"Alert","Order time should be 1 hour after the current time");
                        }
                    }else if (date_selected){
                        Calendar css1 = Calendar.getInstance();
                        dtt1 = css1.getTime();
                        sel_date = format.format(dtt1);

                        if (format.format(select).equals(format.format(dtt1))){
                            if (date.after(current_time)){
                                laundryModle.setRequestDate(requestDate);
                                controller.ticketingCreation(laundryModle);
                            }else {
                                GlobalClass.ShowAlert(getContext(),"Alert","Order time should be 1 hour after the current time");
                            }
                        }else if (select.after(dtt1)){
                            laundryModle.setRequestDate(requestDate);
                            controller.ticketingCreation(laundryModle);
                        }else{
                            GlobalClass.ShowAlert(getContext(),"Alert","Order time should be 1 hour after the current time");
                        }

                    }else{
                        laundryModle.setRequestDate(requestDate);
                        controller.ticketingCreation(laundryModle);
                        break;
                    }
                    break;

                case "k9-amenities":
                    requestDate = sel_date + " " + reqtime;
                    petServicesModle.setSpecial_instructions(special_instruction);
                    if (today){
                        if (date.after(current_time)){
                            petServicesModle.setRequestDate(null);
                            controller.k9TicketCreation(petServicesModle);
                            break;
                        }else{
                            GlobalClass.ShowAlert(getContext(),"Alert","Please select above 30 minutes from current time");
                        }
                    }else{
                        petServicesModle.setRequestDate(requestDate);
                        controller.k9TicketCreation(petServicesModle);
                        break;
                    }
                    break;
                case "k9-services":
                    requestDate = sel_date + " " + reqtime;
                    petServicesModle.setSpecial_instructions(special_instruction);

                    if (today){
                        if (date.after(current_time)){
                            petServicesModle.setRequestDate(requestDate);
                            controller.k9TicketCreation(petServicesModle);
                            break;
                        }else{
                            GlobalClass.ShowAlert(getContext(),"Alert","Please select above 30 minutes from current time");
                        }
                    }else{
                        petServicesModle.setRequestDate(requestDate);
                        controller.k9TicketCreation(petServicesModle);
                        break;
                    }
                    break;


                case "in-room-dining":
                    diningModle.setSpecial_instructions(special_instruction);
                    requestDate = sel_date + " " + reqtime;
                    diningModle.setRequestDate(requestDate);
                    controller.diningTicketCreation(diningModle);
                    break;



                case "k9-menu":

                    petServicesModle.setSpecial_instructions(special_instruction);

                    if (dogCake){
                        petServicesModle.setRequestDate(requestDate);
                    }else{
                        petServicesModle.setRequestDate(null);
                    }

                    if (bundle.getBoolean("DogCake") && tomorrow){
                        if (date.after(selected_time)){
                            controller.k9TicketCreation(petServicesModle);
                        }else {
                            GlobalClass.ShowAlert(getContext(),"Alert","Selected time cannot be less than 24 hours for Dog cake");
                        }
                    }else if (date_selected){
                        Calendar css = Calendar.getInstance();
                        css.add(Calendar.DATE, 1);
                        dtt = css.getTime();
                        sel_date = format.format(dtt);

                         if (format.format(select).equals(format.format(dtt))){
                            if (date.after(selected_time)){
                                controller.k9TicketCreation(petServicesModle);
                            }else {
                                GlobalClass.ShowAlert(getContext(),"Alert","Selected time cannot be less than 24 hours for Dog cake");
                            }
                        }else if (select.after(dtt)){
                             controller.k9TicketCreation(petServicesModle);
                         }else{
                            GlobalClass.ShowAlert(getContext(),"Alert","Selected time cannot be less than 24 hours for Dog cake");
                        }


                    }
                    else{
                        controller.k9TicketCreation(petServicesModle);
                    }

                    break;

                case "wine-and-dine":
                    ticketModle.setSpecial_instructions(special_instruction);
                    requestDate = sel_date + " " + reqtime;
                    ticketModle.setRequestDate(requestDate);
                    /*controller.generalTicket(ticketModle);*/
                    preferredRequestDate = "";
                    String time = pr_hr + ":"  + pr_min;

                    try {
                        Date date = fr.parse(time);
                        preferredRequestDate = sel_date + " " + fr.format(date) + " " + am_or_pm;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    ticketModle.setPersonalisedTime(preferredRequestDate);

                    if (today){
                        if (date.after(current_time)){
                            ticketModle.setRequestDate(requestDate);
                            this.dismiss();
                            fragmentCallback.onDataSent(ticketModle);
                            break;
                        }else{
                            GlobalClass.ShowAlert(getContext(),"Alert","Please select above 1 hour from current time");
                        }
                    }else if (date_selected){
                        Calendar css2 = Calendar.getInstance();
                        dtt2 = css2.getTime();
                        sel_date = format.format(dtt2);

                        if (format.format(select).equals(format.format(dtt2))){
                            if (date.after(current_time)){
                                ticketModle.setRequestDate(requestDate);
                                this.dismiss();
                                fragmentCallback.onDataSent(ticketModle);
                                break;
                            }else {
                                GlobalClass.ShowAlert(getContext(),"Alert","Please select above 1 hour from current time");
                            }
                        }else if (select.after(dtt2)){
                            ticketModle.setRequestDate(requestDate);
                            this.dismiss();
                            fragmentCallback.onDataSent(ticketModle);
                            break;
                        }else{
                            GlobalClass.ShowAlert(getContext(),"Alert","Please select above 1 hour from current time");
                        }

                    }else{
                        ticketModle.setRequestDate(requestDate);
                        this.dismiss();
                        fragmentCallback.onDataSent(ticketModle);
                    }
                    break;

                case "spa":
                    ticketModle.setSpecial_instructions(special_instruction);
                    requestDate = sel_date + " " + reqtime;
                    preferredRequestDate = "";
                    String time1 = pr_hr + ":"  + pr_min;

                    try {
                        Date date1 = fr.parse(time1);
                        preferredRequestDate = sel_date + " " + fr.format(date1) + " " + am_or_pm;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    ticketModle.setPersonalisedTime(preferredRequestDate);


                    if (today){
                        if (date.after(current_time)){
                            ticketModle.setRequestDate(requestDate);
                            this.dismiss();
                            fragmentCallback.onDataSent(ticketModle);
                            break;
                        }else{
                            GlobalClass.ShowAlert(getContext(),"Alert","Please select above 2 hour from current time");
                        }
                    }else if(date_selected){
                        Calendar css3 = Calendar.getInstance();
                        dtt3 = css3.getTime();
                        sel_date = format.format(dtt3);

                        if (format.format(select).equals(format.format(dtt3))){
                            if (date.after(current_time)){
                                ticketModle.setRequestDate(requestDate);
                                this.dismiss();
                                fragmentCallback.onDataSent(ticketModle);
                                break;
                            }else {
                                GlobalClass.ShowAlert(getContext(),"Alert","Please select above 2 hour from current time");
                            }
                        }else if (select.after(dtt3)){
                            ticketModle.setRequestDate(requestDate);
                            this.dismiss();
                            fragmentCallback.onDataSent(ticketModle);
                            break;
                        }else{
                            GlobalClass.ShowAlert(getContext(),"Alert","Please select above 2 hour from current time");
                        }
                    } else{
                        ticketModle.setRequestDate(requestDate);
                        this.dismiss();
                        fragmentCallback.onDataSent(ticketModle);
                    }


                    break;

            }


        });

        next_order.setOnClickListener(v -> {
            lyt_select_date.setVisibility(View.VISIBLE);
            lyt_folded_hanger.setVisibility(View.GONE);

            if (checkbox_folded.isChecked()) {
                laundryModle.setHangertype("Folded");

            } else if (checkbox_hanger.isChecked()) {
                laundryModle.setHangertype("On Hanger");
            }


        });


        checkbox_folded.setOnClickListener(v -> {
            checkbox_hanger.setChecked(false);
        });

        checkbox_hanger.setOnClickListener(v -> {
            checkbox_folded.setChecked(false);
        });

        tv_min.setText(String.valueOf(min) );
        tv_hr.setText(String.valueOf(hr) );
        pr_hr = String.valueOf(hr);
        pr_min = String.valueOf(min);


        if (am >= 12) {
            tv_pm.setTextColor(Color.BLACK);
            tv_am.setTextColor(Color.parseColor("#a5a5a5"));
            am_or_pm = "pm";


        } else {
            tv_pm.setTextColor(Color.parseColor("#a5a5a5"));
            tv_am.setTextColor(Color.BLACK);
            am_or_pm = "am";
        }


        tv_am.setOnClickListener(v -> {
            tv_pm.setTextColor(Color.parseColor("#a5a5a5"));
            tv_am.setTextColor(Color.BLACK);
            am_or_pm = "am";
        });

        tv_pm.setOnClickListener(v -> {
            tv_pm.setTextColor(Color.BLACK);
            tv_am.setTextColor(Color.parseColor("#a5a5a5"));
            am_or_pm = "pm";
        });



        img_up_hr.setOnClickListener(v -> {
            if (hr < 12) {
                hr = hr + 1;
                tv_hr.setText(String.valueOf(hr));
                pr_hr = String.valueOf(hr);
                reqtime = hr + ":" + min;
                updateReqTime(reqtime);


            }
        });

        img_down_hr.setOnClickListener(v -> {
            if (hr > 1) {
                hr = hr - 1;
                tv_hr.setText(String.valueOf(hr));
                pr_hr = String.valueOf(hr);
                reqtime = hr + ":" + min;
                updateReqTime(reqtime);
            }
        });

        img_up_min.setOnClickListener(v -> {
            if (min < 59) {
                min = min + 1;
                tv_min.setText(String.valueOf(min));
                pr_min = String.valueOf(min);
                reqtime = hr + ":" + min;
                updateReqTime(reqtime);
            }
        });


        img_down_min.setOnClickListener(v -> {
            if (min > 1) {
                min = min - 1;
                tv_min.setText(String.valueOf(min));
                pr_min = String.valueOf(min);
                reqtime = hr + ":" + min;
                updateReqTime(reqtime);
            }
        });


        img_hour_add.setOnClickListener(v -> {
            if (count < 12) {
                count = count + 1;
                if (count == 1) {
                    tv_hour.setText(count + " hour");

                    updateReqHour(tv_hour);

                } else {
                    tv_hour.setText(count + " hours");
                    updateReqHour(tv_hour);
                }
            }
        });

        img_hour_minus.setOnClickListener(v -> {
            if (count > 1) {
                count = count - 1;
                if (count == 1) {
                    tv_hour.setText(count + " hour");
                    updateReqHour(tv_hour);

                } else {
                    tv_hour.setText(count + " hours");
                    updateReqHour(tv_hour);

                }
            }
        });


        bt_today.setOnClickListener(v -> {

                tomorrow = false;
                today = true;
                date_selected = false;

                bt_today.setBackgroundColor(getResources().getColor(R.color.custom_purple));
                bt_today.setTextColor(Color.WHITE);
                bt_tomorrow.setBackgroundColor(getResources().getColor(R.color.white));
                bt_tomorrow.setTextColor(Color.parseColor("#a5a5a5"));
                tv_select_date.setText("Select Date");
                tv_select_date.setTextColor(Color.parseColor("#a5a5a5"));
                select_date.setBackgroundResource(R.drawable.cardview_corner_radius_white);
                /*img_select_date_arrow.setImageResource(R.drawable.ccp_down_arrow);*/

                requestDate = String.valueOf(java.time.LocalDate.now()) + " " + reqtime;

                preferredRequestDate = String.valueOf(java.time.LocalDate.now()) + " " + pr_hr + ":"  + pr_min + " " + am_or_pm;


                sel_date = String.valueOf(java.time.LocalDate.now());


                Log.d("selectedDate", requestDate);
                updateReqDate(requestDate);

        });

        bt_tomorrow.setOnClickListener(v -> {

            tomorrow = true;
            today = false;
            date_selected = false;

            bt_tomorrow.setBackgroundColor(getResources().getColor(R.color.custom_purple));
            bt_tomorrow.setTextColor(Color.WHITE);
            bt_today.setBackgroundColor(getResources().getColor(R.color.white));
            bt_today.setTextColor(Color.parseColor("#a5a5a5"));
            tv_select_date.setText("Select Date");
            tv_select_date.setTextColor(Color.parseColor("#a5a5a5"));
            select_date.setBackgroundResource(R.drawable.cardview_corner_radius_white);
            /*img_select_date_arrow.setImageResource(R.drawable.ccp_down_arrow);*/
            c.setTime(dt);
            c.add(Calendar.DATE, 1);
            dt = c.getTime();
            requestDate = format.format(dt) + " " + reqtime;
            preferredRequestDate = format.format(dt) + " " + pr_hr + ":"  + pr_min + " " + am_or_pm;

            sel_date = format.format(dt);

            Log.d("selectedDate", requestDate);
            updateReqDate(requestDate);
        });


        bt_save.setOnClickListener(v1 -> {
            lyt_calendar.setVisibility(View.GONE);
            tv_select_date.setTextColor(Color.WHITE);
            select_date.setBackgroundResource(R.drawable.cardview_corner_radius);
            /*img_select_date_arrow.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24);*/
            lyt_select_date.setVisibility(View.VISIBLE);

            if(selected_date == ""){
                if (dogCake){
                    Calendar csss = Calendar.getInstance();
                    csss.add(Calendar.DATE, 1);
                    Date dsss = new Date();
                    dsss = csss.getTime();
                    sel_date = format.format(dsss);
                    tv_select_date.setText(format.format(dsss));
                    select = dsss;

                }else{
                    tv_select_date.setText( String.valueOf(java.time.LocalDate.now()));
                    sel_date = String.valueOf(java.time.LocalDate.now());
                    select = c22.getTime();
                }
            }else{
                tv_select_date.setText(selected_date);
                requestDate = selected_date + " " + reqtime;
                sel_date = selected_date;
            }



            preferredRequestDate = selected_date + " " + pr_hr + ":"  + pr_min + " " + am_or_pm;
            Log.d("selectedDate", requestDate);
            updateReqDate(requestDate);
        });

        bt_back.setOnClickListener(v -> {

            if (dogCake){

                lyt_calendar.setVisibility(View.GONE);
                lyt_select_date.setVisibility(View.VISIBLE);
                tomorrow = true;
                today = false;
                date_selected = false;

                bt_tomorrow.setBackgroundColor(getResources().getColor(R.color.custom_purple));
                bt_tomorrow.setTextColor(Color.WHITE);
                bt_today.setBackgroundColor(getResources().getColor(R.color.white));
                bt_today.setTextColor(Color.parseColor("#a5a5a5"));
                tv_select_date.setText("Select Date");
                tv_select_date.setTextColor(Color.parseColor("#a5a5a5"));
                select_date.setBackgroundResource(R.drawable.cardview_corner_radius_white);
                /*img_select_date_arrow.setImageResource(R.drawable.ccp_down_arrow);*/
                Calendar cc = Calendar.getInstance();
                cc.add(Calendar.DATE, 1);
                Date d = new Date();
                d = cc.getTime();
                requestDate = format.format(d) + " " + reqtime;
                preferredRequestDate = format.format(d) + " " + pr_hr + ":"  + pr_min + " " + am_or_pm;

                sel_date = format.format(d);

                Log.d("selectedDate", requestDate);
                updateReqDate(requestDate);
            }else{
                tomorrow = false;
            today = true;
            date_selected = false;

            lyt_calendar.setVisibility(View.GONE);
            lyt_select_date.setVisibility(View.VISIBLE);

            bt_today.setBackgroundColor(getResources().getColor(R.color.custom_purple));
            bt_today.setTextColor(Color.WHITE);
            bt_tomorrow.setBackgroundColor(getResources().getColor(R.color.white));
            bt_tomorrow.setTextColor(Color.parseColor("#a5a5a5"));
            tv_select_date.setText("Select Date");
            tv_select_date.setTextColor(Color.parseColor("#a5a5a5"));
            select_date.setBackgroundResource(R.drawable.cardview_corner_radius_white);
            /*img_select_date_arrow.setImageResource(R.drawable.ccp_down_arrow);*/

            requestDate = String.valueOf(java.time.LocalDate.now()) + " " + reqtime;

            preferredRequestDate = String.valueOf(java.time.LocalDate.now()) + " " + pr_hr + ":"  + pr_min + " " + am_or_pm;


            sel_date = String.valueOf(java.time.LocalDate.now());
            }


        });


        select_date.setOnClickListener(v -> {

            tomorrow = false;
            today = false;
            date_selected = true;
            lyt_calendar.setVisibility(View.VISIBLE);
            lyt_select_date.setVisibility(View.GONE);

            Date c1 = Calendar.getInstance().getTime();

            SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
            date5 = null;
            try {
                date5 = sdf2.parse(ch);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cal5 = Calendar.getInstance();
            cal5.setTime(date5);


            if (dogCake){
                calendar_view.setMinimumDate(mindate1);
            }else{
                calendar_view.setMinimumDate(mindate2);
            }



            calendar_view.setMaximumDate(cal5);
            Log.d("checkout_date", String.valueOf(checkout_date));


            if (selected_date == null || selected_date.isEmpty()) {
                try {
                    if (dogCake){
                        calendar_view.setDate(mindate1);
                    }else{
                        calendar_view.setDate(mindate2);
                    }
                } catch (OutOfDateRangeException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    calendar_view.setDate(select);
                } catch (OutOfDateRangeException e) {
                    e.printStackTrace();
                }
            }


            calendar_view.setOnDayClickListener(eventDay -> {
                Calendar clickedDayCalendar = eventDay.getCalendar();

                Date date = clickedDayCalendar.getTime();
                Log.d("dateeee", String.valueOf(date));
                Log.d("nodate", String.valueOf(c1));
                if (date.after(c1) && (date.equals(date5) || date.before(date5))){
                    selected_date = format.format(clickedDayCalendar.getTime());
                    select = clickedDayCalendar.getTime();
                }else{
                    if (dogCake){
                        Date dttt = new Date();
                        Calendar cc = Calendar.getInstance();
                        cc.setTime(dttt);
                        cc.add(Calendar.DATE, 1);
                        dttt = c.getTime();
                        LocalDateTime ldt = LocalDateTime.now().plusDays(1);
                        DateTimeFormatter formmat1 = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
                        selected_date = formmat1.format(ldt);
                        select = dttt;
                    }else{
                        selected_date = format.format(c1.getTime());
                        select = c22.getTime();
                    }

                }

            });

            bt_today.setBackgroundColor(getResources().getColor(R.color.white));
            bt_today.setTextColor(Color.parseColor("#a5a5a5"));
            bt_tomorrow.setBackgroundColor(getResources().getColor(R.color.white));
            bt_tomorrow.setTextColor(Color.parseColor("#a5a5a5"));

        });

        return view;
    }

    private void updateReqTime(String reqtime) {
        serviceModle.setRequestTime(reqtime);
        sleepWellModle.setRequestTime(reqtime);
        laundryModle.setRequestTime(reqtime);
        petServicesModle.setRequestTime(reqtime);
        diningModle.setRequestTime(reqtime);
        ticketModle.setRequestTime(reqtime);
        /*ticketModle.setPersonalisedTime(requestDate + " " + hr +"h" + min + "m" );*/
    }

    private void updateReqHour(TextView tv_hour) {
        serviceModle.setRequestHours(tv_hour.getText().toString());
        sleepWellModle.setRequestHours(tv_hour.getText().toString());
        laundryModle.setRequestHours(tv_hour.getText().toString());
        petServicesModle.setRequestHours(tv_hour.getText().toString());
        diningModle.setRequestHours(tv_hour.getText().toString());
        ticketModle.setRequestHours(tv_hour.getText().toString());
    }

    private void updateReqDate(String requestDate) {
        serviceModle.setRequestDate(requestDate);
        diningModle.setRequestDate(requestDate);
        ticketModle.setRequestDate(requestDate);
        petServicesModle.setRequestDate(requestDate);
        laundryModle.setRequestDate(requestDate);
        sleepWellModle.setRequestDate(requestDate);
        ticketModle.setPersonalisedTime(preferredRequestDate);
    }

    public void setFragmentCallback(GlobalClass.FragmentCallback callback) {
        this.fragmentCallback = callback;
    }


    private void closeBottomSheetFragment() {
        Fragment myfrag = getActivity().getSupportFragmentManager().findFragmentByTag(GlobalClass.BOTTOM_VIEW);
        if (myfrag != null)
            getActivity().getSupportFragmentManager().beginTransaction().remove(myfrag).commit();
    }

    @Override
    public void onFetchProgress() {
        bt_save_order.setEnabled(false);
    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {
        if (response != null) {
            bt_save_order.setEnabled(true);

            try {
                General ticket = (General) response.body();
                Fragment fragment1 = new OrderConfirmedFragment();
                Bundle bundle = new Bundle();
                fragment1.setArguments(bundle);
                /*closeBottomSheetFragment();*/
                this.dismiss();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container, fragment1).addToBackStack(null).commit();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
    @Override
    public void onFetchError(String error) {
        bt_save_order.setEnabled(true);
        GlobalClass.ShowAlert(getContext(),"Alert",error);
    }
}