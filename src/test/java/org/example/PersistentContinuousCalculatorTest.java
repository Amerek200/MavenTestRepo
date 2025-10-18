package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

//Testinstance PersistentContinuousCalculatorTest will be created ONCE and every @Test method ran.
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersistentContinuousCalculatorTest {

    //field initializer works fine, but @BeforeAll method follows conventions and improves readability
    //ContinuousCalculator contCalc = new ContinuousCalculator(10.0);
    double DIVISION_DELTA = 0.00001;
    ContinuousCalculator contCalc;

    @BeforeAll
    void setup() {
        contCalc = new ContinuousCalculator(10.0);
    }

    @Test
    void testOne() {
        Assertions.assertEquals(15, contCalc.multiply(1.5), DIVISION_DELTA);
    }

    @Test
    void testTwo() {
        Assertions.assertEquals(16, contCalc.add(1), DIVISION_DELTA);
    }
}
