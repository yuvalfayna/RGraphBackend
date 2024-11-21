package org.example;

import java.util.Random;


public class Randomizer {
    //פעולת יצירת ערכים רנדומליים לטובת הגרף (מספרים שלמים)
    public static int[][] GraphRandomizer(int[] arr) {
        Random random = new Random();
        int[][] random_arr = new int[arr[4]][3];
        for (int i = 0; i < arr[4]; i++) {
            random_arr[i][0] = random.nextInt(arr[1] - arr[0] + 1) + arr[0]; //ערכי X
            random_arr[i][1] = random.nextInt(arr[3] - arr[2] + 1) + arr[2]; //ערכי Y
            random_arr[i][2] = random.nextInt(arr[6] - arr[5] + 1) + arr[5]; //ערכי Seconds
        }
        return random_arr;
    }
    //פעולת יצירת ערכים רנדומליים לטובת המפה (מספרים עשרוניים)
    public static double[][] MapRandomizer(double[] arr) {
        Random random = new Random();
        double[][] random_arr = new double[(int) arr[4]][3];
        for (int i = 0; i < arr[4]; i++) {
            random_arr[i][0] = random.nextDouble() * (arr[1] - arr[0]) + arr[0]; //ערכי Lat
            random_arr[i][1] = random.nextDouble() * (arr[3] - arr[2]) + arr[2]; //ערכי Lng
            random_arr[i][2] = random.nextInt((int) (arr[6] - arr[5] + 1)) + arr[5]; //ערכי Seconds
        }
        return random_arr;

    }

}
