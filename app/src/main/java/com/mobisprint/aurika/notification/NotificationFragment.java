package com.mobisprint.aurika.notification;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.udaipur.adapter.NotificationAdapter;
import com.mobisprint.aurika.helper.CustomMessageHelper;
import com.mobisprint.aurika.helper.GlobalClass;

import com.mobisprint.aurika.udaipur.pojo.notification.PushNotificationResponse;
import com.mobisprint.aurika.udaipur.pojo.notification.Result;
import com.mobisprint.aurika.retrofit.ClientServiceGenerator;
import com.mobisprint.aurika.udaipur.services.APIMethods;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment {
    private Context context;
    private RecyclerView notificationRecycler;
    private NotificationAdapter adapter;
    private List<Result> result;
    private TextView tv_no_notification;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sleepwell_internal_recyclerview, container, false);
        try {
            context = view.getContext();
          /*  TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
            tv_no_notification=view.findViewById(R.id.tv_no_notification);
            notificationRecycler = view.findViewById(R.id.notification_recycler);
            tv_no_notification.setVisibility(View.GONE);
            notificationRecycler.setVisibility(View.GONE);
            RelativeLayout lyt_notification = getActivity().findViewById(R.id.lyt_notification);
            lyt_notification.setVisibility(View.GONE);

            ImageView backBtn = getActivity().findViewById(R.id.naviagation_hamberger);
            toolbar_title.setText("Notification");
            backBtn.setVisibility(View.VISIBLE);
            fetchNotification();*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private void fetchNotification() {
        try {
            Map map = new HashMap();
            map.put("token", GlobalClass.user_token);
            APIMethods api = ClientServiceGenerator.getUrlClient(GlobalClass.UDAIPUR_DOOR_UNLOCK).create(APIMethods.class);
            Call<PushNotificationResponse> call = api.pushNotificationApi(map);
            call.enqueue(new Callback<PushNotificationResponse>() {
                @Override
                public void onResponse(Call<PushNotificationResponse> call, Response<PushNotificationResponse> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus().equalsIgnoreCase("Success")) {
                                result = new ArrayList<>();
                                result = response.body().getResult();
                                if(result.isEmpty()){
                                    tv_no_notification.setVisibility(View.VISIBLE);
                                }else{
                                    notificationRecycler.setVisibility(View.VISIBLE);
                                    adapter = new NotificationAdapter(result);
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                                    notificationRecycler.setLayoutManager(mLayoutManager);
                                    notificationRecycler.setItemAnimator(new DefaultItemAnimator());
                                    notificationRecycler.setAdapter(adapter);
                                }

                            } else {
                                CustomMessageHelper showDialog = new CustomMessageHelper(context);
                                showDialog.showCustomMessage((Activity) context, "Alert!!", response.body().getError().toString(), false, false);
                            }
                        } else {
                            CustomMessageHelper showDialog = new CustomMessageHelper(context);
                            showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.ERROR), false, false);
                        }
                    } catch (Exception e) {
                        CustomMessageHelper showDialog = new CustomMessageHelper(context);
                        showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.ERROR), false, false);
                        e.printStackTrace();
                        e.getMessage();
                    }
                }

                @Override
                public void onFailure(Call<PushNotificationResponse> call, Throwable t) {
                    try {

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
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }


}
