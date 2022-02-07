package controller;

import dbaccess.DBAccess;

import javafx.collections.ObservableList;
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
    private ComboBox<Country> comboCountry;

    @FXML
    private ComboBox<?> comboEndTime;

    @FXML
    private ComboBox<FirstLevelDivision> comboFirstLevelDiv;

    @FXML
    private ComboBox<?> comboStartTime;

    @FXML
    private ChoiceBox<?> comboType;

    // Month/Week Toggle
    @FXML
    private ToggleGroup viewType;

    @FXML
    private RadioButton radioBtnMonth;

    @FXML
    private RadioButton radioBtnWeek;

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

    //Table View Customer
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
        tblViewCustomer.setItems(DBAccess.addDBCustomers());
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        colFirstLevelDivision.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
    }

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

    @FXML
    void onActionCustomerEdit(ActionEvent event) {
        Customer customer = tblViewCustomer.getSelectionModel().getSelectedItem();
        String name = customer.getCustomerName();
        String address = customer.getAddress();
        String phone = customer.getPhoneNumber();
        String postalCode = customer.getPostalCode();
        int divisionId = customer.getDivisionId();
        FirstLevelDivision division = DBAccess.getFirstLevelDivisionByID(divisionId);
        Country country = DBAccess.getCountryByID(division.getCountryId());

        txtFldName.setText(name);
        txtFldAddress.setText(address);
        txtFldPhoneNumber.setText(phone);
        txtFldPostalCode.setText(postalCode);
        comboFirstLevelDiv.setValue(division);
        comboCountry.setValue(country);
    }

    @FXML
    void onActionSaveCustomer(ActionEvent event) {
        String customerName = txtFldName.getText();
        String address = txtFldAddress.getText();
        String postalCode = txtFldPostalCode.getText();
        String phoneNumber = txtFldPhoneNumber.getText();
        int divisionID = comboFirstLevelDiv.getSelectionModel().getSelectedItem().getDivisionId();

        Customer customer = new Customer(0,customerName,address,postalCode,phoneNumber,divisionID);
        DBAccess.addCustomer(customer);
        updateCustomerTable();
    }

    @FXML
    void onActionComboCountry(ActionEvent event) {
        Country country = comboCountry.getSelectionModel().getSelectedItem();
        comboFirstLevelDiv.setItems(DBAccess.getFirstLevelDivision(country.getCountryId()));
    }


        @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateCustomerTable();
        updateAppointmentTable();
        comboCountry.setItems(DBAccess.getCountries());
    }
}
