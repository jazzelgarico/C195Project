package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Models an appointment list. AppointmentList should match the rows in the appointment table in the database.
 */
public class AppointmentList {

    private static ObservableList<Appointment> list = FXCollections.observableArrayList();

    /**
     * Adds the appointment to AppointmentList and updates MonthTypeReport and ContactHoursReport
     *
     * @param appointment the appointment to add
     */
    public static void add(Appointment appointment) {
        list.add(appointment);
        MonthTypeReport.add(appointment);
        ContactHoursReport.add(appointment);
    }

    /**
     * Removes the appointment from AppointmentList and updates MonthTypeReport and ContactHoursReport
     *
     * @param appointment the appointment to remove
     */
    public static void remove(Appointment appointment) {
        list.remove(appointment);
        MonthTypeReport.remove(appointment);
        ContactHoursReport.remove(appointment);
    }

    /**
     * Updates the appointment in AppointmentList and updates MonthTypeReport and ContactHoursReport.
     * <p>
     * Finds the original appointment in AppointmentList, retrieves its index, and sets the updatedAppointment to that
     * index.
     * <p>
     * Gives an alert if appointment cannot be found.
     *
     * @param appointment the appointment to update
     */
    public static void replace(Appointment appointment){
        Appointment original = getById(appointment.getAppointmentId());
        if (original != null) {
            list.set(list.indexOf(original),appointment);
            MonthTypeReport.edit(original,appointment);
            ContactHoursReport.edit(original,appointment);

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Edit Message");
            alert.setHeaderText("Appointment cannot be edited.");
            alert.setContentText("The appointment you are trying to edit doesn't exist.");
            alert.showAndWait();
        }
    }

    /**
     * Gets the appointment from AppointmentList with the given id. If no matching appointment is found, returns null.
     *
     * @param id the id of the appointment to return
     * @return the appointment with the matching id
     */
    public static Appointment getById(int id) {
        Optional<Appointment> appointment = list.stream().filter(a -> a.getAppointmentId() == id).findAny();
        if(appointment.isPresent()){
            return appointment.get();
        } else{
            return null;
        }
    }

    public static ObservableList<Appointment> get(){
        return list;
    }

    /**
     * Checks for any appointment time conflicts.
     * <p>
     * An appointment conflict is defined to be when (1) an appointment's start time is equal to or greater than an
     * existing appointment's start time but less than that existing appointment's end time or (2) an appointment's
     * end time is greater than an existing appointment's start time and the appointment's start time is before the
     * existing appointment's end time. Appointment conflicts can happen with a new appointment or an edited appointment
     * with new start and end times, but an edited appointment cannot be in conflict with itself.
     *
     * @param appointment the appointment to check for conflict
     * @return true if there is conflict, false if there is no conflict
     */
    public static boolean checkConflict(Appointment appointment) {
        int customerId = appointment.getCustomerId();
        int appointmentId = appointment.getAppointmentId();
        LocalDateTime start = appointment.getStartTime();
        LocalDateTime end = appointment.getEndTime();

        Optional<Appointment> startConflict = get().stream()
                .filter(a -> a.getCustomerId() == customerId)
                .filter(a -> a.getAppointmentId() != appointmentId)
                .filter(a -> (start.isAfter(a.getStartTime()) || start.isEqual(a.getStartTime()))
                        && start.isBefore(a.getEndTime()))
                .findAny();

        Optional<Appointment> endConflict = get().stream()
                .filter(a -> a.getCustomerId() == customerId)
                .filter(a -> a.getAppointmentId() != appointmentId)
                .filter(a -> end.isAfter(a.getStartTime()) && start.isBefore(a.getEndTime()))
                .findAny();

        return startConflict.isPresent() || endConflict.isPresent();
    }
}
