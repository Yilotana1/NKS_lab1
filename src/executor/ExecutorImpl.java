package executor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import static util.MathUtil.*;
public class ExecutorImpl implements Executor {

    private final int[] timeArray;
    private final int N;
    private final double[][] intervals;
    private final double[] distributionDensities;
    private final double γ;
    private final double h;


    public ExecutorImpl(int[] timeArray, int N, double guama) {
        this.timeArray = timeArray;
        this.N = N;
        this.γ = guama;
        this.h = getH(timeArray, N);
        this.intervals = getIntervals(timeArray, N);
        this.distributionDensities = getDistributionDensities(timeArray, N);
    }


    @Override
    public double getAverageFailLessTime() {
        return Arrays.stream(timeArray)
                .average().getAsDouble();
    }

    @Override
    public double getT() {
        double h = getH(timeArray, N);
        Map<Double, Double> probabilities = getFailLessProbabilities(timeArray, N);

        Map.Entry<Double, Double> pti = getPti(probabilities, γ);
        Map.Entry<Double, Double> pti_1 = getPti_1(probabilities, γ);

        double d = (pti.getValue() - γ) / (pti.getValue() - pti_1.getValue());

        return round(pti.getKey() - h * d, 3);
    }

    @Override
    public double getIntensity(double time) {
        double f = distributionDensities[(int) (time / h)];
        double probability = getFailLessWorkProbability(time);
        return round(f / probability, 6);
    }


    @Override
    public double getFailLessWorkProbability(double time) {
        double probability = 1;

        for (int i = 0; i < N; i++) {
            if (i == (int) (time / h)) {
                probability -= distributionDensities[i] * (time - intervals[i][0]);
                break;
            }
            probability -= distributionDensities[i] * h;
        }
        return round(probability, 5);
    }


    // Отримати ймовірність за формулаю P(ti - 1) > γ
    // Map<Double, Double> probabilities - структура для зберігання ймовірностей, де key - інтервал ймовірності,
    // value - значення ймовірності від о до 1.
    private Map.Entry<Double, Double> getPti_1(Map<Double, Double> probabilities, double guama) {

        return probabilities
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() > guama)
                .findFirst().get();
    }


    // Отримати ймовірність за формулаю P(ti) < γ
    // Map<Double, Double> probabilities - структура для зберігання ймовірностей, де key - інтервал ймовірності,
    // value - значення ймовірності від о до 1.
    private Map.Entry<Double, Double> getPti(Map<Double, Double> probabilities, double guama) {
        return probabilities
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() < guama)
                .findFirst().get();
    }

    // Отримати ймовірність безвідмовної роботи для кожного інтервалу
    // int[] timeArray - вибірка наробітків до відмвови
    // int N - кількість інтервалів
    private Map<Double, Double> getFailLessProbabilities(int[] timeArray, int N) {
        double[][] intervals = getIntervals(timeArray, N);
        Map<Double, Double> probabilities = new HashMap<>();
        for (int i = 0; i < N; i++) {
            probabilities.put(intervals[i][1], getFailLessWorkProbability(intervals[i][1]));
        }
        probabilities.put(0.0, getFailLessWorkProbability(0));
        return probabilities;
    }


    // Отримати масив щільностей розподілу на інтервалах
    // int[] timeArray - вибірка наробітків до відмвови
    // int N - кількість інтервалів
    private double[] getDistributionDensities(int[] timeArray, int N) {
        double h = getH(timeArray, N);
        int[] intervalValuesNumber = getIntervalValuesNumber(timeArray, N);

        double[] densities = new double[N];
        for (int i = 0; i < N; i++) {
            densities[i] = round(intervalValuesNumber[i] / (N * h * 10), 6);
        }

        return densities;

    }


    // Масив кількісті значень вибірки, що потрапили в кожний інтервал
    // int[] timeArray - вибірка наробітків до відмвови
    // int N - кількість інтервалів
    private int[] getIntervalValuesNumber(int[] timeArray, int N) {
        double[][] intervals = getIntervals(timeArray, N);
        int[] intervalValuesNumber = new int[N];

        int counter;
        for (int i = 0; i < N; i++) {
            counter = 0;
            for (int time : timeArray) {
                if (time >= intervals[i][0] && time <= intervals[i][1]) {
                    counter++;
                }
            }
            intervalValuesNumber[i] = counter;
        }
        return intervalValuesNumber;
    }


    // Отримати довжнину інтервалу
    // int[] timeArray - вибірка наробітків до відмвови
    // int N - кількість інтервалів
    private double getH(int[] timeArray, int N) {
        int[] sortedArray = Arrays.stream(timeArray).sorted().toArray();
        return (double) (sortedArray[timeArray.length - 1]) / N;
    }


    // Отримати масив інтервалів, де intervals[i][0] - ліва межа, intervals[i][1] - права межа
    // int[] timeArray - вибірка наробітків до відмвови
    // int N - кількість інтервалів
    private double[][] getIntervals(int[] timeArray, int N) {
        double[][] intervals = new double[N][2];
        double length = getH(timeArray, N);

        for (int i = 0; i < N; i++) {
            if (i == 0) {
                intervals[i][0] = 0;
                intervals[i][1] = round(length, 1);
                continue;
            }

            intervals[i][0] = round(intervals[i - 1][1], 1);
            intervals[i][1] = round(intervals[i][0] + length, 1);
        }
        return intervals;
    }
}
