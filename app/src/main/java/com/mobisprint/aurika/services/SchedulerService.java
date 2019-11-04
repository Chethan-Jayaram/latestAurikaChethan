package com.mobisprint.aurika.services;

import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.unlock.Aurika;
import com.onesignal.OneSignal;

import java.util.ArrayList;
import java.util.Collection;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class SchedulerService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        try {
            SharedPreferences preferences = getSharedPreferences("aurika", 0);
            SharedPreferences.Editor editor = preferences.edit();
            GlobalClass.user_token = "";
            editor.clear();
            editor.apply();
            Collection<String> tempList = new ArrayList<String>();
            tempList.add("isCheckedIn");
            tempList.add("timeStamp");
            OneSignal.deleteTags(tempList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }


}
