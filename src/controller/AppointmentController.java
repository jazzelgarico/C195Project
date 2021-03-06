package controller;

import dbaccess.DBAppointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.Appointment;
import model.AppointmentList;
import model.Contact;
import model.ContactList;
import utility.TimeFormatCell;
import utility.TimeHelper;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Controller for appointment-view
 */
public class AppointmentController implements Initializable {

    private static final Callback<ListView<LocalDateTime>, ListCell<LocalDateTime>> LIST_TIME_FACTORY =
            localTimeListView -> new TimeFormatCell();

    // Month/Week Toggle
    @FXML private ToggleGroup viewType;
    @FXML private RadioButton radioBtnMonth;
    @FXML private RadioButton radioBtnWeek;

    // Appointment Tab Buttons
    @FXML private Button btnEditAppointment;
    @FXML private Button btnDeleteAppointment;
    @FXML private Button btnClearAppointment;
    @FXML private Button btnSaveAppointment;

    // TableView Appointment
    @FXML private TableView<Appointment> tblViewAppointment;
    @FXML private TableColumn<Appointment, Integer> colAppID;
    @FXML private TableColumn<Appointment, String> colTitle;
    @FXML private TableColumn<Appointment, String> colDesc;
    @FXML private TableColumn<Appointment, String> colLocation;
    @FXML private TableColumn<Appointment, Integer> colContact;
    @FXML private TableColumn<Appointment, String> colType;
    @FXML private TableColumn<Appointment, LocalDate> colDate;
    @FXML private TableColumn<Appointment, LocalDateTime> colStart;
    @FXML private TableColumn<Appointment, LocalDateTime> colEnd;
    @FXML private TableColumn<Appointment, Integer> colCustomerId;
    @FXML private TableColumn<Appointment, Integer> colUserId;

    // Appointment Form
    @FXML private TextField txtFldAppID;
    @FXML private TextField txtFldTitle;
    @FXML private TextField txtFldDesc;
    @FXML private TextField txtFldLocation;
    @FXML private ComboBox<Contact> comboContact;
    @FXML private TextField txtFldType;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<LocalDateTime> comboStartTime;
    @FXML private ComboBox<LocalDateTime> comboEndTime;
    @FXML private TextField txtFldCustomerIDApp;
    @FXML private TextField txtFldUserID;

    /**
     * Sets ContactList, adds the database's appointment table to the AppointmentList, updates the appointment table,
     * and sets the items in comboContact according to ContactList.
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ContactList.set();
        DBAppointment.addAll();
        updateAppointmentTable();
        comboContact.setItems(ContactList.get());
    }

    /**
     * Updates Appointments Table View from viewMonth or viewWeek. When radioBtnMonth is selected, shows viewMonth.
     * When radioBtnWeek is selected, shows viewWeek
     */
    public void updateAppointmentTable() {
        if (radioBtnMonth.isSelected()){
            viewMonth();
        } else if (radioBtnWeek.isSelected()){
            viewWeek();
        }
    }

    /**
     * Updates tblViewAppointment given an ObservableList of Appointments.
     *
     * @param list the list which tblViewAppointment should display
     */
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
     * Updates tblViewAppointment with appointments in AppointmentList that start in the current month.
     * <p>
     * Lambda expression used in this method for increased legibility. THe expression filters AppointmentList for
     * appointments that are in the current month instead of using for loop to add appointments to a new
     * ObservableList<Appointment>.
     */
    public void viewMonth() {
        Month thisMonth = LocalDateTime.now().getMonth();
        int thisYear = LocalDateTime.now().getYear();
        updateAppointmentTable(AppointmentList.get().stream()
                .filter(a -> a.getAppointmentDate().getMonth() == thisMonth &&
                        a.getAppointmentDate().getYear() == thisYear)
                .collect(Collectors.toCollection(FXCollections::observableArrayList)));
    }

    /**
     * Updates tblViewAppointment with appointments in AppointmentList that start in the current week.
     * <p>
     * Lambda expression used in this method for increased legibility. THe expression filters AppointmentList for
     * appointments that are in the current week instead of using for loop to add appointments to a new
     * ObservableList<Appointment>.
     */
    public void viewWeek() {
        LocalDateTime weekEnd = LocalDateTime.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
        LocalDateTime weekStart = weekEnd.minusDays(6);
        updateAppointmentTable(AppointmentList.get().stream()
                .filter(a -> a.getStartTime().isBefore(weekEnd) &&
                        a.getStartTime().isAfter(weekStart))
                .collect(Collectors.toCollection(FXCollections::observableArrayList)));
    }

