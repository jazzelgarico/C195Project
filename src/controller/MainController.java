package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainController {

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

    @FXML
    private RadioButton radioBtnMonth;

    @FXML
    private RadioButton radioButtonWeek;

    @FXML
    private TableView<?> tblViewAppointment;

    @FXML
    private TableView<?> tblViewCustomer;

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

}
