package com.xufangfang.springmvc.handler.iface;


import com.xufangfang.springmvc.model.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 模仿Servlet的处理方式，而且可以在response之前，针对响应结果进行拦截处理
 */
public interface SimpleControllerHandler {
    ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
