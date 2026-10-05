package com.example.app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("App")
class AppTest {

    @Test
    void mainRunsWithoutExceptionAndPrintsBanner() {
        PrintStream original = System.out;
        ByteArrayOutputStream capture = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(capture));
            App.main(new String[0]);
        } finally {
            System.setOut(original);
        }
        String output = capture.toString();
        assertThat(output).contains("Simple Java App");
        assertThat(output).contains("2 + 3 = 5");
        assertThat(output).contains("valid@example.com valid: true");
    }

    @Test
    void mainAcceptsEmptyArgs() {
        App.main(new String[]{});
    }
}
