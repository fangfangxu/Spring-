package dao.impl;

import dao.UserDao;
import po.User;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserDaoImpl implements UserDao {

    private DataSource dataSource;

    //数据源对象
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void init() {
        System.out.println("初始化方法被调用");
    }

    public List<User> queryUserList(Map<String, Object> param) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = dataSource.getConnection();
            String sql = "select * from user where username=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, param.get("username"));
            rs = preparedStatement.executeQuery();
            List<User> users = new ArrayList<User>();
            User result = null;
            Class<?> clazz = User.class;

            while (rs.next()) {
                result = (User) clazz.newInstance();
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int i = 0; i < columnCount; i++) {
                    String columnName = metaData.getColumnName(i + 1);
                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(result, rs.getObject(i + 1));
                }
                users.add(result);
            }
            return users;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

        return null;
    }
}
