// File:    AppTest.java
// Project: CSIS3101 JUnit Assignment 04
// Author:  Alexander Lotz
// History: Version 1.0 January 26, 2021

package custom.junit.jupiter;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * The FileMethodFactory program acts a container for a Java Class
 * by which you can invoke said class's methods using Reflection.
 *
 * @author  Alexander Lotz
 * @version 1.0
 * @since   01-28-2021
 */
public class FileMethodFactory {
    /**
     * The focused class of FileMethodFactory.
     */
    private Class<?> targetClass;
    /**
     * A list of all methods declared for target Class.
     */
    private final ArrayList<Method> methodList = new ArrayList<>();
    /**
     *
     * @param className desc
     * @param methParamMap desc
     */
    public FileMethodFactory(String className,
                        Map<String, List<Class<?>[]>> methParamMap) throws
            MalformedURLException,
            ClassNotFoundException,
            NoSuchMethodException {
        loadClass(className);
        for (Map.Entry<String, List<Class<?>[]>> m : methParamMap.entrySet()) {
            loadMethod(m);
        }
    }

    private void loadClass(String className) throws ClassNotFoundException,
                                                    MalformedURLException {
        ClassLoader loader = Test.class.getClassLoader();
        String pwd = Objects.requireNonNull(loader.getResource("coursetests"))
                .toString();
        pwd = pwd.substring(0, pwd.length() - "test/coursetests".length());
        URL dirUrl = new URL("file:/" + pwd);
        URLClassLoader cl = new URLClassLoader(new URL[]{dirUrl});
        targetClass = cl.loadClass(className);
    }

    private void loadMethod(Map.Entry<String, List<Class<?>[]>> m) throws
            NoSuchMethodException {
        if (m.getValue() == null) {
            Method loadedMethod = targetClass.getDeclaredMethod(m.getKey());
            methodList.add(loadedMethod);
        } else {
            for (Class<?>[] argTypes : m.getValue()) {
                Method loadedMethod = targetClass.getDeclaredMethod(m.getKey(),
                                                                    argTypes);
                methodList.add(loadedMethod);
            }
        }
    }

    public Stream<Object> methodCalls() {
           return Stream.of(
                arguments("HelloWorld", "getGreeting")
        );
    }

    /**
     *
     * @return {@link FileMethodFactory#targetClass}
     */
    public Class<?> getFactoryClass() {
        return targetClass;
    }

    public List<String> getMethods() {
        return new ArrayList<>();
    }

    public void listMethods() {
        for (Method meth : methodList) {
            System.out.println(meth.getName());
            for (Class<?> argType : meth.getParameterTypes()) {
                System.out.println("  - " + argType.getName());
            }
        }
    }

    public Method getMethod(String methodName, Object... args) throws
            NoSuchMethodException,
            MethodOverloadException {
        Method targetMethod = null;
        for (Method m : methodList) {
            if (m.getName().equals(methodName)
                    && m.getParameterCount() == args.length) {
                if (targetMethod != null) {
                    throw new MethodOverloadException("The target method has"
                            + " multiple overloads with the same number of"
                            + " parameters.\nThe method " + methodName
                            + " should instead be invoked using"
                            + " invokeDirect(String methodName,"
                            + " List<Map.Entry<Class<?>, Object>> typesAndArgs"
                            + ").");
                } else {
                    targetMethod = m;
                }
            }
        }
        if (targetMethod == null) {
            throw new NoSuchMethodException("Method " + methodName + " of "
                    + "Class " + targetClass.getName() + " does not exist.");
        }
        return targetMethod;
    }

    public List<Class<?>> getMethodTypes(Method method) {
        return new ArrayList<>();
    }


    /**
     * Creates a new Entry object given a key-value pair.
     * This is just a helper method for concisely creating a new Entry.
     *
     * @see <pre><a href="https://stackoverflow.com/questions/39441096/
     * how-to-put-an-entry-into-a-map">Source</a></pre>
     * @param key   key of the entry
     * @param value value of the entry
     * @param <K>
     * @param <V>
     * @return  the Entry object containing the given key-value pair
     */
    private static <K, V> Map.Entry<K, V> newEntry(K key, V value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    public void treeMapBuilder() {

    }

    /**
     * Invokes the specified method of the contained class.
     *
     * @param methodName desc
     * @param args desc
     * @return Returns the output of the invoked method or null
     *         if the invoked method is void
     * @throws InvocationTargetException f
     * @throws IllegalAccessException f
     */
    public Object invoke(String methodName, Object... args) throws
            InvocationTargetException,
            IllegalAccessException,
            NoSuchMethodException,
            InstantiationException,
            MethodOverloadException {
        Object obj = targetClass.getDeclaredConstructor().newInstance();
        Method m = getMethod(methodName, args);
        if (args.length == 0) {
            if (m.getReturnType().equals(Void.TYPE)) {
                m.invoke(obj);
                return null;
            } else {
                return m.invoke(obj);
            }
        } else {
            if (m.getReturnType().equals(Void.TYPE)) {
                m.invoke(obj, args);
                return null;
            } else {
                return m.invoke(obj, args);
            }
        }
    }

    public Object invokeDirect(String methodName,
                           List<Map.Entry<Class<?>, Object>> argsList) throws
            InvocationTargetException,
            IllegalAccessException,
            NoSuchMethodException,
            InstantiationException {
        Object obj = targetClass.getDeclaredConstructor().newInstance();
        Class<?>[] argTypes = new Class<?>[argsList.size()];
        Object[] args = new Object[argsList.size()];
        for (int i = 0; i < argsList.size(); i++) {
            argTypes[i] = (argsList.get(i).getKey());
            args[i] = (argsList.get(i).getValue());
        }
        Method m = targetClass.getDeclaredMethod(methodName, argTypes);
        if (m.getReturnType().equals(Void.TYPE)) {
            m.invoke(obj, args);
            return null;
        } else {
            return m.invoke(obj, args);
        }
    }

    public static class MethodOverloadException extends Exception {

        public MethodOverloadException() {
            super();
        }

        public MethodOverloadException(String message) {
            super(message);
        }

        public MethodOverloadException(String message, Throwable cause) {
            super(message, cause);
        }

        public MethodOverloadException(Throwable cause) {
            super(cause);
        }
    }
}
