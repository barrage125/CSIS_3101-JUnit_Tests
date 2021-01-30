/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package custom.JUnit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileMethodFactoryTest {
    @Disabled
    @Test
    public void testFileMethodFactory() throws MalformedURLException,
            ClassNotFoundException,
            NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException,
            FileMethodFactory.MethodOverloadException {
        String className = "Greeter";
        Map<String, List<Class<?>[]>> methParamMap = new TreeMap<>();
        ArrayList<Class<?>[]> argTypes = new ArrayList<>();
        argTypes.add(new Class<?>[]{String[].class});
        methParamMap.put("getGreeting", null);
        methParamMap.put("main", argTypes);
        FileMethodFactory fac = new FileMethodFactory(className, methParamMap);
        fac.invoke("main", (Object) new String[]{});
        assertEquals("Hello World!", fac.invoke("getGreeting"));
    }
}
