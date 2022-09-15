package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelFindIndex<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T value;
    private final int start;
    private final int finish;

    public ParallelFindIndex(T[] array, T value, int start, int finish) {
        this.array = array;
        this.value = value;
        this.start = start;
        this.finish = finish;
    }

    @Override
    protected Integer compute() {
        int length = finish - start + 1;
        if (length <= 10) {
            return find();
        }
        int halfIndex = (start + finish) / 2;
        ParallelFindIndex<T> first = new ParallelFindIndex<>(array, value, start, halfIndex - 1);
        ParallelFindIndex<T> second = new ParallelFindIndex<>(array, value, halfIndex, finish);
        first.fork();
        second.fork();
        int f = first.join();
        int s = second.join();
        return Math.max(f, s);
    }

    private int find() {
        for (int i = start; i <= finish; i++) {
            if (array[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    public static <T> int findIndex(T[] array, T value) {
        ForkJoinPool fjk = new ForkJoinPool();
        return fjk.invoke(new ParallelFindIndex<>(array, value, 0, array.length - 1));
    }
}
