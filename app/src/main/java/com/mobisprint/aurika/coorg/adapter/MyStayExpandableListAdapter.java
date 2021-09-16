package com.mobisprint.aurika.coorg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.controller.MyStayController;
import com.mobisprint.aurika.coorg.pojo.guestbooking.ActiveBooking;
import com.mobisprint.aurika.coorg.pojo.guestfoilos.GuestFoilos;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Response;

public class MyStayExpandableListAdapter extends BaseExpandableListAdapter implements ApiListner {

    private Context mContext;
    private List<ActiveBooking> guestList;
    private GlobalClass.MystayListener mystayListener;
    private MyStayController myStayController;

    public MyStayExpandableListAdapter(MyStayController myStayController,Context mContext, List<ActiveBooking> guestList,GlobalClass.MystayListener mystayListener) {
        this.mContext = mContext;
        this.guestList = guestList;
        this.mystayListener = mystayListener;
        this.myStayController = myStayController;
    }

    @Override
    public int getGroupCount() {
        return guestList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return guestList.get(groupPosition).getBookingTickets().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return guestList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return guestList.get(groupPosition).getBookingTickets();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.single_item_mystay, null);
        }

        TextView tv_checkin_date = convertView.findViewById(R.id.tv_checkin_date);
        TextView tv_checkout_date = convertView.findViewById(R.id.tv_checkout_date);
        TextView tv_nymber_of_guests = convertView.findViewById(R.id.tv_nymber_of_guests);
        TextView tv_number_of_rooms = convertView.findViewById(R.id.tv_number_of_rooms);
        ImageView drop_img = convertView.findViewById(R.id.drop_img);
        Button bt_activity_status = convertView.findViewById(R.id.bt_activity_status);
        RelativeLayout lyt_expand = convertView.findViewById(R.id.lyt_expand);
        RelativeLayout lyt_payment = convertView.findViewById(R.id.lyt_payment);
        TextView total_price = convertView.findViewById(R.id.total_price);
        Button btn_make_payment= convertView.findViewById(R.id.btn_make_payment);

        if (isExpanded){
            drop_img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_down_arrow));
        }else {
            drop_img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_right_arrow));
        }

        bt_activity_status.setText(guestList.get(groupPosition).getReservationStatus());

        try {
            Date checkoutDate=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(guestList.get(groupPosition).getCheckoutDateTime());
            DateFormat dateFormatcheckout = new SimpleDateFormat("yyyy-mm-dd");
            String checkout = dateFormatcheckout.format(checkoutDate);
            tv_checkout_date.setText(checkout);

            Date checkindate=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(guestList.get(groupPosition).getCheckinDateTime());
            DateFormat dateFormatcheckin = new SimpleDateFormat("yyyy-mm-dd");
            String checkin = dateFormatcheckin.format(checkindate);
            tv_checkin_date.setText(checkin);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (guestList.get(groupPosition).getGuestCount() != null){
            tv_nymber_of_guests.setText((Integer) guestList.get(groupPosition).getGuestCount());
        }else{
            tv_nymber_of_guests.setText("1");
        }

        lyt_expand.setOnClickListener(v -> {
            if (isExpanded){
                ExpandableListView elv = (ExpandableListView)  parent;
                elv.collapseGroup(groupPosition);
                lyt_payment.setVisibility(View.GONE);
            }else
            {
                ExpandableListView elv = (ExpandableListView)  parent;
                elv.expandGroup(groupPosition);

                if (guestList.get(groupPosition).getReservationStatus().equalsIgnoreCase("INHOUSE")){
                    lyt_payment.setVisibility(View.VISIBLE);

                    myStayController.getGuestFoilos(guestList.get(0).getBookingNumber());

                    total_price.setText("INR " + String.valueOf(guestList.get(0).getBalanaceAmount()) +" rs");
                    btn_make_payment.setOnClickListener(v1 -> {
                        /*mystayListener.onItemClicked((guestList.get(0).getBalanceAmount()));*/
                    });
                }else {
                    lyt_payment.setVisibility(View.GONE);
                }
            }
        });

        tv_number_of_rooms.setText(guestList.get(groupPosition).getRoom().size()+"");

        /*ExpandableListView elv = (ExpandableListView)  parent;
        elv.expandGroup(groupPosition);*/

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.inner_single_item_mystay, null);
        }

        TextView service_title = convertView.findViewById(R.id.service_title);
        TextView booking_date = convertView.findViewById(R.id.booking_date);
        TextView booking_time = convertView.findViewById(R.id.booking_time);
        TextView number_of_guest = convertView.findViewById(R.id.number_of_guest);


        try{
            Date checkoutDate=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(guestList.get(groupPosition).getBookingTickets().get(childPosition).getStartDateTime());
            DateFormat dateFormatcheckout = new SimpleDateFormat("yyyy-mm-dd");
            String date = dateFormatcheckout.format(checkoutDate);
            booking_date.setText(date);

            DateFormat timeFormatcheckout = new SimpleDateFormat("hh:mm a");
            String time = timeFormatcheckout.format(checkoutDate);
            booking_time.setText(time);


        }catch (Exception e){
            e.printStackTrace();
        }


        service_title.setText(guestList.get(groupPosition).getBookingTickets().get(childPosition).getDepartment());
        /*booking_date.setText(guestList.get(groupPosition).getBookingTickets().get(childPosition).getStartDateTime());
        booking_time.setText(guestList.get(groupPosition).getBookingTickets().get(childPosition).getStartDateTime());*/

        if (guestList.get(groupPosition).getGuestCount() != null){
            number_of_guest.setText((Integer) guestList.get(groupPosition).getGuestCount());
        }else{
            number_of_guest.setText("1");
        }


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onFetchProgress() {

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {

        if (response != null){
            GuestFoilos guestFoilos = (GuestFoilos) response.body();

        }


    }

    @Override
    public void onFetchError(String error) {
        GlobalClass.ShowAlert(mContext,"Alert",error);
    }
}
