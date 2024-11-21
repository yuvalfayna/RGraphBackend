// מחלקה האחראית טיפול באובייקטים של בקשת משתמש בדף מפה
package org.example;

public class SettingsRequestMap {
    private double[] settings;
    private String ip;

    public SettingsRequestMap() {
    }

    public double[] getSettings() {
        return settings;
    }

    public void setSettings(double[] settings) {
        this.settings = settings;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
