package util;

public class MathUtil {


    public static double round(double value, int n){
        return (double)(Math.round(value * Math.pow(10, n))) / Math.pow(10, n);
    }
}
