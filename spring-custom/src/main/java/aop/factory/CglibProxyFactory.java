package aop.factory;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * cglib动态代理
 */
public class CglibProxyFactory implements MethodInterceptor {

   public Object getProxyByCglib(Class clazz){
       //设置增强器
       Enhancer enhancer=new Enhancer();
       //设置需要增强的类的类对象
       enhancer.setSuperclass(clazz);
       //设置回调函数
       enhancer.setCallback(this);
       //获取增强之后的代理对象
       return enhancer.create();
   }

    /**
     *
     * @param o  ：这是代理对象，也就是【目标对象】的子类
     * @param method ：【目标对象】的方法
     * @param objects ：参数
     * @param methodProxy：代理对象的方法
     * @return
     * @throws Throwable
     */
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        //因为代理对象是目标对象的子类
        //该行代码，实际调用的是父类目标对象的方法
        System.out.println("这是cglib的代理方法");
        //通过调用子类【代理类】的invokeSuper方法，去实际调用【目标类】的方法
        Object o1 = methodProxy.invokeSuper(o, objects);
        //代理对象调用代理对象的invokeSuper方法，而invokeSuper方法会调用
        //目标类的invoke方法完成目标对象的调用
        return o1;
    }
}
