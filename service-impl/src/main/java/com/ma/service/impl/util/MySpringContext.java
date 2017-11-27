package com.ma.service.impl.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/11/23 0023.
 */
@Component
public class MySpringContext implements BeanFactoryAware {

    private static BeanFactory beanFactory = null;
    private static MySpringContext serviceLocator = null;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        MySpringContext.beanFactory = beanFactory;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public static MySpringContext getInstance() {
        if (serviceLocator == null) {
            serviceLocator = (MySpringContext) beanFactory.getBean("serviceLocator");
        }
        return serviceLocator;
    }

    public static Object wantBean(String serviceName) {
        return beanFactory.getBean(serviceName);
    }

}
