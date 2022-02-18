package dbaccess;

import utility.TimeHelper;
import dbconnection.DBConnection;
import javafx.scene.control.Alert;
import model.Appointment;
import model.AppointmentList;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Contains static methods to access the appointments table and update the Appointment and AppointmentList model.
 */
public class DBAppointment {

    /**
     * Adds all the appointments from the appointments table in the database to AppointmentList.
     * <p>
     * Queries the database for Appointment_ID, Title, Description Location, TYpe, Start, End, Customer_ID, User_ID,
     * and Contact_ID from appointments and uses those values to creates a new Appointment and adds that new Appointment
     * to AppointmentList
     *
     */
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
     * Adds the given appointment to the appointmentList and inserts the appointment into the database.
     * <p>
     * This method checks for conflicts first. If a conflict is detected, it shows an error alert. If a conflict is not
     * detected, the appointment is added to the AppointmentList and into the appointments table in the database.
     *
     * @param appointment the appointment to add to AppointmentList and insert into the database
     */
    public static void add(Appointment appointment) {
        if (AppointmentList.checkConflict(appointment)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Conflict");
            alert.setHeaderText("Appointment could not be created.");
            alert.setContentText("Customer has an existing appointment which conflicts " +
                    "with this appointment's start and end time.");
            alert.showAndWait();
        } else {
            String query = "INSERT INTO appointments VALUES(NULL, ?, ?, ?, ?, ?, ?, NOW(), 'script', NOW()," +
                    " 'script', ?, ?, ?);";
            try {
                PreparedStatement ps = DBConnection.getConnection().
                        prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
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
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                int id = rs.getInt(1);
                appointment.setAppointmentId(id);
                AppointmentList.add(appointment);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Edits the given appointment to the appointmentList and updates the appointment in the database.
     * <p>
     * This method checks for conflicts first. If a conflict is detected, it shows an error alert. If a conflict is not
     * detected, the appointment is edited in the AppointmentList and updated in the appointments table in the
     * database.
     *
     * @param appointment the appointment to be edited
     */
    public static void edit(Appointment appointment) {
        if (AppointmentList.checkConflict(appointment)) {
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
                if (ps.executeUpdate() > 0) {
                    AppointmentList.replace(appointment);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Deletes the appointment matching the given appointmentId from the database and in AppointmentList.
     * <p>
     * Gives an alert when appointment is successfully deleted with the appointmentId and Type.
     *
     * @param appointment appointment to delete
     */
    public static void delete(Appointment appointment) {
        int appointmentId = appointment.getAppointmentId();
        String type = appointment.getType();
        String query =  "DELETE FROM appointments WHERE Appointment_ID=" + appointmentId;
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            if (ps.executeUpdate() > 0) {
                AppointmentList.remove(appointment);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment Deletion");
                alert.setHeaderText("Deletion successful.");
                alert.setContentText("Appointment with ID " + appointmentId + " for " + type + " has been deleted.");
                alert.showAndWait();
            }
        } catch (SQLException e) { e.printStackTrace(); };
    }

    /**
     * Triggers an alert displaying if there is an appointment within 15 minutes of the user's login.
     * <p>
     * If there is an appointment within 15 minutes, the alert informs the user of the appointment ID, date, and time.
     * If there is no appointment within 15 minutes, the alert informs the user that there are no upcoming appointments.
     *
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
