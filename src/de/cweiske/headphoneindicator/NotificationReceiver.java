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

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras == null) {
            return;
        }

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (extras.getInt("state") == 1) {
            //plugged
            nm.notify(
                NOT_ID,
                new Notification.Builder(context)
                    .setSmallIcon(R.drawable.headphones_w)
                    .setContentTitle(context.getResources().getString(R.string.plugged))
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
        } else {
            //unplugged
            nm.cancel(NOT_ID);
        }
    }
}
