package controller;

import dbaccess.DBAccess;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    // Customer Tab

    // Customer Tab Buttons
    @FXML
    private Button btnDeleteCustomer;

    @FXML
    private Button btnEditCustomer;

    @FXML
    private Button btnSaveCustomer;

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

    // Appointment Tab

    @FXML
    private Tab tabAppointment;

    // Month/Week Toggle
    @FXML
    private ToggleGroup viewType;

    @FXML
    private RadioButton radioBtnMonth;

    @FXML
    private RadioButton radioBtnWeek;

    // Appointment Tab Buttons

    @FXML
    private Button btnDeleteAppointment;

    @FXML
    private Button btnEditAppointment;

    @FXML
    private Button btnSaveAppointment;

    // TableView Appointment
    @FXML
    private TableView<Appointment> tblViewAppointment;

    @FXML
    private TableColumn<Appointment, Integer> colAppID;

    @FXML
    private TableColumn<Appointment, String> colTitle;

    @FXML
    private TableColumn<Appointment, String> colDesc;

    @FXML
    private TableColumn<Appointment, String> colLocation;

    @FXML
    private TableColumn<Appointment, Integer> colContact;

    @FXML
    private TableColumn<Appointment, String> colType;

    @FXML
    private TableColumn<Appointment, LocalDate> colDate;

    @FXML
    private TableColumn<Appointment, LocalTime> colStart;

    @FXML
    private TableColumn<Appointment, LocalTime> colEnd;

    @FXML
    private TableColumn<Appointment, Integer> colCustomerId;

    @FXML
    private TableColumn<Appointment, Integer> colUserId;

    // Appointment Form

    @FXML
    private ComboBox<?> comboContact;

    @FXML
    private ComboBox<?> comboEndTime;

    @FXML
    private ComboBox<?> comboStartTime;

    @FXML
    private ChoiceBox<?> comboType;

    @FXML
    private TextField txtFldAppID;

    @FXML
    private TextField txtFldDate;

    @FXML
    private TextField txtFldDesc;

    @FXML
    private TextField txtFldLocation;

    @FXML
    private TextField txtFldTitle;

    @FXML
    private TextField txtFldUserID;

    /**
     * Updates Customer Table View from Customers in the database.
     */
    private void updateCustomerTable() {
        tblViewCustomer.setItems(DBAccess.addDBCustomers());
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        colFirstLevelDivision.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
    }

    /**
     * Updates Appointments Table View from Appointments in the database.
     */
    private void updateAppointmentTable() {
        tblViewAppointment.setItems(DBAccess.addAllAppointments());
        colAppID.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        colStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        colEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    /**
     * Deletes selected customer in Customer TableView from the database. After Customer is deleted, Customer Table
     * View is refreshed. Informs user that deletion is successful with an alert.
     *
     * @param event the event which triggers customer to be deleted
     */
    @FXML
    void onActionCustomerDelete(ActionEvent event) {
        int customerId = tblViewCustomer.getSelectionModel().getSelectedItem().getCustomerId();
        DBAccess.deleteCustomer(customerId);
        updateCustomerTable();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Deletion successful.");
        alert.setContentText("Customer with ID number "+ customerId+" has been deleted.");
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
     * <p></p>
     * If the customerId text field is filled in, updates the customer in the database
     * with the given customerId from the contents of the Customer Form text fields. If the customerID text filled is
     * empty, creates a new customer in the database. After customer is updated or added, updates the Customer Table
     * View and clears the Customer Form.
     *
     * @param event the event object which triggers customer to be saved
     */
    @FXML
    void onActionSaveCustomer(ActionEvent event) {
        //Get Form Data
        if (comboFirstLevelDiv.getValue().equals(null)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("First-Level Division must be specified");
            alert.showAndWait();
        }
        else {
            String customerName = txtFldName.getText();
            String address = txtFldAddress.getText();
            String postalCode = txtFldPostalCode.getText();
            String phoneNumber = txtFldPhoneNumber.getText();
            int divisionID = comboFirstLevelDiv.getSelectionModel().getSelectedItem().getDivisionId();

            if (txtFldCustomerIDCustomer.getText().isEmpty()) {
                //Adds new customer
                Customer customer = new Customer(customerName, address, postalCode, phoneNumber, divisionID);
                DBAccess.addCustomer(customer);
            } else {
                //Edits existing customer
                int customerId = Integer.parseInt(txtFldCustomerIDCustomer.getText());
                Customer customer = new Customer(customerId, customerName, address, postalCode, phoneNumber, divisionID);
                DBAccess.editCustomer(customer);
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

    /**
     * On initialize, updates Customer TableView, Appointment TableView, and country combo box
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateCustomerTable();
        updateAppointmentTable();
        comboCountry.setItems(DBAccess.getCountries());
    }
}
