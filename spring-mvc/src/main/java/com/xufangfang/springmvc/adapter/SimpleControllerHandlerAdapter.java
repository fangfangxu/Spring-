package com.xufangfang.springmvc.adapter;

import com.xufangfang.springmvc.adapter.iface.HandlerAdapter;
import com.xufangfang.springmvc.handler.iface.SimpleControllerHandler;
import com.xufangfang.springmvc.model.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 适配并处理SimpleControllerHandler处理器类的
 */
public class SimpleControllerHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof SimpleControllerHandler);
    }

    @Override
    public ModelAndView handleRequest(Object handler ,HttpServletRequest request, HttpServletResponse response) throws Exception {
        return ((SimpleControllerHandler)handler).handleRequest(request,response);
    }
}
