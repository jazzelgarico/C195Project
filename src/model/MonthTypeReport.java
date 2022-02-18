package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.Month;
import java.util.Optional;

/**
 * Models the month-type report which lists the total number of appointments by type and month
 */
public class MonthTypeReport {

    private static ObservableList<MonthTypeCount> mtcReport = FXCollections.observableArrayList();

    /**
     * Gets the mtcReport.
     *
     * @return the list of MonthTypeCount to get
     */
    public static ObservableList<MonthTypeCount> get() {
        return mtcReport;
    }

    /**
     * Updates mtcReport with the addition of an appointment. If the month-type is already in mtcReport, increases the
     * count by one. If the month-type is not already in mtcReport, adds it to mtcReport with an initial count of one.
     *
     * @param appointment the appointment to add to mtcReport
     */
    public static void add(Appointment appointment) {
        String type = appointment.getType();
        Month month = appointment.getStartTime().getMonth();
        MonthTypeCount initialMtc = new MonthTypeCount(type,month,1);
        Optional<MonthTypeCount> mtcList = mtcReport.stream()
                .filter(mtc -> mtc.getType().equals(type))
                .filter(mtc -> mtc.getMonth() == month)
                .findAny();
        if (mtcList.isPresent()) {
            int indexMtc = mtcReport.indexOf(mtcList.get());
            int countMtc = mtcReport.get(indexMtc).getCount();
            mtcReport.get(indexMtc).setCount(countMtc + 1);
        } else {
            mtcReport.add(initialMtc);
        }
    }

    /**
     * Adds the updated appointment to mtcReport and removes the original appointment from mtcReport.
     *
     * @param original the appointment to edit
     * @param updated the new appointment to replace the original
     */
    public static void edit(Appointment original,Appointment updated) {
        add(updated);
        remove(original);
    }

    /**
     * Updates the mtcReport with the removal of an appointment.If the month-type count is one, removes the month-type
     * from mtcReport. If the month-type count is not one, reduces the month-type count by one.
     *
     * @param appointment
     */
    public static void remove(Appointment appointment) {
        String type = appointment.getType();
        Month month = appointment.getStartTime().getMonth();
        MonthTypeCount setMtc = new MonthTypeCount(type,month,1);
        Optional<MonthTypeCount> mtcList = mtcReport.stream()
                .filter(mtc -> mtc.getType().equals(type))
                .filter(mtc -> mtc.getMonth() == month)
                .findAny();
        int indexMtc = mtcReport.indexOf(mtcList.get());
        int countMtc = mtcReport.get(indexMtc).getCount();
        if (countMtc == 1) {
            mtcReport.remove(indexMtc);
        } else {
            mtcReport.get(indexMtc).setCount(countMtc - 1);
        }
    }

}
