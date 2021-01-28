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
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@ExtendWith(TestParameterResolver.class)
class AppTest {
    //@Test
    @ParameterizedTest()
    //@CsvSource({"HelloWorld,getGreeting"})
    @MethodSource("methodCalls")
    public void testNoArgFunc(String className, String methodName/*, Class[]... classTypes*/) throws MalformedURLException,
                                  ClassNotFoundException,
                                  NoSuchMethodException,
                                  IllegalAccessException,
                                  InvocationTargetException,
                                  InstantiationException {
        ClassLoader loader = Test.class.getClassLoader();
        String pwd = loader.getResource("coursetests").toString();
        pwd = pwd.substring(0,pwd.length()-"test/coursetests".length());
        URL dirUrl = new URL("file:/" + "path_to_dir");
        URLClassLoader cl = new URLClassLoader(new URL[] {dirUrl},
                getClass().getClassLoader());
        Class loadedClass = cl.loadClass(className);
        Object obj = loadedClass.getDeclaredConstructor().newInstance();
        Method m = loadedClass.getDeclaredMethod(methodName); //, new Class[] {});
        pwd = m.invoke(obj, new Object[] {}).toString();
        System.out.println(pwd);
        assertNotNull("notNull");
    }

    private static Stream<Arguments> methodCalls() {
        return Stream.of(
                arguments("HelloWorld","getGreeting")
        );
    }

    @Disabled("Setting up other parts of project before resolving")
    @Test void appHasAGreeting() {
        /*URL dirUrl = new URL("file:/" + "path_to_dir" + "/");
        URLClassLoader cl = new URLClassLoader(new URL[] {dirUrl},
                getClass().class.getClassLoader());  // 2
        Class loadedClass = cl.loadClass("com.xyz.MyClass");
        Object obj = Class.forName("com.example.Foo").newInstance();
        obj.invoke();doSomething();

        HelloWorld classUnderTest = new HelloWorld();
        assertNotNull(classUnderTest.getGreeting(),
                      "app should have a greeting");*/
    }
}
