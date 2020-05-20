package test;

import com.xufangfang.spring.framework.reader.XmlBeanDefinitionReader;
import com.xufangfang.spring.framework.resource.Resource;
import com.xufangfang.spring.framework.resource.support.ClassPathResource;
import com.xufangfang.spring.framework.utils.DocumentUtils;
import org.dom4j.Document;
import org.junit.Before;
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
        // 创建文档对象:找个工具类
        Document document= DocumentUtils.createDocument(inputStream);
        //BeanDefinition阅读器:解析并注册BeanDefinition的功能
        //将BeanDefinition存储到一个地方：BeanDefinitionRegistry
        XmlBeanDefinitionReader beanDefinitionReader=new XmlBeanDefinitionReader();


        parseBeanDefinitions(document.getRootElement());



        UserService userService = (UserService) getBean("userService");
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("username", "方方");
        List<User> users = userService.queryUsers(param);
        System.out.println(users);
    }
}
