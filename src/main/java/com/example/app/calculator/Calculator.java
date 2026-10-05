package com.example.app.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Basic arithmetic with explicit error handling for invalid inputs.
 */
public class Calculator {

    private static final int DEFAULT_SCALE = 10;

    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    /**
     * Integer division; throws if divisor is zero.
     */
    public int divide(int dividend, int divisor) {
        if (divisor == 0) {
            throw new ArithmeticException("Division by zero");
        }
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            throw new ArithmeticException("Integer overflow");
        }
        return dividend / divisor;
    }

    public double divide(double dividend, double divisor) {
        if (divisor == 0.0) {
            throw new ArithmeticException("Division by zero");
        }
        return dividend / divisor;
    }

    public int factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is undefined for negative numbers");
        }
        if (n > 12) {
            throw new IllegalArgumentException("Factorial overflows int for n > 12");
        }
        int result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        if (n == 2) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }
        int limit = (int) Math.sqrt(n);
        for (int i = 3; i <= limit; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public BigDecimal power(BigDecimal base, int exponent) {
        if (exponent < 0) {
            throw new IllegalArgumentException("Negative exponents not supported");
        }
        return base.pow(exponent, new java.math.MathContext(DEFAULT_SCALE, RoundingMode.HALF_UP));
    }
}
