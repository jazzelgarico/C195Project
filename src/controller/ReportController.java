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
import javafx.scene.layout.VBox;
import view.MonthTypeView;

public class ReportController implements Initializable {

    @FXML
    private VBox vbox;

    @FXML
    private Label test;

    @FXML
    ComboBox comboReport;

    @FXML
    void onActionComboReport(ActionEvent event){
        if (comboReport.getSelectionModel().getSelectedItem() == "MonthTypeReport") {
            vbox.getChildren().add(MonthTypeView.get());
        } else if (comboReport.getSelectionModel().getSelectedItem() == "ContactSchedule") {

        } else if (comboReport.getSelectionModel().getSelectedItem() == "Additional") {

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> reportList = FXCollections.observableArrayList(
                "MonthTypeReport","ContactSchedule","Additional");
        comboReport.setItems(reportList);

    }

}
