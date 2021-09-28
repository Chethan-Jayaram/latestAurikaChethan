package com.mobisprint.aurika.coorg.adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.reservation.ActiveBooking;
import com.mobisprint.aurika.helper.GlobalClass;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class DoorUnlockAdapter extends RecyclerView.Adapter<DoorUnlockAdapter.ViewHolder> {

    private List<ActiveBooking> guestList;
    private GlobalClass.RoomNumberListener roomNumberListener;


    public DoorUnlockAdapter(List<ActiveBooking> guestList,GlobalClass.RoomNumberListener roomNumberListener) {
        this.guestList = guestList;
        this.roomNumberListener = roomNumberListener;
    }

    @NonNull
    @Override
    public DoorUnlockAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_door_unlock_recyclerview_coorg,parent,false);

        return new DoorUnlockAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull DoorUnlockAdapter.ViewHolder holder, int position) {
        /*holder.check_in.setText(guestList.get(position).getCheckinDateTime());*/
        holder.room_num.setText(guestList.get(0).getRoom().get(position).getRoom().getRoomNo());

        /*holder.room_num.setText("227");*/


        try {
            Date checkoutDate=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(guestList.get(position).getCheckoutDateTime());
            DateFormat dateFormatcheckout = new SimpleDateFormat("dd-MM-yyyy");
            String checkout = dateFormatcheckout.format(checkoutDate);
            holder.check_out.setText(checkout);


            Date checkindate=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(guestList.get(position).getCheckinDateTime());
            DateFormat dateFormatcheckin = new SimpleDateFormat("dd-MM-yyyy");
            String checkin = dateFormatcheckin.format(checkindate);
            holder.check_in.setText(checkin);

        } catch (ParseException e) {
            e.printStackTrace();
        }

/*
        OffsetDateTime odt = OffsetDateTime.parse ( guestList.get(position).getCheckinDateTime() , DateTimeFormatter.ofPattern ( "yyyy-MM-dd") ) ;
*/

/*
        holder.check_in.setText(odt.toString());
*/

        /*holder.room_num.setText("102");*/


        holder.bt_unlock_door.setOnClickListener(v -> {
            roomNumberListener.onItemClicked(guestList.get(0).getRoom().get(position).getRoom().getRoomNo());
        });
    }

    @Override
    public int getItemCount() {
        return guestList.get(0).getRoom().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView room_num,check_in,check_out;
        Button bt_unlock_door;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            check_in = itemView.findViewById(R.id.check_in_coorg);
            check_out = itemView.findViewById(R.id.check_out_coorg);
            room_num = itemView.findViewById(R.id.room_num);
            bt_unlock_door = itemView.findViewById(R.id.bt_unlock_door);

        }
    }
}
