package lk.thogakade.Model;

import lk.thogakade.To.Customer;
import lk.thogakade.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {

    public static ArrayList <Customer> getCustomerList() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select * from Customer");

        ArrayList<Customer> customerArrayList = new ArrayList<>();

        while (resultSet.next()){
            customerArrayList.add(new Customer(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4)));
        }
        return customerArrayList;
    }
    public static Customer getCustomer(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select * from Customer where id = ?", id);

          if(resultSet.next()){
              return new Customer(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4));
          }
        return null;
    }
}
