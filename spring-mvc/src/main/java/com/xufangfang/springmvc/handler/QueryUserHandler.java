package com.xufangfang.springmvc.handler;

import com.xufangfang.springmvc.handler.iface.HttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理器类：处理queryUser请求
 */
public class QueryUserHandler implements HttpRequestHandler {

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/plain;charset=utf8");
        response.getWriter().write("wyx最帅-QueryUserHandler");
    }
}
