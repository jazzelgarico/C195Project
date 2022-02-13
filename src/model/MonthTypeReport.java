package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashSet;
import java.util.Set;

public class MonthTypeReport {

    private static ObservableList<MonthTypeCount> mtcReport = FXCollections.observableArrayList();
    private static Set<MonthTypeCount> mtcReportSet = new HashSet<>();

    public static ObservableList<MonthTypeCount> get() {
        return mtcReport;
    }

    public static void add(Appointment appointment) {
        MonthTypeCount mtc = new MonthTypeCount(appointment.getType(),appointment.getStartTime().getMonth(),1);
        if (mtcReportSet.contains(mtc)) {
            int indexMtc = mtcReport.indexOf(mtc);
            int countMtc = mtcReport.get(indexMtc).getCount();
            mtcReport.get(indexMtc).setCount(countMtc+1);
        } else {
            mtcReportSet.add(mtc);
            mtcReport.add(mtc);
        }
    }

    public static void remove(Appointment appointment) {
        MonthTypeCount mtc = new MonthTypeCount(appointment.getType(),appointment.getStartTime().getMonth(),1);
        int indexMtc = mtcReport.indexOf(mtc);
        int countMtc = mtcReport.get(indexMtc).getCount();
        if (countMtc == 1) {
            mtcReport.remove(indexMtc);
            mtcReportSet.remove(mtc);
        } else {
            mtcReport.get(indexMtc).setCount(countMtc-1);
        }
    }

}
