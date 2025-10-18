package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CalculatorTest {
    Calculator calc = new Calculator();
    static double DIVISION_DELTA = 0.00001;

    @Test //Annotation declaring method as Test Method.
    @DisplayName("We are testing addition")
    void additionTest() {
        Assertions.assertEquals(50, calc.add(2, 3));
        Assertions.assertEquals(5, calc.add(3, 2));
        Assertions.assertEquals(50, calc.add(50, 0));
        Assertions.assertEquals(0, calc.add(0, 0));
        Assertions.assertEquals(-5, calc.add(-10, 5));
        Assertions.assertEquals(17, calc.add(20, -3));
    }

    @Test
    @DisplayName("Testing substraction!")
    void substractionTest() {
        Assertions.assertEquals(5, calc.substract(6, 1));
        Assertions.assertEquals(5, calc.substract(-1, -6));
        Assertions.assertEquals(5, calc.substract(5, 0));
        Assertions.assertEquals(0, calc.substract(0, 0));
        Assertions.assertEquals(-1, calc.substract(2, 3));
    }

    //a-b=c <-> a=c+b <-> b=a-c
    @ParameterizedTest
    @DisplayName("Testing calculator, parameterized!")
    @CsvSource({
        "6, 1, 5",
        "-1, -6, 5",
        "5, 0, 5",
        "0, 0, 0",
        "2, 3, -1"
    })
    void calculatorTestParameterized(float a, float b, float c) {
        Assertions.assertEquals(c, calc.substract(a, b));
        Assertions.assertEquals(a, calc.add(c, b));
        Assertions.assertEquals(b, calc.substract(a, c));
    }

    @Test
    @DisplayName("We are testing multiplication!")
    void multiplicationTest() {
        Assertions.assertEquals(5, calc.multiply(2.5, 2));
        Assertions.assertEquals(0, calc.multiply(0, 7845));
        Assertions.assertEquals(-18, calc.multiply(3, -6.0));
        Assertions.assertEquals(10, calc.multiply(-2, -5));
    }

    @Test
    @DisplayName("We are testing division!")
    void divisionTest() {
        Assertions.assertEquals(3, calc.divide(6, 2), DIVISION_DELTA);
        Assertions.assertEquals(0.3333333, calc.divide(2, 6), DIVISION_DELTA);
        Assertions.assertEquals(-1, calc.divide(-50, 50), DIVISION_DELTA);
        Assertions.assertEquals(-3.2, calc.divide(12.8, -4), DIVISION_DELTA);
        Assertions.assertEquals(0, calc.divide(0, 12345), DIVISION_DELTA);
        Assertions.assertEquals(Double.POSITIVE_INFINITY, calc.divide(12, 0), DIVISION_DELTA);
        Assertions.assertThrows(ArithmeticException.class,
                () -> calc.divideWithException(15, 0));
    }
}
