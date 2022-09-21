package gc.webPro.service.impl;

import gc.webPro.dao.impl.OrderItemDaoImpl;
import gc.webPro.pojo.OrderItem;
import gc.webPro.service.OrderItemService;
import gc.webPro.utils.JDBCUtil;

import java.sql.Connection;

/**
 * @Author: GC
 * @Description: TODO
 * @Version: 1.0
 */
public class OrderItemServiceImpl implements OrderItemService {

    private static final OrderItemServiceImpl OrderItemServiceImpl = new OrderItemServiceImpl();

    private static final OrderItemDaoImpl orderItemDaoImpl = OrderItemDaoImpl.getOrderItemDaoImplInstance();

    private OrderItemServiceImpl() {

    }
    public static OrderItemServiceImpl getOrderItemServiceImplInstance() {
        return OrderItemServiceImpl;
    }


    public boolean saveOrderItem(OrderItem orderItem) {
        Connection conn = null;
        boolean isSuccess = false;
        try {
            conn = JDBCUtil.getMysqlConnect();
            conn.setAutoCommit(false);

            isSuccess = orderItemDaoImpl.saveOrderItem(conn,orderItem);

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            JDBCUtil.closeResources(conn, null, null);
        }
        return isSuccess;
    }
}
