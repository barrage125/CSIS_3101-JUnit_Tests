package com.CSIS3101.tests;

import com.CSIS3101.coursework.*;

import org.junit.jupiter.api.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PrintTester {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeAll
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        String[] args = null;
        PData.main(args);
    }

    @AfterAll
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void testName() {
        assertTrue(outContent.toString().contains("Name"));
    }

    @Test
    public void testEmail() {
        assertTrue(outContent.toString().contains("Email address"));
    }

    @Test
    public void testMajor() {
        assertTrue(outContent.toString().contains("Major"));
    }

    @Test
    public void testStuStatus() {
        assertTrue(outContent.toString().contains("Status at Nova (full-time, part-time, non-degree seeking)"));
    }

    @Test
    public void testEmpStatus() {
        assertTrue(outContent.toString().contains("Employment status (full-time, part-time, intern, student)"));
    }

    @Test
    public void testHousehold() {
        assertTrue(outContent.toString().contains("Campus dorm or town where you now live"));
    }

    @Test
    public void testHometown() {
        assertTrue(outContent.toString().contains("Hometown where you grew up"));
    }

    @Test
    public void testProgramming() {
        assertTrue(outContent.toString().contains("Previous programming course(s) taken, where, and instructor’s name"));
    }

    @Test
    public void testCS() {
        assertTrue(outContent.toString().contains("Other CS or Tech course(s) taken (and where if not Nova)"));
    }

    @Test
    public void testLanguages() {
        assertTrue(outContent.toString().contains("Programming languages with which you have even a little experience"));
    }

}
