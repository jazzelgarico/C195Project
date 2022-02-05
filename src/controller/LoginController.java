package controller;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.print("Start");
    }

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
        Stage stage;
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.close();

        String username = txtFldUserName.getText();
        String password = txtFldPassword.getText();

        if (validateLogin(username,password)) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/main-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("Welcome");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            warnInvalidLogin();
        }

    }

    /**
     * Label shows login warning regarding incorrect username or password.
     */
    public void warnInvalidLogin() {
        lblUserWarning.setText("The username or password provided is not correct.");
    }

    /**
     * Checks login credentials for valid username and password combination.
     *
     * @return true if login credentials are valid, false if login credentials are not valid
     */
    public boolean validateLogin(String username, String password) {
        return true;
    }


}
