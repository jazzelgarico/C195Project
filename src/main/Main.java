package main;

import dbconnection.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ResourceBundle rb = ResourceBundle.getBundle("Lang",Locale.getDefault());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(rb.getString("Welcome")+"!");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        DBConnection.openConnection();
        launch();
        DBConnection.closeConnection();
    }
}