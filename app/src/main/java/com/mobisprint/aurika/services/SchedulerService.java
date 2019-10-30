package com.mobisprint.aurika.services;

import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class SchedulerService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        try {
            SharedPreferences preferences = getSharedPreferences("aurika", 0);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
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