    /**
     * Deletes the selected appointment from tblViewAppointment. Updates tblViewAppointment after deletion.
     *
      * @param event the event which triggers an appointment to be deleted
     */
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
            comboContact.setValue(ContactList.getContactFromId(appointment.getContactId()));
            txtFldType.setText(appointment.getType());
            datePicker.setValue(appointment.getAppointmentDate());
            comboStartTime.setValue(appointment.getStartTime());
            comboEndTime.setValue(appointment.getEndTime());
            txtFldCustomerIDApp.setText(Integer.toString(appointment.getCustomerId()));
            txtFldUserID.setText(Integer.toString(appointment.getUserId()));
        }
    }

    /**
     * Creates an appointment from Appointment Form text fields if txtFldAppID is empty or updates appointment with the
     * id specified in txtFldAppId if txtFldAppID is not empty.
     *
     * @param event the event which triggers an appointment to be saved
     */
    @FXML
    void onActionSaveAppointment(ActionEvent event) {
        if (isFormCompleted()) {
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
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Form Message");
            alert.setHeaderText("Incomplete form.");
            alert.setContentText("Please fill in all fields in the Appointment Form.");
            alert.showAndWait();
        }
    }

    /**
     * Clears the text fields and sets combo boxes to null associated with the Appointment Form.
     *
     * @param event the event which triggers the Appointment Form Fields to be cleared
     */
    @FXML
    void onActionClearAppointment(ActionEvent event) {
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

    /**
     * Checks if AppointmentForm is completed. Returns false if any text field except txtFldAppID is empty, if any text
     * field only contains white space, or if any combo box is null.
     *
     * @return true if the form is completed, false if the form is not completed
     */
    boolean isFormCompleted() {
        boolean anyTextFieldEmpty = txtFldTitle.getText().trim() == "" || txtFldDesc.getText().trim() == "" ||
                txtFldLocation.getText().trim() == "" || txtFldType.getText().trim() == "" ||
                txtFldCustomerIDApp.getText().trim() == "" || txtFldUserID.getText().trim() == "";

        boolean anyComboBoxNull = comboContact.getValue() == null ||  datePicker.getValue() == null ||
                comboStartTime.getValue() == null || comboEndTime.getValue() == null;

        return !(anyTextFieldEmpty || anyComboBoxNull);
    }

    /**
     * Sets the items for comboStartTime and comboEndTime according the date picked. comboStartTime's items start at
     * the time the business is open and continues with an option every 15 minutes after until 15 minutes before closing.
     * comboEndTime's items start at 15 minutes after business open and continues with an option every 15 minutes until
     * closing.
     *
     * @param event the event which triggers datePicker value to change
     */
    @FXML
    void onActionDatePicker(ActionEvent event) {
        if (datePicker.getValue() != null) {
            final LocalDate PICKED_DATE = datePicker.getValue();
            final LocalDateTime BUSINESS_CLOSE = TimeHelper.getTimeClose(PICKED_DATE);
            LocalDateTime localStart = TimeHelper.getTimeOpen(PICKED_DATE);

            // Fill in comboStartTime and comboEndTime
            ObservableList<LocalDateTime> startList = FXCollections.observableArrayList();
            ObservableList<LocalDateTime> endList = FXCollections.observableArrayList();
            while (localStart.isBefore(BUSINESS_CLOSE)) {
                startList.add(localStart);
                endList.add(localStart.plusMinutes(15));
                localStart = localStart.plusMinutes(15);
            }
            comboStartTime.setItems(startList);
            comboEndTime.setItems(endList);
            comboStartTime.setCellFactory(LIST_TIME_FACTORY);
            comboStartTime.setButtonCell(LIST_TIME_FACTORY.call(null));
            comboEndTime.setCellFactory(LIST_TIME_FACTORY);
            comboEndTime.setButtonCell(LIST_TIME_FACTORY.call(null));
        }
    }

    /**
     * Calls viewMonth when the event is triggered
     *
     * @param event the event which triggers viewMonth to be shown
     */
    @FXML
    void onActionMonthRadio(ActionEvent event) {
        viewMonth();
    }

    /**
     * Calls viewMonth when the event is triggered
     *
     * @param event the event which triggers viewMonth to be shown
     */
    @FXML
    void onActionWeekRadio(ActionEvent event) {
        viewWeek();
    }

    /**
     * Sets comboEndTime's items to start from 15 minutes after the selected item from comboStartTime and continues
     * every 15 minutes until business closing time.
     *
     * @param event the event which triggers comboStartTime value to change
     */
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
            comboEndTime.setCellFactory(LIST_TIME_FACTORY);
            comboEndTime.setButtonCell(LIST_TIME_FACTORY.call(null));
        }
    }



}
