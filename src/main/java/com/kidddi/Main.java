package com.kidddi;

import java.util.Scanner;

import com.kidddi.service.MatrixService;
import com.kidddi.service.SquareMatrixServiceImpl;
import com.kidddi.utils.MultiplyTask;

import org.apache.commons.validator.routines.IntegerValidator;
import org.apache.log4j.Logger;

public class Main {
    private static Logger logger = Logger.getLogger(MultiplyTask.class);

    public static void main(String[] args) {

        MatrixService matrixService = new SquareMatrixServiceImpl();

        int matrix_size = getMatrixSize();

        float[][] firstMatrix = matrixService.generateMatrix(matrix_size, matrix_size);
        float[][] secondMatrix = matrixService.generateMatrix(matrix_size, matrix_size);

        logger.info("Start multiplying for matrix of size " + matrix_size);

        matrixService.multiplyParallel(firstMatrix, secondMatrix);

        matrixService.multiplySequential(firstMatrix, secondMatrix);
    }

    private static int getMatrixSize() {
        IntegerValidator validator = IntegerValidator.getInstance();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            logger.info("Please enter the size of matrix to multiply in range from 1 to 10000: ");
            int matrix_size = scanner.nextInt();

            if (validator.isInRange(matrix_size, 1, 10000)) {
                return matrix_size;
            }
            logger.info("The matrix size " + matrix_size + " is not in the range.");
        }
    }
}
