package gc.webPro.dao.impl;

import gc.webPro.dao.UserDao;
import gc.webPro.dao.basedao.BaseDao;
import gc.webPro.pojo.User;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class UserDaoImpl extends BaseDao<User> implements UserDao {

    private static final UserDaoImpl userImpl = new UserDaoImpl();

    private UserDaoImpl(){

    }

    public static UserDaoImpl getUserDaoImplInstance(){
        return userImpl;
    }

    public boolean executeAdd(Connection conn, User user) {
        String sql = "insert into t_user(username,password,email) value(?,?,?)";
        return executeCommonQuery(conn, sql, user.getUsername(), user.getPassword(), user.getEmail());
    }


    public boolean executeDelete(Connection conn, Integer id) {
        String sql = "delete from t_user where id = ?";
        return executeCommonQuery(conn, sql, id);
    }


    public boolean executeUpdate(Connection conn, User user) {
        String sql = "update t_user set username = ?,password = ?,email = ? where id = ?";
        return executeCommonQuery(conn, sql, user.getUsername(),user.getPassword(),user.getEmail(),user.getId());
    }


    public Map<Integer, User> executeQueryMap(Connection conn, Integer id) {
        String sql = "select * from t_user where id = ?";
        return executeCustomQueryMap(conn,sql,id);
    }


    public  List<User> executeQueryList(Connection conn, Integer id) {
        String sql = "select * from t_user where id = ?";
        return executeCustomQueryList(conn,sql,id);
    }

    public Long getCount(Connection conn) {
        String sql = "select count(*) from t_user";

        return getValue(conn,sql);
    }

    @Override
    public boolean validateUsernameUnique(Connection conn,String username) {
        String sql = "select 1 from t_user where username = ? limit 1";
        return getValue(conn, sql, username) == null;
    }

    @Override
    public boolean validateUserPasswordUnique(Connection conn,String username, String password) {
        String sql = "select 1 from t_user where username = ? and password = ? limit 1";
        return getValue(conn, sql, username,password) != null;
    }
}
