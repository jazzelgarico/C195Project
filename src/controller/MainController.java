package controller;

import dbconnection.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import model.CustomerList;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button btnDeleteAppointment;

    @FXML
    private Button btnDeleteCustomer;

    @FXML
    private Button btnEditAppointment;

    @FXML
    private Button btnEditCustomer;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSaveAppointment;

    @FXML
    private ComboBox<?> comboContact;

    @FXML
    private ComboBox<?> comboCountry;

    @FXML
    private ComboBox<?> comboEndTime;

    @FXML
    private ComboBox<?> comboFirstLevelDiv;

    @FXML
    private ComboBox<?> comboStartTime;

    @FXML
    private ChoiceBox<?> comboType;

    @FXML
    private RadioButton radioBtnMonth;

    @FXML
    private RadioButton radioButtonWeek;

    @FXML
    private TableView<?> tblViewAppointment;

    @FXML
    private TableView<Customer> tblViewCustomer;

    @FXML
    private TableColumn<Customer, String> colAddress;

    @FXML
    private TableColumn<Customer, String> colCustomerName;

    @FXML
    private TableColumn<Customer, Integer> colFirstLevelDivision;

    @FXML
    private TableColumn<Customer, String> colPhoneNumber;

    @FXML
    private TableColumn<Customer, String> colPostalCode;


    @FXML
    private TextField txtFldAddress;

    @FXML
    private TextField txtFldAppID;

    @FXML
    private TextField txtFldCustomerID;

    @FXML
    private TextField txtFldDate;

    @FXML
    private TextField txtFldDesc;

    @FXML
    private TextField txtFldLocation;

    @FXML
    private TextField txtFldName;

    @FXML
    private TextField txtFldPhoneNumber;

    @FXML
    private TextField txtFldPostalCode;

    @FXML
    private TextField txtFldTitle;

    @FXML
    private TextField txtFldUserID;


    private void updateCustomerTable() {
        tblViewCustomer.setItems(CustomerList.getCustomerList());
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        colFirstLevelDivision.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
    }

    public static void addDBCustomers() {
        try {
            String query = "SELECT Customer_ID,Customer_Name,Address,Postal_Code,Phone,Division_ID FROM customers;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionID = rs.getInt("Division_ID");

                Customer customer = new Customer(customerID,customerName,address,postalCode,phone,divisionID);
                CustomerList.addCustomer(customer);
            }

        } catch (SQLException e) { e.printStackTrace(); }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialize?");
        addDBCustomers();
        updateCustomerTable();
        System.out.println("Maybe?");

    }
}
