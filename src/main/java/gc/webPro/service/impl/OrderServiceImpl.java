package gc.webPro.service.impl;

import gc.webPro.dao.impl.OrderDaoImpl;
import gc.webPro.pojo.Cart;
import gc.webPro.pojo.Order;
import gc.webPro.service.OrderService;
import gc.webPro.utils.JDBCUtil;

import java.sql.Connection;

/**
 * @Author: GC
 * @Description: TODO
 * @Version: 1.0
 */
public class OrderServiceImpl implements OrderService {

    private static final OrderServiceImpl OrderServiceImpl = new OrderServiceImpl();

    private static final OrderDaoImpl orderDaoImpl = OrderDaoImpl.getOrderDaoImplInstance();

    private OrderServiceImpl() {

    }
    public static OrderServiceImpl getOrderServiceImplInstance() {
        return OrderServiceImpl;
    }
    @Override
    public boolean saveOrder(Cart cart, Integer user_id) {
        Connection conn = null;
        boolean isSuccess = false;
        try {
            conn = JDBCUtil.getMysqlConnect();
            conn.setAutoCommit(false);

            //isSuccess = orderDaoImpl.executeSave(conn,or);

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            JDBCUtil.closeResources(conn, null, null);
        }
        return isSuccess;
    }
}
