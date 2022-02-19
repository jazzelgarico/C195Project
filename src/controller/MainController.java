package controller;

import dbaccess.DBAppointment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Tab;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.stage.Stage;

/**
 * Controller for main-view
 */
public class MainController implements Initializable {

    // Controllers
    @FXML private CustomerController tabCustomerController;
    @FXML private AppointmentController tabAppointmentController;
    @FXML private ReportController tabReportController;
    // Tabs
    @FXML private Tab tabCustomer;
    @FXML private Tab tabAppointment;
    @FXML private Tab tabReports;

    /**
     * On initialize, updates Customer TableView, Appointment TableView, and country combo box
     * <p>
     * Lambda expression used to implement EventHandler interface.
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
