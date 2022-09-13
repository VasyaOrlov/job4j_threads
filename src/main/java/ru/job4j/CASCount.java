package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        int temp;
        Integer ref = count.get();
        do {
            temp = ref + 1;
        } while (!count.compareAndSet(ref, temp));
    }

    public int get() {
        count.compareAndSet(null, 0);
        return count.get();
    }

    public static void main(String[] args) throws InterruptedException {
        CASCount i = new CASCount();
        System.out.println(i.get());
        Thread first = new Thread(
                () -> {
                    for (int j = 1; j <= 5; j++) {
                        i.increment();
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    for (int j = 1; j <= 10; j++) {
                        i.increment();
                    }
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
        System.out.println(i.get());
    }
}