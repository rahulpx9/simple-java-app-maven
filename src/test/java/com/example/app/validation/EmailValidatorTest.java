package com.example.app.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("EmailValidator")
class EmailValidatorTest {

    private EmailValidator validator;

    @BeforeEach
    void setUp() {
        validator = new EmailValidator();
    }

    @Nested
    @DisplayName("isValid")
    class IsValid {
        @ParameterizedTest
        @ValueSource(strings = {
                "user@example.com",
                "valid@example.com",
                "first.last@company.co.uk",
                "user+tag@domain.org",
                "a@b.co"
        })
        void acceptsValidEmails(String email) {
            assertThat(validator.isValid(email)).isTrue();
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"   ", "not-an-email", "@domain.com", "user@", "user@domain"})
        void rejectsInvalidEmails(String email) {
            assertThat(validator.isValid(email)).isFalse();
        }

        @Test
        void rejectsDoubleDotsInLocalPart() {
            assertThat(validator.isValid("user..name@example.com")).isFalse();
        }

        @Test
        void rejectsDoubleDotsInDomain() {
            assertThat(validator.isValid("user@exam..ple.com")).isFalse();
        }

        @Test
        void rejectsLeadingDotInLocal() {
            assertThat(validator.isValid(".user@example.com")).isFalse();
        }

        @Test
        void rejectsMultipleAtSigns() {
            assertThat(validator.isValid("user@@example.com")).isFalse();
        }

        @Test
        void trimsBeforeValidation() {
            assertThat(validator.isValid("  user@example.com  ")).isTrue();
        }

        @Test
        void rejectsOverlyLongAddress() {
            String longLocal = "a".repeat(250);
            assertThat(validator.isValid(longLocal + "@example.com")).isFalse();
        }
    }

    @Nested
    @DisplayName("normalize")
    class Normalize {
        @Test
        void trimsAndLowercases() {
            assertThat(validator.normalize("  User@Example.COM  "))
                    .isEqualTo("user@example.com");
        }

        @Test
        void throwsForInvalidEmail() {
            assertThatThrownBy(() -> validator.normalize("bad"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Invalid email");
        }
    }
}
