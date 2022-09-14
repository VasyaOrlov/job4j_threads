package ru.job4j.pool;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static ru.job4j.pool.ParallelFindIndex.findIndex;

class ParallelFindIndexTest {

    @Test
    public void findIndexLineTest() {
        Integer[] array = new Integer[8];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        assertThat(findIndex(array, 4)).isEqualTo(4);
    }

    @Test
    public void findIndexRecursTest() {
        Integer[] array = new Integer[28];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        assertThat(findIndex(array, 18)).isEqualTo(18);
    }

    @Test
    public void findIndexAndValueMissing() {
        Integer[] array = new Integer[28];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        assertThat(findIndex(array, 44)).isEqualTo(-1);
    }

    @Test
    public void findIndexTypeAnother() {
        Integer[] array = new Integer[28];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        assertThat(findIndex(array, new int[]{1, 2})).isEqualTo(-1);
    }
}