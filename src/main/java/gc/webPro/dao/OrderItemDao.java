package gc.webPro.dao;

import gc.webPro.pojo.OrderItem;

import java.sql.Connection;

/**
 * @Author: GC
 * @Description: TODO
 * @Version: 1.0
 */
public interface OrderItemDao {
    boolean saveOrderItem(Connection conn,OrderItem orderItem);
}
