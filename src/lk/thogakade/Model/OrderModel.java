package lk.thogakade.Model;

import lk.thogakade.To.Order;
import lk.thogakade.To.OrderDetails;
import lk.thogakade.Util.CrudUtil;

import java.sql.SQLException;

public class OrderModel {
    public static void addOrder(Order order) throws SQLException, ClassNotFoundException {
        boolean b = CrudUtil.execute("insert into Orders Values (?,?,?)", order.getId(), order.getDate(), order.getCustomerId());
        if(b){
            if(OrderDetailsModel.addOrderDetail(order.getOrderDetailsArrayList())){
                System.out.println("Done...");
            }else{
                //order details not added
            }
        }else{
            //order not added
        }
    }
}
