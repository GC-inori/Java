package gc.webPro.dao;


import gc.webPro.pojo.User;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 *@Author: GC
 *@Description: 自定义用户类接口规范
 *@Version: 1.0
 */
public interface UserDao {

    public boolean executeAdd(Connection conn, User user);

    public boolean validateUsernameUnique(Connection conn,String username);

    public boolean validateUserPasswordUnique(Connection conn,String username, String password);
}
