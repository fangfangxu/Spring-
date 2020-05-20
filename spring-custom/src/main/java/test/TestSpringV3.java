package test;

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

    @Before
    public void before() {
        // XML解析，将BeanDefinition注册到beanDefinitions集合中
        String location="beans.xml";
        // 获取流对象
        InputStream inputStream = getInputStream(location);
        // 创建文档对象
        Document document=createDocument(inputStream);
        parseBeanDefinitions(document.getRootElement());
    }
    @Test
    public void test(){
        UserService userService = (UserService) getBean("userService");
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("username", "方方");
        List<User> users = userService.queryUsers(param);
        System.out.println(users);
    }
}
