package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDateTime;
import java.util.Optional;

public class AppointmentList {

    private static ObservableList<Appointment> list = FXCollections.observableArrayList();

    public static void add(Appointment appointment) {
        list.add(appointment);
        MonthTypeReport.add(appointment);
        ContactHoursReport.add(appointment);
    }

    public static void remove(Appointment appointment) {
        list.remove(appointment);
        MonthTypeReport.remove(appointment);
        ContactHoursReport.remove(appointment);
    }

    public static void replace(Appointment appointment){
        Optional<Appointment> oldAppointment = list.stream()
                .filter(a -> a.getAppointmentId() == appointment.getAppointmentId())
                .findAny();
        if (oldAppointment.isEmpty()) {
        } else {
            list.set(list.indexOf(oldAppointment.get()),appointment);
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
