package util;

public class MathUtil {


    // Округлення числа до певної цифри після коми
    // double value - число для округлення
    // int n - кількість цифр після коми
    public static double round(double value, int n){
        return (double)(Math.round(value * Math.pow(10, n))) / Math.pow(10, n);
    }
}
