package com.mako.annotationBean;

import com.mako.annotations.Autowired;
import com.mako.annotations.Service;
import com.mako.annotations.Value;

@Service(beanId="annotatedBeanF")
public class AnnotatedBeanF {
    @Autowired
    AnnotatedBeanE annotatedBeanE;

    @Value(value="name of annotated bean F")
    public String name;

    public String password;
}
