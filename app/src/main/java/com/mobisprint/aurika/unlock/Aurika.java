package com.mobisprint.aurika.unlock;

import android.app.Application;
import android.content.Context;
import android.util.Log;

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
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.mobisprint.aurika.BuildConfig;
import com.mobisprint.aurika.notification.AurikaNotificationHandler;
import com.onesignal.OSSubscriptionObserver;
import com.onesignal.OSSubscriptionStateChanges;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

import javax.net.ssl.SSLContext;


/**
 * Application class handling the initialization of the Mobile Keys API
 */
public class Aurika extends Application implements MobileKeysApiFactory, OSSubscriptionObserver
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
        OneSignal.initWithContext(this);
        OneSignal.setAppId("cc328b3a-7b1f-414c-bd94-8dbee25a8bf0");
        OneSignal.addSubscriptionObserver(this);
               /* .setNotificationOpenedHandler(new AurikaNotificationHandler())
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();*/
        try {
            // Google Play will install latest OpenSSL
            ProviderInstaller.installIfNeeded(getApplicationContext());
            SSLContext sslContext;
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
            sslContext.createSSLEngine();
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException
                | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        } 
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
                .setConnectingToReaderStopsScanning(false)
                .build();

        ApiConfiguration apiConfiguration = new ApiConfiguration.Builder()
                .setApplicationId(BuildConfig.AAMK_APP_ID)
                .setApplicationDescription(BuildConfig.AAMK_APP_ID_DESCRIPTION)
                .setNfcParameters(new NfcConfiguration.Builder()
                        .unsafe_setAttemptNfcWithScreenOff(false)
                        .build())
                .build();
        OneSignal.sendTag("isCheckedIn", "false");


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
        if(!mobileKeysFactory.isInitialized()) {
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

    @Override
    public void onOSSubscriptionChanged(OSSubscriptionStateChanges osSubscriptionStateChanges) {

     //   Log.d("sub","yes");

    }
}
