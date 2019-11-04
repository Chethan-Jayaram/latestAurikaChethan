package com.mobisprint.aurika.notification;
import android.content.Context;
import android.content.Intent;
import com.mobisprint.aurika.activitys.HomeScreenActivity;
import com.mobisprint.aurika.unlock.Aurika;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;


public class AurikaNotificationHandler implements OneSignal.NotificationOpenedHandler {

    @Override
    public void notificationOpened(OSNotificationOpenResult result) {
        try {
            Context context = Aurika.getAppContext();
            Intent intent = new Intent(context, HomeScreenActivity.class);
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
