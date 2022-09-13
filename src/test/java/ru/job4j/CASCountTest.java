package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {
    @Test
    public void incrementTest() throws InterruptedException {
        CASCount count = new CASCount();
        Thread first = new Thread(
                () -> {
                    for (int j = 1; j <= 5; j++) {
                        count.increment();
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    for (int j = 1; j <= 10; j++) {
                        count.increment();
                    }
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(count.get()).isEqualTo(15);
    }

}