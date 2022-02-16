package dbaccess;

import dbconnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;
import utility.TimeHelper;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DBAccess {

    /**
     * Validates login credentials given by the user.
     *
     * @param username the username provided
     * @param password the password provided
     * @return true if login credentials are valid, false if login credentials are not valid
     */
    public static boolean validateLogin(String username,String password) {
        boolean isLoginValid = false;
        try{
            String query = "SELECT * from users";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next() && !isLoginValid) {
                String myUser = rs.getString("User_Name");
                String myPassword = rs.getString("Password");
                isLoginValid = username.equals(myUser) && password.equals(myPassword);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        String logMessage;
        Logger logger = Logger.getLogger(DBAccess.class.getName());
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler(DBAccess.class.getSimpleName() + ".log");
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.addHandler(fileHandler);
        if (isLoginValid) {
            logMessage = "Successful login attempt by user " + username + "at " +
                    TimeHelper.clientToServerTime(LocalDateTime.now()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) +
                    " UTC";
        }
       else {
            logMessage = "Unsuccessful login attempt by user " + username + "at " +
                    TimeHelper.clientToServerTime(LocalDateTime.now()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) +
                    " UTC";
        }

        logger.log(Level.INFO, logMessage);
        return isLoginValid;
    }


    public static ObservableList<Country> getCountries() {
        ObservableList<Country> list = FXCollections.observableArrayList();
        try {
            String query = "SELECT Country_ID,Country FROM countries;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Country country = new Country(countryId,countryName);
                list.add(country);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public static ObservableList<FirstLevelDivision> getFirstLevelDivision(int countryId) {
        ObservableList<FirstLevelDivision> list = FXCollections.observableArrayList();
        try {
            String query = "SELECT Division_ID,Division FROM first_level_divisions WHERE Country_ID ="+countryId+";";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String name = rs.getString("Division");
                FirstLevelDivision division = new FirstLevelDivision(divisionId, name, countryId);
                list.add(division);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public static FirstLevelDivision getFirstLevelDivisionByID(int divisionId) {
        FirstLevelDivision division = new FirstLevelDivision();
        try {
            String query = "SELECT Division,Country_ID FROM first_level_divisions WHERE Division_ID="+divisionId+";";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            rs.next();
            String name = rs.getString("Division");
            int countryId = rs.getInt("Country_ID");

            division = new FirstLevelDivision(divisionId,name,countryId);
        } catch (SQLException e) { e.printStackTrace(); }
        return division;
    }

    public static Country getCountryByID(int countryID) {
        Country country = new Country();
        try {
            String query = "SELECT Country,Country_ID FROM countries WHERE Country_ID="+countryID+";";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int countryId = rs.getInt("Country_ID");
            String name = rs.getString("Country");

            country = new Country(countryId,name);
        } catch (SQLException e) { e.printStackTrace(); }
        return country;
    }

}