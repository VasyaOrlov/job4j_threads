package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        int temp;
        Integer ref = count.get();
        do {
            temp = ref + 1;
        } while (!count.compareAndSet(ref, temp));

    }

    public int get() {
        return count.get();
    }
}