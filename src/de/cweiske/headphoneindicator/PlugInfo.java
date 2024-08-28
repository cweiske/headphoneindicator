package de.cweiske.headphoneindicator;

public class PlugInfo {
    public boolean isAudioEvent;
    public boolean isPlugged;
    public boolean hasMicrophone;

    public PlugInfo(boolean isAudioEvent, boolean isPlugged, boolean hasMicrophone)
    {
        this.isAudioEvent = isAudioEvent;
        this.isPlugged = isPlugged;
        this.hasMicrophone = hasMicrophone;
    }
}
