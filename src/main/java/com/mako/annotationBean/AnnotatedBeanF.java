package com.mako.annotationBean;

import com.mako.annotations.Autowired;
import com.mako.annotations.Service;
import com.mako.annotations.Value;

@Service(beanId="annotatedBeanF")
public class AnnotatedBeanF {
    @Autowired
    public AnnotatedBeanE annotatedBeanE;

    @Value(value="Kiki")
    public String name;

    public String password;
}
