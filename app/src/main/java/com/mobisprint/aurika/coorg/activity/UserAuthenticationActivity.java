package com.mobisprint.aurika.coorg.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.fragments.loginfragments.LoginFragment;
import com.mobisprint.aurika.coorg.fragments.loginfragments.ForgotMpinFragment;
import com.mobisprint.aurika.helper.GlobalClass;

public class UserAuthenticationActivity extends AppCompatActivity {

    public TextView coorg_toolbar_title;
    public ImageView bt_bck;
    private RelativeLayout lyt_notification;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coorg_toolbar_title = findViewById(R.id.toolbar_title);
        bt_bck = findViewById(R.id.naviagation_hamberger);
        lyt_notification = findViewById(R.id.lyt_notification);

        lyt_notification.setVisibility(View.GONE);

        coorg_toolbar_title.setText("Welcome to Aurika, Coorg");

        bt_bck.setOnClickListener(v -> {

            onClicked();

        });


        if (GlobalClass.sharedPreferences.getBoolean("isMpinSetUpComplete",false)){
            this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag, new  LoginFragment()).commit();
        }else {
            this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag, new ForgotMpinFragment()).commit();

        }
    }

    private void onClicked() {

        try {
            onResume();
            super.onBackPressed();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
