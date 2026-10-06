package com.example.app.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Calculator")
class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Nested
    @DisplayName("add")
    class Add {
        @ParameterizedTest
        @CsvSource({
                "0, 0, 0",
                "1, 2, 3",
                "-1, 1, 0",
                "10, 20, 30",
                "2147483646, 1, 2147483647"
        })
        void addsIntegers(int a, int b, int expected) {
            assertEquals(expected, calculator.add(a, b));
        }
    }

    @Nested
    @DisplayName("subtract")
    class Subtract {
        @Test
        void subtractsPositiveNumbers() {
            assertThat(calculator.subtract(10, 3)).isEqualTo(7);
        }

        @Test
        void subtractsNegativeResult() {
            assertThat(calculator.subtract(3, 10)).isEqualTo(-7);
        }
    }

    @Nested
    @DisplayName("multiply")
    class Multiply {
        @Test
        void multiplyByZero() {
            assertThat(calculator.multiply(42, 0)).isZero();
        }

        @Test
        void multiplyNegatives() {
            assertThat(calculator.multiply(-3, -4)).isEqualTo(12);
        }
    }

    @Nested
    @DisplayName("integer divide")
    class IntegerDivide {
        @Test
        void dividesEvenly() {
            assertThat(calculator.divide(10, 2)).isEqualTo(5);
        }

        @Test
        void truncatesTowardZero() {
            assertThat(calculator.divide(7, 2)).isEqualTo(3);
            assertThat(calculator.divide(-7, 2)).isEqualTo(-3);
        }

        @Test
        void rejectsZeroDivisor() {
            assertThatThrownBy(() -> calculator.divide(1, 0))
                    .isInstanceOf(ArithmeticException.class)
                    .hasMessageContaining("zero");
        }

        @Test
        void rejectsMinValueOverflow() {
            assertThatThrownBy(() -> calculator.divide(Integer.MIN_VALUE, -1))
                    .isInstanceOf(ArithmeticException.class)
                    .hasMessageContaining("overflow");
        }
    }

    @Nested
    @DisplayName("double divide")
    class DoubleDivide {
        @Test
        void dividesDoubles() {
            assertThat(calculator.divide(5.0, 2.0)).isEqualTo(2.5);
        }

        @Test
        void rejectsZeroDivisor() {
            assertThatThrownBy(() -> calculator.divide(1.0, 0.0))
                    .isInstanceOf(ArithmeticException.class);
        }
    }

    @Nested
    @DisplayName("factorial")
    class Factorial {
        @ParameterizedTest
        @CsvSource({
                "0, 1",
                "1, 1",
                "5, 120",
                "12, 479001600"
        })
        void factorialValues(int n, int expected) {
            assertThat(calculator.factorial(n)).isEqualTo(expected);
        }

        @Test
        void rejectsNegative() {
            assertThatThrownBy(() -> calculator.factorial(-1))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void rejectsTooLarge() {
            assertThatThrownBy(() -> calculator.factorial(13))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("gcd")
    class Gcd {
        @ParameterizedTest
        @CsvSource({
                "48, 18, 6",
                "0, 5, 5",
                "-12, 8, 4",
                "17, 13, 1"
        })
        void greatestCommonDivisor(int a, int b, int expected) {
            assertThat(calculator.gcd(a, b)).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("isPrime")
    class IsPrime {
        @ParameterizedTest
        @ValueSource(ints = {2, 3, 5, 7, 11, 97, 7919})
        void primesArePrime(int n) {
            assertThat(calculator.isPrime(n)).isTrue();
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, 0, 1, 4, 6, 9, 100})
        void nonPrimesAreNotPrime(int n) {
            assertThat(calculator.isPrime(n)).isFalse();
        }
    }

    @Nested
    @DisplayName("power")
    class Power {
        @Test
        void raisesToNonNegativeExponent() {
            assertThat(calculator.power(new BigDecimal("2"), 10))
                    .isEqualByComparingTo(new BigDecimal("1024"));
        }

        @Test
        void zeroExponent() {
            assertThat(calculator.power(new BigDecimal("99"), 0))
                    .isEqualByComparingTo(BigDecimal.ONE);
        }

        @Test
        void rejectsNegativeExponent() {
            assertThatThrownBy(() -> calculator.power(BigDecimal.TEN, -1))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
