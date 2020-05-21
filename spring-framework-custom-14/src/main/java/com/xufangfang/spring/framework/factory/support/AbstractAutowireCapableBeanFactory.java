package com.xufangfang.spring.framework.factory.support;

import com.xufangfang.spring.framework.ioc.BeanDefinition;
import com.xufangfang.spring.framework.ioc.PropertyValue;
import com.xufangfang.spring.framework.ioc.RuntimeBeanReference;
import com.xufangfang.spring.framework.ioc.TypedStringValue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 该类提供了创建Bean和依赖注入Bean的功能
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
    @Override
    public Object createBean(BeanDefinition beanDefination) {
        Class<?> clazzType = beanDefination.getClazzType();
        if (clazzType == null) {
            return null;
        }
        //第一步:Bean的实例化（new 对象）
        Object bean = createBeanInstance(clazzType);
        //第二步:装配Bean、Bean的依赖注入
        populateBean(bean, beanDefination);
        //第三步：Bean的初始化
        initializeBean(bean, beanDefination);
        return bean;
    }
    /**
     * 创建Bean实例:默认使用无参构造
     *
     * @param clazzType
     * @return
     */
    private Object createBeanInstance(Class<?> clazzType) {
        try {
            Constructor<?> constructor = clazzType.getConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 一般在该方法中会处理init-method标签和InitializingBean接口的初始化工作
     * 还会处理Aware接口
     *
     * @param bean
     * @param beanDefinition
     */
    private void initializeBean(Object bean, BeanDefinition beanDefinition) {
        //TODO 处理Aware接口（标记）
        //TODO  处理InitializingBean的初始化
        String initMethod = beanDefinition.getInitMethod();
        if (initMethod == null) {
            return;
        }
        //处理初始化方法init-method
        invokeInitMethod(bean, beanDefinition);
    }

    /**
     * 处理init-method
     *
     * @param beanDefination
     */
    private void invokeInitMethod(Object bean, BeanDefinition beanDefination) {

        String initMethod = beanDefination.getInitMethod();
        Class<?> clazzType = beanDefination.getClazzType();
        try {
            Method method = clazzType.getDeclaredMethod(initMethod);
            method.invoke(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 依赖注入
     *
     * @param bean
     * @param beanDefination
     */
    private void populateBean(Object bean, BeanDefinition beanDefination) {
        List<PropertyValue> propertyValues = beanDefination.getPropertyValues();
        for (PropertyValue pv : propertyValues) {
            String name = pv.getName();
            Object value = pv.getValue();
            //处理参数
            Object valueToUse = resolveValue(value);
            //设置属性
            setProperty(bean, name, valueToUse, beanDefination);
        }

    }
    /**
     * 设置属性
     *
     * @param bean
     * @param name
     * @param valueToUse
     * @param beanDefination
     */
    private void setProperty(Object bean, String name, Object valueToUse, BeanDefinition beanDefination) {
        Class<?> clazzType = beanDefination.getClazzType();
        try {
            Field field = clazzType.getDeclaredField(name);
            field.setAccessible(true);
            field.set(bean, valueToUse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object resolveValue(Object value) {
        if (value instanceof TypedStringValue) {
            TypedStringValue tsv = (TypedStringValue) value;
            String stringValue = tsv.getValue();
            Class<?> targetType = tsv.getTargetType();
            if (targetType == Integer.class) {
                return Integer.valueOf(stringValue);
            } else if (targetType == String.class) {
                return stringValue;
            }

        } else if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference rbf = (RuntimeBeanReference) value;
            String ref = rbf.getRef();
            return getBean(ref);
        }
        return null;
    }

}
