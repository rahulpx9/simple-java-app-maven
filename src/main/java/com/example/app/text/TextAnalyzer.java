package com.example.app.text;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * String analysis utilities.
 */
public class TextAnalyzer {

    private static final Pattern WORD_PATTERN = Pattern.compile("\\b\\w+\\b");

    public int wordCount(String text) {
        if (text == null || text.isBlank()) {
            return 0;
        }
        var matcher = WORD_PATTERN.matcher(text);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    public int characterCount(String text, boolean includeWhitespace) {
        if (text == null) {
            return 0;
        }
        if (includeWhitespace) {
            return text.length();
        }
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (!Character.isWhitespace(text.charAt(i))) {
                count++;
            }
        }
        return count;
    }

    public String reverse(String text) {
        if (text == null) {
            return null;
        }
        return new StringBuilder(text).reverse().toString();
    }

    public boolean isPalindrome(String text) {
        if (text == null) {
            return false;
        }
        String normalized = normalizeForPalindrome(text);
        if (normalized.isEmpty()) {
            return false;
        }
        return normalized.contentEquals(new StringBuilder(normalized).reverse());
    }

    public String truncate(String text, int maxLength, String suffix) {
        if (text == null) {
            return null;
        }
        if (maxLength < 0) {
            throw new IllegalArgumentException("maxLength must be non-negative");
        }
        if (suffix == null) {
            throw new IllegalArgumentException("suffix must not be null");
        }
        if (text.length() <= maxLength) {
            return text;
        }
        if (maxLength == 0) {
            return "";
        }
        int suffixLen = suffix.length();
        if (maxLength <= suffixLen) {
            return suffix.substring(0, maxLength);
        }
        return text.substring(0, maxLength - suffixLen) + suffix;
    }

    public int vowelCount(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = Character.toLowerCase(text.charAt(i));
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                count++;
            }
        }
        return count;
    }

    private String normalizeForPalindrome(String text) {
        StringBuilder sb = new StringBuilder(text.length());
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }

    public String capitalizeWords(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        String trimmed = text.trim();
        if (trimmed.isEmpty()) {
            return trimmed;
        }
        String[] parts = trimmed.split("\\s+");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (i > 0) {
                result.append(' ');
            }
            String word = parts[i];
            if (!word.isEmpty()) {
                result.append(word.substring(0, 1).toUpperCase(Locale.ROOT))
                        .append(word.substring(1).toLowerCase(Locale.ROOT));
            }
        }
        return result.toString();
    }
}
