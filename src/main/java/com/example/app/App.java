package com.example.app;

import com.example.app.calculator.Calculator;
import com.example.app.text.TextAnalyzer;
import com.example.app.validation.EmailValidator;

/**
 * Entry point demonstrating core library features.
 */
public final class App {

    private App() {
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();
        TextAnalyzer analyzer = new TextAnalyzer();
        EmailValidator emailValidator = new EmailValidator();

        System.out.println("Simple Java App");
        System.out.println("2 + 3 = " + calc.add(2, 3));
        System.out.println("Words in 'hello world': " + analyzer.wordCount("hello world"));
        System.out.println("'racecar' palindrome: " + analyzer.isPalindrome("racecar"));
        System.out.println("valid@example.com valid: " + emailValidator.isValid("valid@example.com"));
    }
}
