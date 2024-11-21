// מחלקה האחראית טיפול באובייקטים של בקשת משתמש בדף הגרף
package org.example;

public class SettingsRequest {
    private int[] settings;
    private String ip;

    public SettingsRequest() {
    }

    public int[] getSettings() {
        return settings;
    }

    public void setSettings(int[] settings) {
        this.settings = settings;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
