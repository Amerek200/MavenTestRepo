package org.example;

public class ContinuousCalculator extends Calculator {

    //saves and resuses last result if methods are called with a single argument.

    private double mem;

    public ContinuousCalculator(double initialValue) {
        mem = initialValue;
    }

    public double add(double a) {
        mem = mem + a;
        return mem;
    }

    @Override
    public double add(double a, double b) {
        mem = super.add(a, b);
        return mem;
    }

    public double substract(double a) {
        mem = mem - a;
        return mem;
    }

    @Override
    public double substract(double a, double b) {
        mem = super.substract(a, b);
        return mem;
    }

    public double multiply(double a) {
        mem = mem * a;
        return mem;
    }

    @Override
    public double multiply(double a, double b) {
        mem = super.multiply(a, b);
        return mem;
    }

    public double divide(double a) {
        mem = mem / a;
        return mem;
    }

    @Override
    public double divide(double a, double b) {
        mem = super.divide(a, b);
        return mem;
    }

}
