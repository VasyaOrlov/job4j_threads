package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {

    @Test
    public void sunTest() {
        int[][] array = new int[][]{{1, 1, 1}, {2, 2, 2}, {3, 4, 5}};
        RolColSum.Sums[] sum = RolColSum.sum(array);
        assertThat(sum[2].getColSum()).isEqualTo(8);
        assertThat(sum[2].getRowSum()).isEqualTo(12);
    }

    @Test
    public void asyncSumTest() throws ExecutionException, InterruptedException {
        int[][] array = new int[][]{{1, 1, 1}, {2, 2, 2}, {3, 4, 5}};
        RolColSum.Sums[] sum = RolColSum.asyncSum(array);
        assertThat(sum[1].getColSum()).isEqualTo(7);
        assertThat(sum[2].getRowSum()).isEqualTo(12);
    }

}