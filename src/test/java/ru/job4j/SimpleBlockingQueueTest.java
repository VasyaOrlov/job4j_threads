package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {
    @Test
    public void boundedBlockingQueueTest() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread first = new Thread(
                () -> {
                    queue.offer(1);
                    queue.offer(2);
                    queue.offer(2);
                    queue.offer(2);
                }
        );
        Thread second = new Thread(
                queue::poll
        );
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(queue.poll()).isEqualTo(2);
    }
}