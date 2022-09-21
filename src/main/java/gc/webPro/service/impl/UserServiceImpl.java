package gc.webPro.service.impl;

import gc.webPro.dao.UserDao;
import gc.webPro.dao.impl.UserDaoImpl;
import gc.webPro.pojo.User;
import gc.webPro.service.UserService;
import gc.webPro.utils.JDBCUtil;

import java.sql.Connection;

/**
 * @Author: GC
 * @Description: 用户登录注册业务实现类
 * @Version: 1.0
 */
public class UserServiceImpl implements UserService {

    private static final UserDao userDaoImpl = UserDaoImpl.getUserDaoImplInstance();

    private static final UserService userServiceImpl = new UserServiceImpl();

    private UserServiceImpl(){

    }

    public static UserService getUserServiceImpInstance(){
        return userServiceImpl;
    }

    @Override
    public boolean isUsernameValid(String username) {
        Connection conn= null;
        boolean isValid = false;
        try {
            conn = JDBCUtil.getMysqlConnect();
            conn.setAutoCommit(false);

            isValid = userDaoImpl.validateUsernameUnique(conn,username);

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResources(conn,null,null);
        }
        return isValid;
    }

    @Override
    public boolean registerUser(User user) {
        Connection conn= null;
        boolean isSuccess = false;

        try {
            conn = JDBCUtil.getMysqlConnect();
            conn.setAutoCommit(false);
            //if(userDaoImpl.validateUsernameUnique(conn,user.getUsername()))
            isSuccess = userDaoImpl.executeAdd(conn,user);

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResources(conn,null,null);
        }
        return isSuccess;
    }

    @Override
    public boolean loginUser(String username, String password) {
        Connection conn= null;
        boolean isSuccess = false;
        try {
            conn = JDBCUtil.getMysqlConnect();
            conn.setAutoCommit(false);

            isSuccess = userDaoImpl.validateUserPasswordUnique(conn,username,password);

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResources(conn,null,null);
        }
        return isSuccess;
    }
}
