package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import view.ContactHoursView;
import view.ContactScheduleView;
import view.MonthTypeView;

public class ReportController implements Initializable {

    @FXML private VBox vbox;
    @FXML private VBox reportContent;
    @FXML private ComboBox comboReport;

    /**
     * Updates reportContent when event is triggered
     *
     * @param event the event which triggers reportContent to be updated
     */
    @FXML
    void onActionComboReport(ActionEvent event){
        updateReportContent();
    }

    /**
     * Updates reportContent according to comboReport selection.
     */
    void updateReportContent() {
        if (comboReport.getSelectionModel().getSelectedItem() == "Month-Type Report") {
            MonthTypeView view = new MonthTypeView();
            reportContent.getChildren().clear();
            reportContent.getChildren().add(view.get());
        } else if (comboReport.getSelectionModel().getSelectedItem() == "Contact Schedule") {
            reportContent.getChildren().clear();
            ContactScheduleView view = new ContactScheduleView();
            reportContent.getChildren().add(view.get());
        } else if (comboReport.getSelectionModel().getSelectedItem() == "Contact Hours") {
            reportContent.getChildren().clear();
            ContactHoursView view = new ContactHoursView();
            reportContent.getChildren().add(view.get());
        }
    }

    void test() {
        System.out.println("Test");
    }

    /**
     * Sets the items in comboReport.
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> reportList = FXCollections.observableArrayList(
                "Month-Type Report","Contact Schedule","Contact Hours");
        comboReport.setItems(reportList);

    }

}
