package com.xufangfang.spring.framework.resource.support;

import com.xufangfang.spring.framework.resource.Resource;

import java.io.InputStream;


/**
 * 专门用来解决classpath目录下资源的类
 * 一个ClassPathResource就是一个资源，接口是对外提供该资源的访问功能
 */
public class ClassPathResource implements Resource {
    //资源路径
    private String resource;

    public ClassPathResource(String resource) {
        this.resource = resource;
    }

    @Override
    public InputStream getResource() {
        return this.getClass().getClassLoader().getResourceAsStream(resource);
    }
}
