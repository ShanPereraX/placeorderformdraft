package lk.thogakade.Model;

import lk.thogakade.To.Item;
import lk.thogakade.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemModel {
    public static ArrayList<Item> getItemList() throws SQLException, ClassNotFoundException {
        ArrayList<Item> itemArrayList = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("select * from Item");

        while(resultSet.next()){
            itemArrayList.add(new Item(resultSet.getString(1), resultSet.getString(2),Double.valueOf(resultSet.getString(3)),Integer.valueOf(resultSet.getString(4)) ));
        }
        return itemArrayList;
    }

    public static Item getItem(String code) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select * from Item where code = ?",code);
        while(resultSet.next()){
            return new Item(resultSet.getString(1), resultSet.getString(2),Double.valueOf(resultSet.getString(3)),Integer.valueOf(resultSet.getString(4)) );
        }
        return null;
    }
}
