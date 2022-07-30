package com.mobisprint.aurika.coorg.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.controller.MyStayController;
import com.mobisprint.aurika.coorg.pojo.guestbooking.ActiveBooking;
import com.mobisprint.aurika.coorg.pojo.guestfoilos.GuestFoilos;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.CustomMessageHelper;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.retrofit.ClientServiceGenerator;
import com.mobisprint.aurika.udaipur.pojo.doorunlock.Guest;
import com.mobisprint.aurika.udaipur.pojo.doorunlock.OtpAutentication;
import com.mobisprint.aurika.udaipur.pojo.doorunlock.Result;
import com.mobisprint.aurika.coorg.services.APIMethods;

import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyStayMainAdapter extends RecyclerView.Adapter<MyStayMainAdapter.ViewHolder> {

    private Context mContext;
    private List<ActiveBooking> guestList;
    private MyStayController myStayController;
    private GlobalClass.MystayListener mystayListener;
    private Boolean isExpanded = false;
    private String guest_count;
    private ProgressDialog dialog;

    public MyStayMainAdapter(Context mContext, List<ActiveBooking> guestList, MyStayController myStayController,GlobalClass.MystayListener mystayListener) {
        this.mContext = mContext;
        this.guestList = guestList;
        this.myStayController = myStayController;
        this.mystayListener = mystayListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_mystay,parent,false);
        dialog = new ProgressDialog(parent.getContext());
        return new MyStayMainAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            Date checkoutDate=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(guestList.get(position).getCheckoutDateTime());
            DateFormat dateFormatcheckout = new SimpleDateFormat("dd-MM-yyyy");
            String checkout = dateFormatcheckout.format(checkoutDate);
            holder.tv_checkout_date.setText(checkout);

            Date checkindate=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(guestList.get(position).getCheckinDateTime());
            DateFormat dateFormatcheckin = new SimpleDateFormat("dd-MM-yyyy");
            String checkin = dateFormatcheckin.format(checkindate);
            holder.tv_checkin_date.setText(checkin);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(guestList.get(position).getReservationStatus().equalsIgnoreCase("Reserved")) {
            holder.drop_img.setVisibility(View.GONE);
        }

        //holder.btn_make_payment.setVisibility(View.GONE);

        if (guestList.get(position).getGuestCount() != null){
            holder.tv_nymber_of_guests.setText(guestList.get(position).getGuestCount().toString());


            guest_count = String.valueOf(guestList.get(position).getGuestCount());
        }else{
            holder.tv_nymber_of_guests.setText("1");
            guest_count = "1";
        }

        holder.tv_number_of_rooms.setText(guestList.get(position).getRoom().size()+"");

        holder.bt_activity_status.setText(guestList.get(position).getReservationStatus());


        holder.lyt_expand.setOnClickListener(v -> {
            if (isExpanded){
                holder.drop_img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_right_arrow));
                holder.mystay_inner_recyclerview.setVisibility(View.GONE);
                holder.lyt_payment.setVisibility(View.GONE);
                isExpanded = false;
            }else{
                isExpanded = true;
                holder.drop_img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_down_arrow));
                if (guestList.get(position).getReservationStatus().equalsIgnoreCase("In-house")){
                    GetGuestFoliodetails(guestList,holder,position);
                }else{
                    holder.lyt_payment.setVisibility(View.GONE);
                    holder.mystay_inner_recyclerview.setVisibility(View.VISIBLE);
                    MyStayInnerAdapter adapter = new MyStayInnerAdapter(guestList.get(position).getBookingTickets(),guest_count);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
                    holder.mystay_inner_recyclerview.setLayoutManager(layoutManager);
                    holder.mystay_inner_recyclerview.setAdapter(adapter);
                }
            }

        });

    }

    private void GetGuestFoliodetails(List<ActiveBooking> guestList, ViewHolder holder, int position) {
        dialog.setMessage("please wait, while we are loading more information");
        dialog.setCancelable(false);
        dialog.show();
        APIMethods api = ClientServiceGenerator.getUrlClient(GlobalClass.COORG).create(APIMethods.class);
        Call<GuestFoilos> call = api.getGuestFoilos( guestList.get(position).getBookingNumber());
        call.enqueue(new Callback<GuestFoilos>() {
            @Override
            public void onResponse(Call<GuestFoilos> call, Response<GuestFoilos> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus()) {

                            dismissDialog();

                            if (response.body().getData().isEmpty()){
                                holder.lyt_payment.setVisibility(View.GONE);
                            }else{

                                if(!response.body(). getData().get(0).getGrossAmount().equalsIgnoreCase("0.00")){
                                    guestList.get(position).setGrossAmount(response.body().getData().get(0).getBalanceDueAmount());
                                    guestList.get(position).setGuestFolioId(response.body().getData().get(0).getFolioID());
                                    holder.total_price.setText("₹ " + guestList.get(position).getGrossAmount());
                                }else{
                                    guestList.get(position).setGrossAmount(response.body().getData().get(1).getBalanceDueAmount());
                                    guestList.get(position).setGuestFolioId(response.body().getData().get(1).getFolioID());
                                    holder.total_price.setText("₹ " + guestList.get(position).getGrossAmount());
                                }


                                /*for (int i = 0; i< response.body().getData().size(); i++){
                                    if (!response.body().getData().get(i).getGrossAmount().equalsIgnoreCase("0.00")){
                                        guestList.get(position).setGrossAmount(response.body().getData().get(i).getGrossAmount());
                                        guestList.get(position).setGuestFolioId(response.body().getData().get(i).getFolioID());
                                        holder.total_price.setText("₹ " + guestList.get(position).getGrossAmount());
                                    }
                                }*/

                                /*guestList.get(position).setGrossAmount(response.body().getData().get(0).getGrossAmount());
                                guestList.get(position).setGuestFolioId(response.body().getData().get(0).getFolioID());
                                holder.total_price.setText("₹ " + guestList.get(position).getGrossAmount());*/


                                // Note :  Make Payment disabled


                                if (!guestList.get(position).getGrossAmount().equalsIgnoreCase("0.00") || !guestList.get(position).getGrossAmount().equalsIgnoreCase("0")) {
                                    holder.lyt_payment.setVisibility(View.VISIBLE);
                                }else{
                                    holder.lyt_payment.setVisibility(View.GONE);
                                }


                                holder.btn_make_payment.setOnClickListener(v1 -> {
                                    mystayListener.onItemClicked(guestList.get(position).getGrossAmount(),guestList.get(position).getGuestFolioId());
                                });
                            }

                            holder.mystay_inner_recyclerview.setVisibility(View.VISIBLE);
                            MyStayInnerAdapter adapter = new MyStayInnerAdapter(guestList.get(position).getBookingTickets(),guest_count);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
                            holder.mystay_inner_recyclerview.setLayoutManager(layoutManager);
                            holder.mystay_inner_recyclerview.setAdapter(adapter);

                        } else {
                            GlobalClass.ShowAlert(mContext,"Alert",response.body().getMessage());
                        }
                    } else {

                        GlobalClass.ShowAlert(mContext,"Alert", "Unable to retrieve details. Please try later.");

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    e.getMessage();
                }

            }

            private void dismissDialog() {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<GuestFoilos> call, Throwable t) {
                try {

                    if (t instanceof SocketTimeoutException) {

                        GlobalClass.ShowAlert(mContext,"Alert","The request timed out.");

                    } else {
                        Log.d("message",t.getCause().toString());
                        Log.d("message",t.getLocalizedMessage());
                        GlobalClass.ShowAlert(mContext,"Alert","Please check your internet connection and try again.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return guestList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_checkin_date,tv_checkout_date,tv_nymber_of_guests,tv_number_of_rooms,total_price;
        ImageView drop_img;
        Button bt_activity_status,btn_make_payment;
        RelativeLayout lyt_expand,lyt_payment;
        RecyclerView mystay_inner_recyclerview;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_checkin_date = itemView.findViewById(R.id.tv_checkin_date);
            tv_checkout_date = itemView.findViewById(R.id.tv_checkout_date);
            tv_nymber_of_guests = itemView.findViewById(R.id.tv_nymber_of_guests);
            tv_number_of_rooms = itemView.findViewById(R.id.tv_number_of_rooms);
            total_price = itemView.findViewById(R.id.total_price);
            drop_img = itemView.findViewById(R.id.drop_img);
            bt_activity_status = itemView.findViewById(R.id.bt_activity_status);
            lyt_expand = itemView.findViewById(R.id.lyt_expand);
            lyt_payment = itemView.findViewById(R.id.lyt_payment);
            btn_make_payment = itemView.findViewById(R.id.btn_make_payment);
            mystay_inner_recyclerview = itemView.findViewById(R.id.mystay_inner_recyclerview);


        }
    }
}
