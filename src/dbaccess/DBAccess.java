package dbaccess;

import dbconnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Country;
import model.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;

public class DBAccess {

    public static ObservableList<Customer> addDBCustomers() {
        ObservableList<Customer> list = FXCollections.observableArrayList();
        try {
            String query = "SELECT Customer_ID,Customer_Name,Address,Postal_Code,Phone,Division_ID FROM customers";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionID = rs.getInt("Division_ID");
                //Create new Customer
                Customer customer = new Customer(customerID,customerName,address,postalCode,phone,divisionID);
                //Add customer to list
                list.add(customer);
            }

        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public static ObservableList<Appointment> addAllAppointments() {
        ObservableList<Appointment> list = FXCollections.observableArrayList();
        try {
            String query = "SELECT Appointment_ID,Title,Description,Location,Type,Start,End,Customer_ID,User_ID," +
                    "Contact_ID from appointments";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                LocalDate appointmentDate = rs.getTimestamp("Start").toLocalDateTime().toLocalDate();
                LocalTime startTime = rs.getTime("Start").toLocalTime();
                LocalTime endTime = rs.getTime("End").toLocalTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                //Create new Appointment
                Appointment appointment = new Appointment(appointmentId, title, description, location, contactId, type,
                        appointmentDate, startTime, endTime, customerId, userId);
                //Add appointment to list
                list.add(appointment);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public static void deleteCustomer(int customerID) {
        try {
            String query = "DELETE FROM customers WHERE Customer_ID=" + customerID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ps.execute();
        } catch (SQLException e) { e.printStackTrace();}

    }

    public static void addCustomer(Customer customer) {
        String name = customer.getCustomerName();
        String address = customer.getAddress();
        String phone = customer.getPhoneNumber();
        String postalCode = customer.getPostalCode();
        int divisionId = customer.getDivisionId();
        try {
            String query = "INSERT INTO customers VALUES(NULL, ?, ?, ?, ?, NOW(), 'script', NOW(), 'script', ?)";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,name);
            ps.setString(2,address);
            ps.setString(3,postalCode);
            ps.setString(4,phone);
            ps.setInt(5,divisionId);
            ps.execute();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    /**
     * Validates login credentials given by the user.
     *
     * @param username the username provided
     * @param password the password provided
     * @return true if login credentials are valid, false if login credentials are not valid
     */
    public static boolean validateLogin(String username,String password) {
        boolean isLoginValid = true;//CHANGE THIS
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
}