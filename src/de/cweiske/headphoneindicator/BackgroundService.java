package de.cweiske.headphoneindicator;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

/**
 * Permanent service that runs even when the app itself is stopped
 *
 * @author Christian Weiske, cweiske@cweiske.de
 */
public class BackgroundService extends Service
{
    protected NotificationReceiver notificationReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        this.notificationReceiver = new NotificationReceiver();
        registerReceiver(this.notificationReceiver, filter);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(this.notificationReceiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
}
