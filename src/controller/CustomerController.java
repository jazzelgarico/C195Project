package controller;

import dbaccess.DBAccess;
import dbaccess.DBCustomer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    // Customer Tab

    // Customer Tab Buttons
    @FXML
    private Button btnDeleteCustomer;

    @FXML
    private Button btnEditCustomer;

    @FXML
    private Button btnSaveCustomer;

    @FXML
    private Button btnClearCustomer;

    // Table View Customer
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

    // Customer Form
    @FXML
    private TextField txtFldCustomerIDCustomer;

    @FXML
    private TextField txtFldName;

    @FXML
    private TextField txtFldAddress;

    @FXML
    private TextField txtFldPhoneNumber;

    @FXML
    private TextField txtFldPostalCode;

    @FXML
    private ComboBox<Country> comboCountry;

    @FXML
    private ComboBox<FirstLevelDivision> comboFirstLevelDiv;

    /**
     * Updates Customer Table View from Customers in the database.
     */
    public void updateCustomerTable() {
        tblViewCustomer.setItems(DBCustomer.addAll());
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        colFirstLevelDivision.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
    }

    /**
     * Deletes selected customer in Customer TableView and appointments associated with the selected Customer
     * from the database. After Customer is deleted, Customer Table View and Appointment TableView is refreshed.
     * Informs user that deletion is successful with an alert that specifies customerID of deleted Customer and the
     * number of appointments deleted.
     *
     * @param event the event which triggers customer to be deleted
     */
    @FXML
    void onActionCustomerDelete(ActionEvent event) {
        int customerId = tblViewCustomer.getSelectionModel().getSelectedItem().getCustomerId();
        int appointmentsDeleted = DBCustomer.delete(customerId);
        // Update Table Views
        updateCustomerTable();
        // Alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Deletion successful.");
        if (appointmentsDeleted == 1) {
            alert.setContentText("Customer with ID number " + customerId + " and " + appointmentsDeleted + " associated "
                    + "appointment have been deleted.");
        } else {
            alert.setContentText("Customer with ID number " + customerId + " and " + appointmentsDeleted + " associated "
                    + "appointments have been deleted.");
        }
        alert.showAndWait();
    }

    /**
     * Fills in Customer form with the information of the selected Customer in the Customer Table View.
     *
     * @param event the event which triggers customer to be edited
     */
    @FXML
    void onActionEditCustomer(ActionEvent event) {
        System.out.println(event.getEventType().toString());
        Customer customer = tblViewCustomer.getSelectionModel().getSelectedItem();
        int id = customer.getCustomerId();
        String name = customer.getCustomerName();
        String address = customer.getAddress();
        String phone = customer.getPhoneNumber();
        String postalCode = customer.getPostalCode();
        int divisionId = customer.getDivisionId();
        FirstLevelDivision division = DBAccess.getFirstLevelDivisionByID(divisionId);
        Country country = DBAccess.getCountryByID(division.getCountryId());
        // Fill text fields and combo boxes for selected Customer
        txtFldCustomerIDCustomer.setText(Integer.toString(id));
        txtFldName.setText(name);
        txtFldAddress.setText(address);
        txtFldPhoneNumber.setText(phone);
        txtFldPostalCode.setText(postalCode);
        comboFirstLevelDiv.setValue(division);
        comboCountry.setValue(country);
    }

    /**
     * Edits or creates a new customer.
     * <p>
     * If the customerId text field is filled in, updates the customer in the database
     * with the given customerId from the contents of the Customer Form text fields. If the customerID text filled is
     * empty, creates a new customer in the database. After customer is updated or added, updates the Customer Table
     * View and clears the Customer Form.
     *
     *
     * @param event the event object which triggers customer to be saved
     */
    @FXML
    void onActionSaveCustomer(ActionEvent event) {
        if (comboFirstLevelDiv.getValue().equals(null)) {
            // Alert if comboFirstLevelDiv does not have a value.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("First-Level Division must be specified");
            alert.showAndWait();
        }
        else {
            //Get Form Data
            String customerName = txtFldName.getText();
            String address = txtFldAddress.getText();
            String postalCode = txtFldPostalCode.getText();
            String phoneNumber = txtFldPhoneNumber.getText();
            int divisionID = comboFirstLevelDiv.getSelectionModel().getSelectedItem().getDivisionId();

            if (txtFldCustomerIDCustomer.getText().isEmpty()) {
                //Adds new customer
                Customer customer = new Customer(customerName, address, postalCode, phoneNumber, divisionID);
                DBCustomer.add(customer);
            } else {
                //Edits existing customer
                int customerId = Integer.parseInt(txtFldCustomerIDCustomer.getText());
                Customer customer = new Customer(customerId, customerName, address, postalCode, phoneNumber, divisionID);
                DBCustomer.edit(customer);
            }
            updateCustomerTable();
            clearCustomerForm();
        }
    }

    /**
     * Clears the Customer Form fields. The customer form fields include txtFldCustomerIDCustomer, txtFldName,
     * txtFldAddress, txtFldPostalCode, txtFldPhoneNumber,comboFirstLevelDiv, and comboCountry.
     */
    public void clearCustomerForm() {
        txtFldCustomerIDCustomer.clear();
        txtFldName.clear();
        txtFldAddress.clear();
        txtFldPostalCode.clear();
        txtFldPhoneNumber.clear();
        comboFirstLevelDiv.setValue(null);
        comboCountry.setValue(null);
    }

    /**
     * Gets the selected country from the combo box and fills in the first-divisions combo box with the first-level
     * divisions associated with the selected country.
     * <p>
     * onActionSaveCustomer triggers error during comboCountry.setValue(null) so must check if selection is empty
     *
     * @param event the event object which triggers selection in country combo box
     */
    @FXML
    void onActionComboCountry(ActionEvent event) {
        if (!comboCountry.getSelectionModel().isEmpty()) {
            Country country = comboCountry.getSelectionModel().getSelectedItem();
            comboFirstLevelDiv.setItems(DBAccess.getFirstLevelDivision(country.getCountryId()));
        }
    }

    @FXML
    void onActionClearCustomer(ActionEvent event) {
        clearCustomerForm();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboCountry.setItems(DBAccess.getCountries());
        updateCustomerTable();
    }
}
