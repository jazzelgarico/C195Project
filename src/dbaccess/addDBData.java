package dbaccess;

import dbconnection.DBConnection;
import model.Appointment;
import model.AppointmentList;
import model.Customer;
import model.CustomerList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class addDBData {

    public static void addDBCustomers() {
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

                Customer customer = new Customer(customerID,customerName,address,postalCode,phone,divisionID);
                CustomerList.addCustomer(customer);
            }

        } catch (SQLException e) { e.printStackTrace(); }
    }

    public static void addDBAppointments() {
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
                LocalTime startTime = rs.getTimestamp("Start").toLocalDateTime().toLocalTime();
                LocalTime endTime = rs.getTimestamp("End").toLocalDateTime().toLocalTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");

                Appointment appointment = new Appointment(appointmentId,title,description,location,contactId,type,
                        appointmentDate,startTime,endTime,customerId,userId);
                AppointmentList.addAppointment(appointment);
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
