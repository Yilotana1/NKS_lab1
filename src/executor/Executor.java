package executor;

public interface Executor {

    double getAverageFailLessTime();

    double getT();

    double getIntensity(double time);

    double getFailLessTimeProbability(double time);
}
