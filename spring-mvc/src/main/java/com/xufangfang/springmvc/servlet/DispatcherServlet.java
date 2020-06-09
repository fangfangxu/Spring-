package com.xufangfang.springmvc.servlet;


import com.xufangfang.springmvc.adapter.HttpRequestHandlerAdapter;
import com.xufangfang.springmvc.adapter.SimpleControllerHandlerAdapter;
import com.xufangfang.springmvc.adapter.iface.HandlerAdapter;
import com.xufangfang.springmvc.mapping.BeanNameUrlHandlerMapping;
import com.xufangfang.springmvc.mapping.SimpleUrlHandlerMapping;
import com.xufangfang.springmvc.mapping.iface.HandlerMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class DispatcherServlet extends AbstractServlet {

    private static final long serialVersionUID = -4990721536059971117L;
    private List<HandlerAdapter> handlerAdapters=new ArrayList<>();
    private List<HandlerMapping> handlerMappings=new ArrayList<>();

    @Override
    public void init() throws ServletException {
        handlerAdapters.add(new HttpRequestHandlerAdapter());
        handlerAdapters.add(new SimpleControllerHandlerAdapter());
        handlerMappings.add(new BeanNameUrlHandlerMapping()) ;
        handlerMappings.add(new SimpleUrlHandlerMapping());
    }

    @Override
    public void doDispatch(HttpServletRequest req, HttpServletResponse resp) {
        try {
            //根据请求查找处理器（先得搞清楚处理器有哪些类型）
            Object handler = getHandler(req);
            if (handler == null) {
                return;
            }
            //根据处理器查找处理器适配器
            HandlerAdapter ha = getHandlerAdapter(handler);
            if(ha==null){
                return;
            }
            //请求处理器适配器执行处理器功能
            ha.handleRequest(handler, req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 根据请求查找处理器
     *
     * @param req
     * @return
     */
    private Object getHandler(HttpServletRequest req) throws Exception{
       //需要通过处理器映射器去查找对应的处理器
        if(handlerMappings !=null){
            for(HandlerMapping hm:handlerMappings){ Object handler = null;
               handler = hm.getHandler(req);
                if (handler != null) {
                    return handler;
                }

            }
        }

        return null;
    }

    /**
     * 根据处理器找到对应的处理器适配器
     *
     * @param handler
     * @return
     */
    private HandlerAdapter getHandlerAdapter(Object handler) {
        //根据处理器对象，从一堆的处理器适配器中，匹配到合适的处理器适配器
        /**
         * 策略模式
         */
        if(handlerAdapters !=null){
            for (HandlerAdapter ha:handlerAdapters){
                if(ha.supports(handler)){
                    return ha;
                }
            }
        }
        return null;
    }
}
