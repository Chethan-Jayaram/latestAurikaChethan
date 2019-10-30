package com.mobisprint.aurika.unlock;

import android.app.Application;
import android.content.Context;

import com.assaabloy.mobilekeys.api.ApiConfiguration;
import com.assaabloy.mobilekeys.api.MobileKeys;
import com.assaabloy.mobilekeys.api.MobileKeysApi;
import com.assaabloy.mobilekeys.api.ReaderConnectionController;
import com.assaabloy.mobilekeys.api.ble.BluetoothMode;
import com.assaabloy.mobilekeys.api.ble.OpeningTrigger;
import com.assaabloy.mobilekeys.api.ble.RssiSensitivity;
import com.assaabloy.mobilekeys.api.ble.ScanConfiguration;
import com.assaabloy.mobilekeys.api.ble.ScanMode;
import com.assaabloy.mobilekeys.api.ble.TapOpeningTrigger;
import com.assaabloy.mobilekeys.api.hce.NfcConfiguration;
import com.mobisprint.aurika.BuildConfig;
import com.mobisprint.aurika.notification.AurikaNotificationHandler;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;


/**
 * Application class handling the initialization of the Mobile Keys API
 */
public class Aurika extends Application implements MobileKeysApiFactory
{
    private static final int LOCK_SERVICE_CODE = BuildConfig.AAMK_LOCK_SERVICE_CODE;

    private MobileKeysApi mobileKeysFactory;
    private static Aurika mInstance;
    private static Context context;

    public Aurika(){
        mInstance=this;

    }


    @Override
    public void onCreate()
    {
        super.onCreate();
        mInstance=this;
        context=getApplicationContext();
        initializeMobileKeysApi();
        OneSignal.startInit(this)
                .setNotificationOpenedHandler(new AurikaNotificationHandler())
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }

    /**
     * Configure and initialize the mobile keys library
     */
    private void initializeMobileKeysApi()
    {

        OpeningTrigger[] openingTriggers = {new TapOpeningTrigger(this)};
        ScanConfiguration scanConfiguration = new ScanConfiguration.Builder(openingTriggers, LOCK_SERVICE_CODE)
                .setBluetoothModeIfSupported(BluetoothMode.DUAL)
                .setScanMode(ScanMode.OPTIMIZE_PERFORMANCE)
                .setRssiSensitivity(RssiSensitivity.HIGH)
                .build();

        ApiConfiguration apiConfiguration = new ApiConfiguration.Builder()
                .setApplicationId(BuildConfig.AAMK_APP_ID)
                .setApplicationDescription(BuildConfig.AAMK_APP_ID_DESCRIPTION)
                .setNfcParameters(new NfcConfiguration.Builder()
                    .unsafe_setAttemptNfcWithScreenOff(false)
                    .build())
                .build();
        OneSignal.sendTag("isCheckedIn", "fslse");


        try {
            Date currentTime = Calendar.getInstance().getTime();
            JSONObject tags = new JSONObject();
            tags.put("isCheckedIn", "false");
            tags.put("timeStamp", currentTime);
            OneSignal.sendTags(tags);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mobileKeysFactory = MobileKeysApi.getInstance();
        mobileKeysFactory.initialize(this, apiConfiguration, scanConfiguration);
        if(mobileKeysFactory.isInitialized() == false) {
            throw new IllegalStateException();
        }

        MobileKeysApi.getInstance().getReaderConnectionController().enableHce();
    }

    @Override
    public MobileKeys getMobileKeys()
    {
        return mobileKeysFactory.getMobileKeys();
    }

    @Override
    public ReaderConnectionController getReaderConnectionController()
    {
        return mobileKeysFactory.getReaderConnectionController();
    }

    @Override
    public ScanConfiguration getScanConfiguration()
    {
        return getReaderConnectionController().getScanConfiguration();
    }


    public static synchronized Aurika getInstance(){
        return mInstance;
    }

    public static Context getAppContext() {
        return context;
    }
}
