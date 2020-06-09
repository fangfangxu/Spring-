package com.xufangfang.springmvc.adapter;

import com.xufangfang.springmvc.adapter.iface.HandlerAdapter;
import com.xufangfang.springmvc.handler.iface.HttpRequestHandler;
import com.xufangfang.springmvc.model.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 适配并处理HttpRequestHandler
 */
public class HttpRequestHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof HttpRequestHandler);
    }

    @Override
    public ModelAndView handleRequest(Object handler ,HttpServletRequest request, HttpServletResponse response) throws Exception {
        ((HttpRequestHandler)handler).handleRequest(request,response);
        return null;
    }
}
