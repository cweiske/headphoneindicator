package de.cweiske.headphoneindicator;

import android.app.Activity;
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
            PlugInfo plugInfo = PlugIntentHelper.getPlugInfo(intent);
            if (plugInfo.isAudioEvent) {
                setPlugged(plugInfo.isPlugged, plugInfo.hasMicrophone);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
