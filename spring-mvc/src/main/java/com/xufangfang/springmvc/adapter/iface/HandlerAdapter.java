package com.xufangfang.springmvc.adapter.iface;

import com.xufangfang.springmvc.model.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 适配器接口
 * 1、适配
 * 2、执行
 */
public interface HandlerAdapter {
    /**
     * 适配功能：处理器类和HandlerAdapter之间的适配
     * @param handler
     * @return
     */
    boolean supports(Object handler);

    /**
     * 适配器的执行功能（不同的适配器执行的处理器是不同的）
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    ModelAndView handleRequest(Object handler ,HttpServletRequest request, HttpServletResponse response) throws Exception;


}
