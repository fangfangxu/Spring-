package com.xufangfang.spring.framework.factory.support;

import com.xufangfang.spring.framework.factory.BeanFactory;

import java.util.List;

/**
 * 可列表化的展示Spring容器中的bean实例
 */
public interface ListableBeanFactory extends BeanFactory {
    /**
     * 可以根据参数获取它子类型的实例，比如传递Object.class，
     * 则说明获取的是所有的实例对象
     * @param clazz
     * @return
     */
   List<Object> getBeansByType(Class<?> clazz);
    /**
     * 可以根据参数获取它子类型的实例，比如传递Object.class，
     * 则说明获取的是所有的实例对象的名称
     * @param clazz
     * @return
     */
    List<Object> getBeanNamesByType(Class<?> clazz);

}
