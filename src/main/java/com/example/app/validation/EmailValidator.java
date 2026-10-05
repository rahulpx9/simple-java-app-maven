package com.example.app.validation;

import java.util.regex.Pattern;

/**
 * Validates email addresses using a practical RFC-inspired pattern.
 */
public class EmailValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );

    private static final int MAX_LENGTH = 254;

    public boolean isValid(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }
        String trimmed = email.trim();
        if (trimmed.length() > MAX_LENGTH) {
            return false;
        }
        if (trimmed.startsWith(".") || trimmed.endsWith(".")) {
            return false;
        }
        int atIndex = trimmed.indexOf('@');
        if (atIndex <= 0 || atIndex != trimmed.lastIndexOf('@')) {
            return false;
        }
        String local = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);
        if (local.contains("..") || domain.contains("..")) {
            return false;
        }
        return EMAIL_PATTERN.matcher(trimmed).matches();
    }

    public String normalize(String email) {
        if (!isValid(email)) {
            throw new IllegalArgumentException("Invalid email: " + email);
        }
        return email.trim().toLowerCase();
    }
}
