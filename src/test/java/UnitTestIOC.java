import com.mako.annotationBean.*;
import com.mako.context.ApplicationContext;
import com.mako.context.SimpleXmlApplicationContext;
import org.junit.Test;

import java.lang.reflect.Field;

public class UnitTestIOC {
    @Test
    public void testIOCPlainXmlBean() throws Exception {
        ApplicationContext applicationContext = new SimpleXmlApplicationContext("appContext-PlainXml.xml");
        BeanC beanC = (BeanC) applicationContext.getBean("beanC");
        System.out.println(beanC);
    }

    @Test
    public void TestIOCPlainXmlBeanWithBeanReference() throws Exception {
        ApplicationContext applicationContext = new SimpleXmlApplicationContext("appContext-PlainXmlWithReference.xml");
        BeanA beanA = (BeanA) applicationContext.getBean("beanA");
        System.out.println(beanA);
    }

    @Test
    public void TestIOCPlainXmlBeanWithCircularDependency() throws Exception {
        ApplicationContext applicationContext = new SimpleXmlApplicationContext("appContext-PlainXmlWithCircularDependency.xml");
        BeanB beanB = (BeanB) applicationContext.getBean("beanB");
        System.out.println(beanB);

        BeanD beanD = (BeanD) applicationContext.getBean("beanD");
        System.out.println(beanD);
    }

    @Test
    public void TestIOCWithAnnotation() throws Exception {
        ApplicationContext applicationContext = new SimpleXmlApplicationContext("appContext-AnnotatedBeanAutowire.xml");
        AnnotatedBeanE annotatedBeanE = (AnnotatedBeanE) applicationContext.getBean("annotatedBeanE");
        System.out.println(annotatedBeanE);

        AnnotatedBeanF annotatedBeanF = (AnnotatedBeanF) applicationContext.getBean("annotatedBeanF");
        System.out.println(annotatedBeanF);
    }
}
