package com.mobisprint.aurika.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Bundle;
import android.provider.Settings;

import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.helper.CustomMessageHelper;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.pojo.testing.AppDatum;
import com.mobisprint.aurika.pojo.testing.Testing;
import com.mobisprint.aurika.retrofit.Client2ServiceGenerator;
import com.mobisprint.aurika.services.APIMethods;

import java.net.SocketTimeoutException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends Activity {

    private static int SPLASH_TIME_OUT = 3000;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor edit;
    private Context context;
    private Handler handler;
    private int notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = this;
            setContentView(R.layout.activity_splash);
            Intent intent = getIntent();
            notification = intent.getIntExtra("notification", 0);
            GlobalClass.SharedPreferences = this.getSharedPreferences("aurika", 0);
            GlobalClass.APPDATA = GlobalClass.SharedPreferences.getString("data", "");
            if(!GlobalClass.APPDATA.isEmpty()){
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();
                Testing testing = gson.fromJson(GlobalClass.APPDATA, Testing.class);
                List<AppDatum> appDatum = testing.getAppData();
                GlobalClass.homeNames=new String[appDatum.get(0).getDashboardItems().size()];
                for(int i=0;i<appDatum.get(0).getDashboardItems().size();i++){
                    GlobalClass.homeNames[i]=appDatum.get(0).getDashboardItems().get(i);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        if (GlobalClass.APPDATA.isEmpty()) {
            if (!haveNetworkConnection()) {
                ShowAlet("No Internet Connection", "Turn on your internet and continue");
            } else {
                downloadData();
            }
        } else {
            if (haveNetworkConnection()) {
                downloadData();
            } else {
                handlermethod();
            }
        }
        super.onResume();
    }


    private void handlermethod() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent startAct = new Intent(getApplicationContext(), HomeScreenActivity.class);
            startAct.putExtra("notification", notification);
            startActivity(startAct);
            finish();
        }, SPLASH_TIME_OUT);
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] netInfo = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isConnected())
                        haveConnectedWifi = true;
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if (ni.isConnected())
                        haveConnectedMobile = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    private void ShowAlet(String Title, String Message) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(Title);
            builder.setMessage(Message)
                    .setCancelable(false)
                    .setPositiveButton("ok", (dialog, id) -> startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)))
                    .setNegativeButton("Quit", (dialog, id) -> {
                    });
            AlertDialog alert = builder.create();
            alert.show();
            Button positiveButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
            Button negativeButton = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
            positiveButton.setTextColor(getResources().getColor(R.color.black));
            negativeButton.setTextColor(getResources().getColor(R.color.black));
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }


    private void downloadData() {
        APIMethods api = Client2ServiceGenerator.getUrlClient().create(APIMethods.class);
        Call<Testing> call = api.getData();
        call.enqueue(new Callback<Testing>() {
            @Override
            public void onResponse(Call<Testing> call, Response<Testing> response) {
                try {
                    if (response.isSuccessful()) {
                        List<AppDatum> appDatum = response.body().getAppData();
                        GsonBuilder builder = new GsonBuilder();
                        builder.setPrettyPrinting();
                        Gson gson = builder.create();
                        String st1 = gson.toJson(appDatum);
                        String St2 = "{" + System.getProperty("line.separator") +
                                " \"AppData\": " + st1 + System.getProperty("line.separator") + "}";
                        GlobalClass.APPDATA = St2;
                        GlobalClass.homeNames=new String[appDatum.get(0).getDashboardItems().size()];
                        for(int i=0;i<appDatum.get(0).getDashboardItems().size();i++){
                            GlobalClass.homeNames[i]=appDatum.get(0).getDashboardItems().get(i);
                        }
                        putsharedpreference();
                        Intent startAct = new Intent(getApplicationContext(), HomeScreenActivity.class);
                        startAct.putExtra("notification", notification);
                        startActivity(startAct);
                        if (handler != null) {
                            handler.removeCallbacksAndMessages(null);
                        }
                        finish();
                    } else {
                        CustomMessageHelper showDialog = new CustomMessageHelper(context);
                        showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.ERROR), false, false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    e.getMessage();
                }

            }

            @Override
            public void onFailure(Call<Testing> call, Throwable t) {
                try {
                    Log.d("INSIDE FAILURE", "****");
                    if (t instanceof SocketTimeoutException) {
                        CustomMessageHelper showDialog = new CustomMessageHelper(context);
                        showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.SOCKET_ISSUE), false, false);
                    } else {
                        CustomMessageHelper showDialog = new CustomMessageHelper(context);
                        showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.NETWORK_ISSUE), false, false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void putsharedpreference() {
        sharedPreferences = this.getSharedPreferences("aurika", 0);
        edit = sharedPreferences.edit();
        edit.putString("data", GlobalClass.APPDATA);
        edit.apply();
    }
}
