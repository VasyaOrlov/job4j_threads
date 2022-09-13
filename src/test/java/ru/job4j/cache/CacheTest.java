package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CacheTest {
    @Test
    public void addTest() {
        Cache cache = new Cache();
        Base first = new Base(1, 1);
        Base second = new Base(2, 1);
        assertThat(cache.add(first)).isTrue();
        assertThat(cache.add(second)).isTrue();
    }

    @Test
    public void deleteTest() {
        Cache cache = new Cache();
        Base first = new Base(1, 1);
        cache.add(first);
        assertThat(cache.delete(first)).isTrue();
    }

    @Test
    public void updateTest() {
        Cache cache = new Cache();
        Base first = new Base(1, 1);
        Base second = new Base(1, 1);
        cache.add(first);
        assertThat(cache.update(second)).isTrue();
    }

    @Test
    public void updateExceptionTest() {
        Cache cache = new Cache();
        Base first = new Base(1, 1);
        Base second = new Base(1, 2);
        cache.add(first);
        assertThatExceptionOfType(OptimisticException.class)
                .isThrownBy(() -> cache.update(second));
    }
}