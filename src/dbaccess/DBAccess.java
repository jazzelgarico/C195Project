package dbaccess;

import dbconnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.FirstLevelDivision;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBAccess {

    /**
     * Validates login credentials given by the user. Queries the database for all users and checks if the username
     * and password combination provided is in the query.
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
        return isLoginValid;
    }

    /**
     * Gets an ObservableList of Countries from the database. Queries the database for Country_ID and Country in the
     * coutnries table.
     *
     * @return an ObservableList containing Country_ID and Country from countries table in the database
     */
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

    /**
     * Gets a list of all first-level divisions associated with the countryId given. Queries the first_level_divisions
     * table in the database for Division_ID and Division where the Country_ID matches the countryId given.
     *
     * @param countryId the countryId for which first-level divisions need to be found
     * @return an ObservableList of all first-level divisions associated with the countryId
     */
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

    /**
     * Gets the FirstLevelDivision with the divisionId. Queries the first_level_divisions table from the database for
     * Division and Country_ID where the Division_ID matches the given divisionId
     *
     * @param divisionId the divisionId of the FirstLevelDivision to get
     * @return the FirstLevelDivision with the divisionID
     */
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

    /**
     * Returns a Country with the given countryId. Queries the countries table of the database for the Country where
     * the Country_ID matches the given countryId
     *
     * @param countryId the countryId of the Country to get
     * @return the Country with the matching countryId
     */
    public static Country getCountryByID(int countryId) {
        Country country = new Country();
        try {
            String query = "SELECT Country FROM countries WHERE Country_ID="+countryId+";";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            rs.next();
            String name = rs.getString("Country");

            country = new Country(countryId,name);
        } catch (SQLException e) { e.printStackTrace(); }
        return country;
    }

}