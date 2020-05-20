package com.xufangfang.spring.framework.resource;

import java.io.InputStream;

/**
 * 提供操作资源的功能（XML就是一种资源，这个资源可能在互联网上、
 * 可能在本地磁盘上、classpath上）
 */
public interface Resource {
    InputStream getResource();

}
