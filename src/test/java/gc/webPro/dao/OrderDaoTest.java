package gc.webPro.dao;

import gc.webPro.dao.impl.OrderDaoImpl;
import gc.webPro.pojo.Order;
import gc.webPro.utils.JDBCUtil;
import junit.framework.TestCase;

public class OrderDaoTest extends TestCase {

    public void testExecuteSave() {
        OrderDaoImpl orderDao = OrderDaoImpl. getOrderDaoImplInstance();

        orderDao.executeSave(JDBCUtil.getMysqlConnect(),new Order());
    }

}