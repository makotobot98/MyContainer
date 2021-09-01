import com.mako.context.ApplicationContext;
import com.mako.context.SimpleXmlApplicationContext;
import com.mako.utils.ClassUtils;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

public class DummyTest {
    @Test
    public void test() throws DocumentException {
        ApplicationContext applicationContext = new SimpleXmlApplicationContext("applicationContext.xml");
    }

    @Test
    public void testIO() throws IOException {
        ClassLoader cl = ClassUtils.getClassLoader();
        String packageName = "com.mako.context";
        String packagePath = packageName.replaceAll("[.]", "/") + "/";
        InputStream is = cl.getResourceAsStream(packagePath);
        System.out.println(is);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        Set<Class> set = reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getClass(line, packageName))
                .collect(Collectors.toSet());
        System.out.println(set);
    }

    private Class getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            // handle the exception
        }
        return null;
    }
}

//file:/C:/Users/13354/projects/IntellijProj/MyContainer/target/classes/com/mako/context/
