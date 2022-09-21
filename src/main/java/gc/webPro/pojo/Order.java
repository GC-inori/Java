package gc.webPro.pojo;

import java.util.Date;

/**
 * @Author: GC
 * @Description: TODO
 * @Version: 1.0
 */
enum Status{
    NotSHIPPED,SHIPPED,RECEIVED
}
public class Order {
    private String orderId;
    private double price;
    private Status status;
    private int userId;
    private Date createTime;

    public Order() {

    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
