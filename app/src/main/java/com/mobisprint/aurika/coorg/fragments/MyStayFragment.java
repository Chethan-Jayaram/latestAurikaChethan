package com.mobisprint.aurika.coorg.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mobisprint.aurika.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;


public class MyStayFragment extends Fragment  {
    private RecyclerView mystay_recyclerview;
    private TextView toolbar_title;
    private Checkout checkout = new Checkout();
    private Context ctx;
    private Button btn_make_payment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_stay, container, false);

        try {
            ctx = view.getContext();
            mystay_recyclerview = view.findViewById(R.id.mystay_recyclerview);
            toolbar_title = getActivity().findViewById(R.id.toolbar_title);
            toolbar_title.setText("My Stay");
            btn_make_payment = view.findViewById(R.id.btn_make_payment);
            Checkout.preload(getActivity().getApplicationContext());

        } catch (Exception e) {
            e.printStackTrace();
        }

        btn_make_payment.setOnClickListener(v -> {
            startPayment();
        });

        return view;
    }

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        checkout.setKeyID("rzp_test_kHdWf9jzJMB3eq");
        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Razorpay Corp");
            options.put("description", "Demoing Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", "100");

            JSONObject preFill = new JSONObject();
            preFill.put("email", "test@razorpay.com");
            preFill.put("contact", "9066789265");

            options.put("prefill", preFill);

            co.open(getActivity(), options);
        } catch (Exception e) {
            Toast.makeText(ctx, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            Log.d("error in payment",e.getMessage());
            e.printStackTrace();
        }
    }

}