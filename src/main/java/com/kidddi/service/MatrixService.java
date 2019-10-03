package com.kidddi.service;

import java.util.Random;

public interface MatrixService {

    /**
     * Multiplies matrices in one thread
     *
     * @param firstMatrix
     * @param secondMatrix
     * @return result of multiplication of two matrices
     */
    float[][] multiplySequential(final float[][] firstMatrix, final float[][] secondMatrix);

    /**
     * Multiplies matrices in parallel.
     * Uses number of CPUs to define threads count
     *
     * @param firstMatrix
     * @param secondMatrix
     * @return result of multiplication of two matrices
     */
    float[][] multiplyParallel(final float[][] firstMatrix, final float[][] secondMatrix);

    default float[][] generateMatrix(int rows, int cols) {
        float[][] matrix = new float[rows][cols];
        final Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextFloat();
            }
        }
        return matrix;
    }
}
