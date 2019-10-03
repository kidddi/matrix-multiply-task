package com.kidddi.utils;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

public class MultiplyTask extends Thread implements Callable<Void> {
    private static Logger logger = Logger.getLogger(MultiplyTask.class);

    private float[][] first, second, result;
    private int startRow, endRow;

    public MultiplyTask(float[][] first, float[][] second, float[][] result, int startRow, int endRow) {
        this.first = first;
        this.second = second;
        this.result = result;
        this.startRow = startRow;
        this.endRow = endRow;
    }

    private float calculateCell(int row, int col) {
        float result = 0;
        for (int i = 0; i < second.length; i++) {
            result += first[row][i] * second[i][col];
        }
        return result;
    }


    public Void call() {
        logger.debug("The " + getName() + " started working on rows from: " + startRow + " to: " + endRow);
        for (int row = startRow; row <= endRow; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = calculateCell(row, col);
            }
        }
        logger.debug("Thread " + getName() + " finished.");
        return null;
    }
}
