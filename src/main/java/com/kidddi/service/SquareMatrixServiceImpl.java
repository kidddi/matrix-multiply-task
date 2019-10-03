package com.kidddi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.kidddi.utils.MultiplyTask;

import org.apache.log4j.Logger;

public class SquareMatrixServiceImpl implements MatrixService {
    private static Logger logger = Logger.getLogger(SquareMatrixServiceImpl.class);

    private static int getThreadCount() {
        Runtime rt = Runtime.getRuntime();
        return rt.availableProcessors();
    }

    public float[][] multiplySequential(final float[][] firstMatrix, final float[][] secondMatrix) {
        logger.info("Sequential multiplying started. Please wait...");

        final int matrixSize = firstMatrix.length;
        final int sumLength = secondMatrix.length;
        final float[][] result = new float[matrixSize][matrixSize];

        Long startMills = System.currentTimeMillis();
        for (int row = 0; row < matrixSize; ++row) {
            for (int col = 0; col < matrixSize; ++col) {
                float sum = 0;
                for (int i = 0; i < sumLength; ++i) {
                    sum += firstMatrix[row][i] * secondMatrix[i][col];
                }
                result[row][col] = sum;
            }
        }
        Long endMills = System.currentTimeMillis();
        long processTime = endMills - startMills;
        logger.info("Sequential multiply duration: " + processTime + " ms");
        return result;
    }

    public float[][] multiplyParallel(final float[][] firstMatrix, final float[][] secondMatrix) {
        logger.info("Parallel multiplying started. Please wait...");

        int threadCount = getThreadCount();
        logger.info("Number of threads: " + threadCount);

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        final List<MultiplyTask> tasks = new ArrayList<>();

        final int matrixSize = firstMatrix.length;
        final float[][] result = new float[matrixSize][matrixSize];

        final int rowsToThread = matrixSize / threadCount;
        final int remainderOfRows = matrixSize % threadCount;
        int startRow = 0;
        for (int i = 0; i < threadCount; i++) {
            int count = ((i == 0)) ? rowsToThread + remainderOfRows : rowsToThread;
            int endRow = startRow + count;
            tasks.add(new MultiplyTask(firstMatrix, secondMatrix, result, startRow, endRow - 1));
            startRow = endRow;
        }
        Long startMills = System.currentTimeMillis();
        try {
            executor.invokeAll(tasks).forEach(x -> {
                try {
                    x.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Long endMills = System.currentTimeMillis();
        long processTime = endMills - startMills;
        logger.info("Parallel multiply duration: " + processTime + " ms.");

        executor.shutdownNow();
        return result;
    }
}
