package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Optional;

public class AppointmentList {

    private static ObservableList<Appointment> list = FXCollections.observableArrayList();

    public static void add(Appointment appointment) {
        list.add(appointment);
        MonthTypeReport.add(appointment);
    }

    public static void remove(Appointment appointment) {
        list.remove(appointment);
        MonthTypeReport.remove(appointment);
    }

    public static void replace(Appointment appointment){
        Optional<Appointment> oldAppointment = list.stream()
                .filter(a -> a.getCustomerId() == appointment.getCustomerId())
                .findAny();
        if (oldAppointment.isEmpty()) {
        } else {
            System.out.println(oldAppointment.get());
            System.out.println(list.indexOf(oldAppointment.get()));
            list.set(list.indexOf(oldAppointment.get()),appointment);
        }
    }

    public static ObservableList<Appointment> get(){
        return list;
    }

}
