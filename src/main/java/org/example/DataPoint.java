// מחלקה האחראית טיפול באובייקטים של מסוג נקודה בודדת בדף גרף
package org.example;

public class DataPoint {
    private int x;
    private int y;
    private int seconds;

    public DataPoint(int x, int y, int seconds) {
        this.x = x;
        this.y = y;
        this.seconds = seconds;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public String toString() {
        return "DataPoint{" +
                "x=" + x +
                ", y=" + y +
                ", seconds=" + seconds +
                '}';
    }
}
