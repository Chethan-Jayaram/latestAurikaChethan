package com.mobisprint.aurika.coorg.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.MyStayExpandableListAdapter;
import com.mobisprint.aurika.coorg.adapter.MyStayMainAdapter;
import com.mobisprint.aurika.coorg.controller.MakePaymentController;
import com.mobisprint.aurika.coorg.controller.MyStayController;
import com.mobisprint.aurika.coorg.fragments.CoorgNotificationFragment;
import com.mobisprint.aurika.coorg.fragments.OrderConfirmedFragment;
import com.mobisprint.aurika.coorg.fragments.PaymentStatusFragment;
import com.mobisprint.aurika.coorg.fragments.loginfragments.LoginFragment;
import com.mobisprint.aurika.coorg.modle.PaymentModle;
import com.mobisprint.aurika.coorg.pojo.General;
import com.mobisprint.aurika.coorg.pojo.guestbooking.ActiveBooking;
import com.mobisprint.aurika.coorg.pojo.guestbooking.GuestBooking;
import com.mobisprint.aurika.coorg.pojo.guestfoilos.GuestFoilos;
import com.mobisprint.aurika.coorg.pojo.payment.Data;
import com.mobisprint.aurika.coorg.pojo.payment.GenerateOrderId;
import com.mobisprint.aurika.coorg.pojo.verifysignature.VerifyPaymentSignature;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import retrofit2.Response;

public class MyStayActivity extends AppCompatActivity implements   ApiListner, PaymentResultWithDataListener {

