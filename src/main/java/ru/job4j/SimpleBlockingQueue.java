package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private static final int LIMIT = 3;
    private static final int EMPTY = 0;

    public synchronized void offer(T value) {
        while (queue.size() == LIMIT) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (queue.size() < LIMIT) {
            this.notify();
        }
        queue.add(value);
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.size() == EMPTY) {
            this.wait();
        }
        if (queue.size() > EMPTY) {
            this.notify();
        }
        return queue.poll();
    }

    public synchronized boolean isEmpty() {
        return !queue.isEmpty();
    }
}