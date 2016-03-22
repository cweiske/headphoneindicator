package de.cweiske.headphoneindicator;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
            Bundle extras = intent.getExtras();
            if (extras == null) {
                return;
            }
            if (extras.getInt("state") == 1) {
                setPlugged(true);
            } else {
                setPlugged(false);
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
        registerReceiver(this.headsetPlugReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(this.headsetPlugReceiver);
    }

    public void setPlugged(boolean plugged)
    {
        ImageView view = (ImageView) findViewById(R.id.image);
        TextView label = (TextView) findViewById(R.id.label);
        if (plugged) {
            view.setImageResource(R.drawable.headphones_w);
            label.setText(R.string.plugged);
        } else {
            view.setImageResource(0);
            label.setText(R.string.unplugged);
        }
    }
}
