package dao;

import po.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    List<User> queryUserList(Map<String, Object> param);
}
