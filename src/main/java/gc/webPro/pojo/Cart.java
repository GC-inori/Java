package gc.webPro.pojo;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: GC
 * @Description: TODO
 * @Version: 1.0
 */
public class Cart {
    private int totalCount;
    private double totalPrice;
    private final Map<Integer, CartItem> map;

    public Cart() {
        this.map = new LinkedHashMap<>();

    }

    public boolean addItem(CartItem item) {
        Integer id = item.getId();

        if (map.get(id) == null)
            map.put(id, item);
        else {
            CartItem cartItem = map.get(id);
            cartItem.setCount(cartItem.getCount() + 1);
        }
        totalCount++;
        totalPrice += item.getTotalPrice();
        return true;
    }

    public boolean deleteItem(Integer id) {
        CartItem cartItem = map.get(id);
        if (cartItem == null)
            return false;

        totalCount -= cartItem.getCount();
        totalPrice -= cartItem.getTotalPrice();

        map.remove(id);

        return true;
    }

    public boolean updateCount(Integer id, Integer count) {
        if (map.get(id) == null || count <= 0)
            return false;
        CartItem cartItem = map.get(id);

        int different = count - cartItem.getCount();
        totalCount += different;
        totalPrice += different * cartItem.getPrice();
        cartItem.setCount(count);

        return true;
    }

    public void clearCart() {
        map.clear();
        totalPrice = 0.0;
        totalCount = 0;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Map<Integer, CartItem> getMap() {
        return map;
    }
}
