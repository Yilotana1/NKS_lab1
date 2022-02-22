import executor.Executor;
import executor.ExecutorImpl;

public class Lab1 {

    private static final int[] timeArray = {
            577, 566, 251, 868, 2525, 26, 1040, 580, 57,
            376, 906, 304, 1407, 134, 62, 230, 531, 653,
            2143, 515, 701, 88, 762, 337, 278, 87, 85,
            10, 484, 23, 454, 61, 508, 15, 469, 309, 434,
            2323, 219, 120, 66, 212, 220, 498, 522, 267,
            820, 648, 18, 417, 519, 34, 662, 756, 461,
            221, 291, 224, 234, 365, 416, 114, 448, 395,
            71, 4, 44, 462, 537, 226, 125, 157, 18, 216,
            844, 438, 730, 274, 395, 40, 481, 118, 14,
            1297, 102, 249, 34, 758, 611, 159, 1115,
            353, 117, 15, 158, 259, 489, 38, 1416, 2344
    };

    private final static int N = 10;

    private final static int FAIL_LESS_TIME = 2245;

    private final static int INTENSITY_TIME = 342;

    private final static double GUAMA = 0.91;



    public static void main(String[] args) {

        Executor executor = new ExecutorImpl(timeArray, N, GUAMA);

        System.out.println("Середній час роботи без відмов = " + executor.getAverageFailLessTime());

        System.out.println("Ймовірність безвідмовної роботи за час " + FAIL_LESS_TIME + " = " + executor.getFailLessTimeProbability(FAIL_LESS_TIME));

        System.out.println("T = " + executor.getT());

        System.out.println("Інтенсивність на " + INTENSITY_TIME + " годин = " + executor.getIntensity(INTENSITY_TIME));
    }




}


