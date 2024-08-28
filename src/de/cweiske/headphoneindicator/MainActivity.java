package de.cweiske.headphoneindicator;

import static android.R.style.Theme_DeviceDefault_DayNight;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Shows headphone icon and text, and starts the background service
 *
 * @author Christian Weiske, cweiske@cweiske.de
 */
public class MainActivity extends Activity {
    BroadcastReceiver headsetPlugReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            PlugIntentHelper plugIntentHelper = new PlugIntentHelper(context);
            PlugInfo plugInfo = plugIntentHelper.getPlugInfo(intent);
            if (plugInfo.isAudioEvent) {
                setPlugged(plugInfo.isPlugged, plugInfo.hasMicrophone);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerNotificationChannel();
        requestNotificationPermission();

        setContentView(R.layout.activity_main);
        startService(new Intent(this, BackgroundService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);

        registerReceiver(this.headsetPlugReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(this.headsetPlugReceiver);
    }

    public void setPlugged(boolean plugged, boolean microphone)
    {
        ImageView view = (ImageView) findViewById(R.id.image);
        TextView label = (TextView) findViewById(R.id.label);
        if (plugged) {
            if (microphone) {
                view.setImageResource(R.drawable.headset_w);
                label.setText(R.string.plugged_headset);
            } else {
                view.setImageResource(R.drawable.headphones_w);
                label.setText(R.string.plugged_headphones);
            }
        } else {
            view.setImageResource(0);
            label.setText(R.string.unplugged);
        }
    }

    private void registerNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }

        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_description);
        NotificationChannel channel = new NotificationChannel(
            NotificationReceiver.CHANNEL, name,
            //low = no sound
            NotificationManager.IMPORTANCE_LOW
        );
        channel.setDescription(description);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return;
        }

        if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            return;
        }
        requestPermissions(new String[] {Manifest.permission.POST_NOTIFICATIONS}, 0);
    }
}
