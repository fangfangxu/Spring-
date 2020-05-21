package test;

import com.xufangfang.spring.framework.reader.XmlBeanDefinitionReader;
import com.xufangfang.spring.framework.factory.support.DefaultListableBeanFactory;
import com.xufangfang.spring.framework.resource.Resource;
import com.xufangfang.spring.framework.resource.support.ClassPathResource;
import org.junit.Test;
import po.User;
import service.UserService;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 以面向对象的思维去实现IOC的相应功能
 * IOC思想：调用者-只需要负责bean的使用，不需要负责bean的创建
 */
public class TestSpringV3 {


    @Test
    public void test(){
        // XML解析，将BeanDefinition注册到beanDefinitions集合中
        String location="beans.xml";
        // 获取流对象
        Resource resource=new ClassPathResource(location);
        InputStream inputStream = resource.getResource();
        //BeanDefinition阅读器:解析并注册BeanDefinition的功能
        //将BeanDefinition存储到一个地方：BeanDefinitionRegistry
        //定义Spring容器，它本身也是一个BeanDefinitionRegistry
        DefaultListableBeanFactory beanFactory=new DefaultListableBeanFactory();
        /**
         * 最小认知
         */
        XmlBeanDefinitionReader beanDefinitionReader=new XmlBeanDefinitionReader(beanFactory);

        beanDefinitionReader.registerBeanDefinitions(inputStream);
        UserService userService = (UserService) beanFactory.getBean("userService");
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("username", "方方");
        List<User> users = userService.queryUsers(param);
        System.out.println(users);
    }
}
