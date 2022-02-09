package dbaccess;

import controller.TimeHelper;
import dbconnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Appointment;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DBAppointment {
    public static ObservableList<Appointment> addAll() {
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
                LocalDateTime startTime = TimeHelper.serverToClientTime(rs.getTimestamp("Start").toLocalDateTime());
                LocalDateTime endTime = TimeHelper.serverToClientTime(rs.getTimestamp("End").toLocalDateTime());
                LocalDate appointmentDate = startTime.toLocalDate();
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

    /**
     * Deletes the appointment matching the given appointmentId from the database.
     *
     * @param appointment appointment to delete
     */
    public static void delete(Appointment appointment) {
        int appointmentId = appointment.getAppointmentId();
        String type = appointment.getType();
        String query =  "DELETE FROM appointments WHERE Appointment_ID=" + appointmentId;
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ps.execute();
            // Alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Deletion successful.");
            alert.setContentText("Appointment " + appointmentId + " for " + type + " has been deleted.");
            alert.showAndWait();
        } catch (SQLException e) { e.printStackTrace(); };
    }

    /**
     *
     * @param appointment
     */
    public static void add(Appointment appointment) {
        String query = "INSERT INTO appointments VALUES(NULL, ?, ?, ?, ?, ?, ?, NOW(), 'script', NOW()," +
                " 'script', ?, ?, ?);";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,appointment.getTitle());
            ps.setString(2, appointment.getDescription());
            ps.setString(3,appointment.getLocation());
            ps.setString(4, appointment.getType());
            ps.setTimestamp(5, Timestamp.valueOf(TimeHelper.clientToServerTime(appointment.getStartTime())));
            ps.setTimestamp(6,Timestamp.valueOf(TimeHelper.clientToServerTime(appointment.getEndTime())));
            ps.setInt(7,appointment.getCustomerId());
            ps.setInt(8,appointment.getUserId());
            ps.setInt(9,appointment.getContactId());
            int appointmentId = ps.executeUpdate();
            // Alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment Creation");
            alert.setHeaderText("Successfully created.");
            alert.setContentText("Appointment " + appointmentId + " for " + appointment.getType() + "" +
                    " has been created.");
            alert.showAndWait();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @param appointment
     */
    public static void edit(Appointment appointment) {
        int appointmentID = appointment.getAppointmentId();
        String query = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?," +
                "Last_Update=NOW(), Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID=" + appointmentID +
                ";";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ps.setString(1, appointment.getTitle());
            ps.setString(2, appointment.getDescription());
            ps.setString(3, appointment.getLocation());
            ps.setString(4, appointment.getType());
            ps.setTimestamp(5, Timestamp.valueOf(TimeHelper.clientToServerTime(appointment.getStartTime())));
            ps.setTimestamp(6, Timestamp.valueOf(TimeHelper.clientToServerTime(appointment.getEndTime())));
            ps.setInt(7, appointment.getCustomerId());
            ps.setInt(8, appointment.getUserId());
            ps.setInt(9, appointment.getContactId());
            boolean updateSuccess = ps.execute();
            if (updateSuccess) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment Edit");
                alert.setHeaderText("Successfully updated.");
                alert.setContentText("Appointment " + appointmentID + " for " + appointment.getType() +
                        " has been updated.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
