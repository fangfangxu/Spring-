package com.xufangfang.spring.framework.registry;

import com.xufangfang.spring.framework.ioc.BeanDefinition;

import java.util.List;

/**
 * 提供对BbeanDefinition集合数据的操作功能
 */
public interface BeanDefinitionRegistry {
    //#getBeanDefinition
    BeanDefinition getBeanDefinition(String name);
    //#registerBeanDefinition
    void registerBeanDefinition(String name,BeanDefinition bd);
    //#getBeanDefinitions
    List<BeanDefinition> getBeanDefinitions();
}
