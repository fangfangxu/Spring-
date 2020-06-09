package com.xufangfang.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public abstract class BaseServlet extends HttpServlet {
    @Override
    public final void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    public final void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String methodName = req.getParameter("method");
        if(methodName==null){
            methodName="execute";
        }

        System.out.println("BaseServlet : " + this + " , " + methodName);
        try {
            Method method = this.getClass().getMethod(methodName);
            String result = (String)method.invoke(this);
            resp.getWriter().write(result);
        } catch (Exception e) {
            throw new RuntimeException("服务器异常", e);
        }


    }
    /**
     * 此方法用于复写，方便子类编程，默认执行方法
     */
    public void execute()
            throws ServletException, IOException {
    }
}
