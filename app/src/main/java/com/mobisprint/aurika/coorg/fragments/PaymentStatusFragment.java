package com.mobisprint.aurika.coorg.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobisprint.aurika.R;


public class PaymentStatusFragment extends Fragment {
    private TextView tv_transaction_id,tv_amount_paid,tv_payment_status,toolbar_title;
    private Button btn_back;
    private LinearLayout lyt_trans_id;
    private ImageView img_payment_status;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_status, container, false);

        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText("Payment Status");

        tv_amount_paid = view.findViewById(R.id.tv_amount_paid);
        tv_transaction_id = view.findViewById(R.id.tv_transaction_id);
        btn_back = view.findViewById(R.id.btn_back);
        lyt_trans_id = view.findViewById(R.id.lyt_trans_id);
        tv_payment_status = view.findViewById(R.id.tv_payment_status);
        img_payment_status = view.findViewById(R.id.img_payment_status);


        Bundle bundle = this.getArguments();


        if (bundle.getString("payment_status").equalsIgnoreCase("success")){
            lyt_trans_id.setVisibility(View.VISIBLE);
            tv_payment_status.setText("Payment Successful");
            img_payment_status.setImageDrawable(getContext().getResources().getDrawable(R.drawable.img_payment_succesful));
        }else{
            tv_amount_paid.setVisibility(View.GONE);
            lyt_trans_id.setVisibility(View.GONE);
            tv_transaction_id.setVisibility(View.GONE);
            tv_payment_status.setText("Payment Failed");
            img_payment_status.setImageDrawable(getContext().getResources().getDrawable(R.drawable.img_payment_failure));
        }

        tv_transaction_id.setText(bundle.getString("Order_Id"));
        tv_amount_paid.setText("INR " + bundle.getString("Amount"));


        btn_back.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });

        return view;
    }
}