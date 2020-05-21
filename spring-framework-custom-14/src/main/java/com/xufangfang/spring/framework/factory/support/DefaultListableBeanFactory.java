package com.xufangfang.spring.framework.factory.support;

import com.xufangfang.spring.framework.factory.support.AbstractAutowireCapableBeanFactory;
import com.xufangfang.spring.framework.factory.support.AbstractBeanFactory;
import com.xufangfang.spring.framework.ioc.BeanDefinition;
import com.xufangfang.spring.framework.registry.BeanDefinitionRegistry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 真正的Spring容器类
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ListableBeanFactory {
    /**
     * 解析Bean实例存放Map
     * K：beanName
     * V：BeanDefination
     */
    private Map<String, BeanDefinition> beanDefinitions = new HashMap<String, BeanDefinition>();

    /**
     * #beanDefinitions
     * #getBeanDefinition()
     * #registerBeanDefinition()
     * #getBeanDefinitions()
     *
     * @param name
     * @return
     */
    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return beanDefinitions.get(name);
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition bd) {
        beanDefinitions.put(name, bd);
    }

    @Override
    public List<BeanDefinition> getBeanDefinitions() {
        //TODO SpringMVC
        return null;
    }


    @Override
    public List<Object> getBeansByType(Class<?> clazz) {
        //TODO SpringMVC
        return null;
    }

    @Override
    public List<Object> getBeanNamesByType(Class<?> clazz) {
        //TODO SpringMVC
        return null;
    }
}
