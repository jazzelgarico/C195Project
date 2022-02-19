package main;

import dbconnection.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Starts the application
 */
public class Main extends Application {
    /**
     * Starts the application with login view. Starts the title of the window by using the appropriate greting from
     * the resource bundle.
     */
    @Override
    public void start(Stage stage) {
        try {
        ResourceBundle rb = ResourceBundle.getBundle("Lang",Locale.getDefault());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(rb.getString("Welcome")+"!");
        stage.setScene(scene);
        stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DBConnection.openConnection();
        launch();
        DBConnection.closeConnection();
    }
}