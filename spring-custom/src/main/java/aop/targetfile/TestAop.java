package aop.targetfile;

import aop.factory.CglibProxyFactory;
import aop.factory.JdkProxyFactory;
import org.junit.Test;

public class TestAop {
    @Test
    public void testJdkProxy(){
        UserService userService=new UserServiceImpl();
        JdkProxyFactory jdkProxyFactory=new JdkProxyFactory(userService);
        UserService proxy = (UserService)jdkProxyFactory.getProxy();
        proxy.saveUser();
        proxy.updateUser();
    }

    @Test
    public void testCglibProxy(){
        CglibProxyFactory cglibProxyFactory=new CglibProxyFactory();
        UserServiceImpl proxy=(UserServiceImpl) cglibProxyFactory.getProxyByCglib(UserServiceImpl.class);

        proxy.saveUser();
        proxy.updateUser();
    }
}
