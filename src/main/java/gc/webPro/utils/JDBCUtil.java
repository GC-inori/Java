package gc.webPro.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class JDBCUtil {
    private static DataSource source = null;//连接池只需要一个
    static {
        try {
            Properties pro = new Properties();
            InputStream is = getResourceAsStream();
            pro.load(is);
            source = DruidDataSourceFactory.createDataSource(pro);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DruidDataSource getSource(){
        return (DruidDataSource)source;
    }
    private static InputStream getResourceAsStream() {
       return JDBCUtil.class.getClassLoader().getResourceAsStream("druid.properties");
        //return JDBCUtil.class.getResourceAsStream("druid.properties");//会报空指针异常找不到配置文件
    }

    public static Connection getMysqlConnect() {
        Connection conn = null;
        try {
            conn = source.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

//    public static Connection getMysqlConnect() {
//        Properties properties = new Properties();
//
//        InputStream is = null;
//        try {
//            is = getResourceAsStream();
//            properties.load(is);
//
//            String driver = properties.getProperty("driver");
//            String url = properties.getProperty("url");
//            String user = properties.getProperty("user");
//            String password = properties.getProperty("password");
//
//            Class.forName(driver);//加载驱动类
//
//            return DriverManager.getConnection(url, user, password);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (is != null) {
//                try {
//                    is.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return null;
//    }

//    public static boolean executeCommonQuery(Connection conn, String sql, Object... args) {
//        PreparedStatement ps = null;
//        try {
//            conn = getMysqlConnect();
//
//            if (conn != null) {
//                conn.setAutoCommit(false);//防止自动提交
//                ps = conn.prepareStatement(sql);
//
//                for (int i = 0; i < args.length; i++)
//                    ps.setObject(i + 1, args[i]);//利用多态性 自动装箱 用Obj接受不同类型数据
//
//                conn.commit();//提交数据
//
//                return ps.executeUpdate() > 0;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            try {
//                conn.rollback();//出现异常回滚
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        } finally {
//            if(ps!=null){
//                try {
//                    ps.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return false;
//    }
//
//    public static <T> Map<Integer, T> executeCustomQueryMap(Class<T> tClass, String sql, Object... args) {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet resultSet = null;
//        Map<Integer, T> map = null;
//        try {
//
//            conn = getMysqlConnect();
//
//            if (conn == null)
//                return null;
//
//            ps = conn.prepareStatement(sql);
//
//            map = new HashMap<>();
//
//            resultSet = dealBaseData(ps, args);
//
//            ResultSetMetaData baseData = resultSet.getMetaData();
//
//            int columnCount = baseData.getColumnCount();//获取查询中列的数量
//
//            int index = 1;
//
//            while (resultSet.next()) {
//                T temp = dealInstance(tClass, resultSet, baseData);
//                map.put(index++, temp);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            JDBCUtil.closeResources(conn, ps, resultSet);
//        }
//        return map;
//    }
//
//    public static <T> List<T> executeCustomQueryList(Class<T> tClass, String sql, Object... args) {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet resultSet = null;
//        List<T> list = null;
//        try {
//
//            conn = getMysqlConnect();
//
//            if (conn == null)
//                return null;
//
//            ps = conn.prepareStatement(sql);
//
//            list = new ArrayList<>();
//
//            resultSet = dealBaseData(ps, args);
//
//            ResultSetMetaData baseData = resultSet.getMetaData();
//
//            while (resultSet.next()) {
//                T temp = dealInstance(tClass, resultSet, baseData);
//                list.add(temp);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            JDBCUtil.closeResources(conn, ps, resultSet);
//        }
//        return list;
//    }
//
//    public static ResultSet dealBaseData(PreparedStatement ps, Object[] args) throws SQLException {
//        for (int i = 0; i < args.length; i++)
//            ps.setObject(i + 1, args[i]);//利用多态性
//
//        return ps.executeQuery();
//    }
//
//    public static <T> T dealInstance(Class<T> tClass, ResultSet rs, ResultSetMetaData baseData) throws Exception {
//
//        int columnCount = baseData.getColumnCount();//获取查询中列的数量
//
//        T temp = tClass.getDeclaredConstructor().newInstance();
//
//        for (int i = 0; i < columnCount; i++) {//给查询到的列名依次往对象里赋值
//
//            //String columnName = baseData.getColumnName(i + 1);//获取列名 不是别名 不推荐使用
//
//            String columnName = baseData.getColumnLabel(i + 1);//获取别名 没别名就默认列名 推荐使用
//
//            //获取为该列名的方法名 似乎不好用 没办法知道这个方法参数的数据类型
//            //Method method = aClass.getMethod("set" + catalogName,);
//
//            Object value = rs.getObject(i + 1);//获取列名的值 多态性拿Object接受
//            //自动装箱 把基本数据类型包装到他的包装类 作为Object的引用
//
//            Field field = tClass.getDeclaredField(columnName);//获取与查询列名(别名)同名的属性名
//            field.setAccessible(true);//设置访问权限
//            field.set(temp, value);//给对象的属性赋值 自动拆箱 把Object对应包装类的引用 自动转成 基本数据类型
//        }
//        return temp;
//    }

    public static void closeResources(Connection connection, Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null)
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

    }

    public static void closeResources(Connection connection, Statement statement, ResultSet resultSet) {

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        closeResources(connection, statement);

    }

}
