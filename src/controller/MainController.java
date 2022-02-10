package controller;

import dbaccess.DBAccess;

import dbaccess.DBAppointment;
import dbaccess.DBCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.*;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainController implements Initializable {

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

    @FXML
    private Button btnClearAppointment;

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
    private TableColumn<Appointment, LocalDateTime> colStart;

    @FXML
    private TableColumn<Appointment, LocalDateTime> colEnd;

    @FXML
    private TableColumn<Appointment, Integer> colCustomerId;

    @FXML
    private TableColumn<Appointment, Integer> colUserId;

    // Appointment Form
    @FXML
    private ComboBox<Contact> comboContact;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<LocalDateTime> comboEndTime;

    @FXML
    private ComboBox<LocalDateTime> comboStartTime;

    @FXML
    private TextField txtFldType;

    @FXML
    private TextField txtFldAppID;

    @FXML
    private TextField txtFldDesc;

    @FXML
    private TextField txtFldLocation;

    @FXML
    private TextField txtFldTitle;

    @FXML
    private TextField txtFldCustomerIDApp;

    @FXML
    private TextField txtFldUserID;

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
     * Updates Appointments Table View from Appointments in the database.
     */
    public void updateAppointmentTable() {
        if (radioBtnMonth.isSelected()){
            viewMonth();
        } else if (radioBtnWeek.isSelected()){
            viewWeek();
        }
    }

    public void updateAppointmentTable(ObservableList<Appointment> list) {
        Callback<TableColumn<Appointment,LocalDate>,TableCell<Appointment,LocalDate>> dateFactory =
                localDateTimeTableColumn -> new TableCell<Appointment,LocalDate>() {
                    @Override
                    protected void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item,empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.format(DateTimeFormatter.ofPattern("M/d/yyyy")));
                        }
                    }
                };

        Callback<TableColumn<Appointment,LocalDateTime>,TableCell<Appointment,LocalDateTime>> timeFactory =
                localDateTimeTableColumn -> new TableCell<Appointment,LocalDateTime>() {
                    @Override
                    protected void updateItem(LocalDateTime item, boolean empty) {
                        super.updateItem(item,empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.format(DateTimeFormatter.ofPattern("h:mma")));
                        }
                    }
                };

        tblViewAppointment.setItems(list);
        // Fills table columns
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
        // Formats columns
        colDate.setCellFactory(dateFactory);
        colEnd.setCellFactory(timeFactory);
        colStart.setCellFactory(timeFactory);

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
        updateAppointmentTable();
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
    void onActionDeleteAppointment(ActionEvent event) {
        Appointment appointment = tblViewAppointment.getSelectionModel().getSelectedItem();
        DBAppointment.delete(appointment);
        updateAppointmentTable();
    }

    /**
     * Fills the appointment form with the information for the selected appointment.
     *
     * @param event the event which triggers appointment to be edited
     */
    @FXML
    void onActionEditAppointment(ActionEvent event) {
        if (tblViewAppointment.getSelectionModel().isEmpty()) {
            // Warn user if no appointment is selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No appointment selected.");
            alert.setContentText("Please select an appointment to edit.");
            alert.showAndWait();
        } else {
            // Get Appointment Data
            Appointment appointment = tblViewAppointment.getSelectionModel().getSelectedItem();
            txtFldAppID.setText(Integer.toString(appointment.getAppointmentId()));
            txtFldTitle.setText(appointment.getTitle());
            txtFldDesc.setText(appointment.getDescription());
            txtFldLocation.setText(appointment.getLocation());
            comboContact.setValue(DBAccess.getContactfromId(appointment.getContactId()));
            txtFldType.setText(appointment.getType());
            datePicker.setValue(appointment.getAppointmentDate());
            comboStartTime.setValue(appointment.getStartTime());
            comboEndTime.setValue(appointment.getEndTime());
            txtFldCustomerIDApp.setText(Integer.toString(appointment.getCustomerId()));
            txtFldUserID.setText(Integer.toString(appointment.getUserId()));
        }
    }

    @FXML
    void onActionSaveAppointment(ActionEvent event) {
        String title = txtFldTitle.getText();
        String desc = txtFldDesc.getText();
        String location = txtFldLocation.getText();
        int contactId = comboContact.getSelectionModel().getSelectedItem().getContactId();
        String type = txtFldType.getText();
        LocalDate appDate = datePicker.getValue();
        LocalDateTime start = comboStartTime.getSelectionModel().getSelectedItem();
        LocalDateTime end = comboEndTime.getSelectionModel().getSelectedItem();
        int customerId = Integer.parseInt(txtFldCustomerIDApp.getText());
        int userId = Integer.parseInt(txtFldUserID.getText());
        Appointment appointment = new Appointment(title, desc, location, contactId, type, appDate, start, end,
                customerId, userId);
        if (txtFldAppID.getText().isEmpty()) {
            // Create New Appointment
            DBAppointment.add(appointment);
        }
        else {
            // Edit Existing Appointment
            int appointmentId = Integer.parseInt(txtFldAppID.getText());
            appointment.setAppointmentId(appointmentId);
            DBAppointment.edit(appointment);
        }
        updateAppointmentTable();
    }


    @FXML
    void onActionClearAppointment(ActionEvent event) {
        clearAppointmentForm();
    }

   @FXML
   public void clearAppointmentForm(){
        txtFldAppID.clear();
        txtFldTitle.clear();
        txtFldDesc.clear();
        txtFldLocation.clear();
        comboContact.setValue(null);
        txtFldType.clear();
        datePicker.setValue(null);
        comboStartTime.setValue(null);
        comboEndTime.setValue(null);
        txtFldCustomerIDApp.clear();
        txtFldUserID.clear();
   }

    @FXML
    void onActionClearCustomer(ActionEvent event) {
        clearCustomerForm();
    }


    @FXML
    void onActionDatePicker(ActionEvent event) {
        if (datePicker.getValue() != null) {
            LocalDate ld = datePicker.getValue();
            LocalDateTime localStart = TimeHelper.getTimeOpen(ld);
            LocalDateTime localEnd = TimeHelper.getTimeClose(ld);
            // Fill in comboStartTime and comboEndTime
            while (localStart.isBefore(localEnd)) {
                comboStartTime.getItems().add(localStart);
                comboEndTime.getItems().add(localStart.plusMinutes(15));
                localStart = localStart.plusMinutes(15);
            }
            //Cell factory for start time and end time combo boxes
            Callback<ListView<LocalDateTime>, ListCell<LocalDateTime>> timeFactory = localTimeListView -> new ListCell<LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.format(DateTimeFormatter.ofPattern("h:mma")));
                    }
                }
            };
            // Format combo list
            comboStartTime.setCellFactory(timeFactory);
            comboStartTime.setButtonCell(timeFactory.call(null));
            comboEndTime.setCellFactory(timeFactory);
            comboEndTime.setButtonCell(timeFactory.call(null));
        }
    }

    public void viewMonth() {
        Month thisMonth = LocalDateTime.now().getMonth();
        int thisYear = LocalDateTime.now().getYear();
        updateAppointmentTable(DBAppointment.addAll().stream()
                .filter(a -> a.getAppointmentDate().getMonth() == thisMonth &&
                        a.getAppointmentDate().getYear() == thisYear)
                .collect(Collectors.toCollection(FXCollections::observableArrayList)));
    }

    public void viewWeek() {
        LocalDateTime weekEnd = LocalDateTime.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
        LocalDateTime weekStart = weekEnd.minusDays(6);
        updateAppointmentTable(DBAppointment.addAll().stream()
                .filter(a -> a.getStartTime().isBefore(weekEnd) &&
                        a.getStartTime().isAfter(weekStart))
                .collect(Collectors.toCollection(FXCollections::observableArrayList)));
    }

    @FXML
    void onActionMonthRadio(ActionEvent event) {
        viewMonth();
    }

    @FXML
    void onActionWeekRadio(ActionEvent event) {
        viewWeek();
    }

    @FXML
    void onActionStartTime(ActionEvent event) {
        final LocalDate APPOINTMENT_DATE = datePicker.getValue();
        final LocalDateTime CLOSE_TIME = TimeHelper.getTimeClose(APPOINTMENT_DATE);
        LocalDateTime localStart = comboStartTime.getValue();

        ObservableList<LocalDateTime> endList = FXCollections.observableArrayList();
        while (localStart.isBefore(CLOSE_TIME)) {
            endList.add(localStart.plusMinutes(15));
            localStart = localStart.plusMinutes(15);
        }
        comboEndTime.setItems(endList);
        //Cell factory for start time and end time combo boxes
        Callback<ListView<LocalDateTime>, ListCell<LocalDateTime>> timeFactory = localTimeListView -> new ListCell<LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.format(DateTimeFormatter.ofPattern("h:mma")));
                }
            }
        };
        // Format combo list
        comboEndTime.setCellFactory(timeFactory);
        comboEndTime.setButtonCell(timeFactory.call(null));
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
        comboContact.setItems(DBAccess.getAllContacts());
    }

}
