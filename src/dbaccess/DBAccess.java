package dbaccess;

import dbconnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;
import java.sql.*;


public class DBAccess {

    /**
     * Validates login credentials given by the user.
     *
     * @param username the username provided
     * @param password the password provided
     * @return true if login credentials are valid, false if login credentials are not valid
     */
    public static boolean validateLogin(String username,String password) {
        boolean isLoginValid = true;//FIX ME
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

    /**
     * Given a contactId, returns a Contact with information from associated contact from the database.
     *
     * @param contactId contactId to find
     * @return Contact with associated contactId
     */
    public static Contact getContactfromId(int contactId) {
        String query = "SELECT * from contacts WHERE Contact_ID=" + contactId + ";";
        Contact contact = new Contact();
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            rs.next();
            String name = rs.getString("Contact_Name");
            String email = rs.getString("Email");
            contact = new Contact(contactId, name, email);
        } catch(SQLException e) { e.printStackTrace(); }
        return contact;
    }

    public static ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> list = FXCollections.observableArrayList();
        String query = "SELECT * from contacts;";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int contactId = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contact contact = new Contact(contactId, name, email);
                list.add(contact);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

}