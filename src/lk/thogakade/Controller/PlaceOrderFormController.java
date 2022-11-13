package lk.thogakade.Controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import lk.thogakade.Model.CustomerModel;
import lk.thogakade.Model.ItemModel;
import lk.thogakade.Model.OrderModel;
import lk.thogakade.To.Customer;
import lk.thogakade.To.Item;
import lk.thogakade.To.Order;
import lk.thogakade.To.OrderDetails;
import lk.thogakade.tblModel.ItemTM;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class PlaceOrderFormController implements Initializable {

    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    public TableColumn colTotal;
    public JFXTextField txtOrderId;
    @FXML
    private JFXTextField txtQty;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private Label lblDescription;

    @FXML
    private JFXComboBox<String> cmbCodeList;

    @FXML
    private TableView<ItemTM> tblOrders;

    @FXML
    private Label lblTotal;

    @FXML
    private JFXComboBox<String> cmbCustomerList;

    @FXML
    private Label txtOrderID;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblCustomerName;

    @FXML
    void btnAddOnAction(ActionEvent event) {
  //      txtQty.setFocusColor(Paint.valueOf("BLUE"));

        if (Pattern.compile("^[0-9]{1,}$").matcher(txtQty.getText()).matches()) {

            if (Integer.parseInt(lblQtyOnHand.getText()) >= Integer.parseInt(txtQty.getText())) {



                ObservableList<ItemTM> itemTMObservableList = tblOrders.getItems();

                if (isAvailable(cmbCodeList.getValue(), itemTMObservableList) == -1) {
                    itemTMObservableList.add(
                            new ItemTM(
                                    cmbCodeList.getValue(),
                                    lblDescription.getText(),
                                    Double.parseDouble(lblUnitPrice.getText()),
                                    Integer.parseInt(txtQty.getText()),
                                    Double.parseDouble(lblUnitPrice.getText()) * Integer.parseInt(txtQty.getText())
                            ));
                } else {
                    ItemTM itemTM = tblOrders.getItems().get(isAvailable(cmbCodeList.getValue(), itemTMObservableList));
                    itemTMObservableList.set(isAvailable(cmbCodeList.getValue(), itemTMObservableList),
                            new ItemTM(
                                    cmbCodeList.getValue(),
                                    lblDescription.getText(),
                                    Double.parseDouble(lblUnitPrice.getText()),
                                    Integer.parseInt(txtQty.getText()) + itemTM.getQty(),
                                    itemTM.getTotal() + (Double.parseDouble(lblUnitPrice.getText()) * Integer.parseInt(txtQty.getText()))
                            )
                    );
                }
                tblOrders.setItems(itemTMObservableList);
                calculateTotal(itemTMObservableList);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "check qty").show();
            }
        } else {
            txtQty.setFocusColor(Paint.valueOf("RED"));
            txtQty.requestFocus();
        }
    }

    private void calculateTotal(ObservableList<ItemTM> itemTMObservableList) {
        double total = 0;
        for (ItemTM ele: itemTMObservableList) {
            total+=ele.getTotal();
        }
        lblTotal.setText(String.valueOf(total));
    }

    private int isAvailable(String value, ObservableList<ItemTM> itemTMObservableList) {
        for (int i = 0; i < itemTMObservableList.size(); i++) {
            if (value.equals(itemTMObservableList.get(i).getCode())){
                return i;
            }
        }
        return -1;
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
       if(txtOrderId.getText() != null){
           ArrayList <OrderDetails> orderDetailsArrayList = new ArrayList<>();
           for (ItemTM ele: tblOrders.getItems()){
               orderDetailsArrayList.add(
                       new OrderDetails(
                               txtOrderId.getText(),
                               ele.getCode(),
                               ele.getQty(),
                               ele.getUnitPrice()
                       )
               );
           }
           Order order = new Order(txtOrderId.getText(), Date.valueOf(lblDate.getText()), cmbCustomerList.getValue(), orderDetailsArrayList);
           OrderModel.addOrder(order);
       }

    }

    @FXML
    void btnRemoveOnAction(ActionEvent event) {
        ItemTM selectedItem = tblOrders.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if (new Alert(Alert.AlertType.CONFIRMATION, "Sure?").showAndWait().get().equals(ButtonType.OK)) {
                ObservableList<ItemTM> items = FXCollections.observableArrayList();
                for (ItemTM ele : tblOrders.getItems()) {
                    if (!selectedItem.equals(ele)) {
                        items.add(ele);
                    }
                }
                tblOrders.setItems(items);
                calculateTotal(items);
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Select an item first!").show();
        }
    }
    @FXML
    void cmbCustomerListOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        lblCustomerName.setText(
                CustomerModel.getCustomer(
                        cmbCustomerList.getValue())
                        .getName());
    }

    @FXML
    void cmbItemListOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        Item item = ItemModel.getItem(cmbCodeList.getValue());

        lblDescription.setText(item.getDescription());
        lblUnitPrice.setText(String.valueOf(item.getUnitPrice()));
        lblQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lblDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("20yy-MM-dd")));

        colCode.setCellValueFactory(
                new PropertyValueFactory<>("code"));

        colDescription.setCellValueFactory(
                new PropertyValueFactory<>("description"));

        colQty.setCellValueFactory(
                new PropertyValueFactory<>("qtyOnHand"));

        colUnitPrice.setCellValueFactory(
                new PropertyValueFactory<>("unitPrice"));

        colTotal.setCellValueFactory(
                new PropertyValueFactory<>("total"));

        try {
            setCustomerList();
            setItemList();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }


    private void setItemList() throws SQLException, ClassNotFoundException {
        ObservableList<String> observableList = FXCollections.observableArrayList();

        for (Item ele : ItemModel.getItemList()) {
            observableList.add(ele.getCode());
        }
        cmbCodeList.setItems(observableList);
    }

    private void setCustomerList() throws SQLException, ClassNotFoundException {
        ObservableList<String> observableArrayList = FXCollections.observableArrayList();

        for (Customer customer: CustomerModel.getCustomerList()) {
            observableArrayList.add(customer.getId());
        }
        cmbCustomerList.setItems(observableArrayList);
    }
}
