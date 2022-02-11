package controller;

import dbaccess.DBAppointment;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private CustomerController tabCustomerController;

    @FXML
    private AppointmentController tabAppointmentController;

    @FXML
    private ReportController tabReportController;

    @FXML
    private Tab tabCustomer;

    @FXML
    private Tab tabAppointment;

    @FXML
    private Tab tabReports;

    /**
     * On initialize, updates Customer TableView, Appointment TableView, and country combo box
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBAppointment.checkUpcoming();
        tabAppointment.setOnSelectionChanged(e -> {
            if (tabAppointment.isSelected()) {
                tabAppointmentController.updateAppointmentTable(); }
        });
    }

}
