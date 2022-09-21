package gc.webPro.pojo;

/**
 * @Author: GC
 * @Description: TODO
 * @Version: 1.0
 */
public class CartItem {
    private int id;
    private String name;
    private int count = 1;
    private double price;
    private double totalPrice;


    public CartItem() {
    }

    public CartItem(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        setTotalPrice(count * price);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        setTotalPrice(count * this.price);
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    private void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
