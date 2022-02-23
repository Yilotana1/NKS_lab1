package executor;

// Інтерфейс для реалізації обрахунків заданих в завданні до лабораторної роботи
public interface Executor {

    double getAverageFailLessTime();

    double getT();

    double getIntensity(double time);

    double getFailLessWorkProbability(double time);
}
