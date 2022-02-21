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
import utility.TimeHelper;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Controller for login-view
 */
public class LoginController implements Initializable{

    @FXML private Button btnLogin;
    @FXML private Label lblPassword;
    @FXML private Label lblRegion;
    @FXML private Label lblUserName;
    @FXML private Label lblUserWarning;
    @FXML private PasswordField txtFldPassword;
    @FXML private TextField txtFldUserName;

    ResourceBundle rb = ResourceBundle.getBundle("Lang",Locale.getDefault());

    /**
     * Takes username and password, validates login credentials, and opens main view if credentials are valid.
     *
     * @param event which triggers login attempt
     */
    @FXML
    public void btnLoginOnClick(ActionEvent event) {
        // Get login credentials
        String username = txtFldUserName.getText();
        String password = txtFldPassword.getText();

        // Logger
        final Logger LOGGER = Logger.getLogger(LoginController.class.getName());
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler("login_activity.txt",true);
            fileHandler.setFormatter(new SimpleFormatter());
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.addHandler(fileHandler);
        String logMessage = " login attempt by user " + username + " at " +
                TimeHelper.clientToServerTime(LocalDateTime.now()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) +
                "UTC";

        // Validate login credentials
        if (DBAccess.validateLogin(username, password)) {
            logMessage = "Successful" + logMessage;
            LOGGER.log(Level.INFO, logMessage);
            fileHandler.close();
            try {
                Stage stage;
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.close();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/main-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);
                stage.setTitle("Welcome!");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logMessage = "Unsuccessful" + logMessage;
            LOGGER.log(Level.INFO, logMessage);
            fileHandler.close();
            lblUserWarning.setText(rb.getString("LoginError"));
        }
    }

    /**
     * Sets texts for lblUserName, lblPassword, btnLogin, and lblRegion using the Resource Bundle.
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblUserName.setText(rb.getString("Username"));
        lblPassword.setText(rb.getString("Password"));
        btnLogin.setText(rb.getString("Login"));
        lblRegion.setText(rb.getString("Region") + ": " + TimeHelper.getClientZoneId());
    }
}
