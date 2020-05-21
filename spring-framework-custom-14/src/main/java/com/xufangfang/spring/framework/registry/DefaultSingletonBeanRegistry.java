package com.xufangfang.spring.framework.registry;


import java.util.HashMap;
import java.util.Map;

/**
 * 封装了单例bean存储的Map集合对象，并且提供接口对外提供操作功能
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    /**
     * 存储Bean实例
     * K：beanName
     * V：Bean实例
     */
    private Map<String, Object> singletonObjects = new HashMap<String, Object>();

    @Override
    public Object getSingleton(String name) {
        return singletonObjects.get(name);
    }

    @Override
    public void addSingleton(String name, Object bean) {
        singletonObjects.put(name,bean);
    }
}
