package controller;

import dbaccess.DBAccess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable{

    @FXML
    private Button btnLogin;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblRegion;

    @FXML
    private Label lblUserName;

    @FXML
    private Label lblUserWarning;

    @FXML
    private PasswordField txtFldPassword;

    @FXML
    private TextField txtFldUserName;

    @FXML
    /**
     * Takes username and password, validates login credentials, and opens main view if credentials are valid.
     *
     * @param event which triggers login attempt
     */
    public void btnLoginOnClick(ActionEvent event) {
        //Get Login Credentials
        String username = txtFldUserName.getText();
        String password = txtFldPassword.getText();

        if (DBAccess.validateLogin(username, password)) {
            try {
                Stage stage;
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.close();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/main-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("Welcome");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            lblUserWarning.setText("The username or password provided is incorrect.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblRegion.setText("Region: "+ TimeHelper.getClientZoneId());
    }
}
