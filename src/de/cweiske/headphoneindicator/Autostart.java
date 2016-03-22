package de.cweiske.headphoneindicator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Called when the phone finishes booting.
 *
 * @author Christian Weiske, cweiske@cweiske.de
 */
public class Autostart extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, BackgroundService.class));
    }
}
