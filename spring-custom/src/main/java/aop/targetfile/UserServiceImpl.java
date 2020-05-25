package aop.targetfile;

public class UserServiceImpl implements UserService{
    public UserServiceImpl() {
    }

    public int saveUser() {
        System.out.println("UserServiceImpl----saveUser");
        return 1;
    }

    public int updateUser() {
        System.out.println("UserServiceImpl----updateUser");
        return 2;
    }
}
