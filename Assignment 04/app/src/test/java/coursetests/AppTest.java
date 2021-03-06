// File:    AppTest.java
// Project: CSIS3101 JUnit Assignment 04
// Author:  Alexander Lotz
// History: Version 1.0 January 26, 2021

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package coursetests;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AppTest {
    @Disabled("Setting up other parts of project before resolving")
    @Test void appHasAGreeting() {
        HelloWorld classUnderTest = new HelloWorld();
        assertNotNull(classUnderTest.getGreeting(),
                      "app should have a greeting");
    }
}
