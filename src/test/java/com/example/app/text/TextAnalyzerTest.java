package com.example.app.text;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("TextAnalyzer")
class TextAnalyzerTest {

    private TextAnalyzer analyzer;

    @BeforeEach
    void setUp() {
        analyzer = new TextAnalyzer();
    }

    @Nested
    @DisplayName("wordCount")
    class WordCount {
        @Test
        void countsWordsInSimpleSentence() {
            assertThat(analyzer.wordCount("hello world")).isEqualTo(2);
        }

        @Test
        void countsWordsWithPunctuation() {
            assertThat(analyzer.wordCount("Hello, world! How are you?")).isEqualTo(5);
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"   ", "\t\n"})
        void blankOrNullReturnsZero(String input) {
            assertThat(analyzer.wordCount(input)).isZero();
        }

        @Test
        void singleWord() {
            assertThat(analyzer.wordCount("word")).isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("characterCount")
    class CharacterCount {
        @Test
        void includesWhitespaceWhenRequested() {
            assertThat(analyzer.characterCount("a b", true)).isEqualTo(3);
        }

        @Test
        void excludesWhitespaceWhenRequested() {
            assertThat(analyzer.characterCount("a b c", false)).isEqualTo(3);
        }

        @Test
        void nullReturnsZero() {
            assertThat(analyzer.characterCount(null, true)).isZero();
        }
    }

    @Nested
    @DisplayName("reverse")
    class Reverse {
        @Test
        void reversesString() {
            assertThat(analyzer.reverse("abcd")).isEqualTo("dcba");
        }

        @Test
        void nullReturnsNull() {
            assertThat(analyzer.reverse(null)).isNull();
        }

        @Test
        void emptyString() {
            assertThat(analyzer.reverse("")).isEmpty();
        }
    }

    @Nested
    @DisplayName("isPalindrome")
    class IsPalindrome {
        @ParameterizedTest
        @ValueSource(strings = {"racecar", "A man a plan a canal Panama", "No 'x' in Nixon"})
        void recognizesPalindromes(String text) {
            assertThat(analyzer.isPalindrome(text)).isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {"hello", "java", "palindrome"})
        void rejectsNonPalindromes(String text) {
            assertThat(analyzer.isPalindrome(text)).isFalse();
        }

        @Test
        void nullIsNotPalindrome() {
            assertThat(analyzer.isPalindrome(null)).isFalse();
        }

        @Test
        void punctuationOnlyIsNotPalindrome() {
            assertThat(analyzer.isPalindrome("!!!")).isFalse();
        }
    }

    @Nested
    @DisplayName("truncate")
    class Truncate {
        @Test
        void noTruncationWhenShortEnough() {
            assertThat(analyzer.truncate("hi", 10, "...")).isEqualTo("hi");
        }

        @Test
        void truncatesWithSuffix() {
            assertThat(analyzer.truncate("hello world", 8, "..."))
                    .isEqualTo("hello...");
        }

        @Test
        void maxLengthZeroReturnsEmpty() {
            assertThat(analyzer.truncate("hello", 0, "...")).isEmpty();
        }

        @Test
        void whenMaxLengthSmallerThanSuffixUsesPrefixOfSuffix() {
            assertThat(analyzer.truncate("hello", 2, "...")).isEqualTo("..");
        }

        @Test
        void nullTextReturnsNull() {
            assertThat(analyzer.truncate(null, 5, "...")).isNull();
        }

        @Test
        void rejectsNegativeMaxLength() {
            assertThatThrownBy(() -> analyzer.truncate("x", -1, "..."))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void rejectsNullSuffix() {
            assertThatThrownBy(() -> analyzer.truncate("x", 5, null))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("vowelCount")
    class VowelCount {
        @ParameterizedTest
        @CsvSource({
                "aeiou, 5",
                "xyz, 0",
                "Hello, 2",
                "'', 0"
        })
        void countsVowels(String text, int expected) {
            assertThat(analyzer.vowelCount(text)).isEqualTo(expected);
        }

        @Test
        void nullReturnsZero() {
            assertThat(analyzer.vowelCount(null)).isZero();
        }
    }

    @Nested
    @DisplayName("capitalizeWords")
    class CapitalizeWords {
        @Test
        void capitalizesEachWord() {
            assertThat(analyzer.capitalizeWords("hello world")).isEqualTo("Hello World");
        }

        @Test
        void handlesMultipleSpaces() {
            assertThat(analyzer.capitalizeWords("  foo   bar  ")).isEqualTo("Foo Bar");
        }

        @Test
        void nullReturnsNull() {
            assertThat(analyzer.capitalizeWords(null)).isNull();
        }

        @Test
        void emptyReturnsEmpty() {
            assertThat(analyzer.capitalizeWords("")).isEmpty();
        }
    }
}
