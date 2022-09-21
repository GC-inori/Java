package gc.webPro.dao;

import gc.webPro.pojo.Order;

import java.sql.Connection;


/**
 * @Author: GC
 * @Description: TODO
 * @Version: 1.0
 */
public interface OrderDao {

   boolean executeSave(Connection conn, Order order);

}
