package org.example;

public class CheckInput {
    public static int Check(int value, int min, int max) {
        do {
            if (value < min || value > max) {
                System.out.println("Error, please enter a value between " + min + " and " + max);
            }
        } while (value < min || value > max);
        return value;
    }
}
