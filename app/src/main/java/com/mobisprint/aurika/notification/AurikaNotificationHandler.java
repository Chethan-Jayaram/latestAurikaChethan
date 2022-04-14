package com.mobisprint.aurika.notification;
import android.content.Context;
import android.content.Intent;

import com.mobisprint.aurika.SplashActivity;
import com.mobisprint.aurika.unlock.Aurika;
import com.onesignal.OSNotificationOpenedResult;
import com.onesignal.OneSignal;


public class AurikaNotificationHandler implements OneSignal.OSNotificationOpenedHandler {


    @Override
    public void notificationOpened(OSNotificationOpenedResult osNotificationOpenedResult) {
        try {
            Context context = Aurika.getAppContext();
            Intent intent = new Intent(context, SplashActivity.class);
            intent.putExtra("notification", 1);
            // intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
            // startActivity(intent);
            context.startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
