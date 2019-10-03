package com.kidddi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


public class SquareMatrixServiceImplTest {

    MatrixService service;

    private float[][] first, second, expectedResult;

    @BeforeEach
    void setUp() throws Exception {
        service = new SquareMatrixServiceImpl();

        first = new float[][] {{0.021F, 0.2F}, {0.3F, 0.9F}};
        second = new float[][] {{0.31F, 0.4F}, {0.7F, 0.65F}};

        expectedResult = new float[][] {{0.14651F, 0.13839999F}, {0.723F, 0.705F}};
    }

    @Test
    void multiplySequential() {
        float[][] factResult = service.multiplySequential(first, second);
        assertArrayEquals(factResult, expectedResult);
    }

    @Test
    void multiplyParallel() {
        float[][] factResult = service.multiplyParallel(first, second);
        assertArrayEquals(factResult, expectedResult);
    }
}
