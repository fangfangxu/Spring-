package com.xufangfang.spring.framework.factory;

/**
 * Spring容器的顶级接口
 */
public interface BeanFactory {
    //#getBean
    Object getBean(String beanName);
}
