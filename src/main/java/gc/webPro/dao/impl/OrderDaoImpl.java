package gc.webPro.dao.impl;

import gc.webPro.dao.OrderDao;
import gc.webPro.dao.basedao.BaseDao;
import gc.webPro.pojo.Order;

import java.sql.Connection;

/**
 * @Author: GC
 * @Description: TODO
 * @Version: 1.0
 */
public class OrderDaoImpl extends BaseDao<Order> implements OrderDao {

    private static final OrderDaoImpl orderImpl = new OrderDaoImpl() ;

    private OrderDaoImpl() {

    }

    public static OrderDaoImpl getOrderDaoImplInstance() {
        return orderImpl;
    }

    @Override
    public boolean executeSave(Connection conn, Order order) {
        String sql = "insert into t_order(order_id,price,status,user_id,create_time) value(?,?,?,?,?)";
        return executeCommonQuery(conn,sql,order.getOrderId(),order.getPrice(),order.getStatus(),order.getUserId(),order.getCreateTime());
    }
}
