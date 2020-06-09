package com.xufangfang.springmvc.handler.iface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 模仿Servlet的处理方式
 */
public interface HttpRequestHandler {
     void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
