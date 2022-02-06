package controller;

import dbaccess.addDBData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.AppointmentList;
import model.Customer;
import model.CustomerList;

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
    private ComboBox<?> comboCountry;

    @FXML
    private ComboBox<?> comboEndTime;

    @FXML
    private ComboBox<?> comboFirstLevelDiv;

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

    // Appointment TableView
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

    private void updateAppointmentTable() {
        tblViewAppointment.setItems(AppointmentList.getAppointmentList());
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialize?");
        addDBData.addDBCustomers();
        addDBData.addDBAppointments();
        updateCustomerTable();
        updateAppointmentTable();
        System.out.println("Maybe?");

    }
}
