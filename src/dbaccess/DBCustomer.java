package dbaccess;

import dbconnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBCustomer {

    public static void addAll() {
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
                Customer.addCustomer(customer);
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public static void add(Customer customer) {
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
            if (ps.executeUpdate() == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                int id = rs.getInt(1);
                customer.setCustomerId(id);
                Customer.addCustomer(customer);
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public static void edit(Customer customer) {
        int id = customer.getCustomerId();
        String query = "UPDATE customers SET Customer_Name=?, Address=?,Postal_Code=?,Phone=?,Last_Update=NOW(),"
                +"Last_Updated_By='user',Division_ID=? WHERE Customer_ID="+id+";";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);

            ps.setString(1,customer.getCustomerName());
            ps.setString(2,customer.getAddress());
            ps.setString(3, customer.getPostalCode());
            ps.setString(4, customer.getPhoneNumber());
            ps.setInt(5,customer.getDivisionId());

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 1) {
                Customer.editCustomer(customer);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Update successful.");
                alert.setContentText("Customer with ID number " + id + " has been updated.");
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public static int delete(int customerID) {
        int deletedAppointments = 0;
        try {
            String querySumAppointments= "SELECT COUNT(Appointment_ID) from appointments where Customer_ID="
                    + customerID +";";
            String queryAppointments = "DELETE FROM appointments WHERE Customer_ID=" + customerID + ";";
            String queryCustomers = "DELETE FROM customers WHERE Customer_ID=" + customerID + ";";
            PreparedStatement psSumAppointments = DBConnection.getConnection().prepareStatement(querySumAppointments);
            PreparedStatement psAppointments = DBConnection.getConnection().prepareStatement(queryAppointments);
            PreparedStatement psCustomers = DBConnection.getConnection().prepareStatement(queryCustomers);
            ResultSet rs = psSumAppointments.executeQuery();
            rs.next();
            deletedAppointments = rs.getInt(1);
            psAppointments.execute();
            psCustomers.execute();
        } catch (SQLException e) { e.printStackTrace();}
            return deletedAppointments;
    }


}
