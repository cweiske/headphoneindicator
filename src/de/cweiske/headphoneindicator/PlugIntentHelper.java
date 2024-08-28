package de.cweiske.headphoneindicator;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;

public class PlugIntentHelper {
    private AudioManager audioManager;

    public PlugIntentHelper(Context context) {
        this.audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
    }

    /**
     * Handle all supported intent events and extract audio device information from it
     */
    public PlugInfo getPlugInfo(Intent intent)
    {
        if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            return getHeadsetPlugInfo(intent);

        } else if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_ATTACHED)
            || intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_DETACHED)
        ) {
            return getUsbDevicePlugInfo(intent);
        }

        return new PlugInfo(false, false, false);
    }

    /**
     * Extract interesting information from a "headset plugged" event
     */
    protected PlugInfo getHeadsetPlugInfo(Intent intent)
    {
        Bundle extras = intent.getExtras();
        if (extras == null) {
            return new PlugInfo(false, false, false);
        }

        return new PlugInfo(
            true,
            extras.getInt("state") == 1,
            extras.getInt("microphone") == 1
        );
    }

    /**
     * Extract the interesting information from an USB device attached/detached event
     */
    protected PlugInfo getUsbDevicePlugInfo(Intent intent)
    {
        UsbDevice usbDevice = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
        if (usbDevice == null) {
            return new PlugInfo(false, false, false);
        }

        boolean isAudioDevice = false;
        for (int i = 0; i < usbDevice.getInterfaceCount(); i++) {
            if (usbDevice.getInterface(i).getInterfaceClass() == UsbConstants.USB_CLASS_AUDIO) {
                isAudioDevice = true;
            }
        }
        if (!isAudioDevice) {
            return new PlugInfo(false, false, false);
        }

        if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_DETACHED)) {
            return new PlugInfo(true, false, false);
        }

        if (!intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_ATTACHED)) {
            //should never be called :)
            return new PlugInfo(false, false, false);
        }

        boolean hasMicrophone = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && audioManager != null) {
            for (AudioDeviceInfo audioDevice: audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS)) {
                if (audioDevice.getType() == AudioDeviceInfo.TYPE_USB_HEADSET) {
                    hasMicrophone = true;
                    break;
                }
            }
        }
        return new PlugInfo(true, true, hasMicrophone);
    }
}
