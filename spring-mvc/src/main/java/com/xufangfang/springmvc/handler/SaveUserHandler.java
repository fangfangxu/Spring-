package com.xufangfang.springmvc.handler;

import com.xufangfang.springmvc.handler.iface.SimpleControllerHandler;
import com.xufangfang.springmvc.model.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理器类： 处理添加用户的请求
 */
public class SaveUserHandler implements SimpleControllerHandler {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/plain;charset=utf8");
        response.getWriter().write("wyx最帅-SaveUserHandler");
        return null;
    }
}
