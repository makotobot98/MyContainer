import com.mako.context.ApplicationContext;
import com.mako.context.SimpleXmlApplicationContext;
import org.dom4j.DocumentException;
import org.junit.Test;

public class DummyTest {
    @Test
    public void test() throws DocumentException {
        ApplicationContext applicationContext = new SimpleXmlApplicationContext("applicationContext.xml");
    }
}
