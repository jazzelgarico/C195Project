package dbaccess;


import controller.TimeHelper;
import dbconnection.DBConnection;
import javafx.scene.control.Alert;
import model.Appointment;
import model.AppointmentList;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DBAppointment {
    public static void addAll() {
        try {
            String query = "SELECT Appointment_ID,Title,Description,Location,Type,Start,End,Customer_ID,User_ID," +
                    "Contact_ID FROM appointments";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                LocalDateTime startTime =
                        TimeHelper.serverToClientTime(rs.getTimestamp("Start").toLocalDateTime());
                LocalDateTime endTime =
                        TimeHelper.serverToClientTime(rs.getTimestamp("End").toLocalDateTime());
                LocalDate appointmentDate = startTime.toLocalDate();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                //Create new Appointment
                Appointment appointment = new Appointment(appointmentId, title, description, location, contactId, type,
                        appointmentDate, startTime, endTime, customerId, userId);
                //Add appointment to list
                AppointmentList.add(appointment);
            }
        } catch (SQLException e) { e.printStackTrace(); }
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
        if (checkConflict(appointment)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Appointment conflict.");
            alert.setContentText("Customer has an existing appointment which conflicts with this appointment's" +
                    " start and end time.");
            alert.showAndWait();
        } else {
            String query = "INSERT INTO appointments VALUES(NULL, ?, ?, ?, ?, ?, ?, NOW(), 'script', NOW()," +
                    " 'script', ?, ?, ?);";
            try {
                PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
                ps.setString(1,appointment.getTitle());
                ps.setString(2, appointment.getDescription());
                ps.setString(3,appointment.getLocation());
                ps.setString(4, appointment.getType());
                ps.setTimestamp(5,
                        Timestamp.valueOf(TimeHelper.clientToServerTime(appointment.getStartTime())));
                ps.setTimestamp(6,
                        Timestamp.valueOf(TimeHelper.clientToServerTime(appointment.getEndTime())));
                ps.setInt(7,appointment.getCustomerId());
                ps.setInt(8,appointment.getUserId());
                ps.setInt(9,appointment.getContactId());
                ps.execute();

            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param appointment
     */
    public static void edit(Appointment appointment) {
        if (checkConflict(appointment)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Appointment conflict.");
            alert.setContentText("Customer has an existing appointment which conflicts with this appointment's" +
                    " start and end time.");
            alert.showAndWait();
        } else {
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
                ps.setTimestamp(5,
                        Timestamp.valueOf(TimeHelper.clientToServerTime(appointment.getStartTime())));
                ps.setTimestamp(6,
                        Timestamp.valueOf(TimeHelper.clientToServerTime(appointment.getEndTime())));
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
    private static boolean checkConflict(Appointment appointment) {
        boolean conflict = true;
        int customerId = appointment.getCustomerId();
        int appointmentId = appointment.getAppointmentId();
        Timestamp start = Timestamp.valueOf(TimeHelper.clientToServerTime(appointment.getStartTime()));
        Timestamp end = Timestamp.valueOf(TimeHelper.clientToServerTime(appointment.getEndTime()));

        String queryStart = "SELECT COUNT(*) from appointments WHERE ";
        String condition1 = "Customer_ID=" + customerId + " AND Appointment_ID<>" + appointmentId;
        String condition2 = "(Start>='" + start + "' AND Start<'" + end + "')";
        String condition3 = "(End>'" + start + "' AND Start<'" + end + "')";
        String query = queryStart + condition1 + " AND (" + condition2 + " OR " + condition3 + ")";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int count = rs.getInt("COUNT(*)");
            if (count==0) {conflict = false;}

        } catch (SQLException e){
            e.printStackTrace();
        }
        return conflict;
    }

    /**
     * Triggers an alert displaying if there is an appointment within 15 minutes of the user's login. If there is an
     * appointment within 15 minutes, the alert informs the user of the appointment ID, date, and time. If there is no
     * appointment within 15 minutes, the alert informs the user that there are no upcoming appointments.
     */
    public static void checkUpcoming() {
        LocalDateTime loginTime = TimeHelper.clientToServerTime(LocalDateTime.now());
        LocalDateTime loginTimePlus15 = loginTime.plusMinutes(15);
        String loginTimeFormat =  loginTime.format(DateTimeFormatter.ofPattern("yyyy-M-dd H:mm:ss"));
        String loginTimePlus15Format =  loginTimePlus15.format(DateTimeFormatter.ofPattern("yyyy-M-dd H:mm:ss"));
        String query = "SELECT Appointment_ID, Start FROM appointments WHERE Start<'" +loginTimePlus15Format +
                "' AND Start>'" + loginTimeFormat + "';";
        String appointmentInfo = "";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                LocalDateTime start =
                        TimeHelper.serverToClientTime(rs.getTimestamp("Start").toLocalDateTime());
                String startFormat = start.format(DateTimeFormatter.ofPattern("h:mma"));
                LocalDate appointmentDate = start.toLocalDate();
                String dateFormat = appointmentDate.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
                appointmentInfo = appointmentInfo + "\nAppointment ID " + appointmentId + " on " + dateFormat + " at " +
                        startFormat + ".";
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Upcoming Appointments");
        if (appointmentInfo == "") {
            alert.setHeaderText("No upcoming appointments.");
            alert.setContentText("There are no upcoming appointments.");
        }
        else {
            alert.setHeaderText("Upcoming appointments");
            alert.setContentText("These are your upcoming appointment(s): " + appointmentInfo);
        }
        alert.showAndWait();
    }


}