package com.CSIS3101.tests;

import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.CSIS3101.coursework.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KT2ETTest {
    InputStream sysInBackup = System.in; // backup System.in to restore it later
    ByteArrayInputStream in;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;


    @BeforeAll
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterAll
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        System.setIn(sysInBackup);
    }

    @Test
    public void testTemp() {
        in = new ByteArrayInputStream("100.0".getBytes());
        System.setIn(in);
        AlexanderLotzKT2ET.main(null);
        assertTrue(outContent.toString().contains("17"));
    }

    /*@DisplayName("Correct Input Types")
    @Test
    public void testIntInput() {
        in = new ByteArrayInputStream("35.012312341234123".getBytes());
        System.setIn(in);
        AlexanderLotzKT2ET.main(null);
        assertTrue(outContent.toString().contains("35.012312341234123"));
    }*/


    /*@DisplayName("Correct Output Types")
    @Test
    public void testIntOutput() {
        Pattern decRegex = Pattern.compile("\\d\\.\\d");
        in = new ByteArrayInputStream("35.0".getBytes());
        System.setIn(in);
        AlexanderLotzKT2ET.main(null);
        Matcher m = decRegex.matcher(outContent.toString());
        assertFalse(m.find(), "Your output should print numbers as integers. Double/float values are invalid.");
    }*/
}
