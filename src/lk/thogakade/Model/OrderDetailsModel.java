package lk.thogakade.Model;

import lk.thogakade.To.OrderDetails;
import lk.thogakade.Util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsModel {
    public static boolean addOrderDetail(ArrayList<OrderDetails> orderDetailsArrayList) throws SQLException, ClassNotFoundException {
        for (OrderDetails ele: orderDetailsArrayList){
            if(!(boolean)CrudUtil.execute("insert into OrderDetail values (?,?,?,?)", ele.getOrderId(), ele.getItemCode(), ele.getOrderQty(), ele.getUnitPrice())){
                return false;
            }
        }
        return true;
    }
}
