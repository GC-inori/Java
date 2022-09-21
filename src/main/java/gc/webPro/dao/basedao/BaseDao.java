package gc.webPro.dao.basedao;

import gc.webPro.utils.JDBCUtil;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseDao<T> {

    private Class<T> tClass = null;

    {//代码块 子类继承父类后 运行时会加载父类结构 就会执行次代码块 this指的是当前类的对象 一般是子类对象 由于子类已经指明了类型 所以可以直接给父类传此类型
        Type Superclass = this.getClass().getGenericSuperclass();//子类获取父类泛型
        //此处类其实是ParameterizedType的实现类ParameterizedTypeImpl
        //ParameterizedType接口里的方法都是返回Type类型

        tClass = (Class<T>) ((ParameterizedType) Superclass).getActualTypeArguments()[0];
        //由于ParameterizedTypeImpl和Class并没有关系 所以getActualTypeArguments()[0]还是返回Type类型使得可以转回Class

        //ParameterizedTypeImpl实现了ParameterizedType接口  ParameterizedType继承Type接口 所以Type自然可以强转ParameterizedType
        //也可以调用ParameterizedTypeImpl里的方法
        //但是为什么实现类是这个ParameterizedTypeImpl尚不清楚
    }

    public boolean executeCommonQuery(Connection conn, String sql, Object... args) {//用Object去接受各种类型参数

        PreparedStatement ps = null;
        try {
            if (conn != null) {

                //conn.setAutoCommit(false);//防止自动提交
                ps = conn.prepareStatement(sql);

                for (int i = 0; i < args.length; i++)
                    ps.setObject(i + 1, args[i]);//会自动识别类型 不用一个个判断了 具体看源码

                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResources(null, ps, null);//这边没有关闭连接 只是关闭了PrepareStatement
        }
        return false;

    }

    public Map<Integer, T> executeCustomQueryMap(Connection conn, String sql, Object... args) {

        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Map<Integer, T> map = null;
        try {

            //conn.setAutoCommit(false);//防止自动提交

            ps = conn.prepareStatement(sql);

            map = new HashMap<>();

            resultSet = dealBaseData(ps, args);

            ResultSetMetaData baseData = resultSet.getMetaData();

            int columnCount = baseData.getColumnCount();//获取查询中列的数量

            int index = 1;

            while (resultSet.next()) {
                T temp = dealInstance(tClass, resultSet, baseData);
                map.put(index++, temp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResources(null, ps, resultSet);
        }

        return map;
    }

    public List<T> executeCustomQueryList(Connection conn, String sql, Object... args) {
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<T> list = null;
        try {

            //conn.setAutoCommit(false);//防止自动提交

            ps = conn.prepareStatement(sql);

            list = new ArrayList<>();

            resultSet = dealBaseData(ps, args);

            ResultSetMetaData baseData = resultSet.getMetaData();

            while (resultSet.next()) {
                T temp = dealInstance(tClass, resultSet, baseData);
                list.add(temp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResources(null, ps, resultSet);
        }
        return list;
    }

    public T getSingleInstance(Connection conn, String sql, Object... args) {
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        T instance = null;
        try {

            //conn.setAutoCommit(false);//防止自动提交

            ps = conn.prepareStatement(sql);

            resultSet = dealBaseData(ps, args);

            ResultSetMetaData baseData = resultSet.getMetaData();

            if (resultSet.next()) {
                instance = dealInstance(tClass, resultSet, baseData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResources(null, ps, resultSet);
        }
        return instance;
    }

    private ResultSet dealBaseData(PreparedStatement ps, Object... args) throws SQLException {
        for (int i = 0; i < args.length && args[i] != null; i++)
            ps.setObject(i + 1, args[i]);
        return ps.executeQuery();
    }

    private T dealInstance(Class<T> tClass, ResultSet rs, ResultSetMetaData baseData) throws Exception {

        int columnCount = baseData.getColumnCount();//获取查询中列的数量

        T temp = tClass.getDeclaredConstructor().newInstance();

        for (int i = 0; i < columnCount; i++) {//给查询到的列名依次往对象里赋值

            //String columnName = baseData.getColumnName(i + 1);//获取列名 不是别名 不推荐使用

            String columnName = baseData.getColumnLabel(i + 1);//获取别名 没别名就默认列名 推荐使用

            //获取为该列名的方法名 似乎不好用 没办法知道这个方法参数的数据类型
            //Method method = aClass.getMethod("set" + catalogName,);

            Object value = rs.getObject(i + 1);//获取列名的值 拿Object接受

            Field field = tClass.getDeclaredField(columnName);//获取与查询列名(别名)同名的属性名
            field.setAccessible(true);//设置访问权限
            field.set(temp, value);//给对象的属性赋值 参数在反射时会自动转为原始类型(自动强转)
        }
        return temp;
    }

    public <E> E getValue(Connection conn, String sql, Object... args) {
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        E eVal = null;
        try {
            ps = conn.prepareStatement(sql);
            resultSet = dealBaseData(ps, args);

            if (resultSet.next())
                eVal = (E) resultSet.getObject(1);//泛型方法类型是在调用此方法时候确定的 由于获取的是Object类型 可以任意强转到他的子类型

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResources(null, ps, resultSet);
        }

        return eVal;
    }
}
