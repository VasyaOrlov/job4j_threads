package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelFindIndex extends RecursiveTask<Integer> {
    private final int[] array;
    private final int value;
    private final int start;
    private final int finish;

    public ParallelFindIndex(int[] array, int value, int start, int finish) {
        this.array = array;
        this.value = value;
        this.start = start;
        this.finish = finish;
    }

    @Override
    protected Integer compute() {
        int length = finish - start + 1;
        if (length <= 10) {
            return find(array);
        }
        int halfIndex = (start + finish) / 2;
        ParallelFindIndex first = new ParallelFindIndex(array, value, start, halfIndex - 1);
        ParallelFindIndex second = new ParallelFindIndex(array, value, halfIndex, finish);
        first.fork();
        second.fork();
        int f = first.join();
        int s = second.join();
        return merge(f, s);
    }

    private int merge(int first, int second) {
        return first != -1 ? first : second;
    }

    private int find(int[] array) {
        for (int i = start; i <= finish; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public int findIndex(int value) {
        ForkJoinPool fjk = new ForkJoinPool();
        return fjk.invoke(new ParallelFindIndex(array, value, 0, array.length - 1));
    }
}