    private RecyclerView mystay_expandable_listview;
    private TextView toolbar_title;
    private Button btn_make_payment;
    private ImageView bt_back;
    private MakePaymentController controller;
    private PaymentModle paymentModle = new PaymentModle();
    private BottomSheetDialog bottomSheetDialog;
    private ProgressDialog dialog;
    private Context mContext;
    private String order_idd="";
    private MyStayController myStayController;
    private List<ActiveBooking> guestList ;
    private ProgressBar progressBar;
    private RelativeLayout lyt,lyt_notification_tool_bar;
    private String folio_id,order_reciept,total_amount,user_name,email_id,ph_num,desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_stay);

        try {
            
            mContext = getApplicationContext();

            bottomSheetDialog = new BottomSheetDialog(this);
            myStayController = new MyStayController(this);
            progressBar = findViewById(R.id.my_stay_progress_bar);
            progressBar.setVisibility(View.GONE);
            lyt = findViewById(R.id.mystay_lyt);
            lyt.setVisibility(View.GONE);

            lyt_notification_tool_bar = findViewById(R.id.lyt_notification);
            mystay_expandable_listview = findViewById(R.id.mystay_expandable_listview);
            toolbar_title = findViewById(R.id.toolbar_title);
            toolbar_title.setText("My Stay");
            btn_make_payment = findViewById(R.id.btn_make_payment);
            Checkout.preload(getApplicationContext());
            bt_back = findViewById(R.id.naviagation_hamberger);
            controller = new MakePaymentController(this);


            bt_back.setOnClickListener(v -> {
                onClicked();
            });

            btn_make_payment.setOnClickListener(v -> {
                /*startPayment();*/
            });

            lyt_notification_tool_bar.setVisibility(View.GONE);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        myStayController.getGuestBooking(GlobalClass.user_token);
    }

    private void showBottomSheetDialog(String amount) {
        bottomSheetDialog.setContentView(R.layout.make_payment_bottom_sheet_dailog);

        Button bt_make_payment = bottomSheetDialog.findViewById(R.id.btn_make_payment_continue);
        TextView tv_amount_to_be_paid = bottomSheetDialog.findViewById(R.id.amount_to_be_paid);
     //   EditText amount_to_pay = bottomSheetDialog.findViewById(R.id.amount_to_pay);
        tv_amount_to_be_paid.setText("INR " + String.valueOf(amount));
      //  amount_to_pay.setHint(String.valueOf(amount));

        bt_make_payment.setOnClickListener(v -> {
            /*if (amount_to_pay.getText().toString().trim().isEmpty()){
                total_amount =amount;
                controller.generateOrderId(String.valueOf(amount));
            }else{
                total_amount = amount_to_pay.getText().toString().trim();
                controller.generateOrderId(amount_to_pay.getText().toString().trim());
            }*/
            controller.generateOrderId(String.valueOf(amount));
        });

        bottomSheetDialog.show();


    }

    private void onClicked() {
        try {
            onResume();
            super.onBackPressed();
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(bt_back.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void startPayment(PaymentModle paymentModle) {

        Log.d("amountt", String.valueOf(paymentModle.getAmount()));

        Checkout checkout = new Checkout();
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        checkout.setKeyID("rzp_test_kHdWf9jzJMB3eq");

        try {
            JSONObject options = new JSONObject();
            options.put("name", user_name);
            options.put("description", desc);
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("order_id", paymentModle.getId());//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", paymentModle.getAmount()*100);//pass amount in currency subunits
            options.put("prefill.email", email_id);
            options.put("prefill.contact",ph_num);
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(this, options);
        } catch (Exception e) {
            Toast.makeText(this, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            Log.d("error in payment",e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public void onFetchProgress() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {
        if (response != null){


            if (response.body() instanceof GuestBooking){


                progressBar.setVisibility(View.GONE);
                lyt.setVisibility(View.VISIBLE);


                if(((GuestBooking) response.body()).getData().getActiveBooking().isEmpty() && ((GuestBooking) response.body()).getData().getUpcomingBookingSerializer().isEmpty() && ((GuestBooking) response.body()).getData().getBookingHistory().isEmpty()){
                    GlobalClass.ShowAlert(MyStayActivity.this,"Alert","You dont have active bookings");
                }else{
                    GuestBooking guestBooking = (GuestBooking) response.body();
                    guestList = guestBooking.getData().getActiveBooking();
                    guestList.addAll(guestBooking.getData().getBookingHistory());
                    guestList.addAll(guestBooking.getData().getUpcomingBookingSerializer());
                    Log.d("guestList", String.valueOf(guestList.size()));

                    if (!((GuestBooking) response.body()).getData().getActiveBooking().isEmpty()){
                        user_name = guestList.get(0).getGuest().getFirstName() + " " +guestList.get(0).getGuest().getFirstName();

                        email_id = guestList.get(0).getGuest().getEmail();

                        ph_num = guestList.get(0).getGuest().getContactNumber();

                        desc = guestList.get(0).getBookingNumber();
                    }


                /*MyStayExpandableListAdapter adapter = new MyStayExpandableListAdapter(myStayController,mContext,guestList,Amount -> {
                    showBottomSheetDialog(Amount);
                });*/

                    MyStayMainAdapter adapter = new MyStayMainAdapter(mContext,guestList,myStayController,(Amount,FolioId) -> {
                        folio_id = FolioId;
                        showBottomSheetDialog(Amount);
                    });
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
                    mystay_expandable_listview.setLayoutManager(layoutManager);
                    mystay_expandable_listview.setAdapter(adapter);
                }

//                if (((GuestBooking) response.body()).getData().getActiveBooking().isEmpty() && ((GuestBooking) response.body()).getData().getUpcomingBookingSerializer().isEmpty() ){
//                    GlobalClass.ShowAlert(MyStayActivity.this,"Alert","You dont have active bookings");
//                }else if (!((GuestBooking) response.body()).getData().getActiveBooking().isEmpty()){
//                    GuestBooking guestBooking = (GuestBooking) response.body();
//                    guestList = guestBooking.getData().getActiveBooking();
//                    guestList.addAll(guestBooking.getData().getBookingHistory());
//                    guestList.addAll(guestBooking.getData().getUpcomingBookingSerializer());
//                    Log.d("guestList", String.valueOf(guestList.size()));
//
//                    user_name = guestList.get(0).getGuest().getFirstName() + " " +guestList.get(0).getGuest().getFirstName();
//
//                    email_id = guestList.get(0).getGuest().getEmail();
//
//                    ph_num = guestList.get(0).getGuest().getContactNumber();
//
//                    desc = guestList.get(0).getBookingNumber();
//                /*MyStayExpandableListAdapter adapter = new MyStayExpandableListAdapter(myStayController,mContext,guestList,Amount -> {
//                    showBottomSheetDialog(Amount);
//                });*/
//
//                    MyStayMainAdapter adapter = new MyStayMainAdapter(mContext,guestList,myStayController,(Amount,FolioId) -> {
//                        folio_id = FolioId;
//                        showBottomSheetDialog(Amount);
//                    });
//                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
//                    mystay_expandable_listview.setLayoutManager(layoutManager);
//                    mystay_expandable_listview.setAdapter(adapter);
//                }else{
//                    GuestBooking guestBooking = (GuestBooking) response.body();
//                    guestList = guestBooking.getData().getActiveBooking();
//                    guestList.addAll(guestBooking.getData().getBookingHistory());
//                    guestList.addAll(guestBooking.getData().getUpcomingBookingSerializer());
//                    Log.d("guestList", String.valueOf(guestList.size()));
//
//                    user_name = guestList.get(0).getGuest().getFirstName() + " " +guestList.get(0).getGuest().getFirstName();
//
//                    email_id = guestList.get(0).getGuest().getEmail();
//
//                    ph_num = guestList.get(0).getGuest().getContactNumber();
//
//                    desc = guestList.get(0).getBookingNumber();
//                /*MyStayExpandableListAdapter adapter = new MyStayExpandableListAdapter(myStayController,mContext,guestList,Amount -> {
//                    showBottomSheetDialog(Amount);
//                });*/
//
//                    MyStayMainAdapter adapter = new MyStayMainAdapter(mContext,guestList,myStayController,(Amount,FolioId) -> {
//                        folio_id = FolioId;
//                        showBottomSheetDialog(Amount);
//                    });
//                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
//                    mystay_expandable_listview.setLayoutManager(layoutManager);
//                    mystay_expandable_listview.setAdapter(adapter);
//                }




            }else if (response.body() instanceof GenerateOrderId){

                bottomSheetDialog.dismiss();

                GenerateOrderId generateOrderId = (GenerateOrderId) response.body();
                Data data = generateOrderId.getData();
                order_reciept = data.getReceipt();
                paymentModle.setId(data.getId());
                Log.d("order_id",data.getId());
                paymentModle.setAmount(data.getAmount());
                paymentModle.setCurrency(data.getCurrency());
                startPayment(paymentModle);
                order_idd = data.getId();


            }else if (response.body() instanceof VerifyPaymentSignature){
                dismissDialog();

                VerifyPaymentSignature verifyPaymentSignature = (VerifyPaymentSignature) response.body();

                com.mobisprint.aurika.coorg.pojo.verifysignature.Data data = verifyPaymentSignature.getData();

                Fragment fragment1 = new PaymentStatusFragment();
                Bundle bundle = new Bundle();
                bundle.putString("Order_Id",data.getOrderReceipt());
                bundle.putString("Amount",data.getOrderAmount());
                bundle.putString("payment_status","success");
                fragment1.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container_mystay, fragment1).commit();

            }
        }

    }




    @Override
    public void onFetchError(String error) {



        progressBar.setVisibility(View.GONE);
        Log.d("Payment_error",error);
        GlobalClass.ShowAlert(MyStayActivity.this,"Alert",error);

    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {

        Log.d("Payment_.Success",s);

        Double amount = paymentModle.getAmount()/100;

        try {

           String enodedSignature =  encode("VwwWeaINg7O6ADoREGcsQu3k",order_idd+"|"+paymentData.getPaymentId());

           if (enodedSignature.equals(paymentData.getSignature())){
               dialog = new ProgressDialog(this);
               dialog.setMessage("please wait, payment in progress");
               dialog.setCancelable(false);
               dialog.show();
               controller.paymentSuccess(paymentData.getSignature(),order_idd,paymentData.getPaymentId(),order_reciept,folio_id,amount.toString());


           }



        }catch (Exception e){
            Log.d("exceptionn", e.getMessage());
            e.printStackTrace();
        }



    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
     //   Toast.makeText(this, "Payment failed: " + paymentData.getOrderId() + " " + paymentData.getSignature(), Toast.LENGTH_SHORT).show();

            dismissDialog();


            Fragment fragment1 = new PaymentStatusFragment();
            Bundle bundle = new Bundle();
            bundle.putString("payment_status","failure");
            fragment1.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container_mystay, fragment1).commit();


    }

    public String encode(String key, String data) {
        try {

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            return new String(Hex.encodeHex(sha256_HMAC.doFinal(data.getBytes("UTF-8"))));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void dismissDialog() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}