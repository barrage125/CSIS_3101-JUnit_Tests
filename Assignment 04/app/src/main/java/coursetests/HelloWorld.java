// File:    HelloWorld.java
// Project: CSIS3101 JUnit Assignment 04
// Author:  Alexander Lotz
// History: Version 1.0 January 26, 2021

/*
 * This Java source file was generated by the Gradle 'init' task.
 */

package coursetests;

public class HelloWorld {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println(new HelloWorld().getGreeting());
    }
}