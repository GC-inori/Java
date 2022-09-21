package gc.webPro.dao.impl;

import gc.webPro.dao.OrderItemDao;
import gc.webPro.dao.basedao.BaseDao;
import gc.webPro.pojo.OrderItem;

import java.sql.Connection;

/**
 * @Author: GC
 * @Description: TODO
 * @Version: 1.0
 */
public class OrderItemDaoImpl extends BaseDao<OrderItem> implements OrderItemDao {

    private static final OrderItemDaoImpl orderItemImpl = new OrderItemDaoImpl() ;

    private OrderItemDaoImpl() {

    }

    public static OrderItemDaoImpl getOrderItemDaoImplInstance() {
        return orderItemImpl;
    }

    @Override
    public boolean saveOrderItem(Connection conn, OrderItem orderItem) {
        String sql = "insert into t_orderItem(id,name,count,price,totalPrice,order_id) value(?,?,?,?,?,?)";
        return executeCommonQuery(conn, sql, orderItem.getId(), orderItem.getName(), orderItem.getCount(),
                orderItem.getPrice(), orderItem.getTotalPrice(), orderItem.getOrder_id());
    }
}
