package com.xufangfang.spring.framework.registry;

/**
 * 对单例对象集合的操作功能
 *
 */
public interface SingletonBeanRegistry {
    //#getSingleton
    Object getSingleton(String name);
   //#addSingleton
    void addSingleton(String name,Object bean);
}
