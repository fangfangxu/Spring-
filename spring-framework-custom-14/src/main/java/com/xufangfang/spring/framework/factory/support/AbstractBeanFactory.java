package com.xufangfang.spring.framework.factory.support;

import com.xufangfang.spring.framework.factory.BeanFactory;
import com.xufangfang.spring.framework.ioc.BeanDefinition;

/**
 * 该抽象类，定义了获取bean实例的统一步骤
 */
public abstract class AbstractBeanFactory implements BeanFactory {



//    #BeanDefinition
//    #PropertyValue
//    #RuntimeBeanReference
//    #TypedStringValue

    //#@Override#getBean
    //里式替换原则、抽象模板设计模式
    @Override
    public Object getBean(String beanName){
        //先根据beanName去singletonObjects查询
        Object bean = singletonObjects.get(beanName);
        //如果有结果，则直接返回该实例
        if (bean != null) {
            return bean;
        }
        //如果没有结果，则需要创建Bean实例
        //现根据beanName去beanDefinations获取对应的BeanDefination（封装了该Bean创建时需要的相关信息）
        BeanDefinition beanDefination = beanDefinitions.get(beanName);
        if (beanDefination == null) {
            return null;
        }


        //根据BeanDefination中封装的信息来判断要创建的Bean是单例方式创建、还是多例方式创建
        if (beanDefination.isSingleton()) {
            //如果是单例，则需要将创建的Bean存储到singletonObjects
            bean = createBean(beanDefination);
            singletonObjects.put(beanName, bean);
        } else if (beanDefination.isPrototype()) {
            //如果是多例，则不需要将创建的Bean存储到singletonObjects
            bean = createBean(beanDefination);
        } else {
            //.....
        }


        return bean;
    }
}
