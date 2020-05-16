package service.impl;

import dao.UserDao;
import po.User;
import service.UserService;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao userDao;

    //依赖注入UserDao
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> queryUsers(Map<String, Object> param) {
        return userDao.queryUserList(param);
    }
}
