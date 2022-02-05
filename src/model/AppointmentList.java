package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppointmentList {
    private static ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    /**
     *
     * @param newAppointment
     */
    public static void addAppointment(Appointment newAppointment) {
        appointmentList.add(newAppointment);
    }

    /**
     *
     * @return
     */
    public static ObservableList<Appointment> getAppointmentList() {
        return appointmentList;
    }


}
