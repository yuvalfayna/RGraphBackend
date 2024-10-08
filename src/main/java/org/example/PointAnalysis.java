package org.example;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import java.util.Arrays;
import java.util.IntSummaryStatistics;

public class PointAnalysis {
    public static double[][] Exporter(int[][] arr) {
        double[] average = AvarageXY(arr);
        double[] standardDeviation = StandardDeviationXY(arr);
        double[] density=DensityXY(arr);
        double[] distance=DistanceXY(arr);
        return new double[][]{average,standardDeviation,density,distance};
    }

    public static double[] AvarageXY(int[][] arr) {
        DescriptiveStatistics sumx = new DescriptiveStatistics();
        DescriptiveStatistics sumy = new DescriptiveStatistics();
        for (int[] ints : arr) {
            sumx.addValue(ints[0]);
            sumy.addValue(ints[1]);
        }
        return new double[]{sumx.getMean(), sumy.getMean()};
    }
    public static double[]StandardDeviationXY(int[][]arr){
        StandardDeviation sd = new StandardDeviation();
        double[]arrx=new double[arr.length];
        double[]arry=new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arrx[i]=arr[i][0];
            arry[i]=arr[i][1];
        }
        return new double[]{sd.evaluate(arrx), sd.evaluate(arry)};
    }
    public static double[] DensityXY(int[][]arr){
        int[]arrx=new int[arr.length];
        int[]arry =new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arrx[i]=arr[i][0];
            arry[i]=arr[i][1];
        }
        IntSummaryStatistics statX=Arrays.stream(arrx).summaryStatistics();
        IntSummaryStatistics statY=Arrays.stream(arry).summaryStatistics();
        int rangex = statX.getMax()-statX.getMin();
        int rangey = statY.getMax()-statY.getMin();
        return new double[]{(double) arr.length /(rangey*rangex)};
    }
    public static double[] DistanceXY(int[][]arr){
        double mind=Double.MAX_VALUE;
        double maxd=0;
        for (int i=0;i<arr.length-1;i++){
            int x1=arr[i][0];
            int y1=arr[i][1];
            for (int j=i+1;j< arr.length;j++){
                int x2=arr[j][0];
                int y2=arr[j][1];
                double distance = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
                if (distance>maxd)
                    maxd=distance;
                if (distance<mind)
                    mind=distance;
            }
        }
        return new double[]{mind,maxd};
    }

}
