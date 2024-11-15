package org.example;

public class SettingsRequestMap {
    private double[] settings; // מערך של מספרים
    private String ip; // מחרוזת

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
