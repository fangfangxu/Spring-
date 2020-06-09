package com.xufangfang.springmvc.mapping;

import com.xufangfang.springmvc.handler.SaveUserHandler;
import com.xufangfang.springmvc.mapping.iface.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 将处理器类配置到xml中的bean标签
 */
public class SimpleUrlHandlerMapping implements HandlerMapping {
    /**
     * 请求和处理器类方法的映射集合
     */
    private Map<String, Object> urlHandlers = new HashMap<>();

    public SimpleUrlHandlerMapping() {
        urlHandlers.put("/saveUser", new SaveUserHandler());
    }

    @Override
    public Object getHandler(HttpServletRequest request) throws Exception {
        String uri = request.getRequestURI();
        return urlHandlers.get(uri);
    }
}
