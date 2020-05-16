package test;

import dao.impl.UserDaoImpl;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;
import po.User;

import service.impl.UserServiceImpl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestSpringV1 {
    @Test
    public void test() {

        UserServiceImpl userService = new UserServiceImpl();
        UserDaoImpl userDao = new UserDaoImpl();
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/oa?characterEncoding=utf-8&serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        userDao.setDataSource(dataSource);
        userService.setUserDao(userDao);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("username", "方方");
        List<User> users = userService.queryUsers(param);
        System.out.println(users);
    }
}
