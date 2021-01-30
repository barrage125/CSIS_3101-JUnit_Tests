// File:    AppTest.java
// Project: CSIS3101 JUnit Assignment 04
// Author:  Alexander Lotz
// History: Version 1.0 January 26, 2021

package custom.JUnit;

import org.junit.jupiter.api.Test;

import java.io.File;
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
 * @since   01-26-2021
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
     * desc.
     * @param className desc
     * @param methParamMap desc
     */
    public FileMethodFactory(final String className,
                        final Map<String, List<Class<?>[]>> methParamMap) throws
            MalformedURLException,
            ClassNotFoundException,
            NoSuchMethodException {
        this(className, methParamMap, "");
    }

    /**
     * desc.
     * @param className desc
     * @param methParamMap desc
     * @param pkg desc
     */
    public FileMethodFactory(final String className,
                             final Map<String, List<Class<?>[]>> methParamMap, final String pkg) throws
            MalformedURLException,
            ClassNotFoundException,
            NoSuchMethodException {
        loadClass(className, pkg);
        for (Map.Entry<String, List<Class<?>[]>> m : methParamMap.entrySet()) {
            loadMethod(m);
        }
    }

    public FileMethodFactory(final String className,
                             final Map<String, List<Class<?>[]>> methParamMap,
                             final URL dirURL) throws
            ClassNotFoundException,
            NoSuchMethodException {
        loadClass(className, dirURL);
        for (Map.Entry<String, List<Class<?>[]>> m : methParamMap.entrySet()) {
            loadMethod(m);
        }
    }

    private void loadClass(final String className, final String pkg) throws
            ClassNotFoundException,
            MalformedURLException {
        ClassLoader loader = Test.class.getClassLoader();
        String pwd = Objects.requireNonNull(loader.getResource(pkg))
                .toString();
        pwd = pwd.substring(0, pwd.length() - ("main/" + pkg).length());
        URL dirUrl = new URL("file:/" + pwd);
        URLClassLoader cl = new URLClassLoader(new URL[]{dirUrl});
        targetClass = cl.loadClass(className);
    }

    private void loadClass(final String className, final URL dirUrl) throws
            ClassNotFoundException {
        URLClassLoader cl = new URLClassLoader(new URL[]{dirUrl});
        targetClass = cl.loadClass(className);
    }

    private void loadMethod(final Map.Entry<String, List<Class<?>[]>> m) throws
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

    /**
     * Streams.
     * @return a stream
     */
    public Stream<Object> methodCalls() {
           return Stream.of(
                arguments("HelloWorld", "getGreeting")
        );
    }

    /**
     * desc.
     * @return {@link FileMethodFactory#targetClass}
     */
    public Class<?> getFactoryClass() {
        return targetClass;
    }

    /**
     * desc.
     * @return a List of Methods
     */
    public List<Method> getMethods() {
        return methodList;
    }

    /**
     * Prints methods to {@link java.lang.System#out}.
     */
    public void listMethods() {
        for (Method meth : methodList) {
            System.out.println(meth.getName());
            for (Class<?> argType : meth.getParameterTypes()) {
                System.out.println("  - " + argType.getName());
            }
        }
    }

    /**
     * desc.
     * @param methodName
     * @param args
     * @return desc
     * @throws NoSuchMethodException
     * @throws MethodOverloadException
     */
    public Method getMethod(final String methodName,
                            final Object... args) throws
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

    /**
     * desc.
     * @param method
     * @return desc.
     */
    public List<Class<?>> getMethodTypes(final Method method) {
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
    private static <K, V> Map.Entry<K, V> newEntry(final K key,
                                                   final V value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    /**
     * desc.
     */
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
    public Object invoke(final String methodName, final Object... args) throws
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

    /**
     * Invokes the specified method of the contained class.
     *
     * @param methodName desc
     * @param argsList
     * @return Returns the output of the invoked method or null
     *         if the invoked method is void
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     */
    public Object invokeDirect(final String methodName,
                               final List<Map.Entry<Class<?>, Object>> argsList)
            throws InvocationTargetException,
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

    /**
     * The MethodOverloadException.
     *
     * @author  Alexander Lotz
     * @version 1.0
     * @since   01-28-2021
     */
    public static class MethodOverloadException extends Exception {

        /**
         * desc.
         */
        public MethodOverloadException() {
            super();
        }

        /**
         * desc.
         * @param message
         */
        public MethodOverloadException(final String message) {
            super(message);
        }

        /**
         * desc.
         * @param message
         * @param cause
         */
        public MethodOverloadException(final String message,
                                       final Throwable cause) {
            super(message, cause);
        }

        /**
         * desc.
         * @param cause
         */
        public MethodOverloadException(final Throwable cause) {
            super(cause);
        }
    }
}
