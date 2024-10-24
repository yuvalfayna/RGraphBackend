package org.example;

public class MapPoint {
    private double lat;
    private double lng;
    private double seconds;

    public MapPoint(double lat, double lng, double seconds) {
        this.lat = lat;
        this.lng = lng;
        this.seconds = seconds;
    }

    // Getters ו-Setters
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getSeconds() {
        return seconds;
    }

    public void setSeconds(double seconds) {
        this.seconds = seconds;
    }

    @Override
    public String toString() {
        return "DataPoint{" +
                "x=" + lat +
                ", y=" + lng +
                ", seconds=" + seconds +
                '}';
    }
}
