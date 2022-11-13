package lk.thogakade.To;

import java.sql.Date;
import java.util.ArrayList;

public class Order {
    String id;
    Date date;
    String customerId;
    ArrayList<OrderDetails> orderDetailsArrayList;

    public String getCustomerId() {
        return customerId;
    }

    public ArrayList<OrderDetails> getOrderDetailsArrayList() {
        return orderDetailsArrayList;
    }

    public Order(String id, Date date, String customerId, ArrayList<OrderDetails> orderDetailsArrayList) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
        this.orderDetailsArrayList = orderDetailsArrayList;
    }

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Order(String id, Date date) {
        this.id = id;
        this.date = date;
    }
}
