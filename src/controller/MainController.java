package controller;

import dbaccess.DBAccess;
import dbaccess.DBAppointment;
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

    @FXML
    private CustomerController tabCustomerController;
    @FXML
    private Tab tabCustomer;

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
    void onActionDatePicker(ActionEvent event) {
        if (datePicker.getValue() != null) {
            LocalDate ld = datePicker.getValue();
            LocalDateTime localStart = TimeHelper.getTimeOpen(ld);
            LocalDateTime localEnd = TimeHelper.getTimeClose(ld);
            // Fill in comboStartTime and comboEndTime
            ObservableList<LocalDateTime> startList = FXCollections.observableArrayList();
            ObservableList<LocalDateTime> endList = FXCollections.observableArrayList();
            while (localStart.isBefore(localEnd)) {
                startList.add(localStart);
                endList.add(localStart.plusMinutes(15));
                localStart = localStart.plusMinutes(15);
            }
            comboStartTime.setItems(startList);
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
        if (comboStartTime.getValue() != null) {
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
    }

    /**
     * On initialize, updates Customer TableView, Appointment TableView, and country combo box
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBAppointment.checkUpcoming();
        updateAppointmentTable();
        comboContact.setItems(DBAccess.getAllContacts());
    }

}
