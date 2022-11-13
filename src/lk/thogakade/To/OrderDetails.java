package lk.thogakade.To;

import java.sql.Date;

public class OrderDetails {
    String orderId;
    String itemCode;
    int orderQty;
    double unitPrice;

    public String getOrderId() {
        return orderId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public OrderDetails(String orderId, String itemCode, int orderQty, double unitPrice) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.orderQty = orderQty;
        this.unitPrice = unitPrice;
    }
}
