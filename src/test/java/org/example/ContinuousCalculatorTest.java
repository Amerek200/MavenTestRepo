package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

public class ContinuousCalculatorTest {

    //JUnit creates a new instance of the test class before invoking each @Test Method
    //therefore this contCalc should be initialized/reset every time.
    ContinuousCalculator contCalc = new ContinuousCalculator(10.0);
    static double DIVISION_DELTA = 0.00001;

    @Test
    void additionTest() {
        Assertions.assertEquals(10, contCalc.add(0));
        Assertions.assertEquals(27, contCalc.add(17));
        Assertions.assertEquals(10, contCalc.add(-17));
        Assertions.assertEquals(30, contCalc.add(20));
    }

    @Test
    void subtractionTest() {
        //fails since contCalc gets created anew before running each test
        //Assertions.assertEquals(20, contCalc.substract(10));
        Assertions.assertEquals(0, contCalc.substract(10));
        Assertions.assertEquals(-5.5, contCalc.substract(5.5));
        Assertions.assertEquals(10.0, contCalc.substract(-15.5));
    }

    @Test
    void combinedTest() {
        Assertions.assertEquals(15, contCalc.multiply(1.5), DIVISION_DELTA);
        Assertions.assertEquals(-30, contCalc.multiply(-2), DIVISION_DELTA);
        //testing the reset
        Assertions.assertEquals(30, contCalc.divide(120, 4), DIVISION_DELTA);
        Assertions.assertEquals(31, contCalc.add(1), DIVISION_DELTA);
    }

}
