package gc.webPro.service;

import gc.webPro.pojo.Cart;
import gc.webPro.pojo.Order;

/**
 * @Author: GC
 * @Description: TODO
 * @Version: 1.0
 */
public interface OrderService {
    boolean saveOrder(Cart cart, Integer user_id);
}
