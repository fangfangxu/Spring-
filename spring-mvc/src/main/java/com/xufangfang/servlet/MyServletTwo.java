package com.xufangfang.servlet;

public class MyServletTwo extends BaseServlet {
    public String query(){
        String str="--MyServletTwo:query";
        return str;
    }

    public String add(){
        String str="--MyServletTwo:add";
        return str;
    }
}
