package aop.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Jdk动态代理工厂
 */
public class JdkProxyFactory implements InvocationHandler {
    //目标类
    private Object target;

    public JdkProxyFactory(Object target) {
        this.target = target;
    }


    public Object getProxy() {
        Object proxy = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
                this);
        return proxy;
    }


    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //增强代码
         method.invoke(target, args);
        //增强代码
        return null;
    }
}
