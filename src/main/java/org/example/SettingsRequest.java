package org.example;

public class SettingsRequest {
    private int[] settings; // מערך של מספרים
    private String ip; // מחרוזת
    public SettingsRequest() {
    }

    public int[] getSettings() {
        return settings;
    }

    public String getIp() {
        return ip;
    }

    public void setSettings(int[] settings) {
        this.settings = settings;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
}
