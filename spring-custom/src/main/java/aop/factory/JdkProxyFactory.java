package aop.factory;

import aop.target.UserService;
import aop.target.UserServiceImpl;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Jdk动态代理工厂
 */
public class JdkProxyFactory implements InvocationHandler {
    //目标对象的引用
    private Object target;

    //通过构造方法将目标对象注入到代理对象中
    public JdkProxyFactory(Object target) {
        this.target = target;
    }

    /**
     * 如何生成一个代理类？
     * 1、编写源文件
     * 2、编译源文件为class文件
     * 3、将class文件加载到JVM中（ClassLoader）
     * 4、将class文件对应的对象进行实例化（反射）
     *
     * @return
     */
    public Object getProxy() {
        /**
         * Proxy是JDK肿的API类
         * 目标对象的类加载器、目标对象的接口、代理对象的执行处理器
         */
        Object proxy = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
                this);
        return proxy;
    }


    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method method2 = target.getClass().getMethod(method.getName());
        Method method3 = Class.forName("com.sun.proxy.$Proxy4").getMethod(method.getName());
        System.out.println("目标对象方法：" + method2.toString());
        System.out.println("目标接口的方法：" + method.toString());
        System.out.println("代理对象的方法：" + method3.toString());
        System.out.println("jdk代理方法！");

        //增强代码
        Object returnValue = method.invoke(target, args);//反射调用目标对象方法
        //增强代码
        return returnValue;
    }


}
