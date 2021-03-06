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
import model.CustomerList;
import model.FirstLevelDivision;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for customer-view
 */
public class CustomerController implements Initializable {

    // Customer Tab Buttons
    @FXML private Button btnDeleteCustomer;
    @FXML private Button btnEditCustomer;
    @FXML private Button btnSaveCustomer;
    @FXML private Button btnClearCustomer;

    // Table View Customer
    @FXML private TableView<Customer> tblViewCustomer;
    @FXML private TableColumn<Customer, String> colAddress;
    @FXML private TableColumn<Customer, String> colCustomerName;
    @FXML private TableColumn<Customer, Integer> colFirstLevelDivision;
    @FXML private TableColumn<Customer, String> colPhoneNumber;
    @FXML private TableColumn<Customer, String> colPostalCode;

    // Customer Form
    @FXML private TextField txtFldCustomerIDCustomer;
    @FXML private TextField txtFldName;
    @FXML private TextField txtFldAddress;
    @FXML private TextField txtFldPhoneNumber;
    @FXML private TextField txtFldPostalCode;
    @FXML private ComboBox<Country> comboCountry;
    @FXML private ComboBox<FirstLevelDivision> comboFirstLevelDiv;

    /**
     * Updates Customer Table View from CustomerList.
     */
    public void updateCustomerTable() {
        tblViewCustomer.setItems(CustomerList.get());
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        colFirstLevelDivision.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
    }

    /**
     * Deletes selected customer in Customer TableView and appointments associated with the selected Customer
     * from the database and CustomerList. After Customer is deleted, Customer Table View is refreshed. Informs user
     * that deletion is successful with an alert that specifies customerID of deleted Customer and the
     * number of appointments deleted.
     *
     * @param event the event which triggers customer to be deleted
     */
    @FXML
    void onActionCustomerDelete(ActionEvent event) {
        Customer customer = tblViewCustomer.getSelectionModel().getSelectedItem();
        DBCustomer.delete(customer);
        updateCustomerTable();
    }

    /**
     * Fills in Customer form with the information of the selected Customer in the Customer Table View.
     *
     * @param event the event which triggers customer to be edited
     */
    @FXML
    void onActionEditCustomer(ActionEvent event) {
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
     * Edits or creates a new customer. If the customerId text field is filled in, updates the customer in the database
     * with the given customerId from the contents of the Customer Form text fields. If the customerID text filled is
     * empty, creates a new customer in the database. After customer is updated or added, updates the Customer Table
     * View and clears the Customer Form.
     *
     * @param event the event object which triggers customer to be saved
     */
    @FXML
    void onActionSaveCustomer(ActionEvent event) {
        if (isFormCompleted()) {
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
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Form Message");
            alert.setHeaderText("Incomplete form.");
            alert.setContentText("Please fill in all fields in the Customer Form.");
            alert.showAndWait();
        }
    }

    /**
     * Checks if Customer Form is completed. Returns false if any text field except txtFldCustomerIDCustomer is empty,
     * if any text field only contains spaces, or if any combo box is null.
     *
     * @return true if the form is completed, false if the form is not completed
     */
    boolean isFormCompleted() {
        boolean anyTextFieldEmpty = txtFldName.getText().trim() == "" || txtFldAddress.getText().trim() == "" ||
                txtFldPhoneNumber.getText().trim() == "" || txtFldPostalCode.getText().trim() == "";

        boolean anyComboBoxNull = comboCountry.getValue() == null ||  comboFirstLevelDiv.getValue() == null;

        return !(anyTextFieldEmpty || anyComboBoxNull);
    }

    /**
     * Gets the selected country from the combo box and fills in the first-divisions combo box with the first-level
     * divisions associated with the selected country.
     * <p>
     * Triggers error if comboCountry is set to null (like when the Customer Form is cleared) so must check if selection
     * is empty.
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

    /**
     * Clears the Customer Form fields. The form fields cleared include txtFldCustomerIDCustomer, txtFldName,
     * txtFldAddress, txtFldPostalCode, txtFldPhoneNumber,comboFirstLevelDiv, and comboCountry.
     *
     * @param event the event which triggers Customer Form fields to be cleared
     */
    @FXML
    void onActionClearCustomer(ActionEvent event) {
        txtFldCustomerIDCustomer.clear();
        txtFldName.clear();
        txtFldAddress.clear();
        txtFldPostalCode.clear();
        txtFldPhoneNumber.clear();
        comboFirstLevelDiv.setValue(null);
        comboCountry.setValue(null);
    }

    /**
     * Adds the database's customer table to CustomerList, sets the Country combo with list of countries from
     * the database, updates the customer table with customers from CustomerList.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBCustomer.addAll();
        comboCountry.setItems(DBAccess.getCountries());
        updateCustomerTable();
    }
}
