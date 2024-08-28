package de.cweiske.headphoneindicator;

import android.content.Intent;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;

public class PlugIntentHelper {
    /**
     * Handle all supported intent events and extract audio device information from it
     */
    public static PlugInfo getPlugInfo(Intent intent)
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
    protected static PlugInfo getHeadsetPlugInfo(Intent intent)
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
    protected static PlugInfo getUsbDevicePlugInfo(Intent intent)
    {
        UsbDevice usbDevice = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
        if (usbDevice == null) {
            return new PlugInfo(false, false, false);
        }

        boolean audioDevice = false;
        for (int i = 0; i < usbDevice.getInterfaceCount(); i++) {
            if (usbDevice.getInterface(i).getInterfaceClass() == UsbConstants.USB_CLASS_AUDIO) {
                audioDevice = true;
            }
        }
        if (!audioDevice) {
            return new PlugInfo(false, false, false);
        }

        if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_ATTACHED)) {
            return new PlugInfo(true, true, false);
        } else if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_DETACHED)) {
            return new PlugInfo(true, false, false);
        }

        //should never be called :)
        return new PlugInfo(false, false, false);
    }
}
