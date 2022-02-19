package dbaccess;

import dbconnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Appointment;
import model.AppointmentList;
import model.Customer;
import model.CustomerList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

/**
 * Contains static methods to access the database customer table and update the Customer and CustomerList model.
 */
public class DBCustomer {
    /**
     * Adds all the Customers from the database's customers table into CustomerList. Queries the customers table for
     * the Customer_ID, Customer_Name, Address, Postal_Code, Phone, and Division_ID columns, creates a new Customer
     * for each row, and then adds that Customer to CustomerList.
     */
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
                CustomerList.add(customer);
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    /**
     * Adds the provided customer to the ContactList and inserts it into the customer table of the database. Performs
     * an update query to the customers table which inserts a new row containing the provided customer's information.
     *
     * @param customer the customer to be added to ContactList and inserted to the database
     */
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
                CustomerList.add(customer);
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    /**
     * Edits the provided customer in the CustomerList and the customers database. Performs an update query to the
     * customers table which updates the row with the provided customer's information
     *
     * @param customer the customer to be edited
     */
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
                CustomerList.replace(customer);
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    /**
     * Deletes the provided customer in the CustomerList and the customers database.
     * <p>
     * Performs a query which deletes rows in the appointments table with the customerId of the provided customer, then
     * performs a query which deletes the row in the customers table with the customerId of the provided customer. Also
     * deletes appointments in AppointmentList and customers in CustomerList with the customerId of the provided
     * customer.
     *
     * @param customer the customer to be edited
     */
    public static void delete(Customer customer) {
        int customerId = customer.getCustomerId();
        int totalDeletedAppointments = 0;
        try {
            String queryAppointments = "DELETE FROM appointments WHERE Customer_ID=" + customerId + ";";
            PreparedStatement psAppointments = DBConnection.getConnection().prepareStatement(queryAppointments);
            totalDeletedAppointments = psAppointments.executeUpdate();

            ObservableList<Appointment> deleteList = AppointmentList.get().stream()
                    .filter(a -> a.getCustomerId() == customerId)
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
            for (Appointment a: deleteList) {
                AppointmentList.remove(a);
            }

            String queryCustomers = "DELETE FROM customers WHERE Customer_ID=" + customerId + ";";
            PreparedStatement psCustomers = DBConnection.getConnection().prepareStatement(queryCustomers);
            psCustomers.execute();

            CustomerList.remove(customer);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Customer Deletion");
            alert.setHeaderText("Deletion successful.");
            alert.setContentText("Customer with ID number " + customerId + " and " +
                    totalDeletedAppointments + " associated " + "appointment(s) have been deleted.");
            alert.showAndWait();
        } catch (SQLException e) { e.printStackTrace();}
    }


}
