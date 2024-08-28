package de.cweiske.headphoneindicator;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Shows and hides the status bar notification.
 *
 * @author Christian Weiske, cweiske@cweiske.de
 */
public class NotificationReceiver extends BroadcastReceiver {
    static int NOT_ID = 1;
    public static String CHANNEL = "plug";

    @Override
    public void onReceive(Context context, Intent intent) {
        PlugInfo plugInfo = PlugIntentHelper.getPlugInfo(intent);
        if (!plugInfo.isAudioEvent) {
            return;
        }

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (!plugInfo.isPlugged) {
            //unplugged
            nm.cancel(NOT_ID);
            return;
        }

        //plugged in
        int iconRes;
        String title;
        if (plugInfo.hasMicrophone) {
            iconRes = R.drawable.headset_w;
            title = context.getResources().getString(R.string.plugged_headset);
        } else {
            iconRes = R.drawable.headphones_w;
            title = context.getResources().getString(R.string.plugged_headphones);
        }

        nm.notify(
            NOT_ID,
            new Notification.Builder(context)
                .setSmallIcon(R.drawable.headphones_w)
                .setContentTitle(title)
                .setContentText("")
                .setContentIntent(
                    PendingIntent.getActivity(
                        context,
                        0,
                        new Intent(context, MainActivity.class),
                       PendingIntent.FLAG_UPDATE_CURRENT
                    )
                )
            .build()
        );
    }
}
