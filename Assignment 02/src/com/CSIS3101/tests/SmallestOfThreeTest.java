package com.CSIS3101.tests;

import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.CSIS3101.coursework.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SmallestOfThreeTest {
    InputStream sysInBackup = System.in; // backup System.in to restore it later
    ByteArrayInputStream in;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeAll
    public void setUpStreams() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterAll
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        System.setIn(sysInBackup);
    }

    @DisplayName("Correct Smallest Calculation")
    @ParameterizedTest(name = "{index} ==> Smallest of ({0}, {1}, {2})")
    @CsvSource({"1,2,3",
            "1,3,2",
            "3,2,1",
            "1,2,2,",
            "2,1,2",
            "2,2,1",
            "1,1,2",
            "1,2,1",
            "2,1,1",
            "1,1,1"
    })
    public void testSmallest(int n1, int n2, int n3) {
        String testData = n1+"\n"+n2+"\n"+n3;
        String min = String.valueOf(Math.min(n1, Math.min(n2, n3)));
        Pattern minRegex = Pattern.compile(new StringBuilder().append("(").append(min).append("|").append(min).append("\\.)$").toString());
        in = new ByteArrayInputStream(testData.getBytes());
        System.setIn(in);
        N00935506_02.main(null);
        Matcher m = minRegex.matcher(outContent.toString());
        assertTrue(m.find());
    }

    @DisplayName("Correct Input Types")
    @Test
    public void testIntInput() {
        in = new ByteArrayInputStream("1.0\n2D\n3F".getBytes());
        System.setIn(in);
        assertThrows(InputMismatchException.class, () -> {
            N00935506_02.main(null);
        }, "Your input should accept only int values. Double/float values are invalid.");
    }


    @DisplayName("Correct Output Types")
    @Test
    public void testIntOutput() {
        Pattern decRegex = Pattern.compile("\\d\\.\\d");
        in = new ByteArrayInputStream("1\n1\n1".getBytes());
        System.setIn(in);
        N00935506_02.main(null);
        Matcher m = decRegex.matcher(outContent.toString());
        assertFalse(m.find(), "Your output should print numbers as integers. Double/float values are invalid.");
    }

    @DisplayName("Correct Output Message")
    @Test
    public void testOutputMsg() {
        in = new ByteArrayInputStream("531\n25034\n6".getBytes());
        System.setIn(in);
        N00935506_02.main(null);
        assertTrue(outContent.toString().contains("The smallest value among 531, " +
                "25034, and 6 is 6"),
                "Your result does not match the format listed in the Assignment" +
                        " requirements.\n The expected format is 'The smallest value" +
                        " among <a>, <b>, and <c> is <x>'");
    }
}
