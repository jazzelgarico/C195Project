package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import view.ContactHoursView;
import view.ContactScheduleView;
import view.MonthTypeView;

/**
 * Controller for report-view
 */
public class ReportController implements Initializable {

    @FXML private VBox reportContent;
    @FXML private ComboBox<String> comboReport;
    @FXML private Tab tabReports;
    @FXML private VBox vbox;

    private static final ObservableList<String> REPORT_LIST = FXCollections.observableArrayList(
            "Month-Type Report","Contact Schedule Report","Contact Hours Report");

    /**
     * Sets the items in comboReport.
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboReport.setItems(REPORT_LIST);
    }

    /**
     * Updates reportContent on change in comboReport value
     *
     * @param event the event which triggers reportContent to be updated
     */
    @FXML
    void onActionComboReport(ActionEvent event){
        updateReportContent();
    }

    /**
     * Updates reportContent on Refresh button interaction
     *
     * @param event the event which triggers reportContent to be updated
     */
    @FXML
    void onActionRefreshReport (ActionEvent event) {
        updateReportContent();
    }

    /**
     * Updates reportContent according to comboReport selection.
     */
    void updateReportContent() {
        if (comboReport.getValue() == "Month-Type Report") {
            MonthTypeView view = new MonthTypeView();
            reportContent.getChildren().clear();
            reportContent.getChildren().add(view);
        } else if (comboReport.getValue() == "Contact Schedule Report") {
            reportContent.getChildren().clear();
            ContactScheduleView view = new ContactScheduleView();
            reportContent.getChildren().add(view);
        } else if (comboReport.getValue() == "Contact Hours Report") {
            reportContent.getChildren().clear();
            ContactHoursView view = new ContactHoursView();
            reportContent.getChildren().add(view);
        }
    }

}
