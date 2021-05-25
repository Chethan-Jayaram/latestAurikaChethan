package com.mobisprint.aurika.udaipur.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.mobisprint.aurika.helper.GlobalClass;
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
