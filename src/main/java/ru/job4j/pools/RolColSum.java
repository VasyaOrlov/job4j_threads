package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {


    public static Sums[] sum(int[][] matrix) {
        Sums[] rsl = new Sums[matrix.length];
        for (int i = 0; i < rsl.length; i++) {
            rsl[i] = calcSum(matrix, i);
        }
        return rsl;
    }



    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] rsl = new Sums[matrix.length];
        for (int i = 0; i <= rsl.length / 2; i++) {
            rsl[i] = getSums(matrix, i).get();
            rsl[rsl.length - i - 1] = getSums(matrix, rsl.length - i - 1).get();
        }
        return rsl;
    }

    public static CompletableFuture<Sums> getSums(int[][] matrix, int index) {
        return CompletableFuture.supplyAsync(
                () -> calcSum(matrix, index)
        );
    }

    private static Sums calcSum(int[][] matrix, int index) {
        int row = 0;
        int col = 0;
        for (int j = 0; j < matrix.length; j++) {
            row += matrix[index][j];
            col += matrix[j][index];
        }
        return new Sums(row, col);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[][] x = new int[][]{{1, 1, 1}, {2, 2, 2}, {3, 3, 3}};
        Sums[] a = sum(x);
        for (Sums sums : a) {
            System.out.println(sums);
        }
        Sums[] b = asyncSum(x);
        for (Sums sums : b) {
            System.out.println(sums);
        }
    }
}