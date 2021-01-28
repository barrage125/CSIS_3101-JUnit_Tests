package custom.junit.jupiter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.AbstractMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;

abstract class FileMethodFactory {
    private String className;
    private TreeMap<String, Map.Entry<Class, Object>[]> methParamMap;
    private TreeMap<Map.Entry<Class, Method>, Stream<Object>> methArgsMap;


    protected FileMethodFactory (String className, TreeMap<String, Map.Entry<Class, Object>[]> methParamMap){
        //Call className.TreeMap@Key for each occurrence using param Class Object entry pair
        this.className = className;
        this.methParamMap = methParamMap;
        methArgsMap = new TreeMap<Map.Entry<Class, Method>, Stream<Object>>();

        ClassLoader loader = Test.class.getClassLoader();
        String pwd = loader.getResource("coursetests").toString();
        pwd = pwd.substring(0, pwd.length() - "test/coursetests".length());
        URL dirUrl = new URL("file:/" + "path_to_dir");
        URLClassLoader cl = new URLClassLoader(new URL[]{dirUrl},
                getClass().getClassLoader());
        Class loadedClass = cl.loadClass(className);


        for (Map.Entry<String, Map.Entry<Class,Object>[]> m : methParamMap.entrySet()) {
            if (m.getValue()==null) {
                Object obj = loadedClass.getDeclaredConstructor().newInstance();
                Method loadedMethod = loadedClass.getDeclaredMethod(m.getKey()); //, new Class[] {});
                loadedMethod.invoke(obj);
                methArgsMap.put(new AbstractMap.SimpleEntry<Class, Method>(loadedClass, loadedMethod), null);
            } else {
                Class[] argTypes = new Class[m.getValue().length];
                Stream<Object> objectStream = Stream.empty();
                for (int i = 0; i < m.getValue().length; i++) {
                    Map.Entry<Class, Object> arg = m.getValue()[i];
                    argTypes[i] = arg.getKey();
                    Stream.concat(objectStream,Stream.of(arg.getValue()));
                }
                Object obj = loadedClass.getDeclaredConstructor().newInstance();
                Method loadedMethod = loadedClass.getDeclaredMethod(m.getKey(), argTypes);
                loadedMethod.invoke(obj, objectStream);
                methArgsMap.put(new AbstractMap.SimpleEntry<Class, Method>(loadedClass, loadedMethod), null)
            }
        }
    }

    protected void testNoArgFunc(String className, String methodName/*, Class[]... classTypes*/) throws MalformedURLException,
            ClassNotFoundException,
            NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        ClassLoader loader = Test.class.getClassLoader();
        String pwd = loader.getResource("coursetests").toString();
        pwd = pwd.substring(0, pwd.length() - "test/coursetests".length());
        URL dirUrl = new URL("file:/" + "path_to_dir");
        URLClassLoader cl = new URLClassLoader(new URL[]{dirUrl},
                getClass().getClassLoader());
        Class loadedClass = cl.loadClass(className);
        Object obj = loadedClass.getDeclaredConstructor().newInstance();
        Method m = loadedClass.getDeclaredMethod(methodName); //, new Class[] {});
        m.invoke(obj, new Object[]{});
    }
    abstract Stream<Object> methodCalls(); /*{

        return Stream.of(
                arguments("HelloWorld","getGreeting")
        );

    }*/
}

/*args = HelloWorld meth1 meth2 meth3

HelloWorld meth args
Map

String className, TreeMap<String, Pair<Class, Object>> methParamMap
Stream<Object>*/

