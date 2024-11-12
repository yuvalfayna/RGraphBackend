package org.example;
import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.PolygonArea;
import net.sf.geographiclib.PolygonResult;

import java.util.Arrays;

public class MapAnalysis {
    public static double[][] MapAnalysisExporter(double[][] arr) {
        double[] centroid = Centroid(arr);
        double[] area=Area(arr);
        double[] distance=Distance(arr);
        return new double[][]{centroid,area,distance};
    }
    public static double[] Centroid(double[][] arr) {
        double radlat = 0, radlng = 0;
        double pai180 = Math.PI / 180;
        double x = 0, y = 0, z = 0;
        double xsum = 0, ysum = 0, zsum = 0, avgx = 0, avgy = 0, avgz = 0;
        for (double[] v : arr) {
            radlat = Math.toRadians(v[0]);
            radlng = Math.toRadians(v[1]);
            x = Math.cos(radlat) * Math.cos(radlng);
            y = Math.cos(radlat) * Math.sin(radlng);
            z = Math.sin(radlat);
            xsum += x;
            ysum += y;
            zsum += z;
        }
        avgx = xsum / arr.length;
        avgy = ysum / arr.length;
        avgz = zsum / arr.length;
        double lat = Math.asin(Math.min(1, Math.max(-1, avgz))) / pai180;
        double lng = (Math.atan2(avgy, avgx)) / pai180;
        return new double[]{lat, lng};
    }

    public static double[] Area(double[][] arr) {
        double[] centroid = Centroid(arr);
        Arrays.sort(arr, (a, b) -> {
            double angleA = Math.atan2(a[1] - centroid[1], a[0] - centroid[0]);
            double angleB = Math.atan2(b[1] - centroid[1], b[0] - centroid[0]);
            return Double.compare(angleA, angleB);
        });

        Geodesic geodesic = Geodesic.WGS84;
        PolygonArea polygon = new PolygonArea(geodesic, false);
        for (double[] x : arr) {
            polygon.AddPoint(x[0], x[1]);
        }

        PolygonResult result = polygon.Compute();
        double area = Math.abs(result.area) / 1000000; // שימוש בערך מוחלט כאן

        return new double[]{area};
    }


    public static double[] Distance(double[][]arr){
        double mind=Double.MAX_VALUE;
        double maxd=0;
        double dlng=0;
        double dlat=0;
        double a=0,c=0;
        final int R = 6371;
        for (int i=0;i<arr.length-1;i++){
            for (int j=i+1;j< arr.length;j++){
                dlat=Math.toRadians(arr[j][0]-arr[i][0]);
                dlng=Math.toRadians(arr[j][1]-arr[i][1]);
                a=Math.pow(Math.sin(dlat/2),2)+Math.cos(Math.toRadians(arr[j][0]))*Math.cos(Math.toRadians(arr[i][0]))
                        *Math.pow(Math.sin(dlng/2),2);
                c=2*(Math.atan2(Math.sqrt(a),Math.sqrt(1-a)));
                double distance = c*R;
                if (distance>maxd)
                    maxd=distance;
                if (distance<mind)
                    mind=distance;
            }
        }
        return new double[]{mind,maxd};
    }
}