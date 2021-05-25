package com.mobisprint.aurika;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobisprint.aurika.coorg.adapter.SelectLocationAdapter;
import com.mobisprint.aurika.coorg.controller.login.SelectLocationController;
import com.mobisprint.aurika.coorg.pojo.location.SelectLocation;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import retrofit2.Response;

public class SelectLocationActivity extends AppCompatActivity implements ApiListner {

    private RecyclerView location_recyclerview;
    private SelectLocationAdapter selectLocationAdapter;

    private EditText txt_location;
    private ImageView txt_search_loaction;
    private ProgressBar progressBar;
    private ImageView back,notification,img_notification;
    private TextView toolbar_title;
    private RelativeLayout lyt_notification;

    private  SelectLocationController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_location);

        try {
            controller = new SelectLocationController(this);
            location_recyclerview = findViewById(R.id.location_recyclerviiew);

            txt_location = findViewById(R.id.txt_location);
            txt_search_loaction = findViewById(R.id.txt_search_location);

            back = findViewById(R.id.naviagation_hamberger);
            notification = findViewById(R.id.toolbar_notification_icon);
            img_notification = findViewById(R.id.img_notification);
            toolbar_title = findViewById(R.id.toolbar_title);
            lyt_notification = findViewById(R.id.lyt_notification);

            lyt_notification.setVisibility(View.GONE);

            back.setVisibility(View.GONE);
            notification.setVisibility(View.GONE);
            img_notification.setVisibility(View.GONE);
            toolbar_title.setText("Select Location");

            toolbar_title.setGravity(Gravity.CENTER);


            progressBar = findViewById(R.id.progressbar);
            progressBar.setVisibility(View.VISIBLE);
            controller.getLocations();


        }catch ( Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onFetchProgress() {
        progressBar.setVisibility(View.VISIBLE);
        Log.d("onProgress","progress");
    }


    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {
        Log.d("onCompleted","Completed");
        if(response!=null){
            progressBar.setVisibility(View.GONE);
            SelectLocation selectLocation= (SelectLocation) response.body();
            selectLocationAdapter = new SelectLocationAdapter(this,selectLocation.getData());
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            location_recyclerview.setLayoutManager(layoutManager);
            location_recyclerview.setAdapter(selectLocationAdapter);
        }

    }

    @Override
    public void onFetchError(String error) {
        progressBar.setVisibility(View.GONE);
        Log.d("onerror",error);
        GlobalClass.ShowAlert(this,"Alert",error);
    }
}
