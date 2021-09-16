package com.mobisprint.aurika.coorg.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.guestbooking.BookingTicket;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyStayInnerAdapter extends RecyclerView.Adapter<MyStayInnerAdapter.ViewHolder> {

    private List<BookingTicket> bookingTickets;
    private String guest_count;

    public MyStayInnerAdapter(List<BookingTicket> bookingTickets, String guest_count) {
        this.bookingTickets = bookingTickets;
        this.guest_count = guest_count;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_single_item_mystay,parent,false);
        return new MyStayInnerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.service_title.setText(bookingTickets.get(position).getDepartment());

        try{
            Date checkoutDate=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(bookingTickets.get(position).getStartDateTime());
            DateFormat dateFormatcheckout = new SimpleDateFormat("dd-MM-yyyy");
            String date = dateFormatcheckout.format(checkoutDate);
            holder.booking_date.setText(date);

            DateFormat timeFormatcheckout = new SimpleDateFormat("hh:mm a");
            String time = timeFormatcheckout.format(checkoutDate);
            holder.booking_time.setText(time);


        }catch (Exception e){
            e.printStackTrace();
        }

        holder.number_of_guest.setText(guest_count);

        holder.bt_activity_staus.setText(bookingTickets.get(position).getCurrentStatus().getName());
        holder.bt_activity_staus.setBackgroundColor(Color.parseColor(bookingTickets.get(position).getCurrentStatus().getEventStyle().getTicketStatusPills().getBackground()));
        holder.bt_activity_staus.setTextColor(Color.parseColor(bookingTickets.get(position).getCurrentStatus().getEventStyle().getTicketStatusPills().getColor()));

    }

    @Override
    public int getItemCount() {
        return bookingTickets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView service_title,booking_date,booking_time,number_of_guest;
        Button bt_activity_staus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            service_title = itemView.findViewById(R.id.service_title);
            booking_date = itemView.findViewById(R.id.booking_date);
            booking_time = itemView.findViewById(R.id.booking_time);
            number_of_guest = itemView.findViewById(R.id.number_of_guest);
            bt_activity_staus = itemView.findViewById(R.id.activity_staus);

        }
    }
}
