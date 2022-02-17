package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.Duration;
import java.time.Month;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ContactHoursReport {
    private static ObservableList<ContactHours> contactHoursReport = FXCollections.observableArrayList();
    private static Set<ContactHours> contactHoursSet = new HashSet<>();

    public static ObservableList<ContactHours> get() {
        return contactHoursReport;
    }

    public static void add(Appointment appointment) {
        Contact contact = ContactList.getContactFromId(appointment.getContactId());
        Month month = appointment.getStartTime().getMonth();
        Duration duration = Duration.between(appointment.getStartTime(),appointment.getEndTime());

        ContactHours chList = new ContactHours(contact, month, duration);
        ContactHours chSet = new ContactHours(contact, month, Duration.ofHours(0));
        if (contactHoursSet.contains(chSet)) {
            Optional<ContactHours> chMatch = contactHoursReport.stream()
                    .filter(ch -> ch.getContact() == contact)
                    .filter(ch -> ch.getMonth() == month)
                    .findFirst();

            int indexCh = contactHoursReport.indexOf(chMatch.get());
            Duration chHours = contactHoursReport.get(indexCh).getTotalHours();
            contactHoursReport.get(indexCh).setTotalHours(chHours.plus(duration));
        } else {
            contactHoursSet.add(chSet);
            contactHoursReport.add(chList);
        }
    }

    public static void remove(Appointment appointment) {
        Contact contact = ContactList.getContactFromId(appointment.getContactId());
        Month month = appointment.getStartTime().getMonth();
        Duration duration = Duration.between(appointment.getStartTime(),appointment.getEndTime());
        ContactHours contactHours = new ContactHours(contact, month, duration);
        Optional<ContactHours> chMatch = contactHoursReport.stream()
                .filter(ch -> ch.getContact() == contact)
                .filter(ch -> ch.getMonth() == month)
                .findFirst();

        int indexCh = contactHoursReport.indexOf(chMatch.get());
        Duration durationList = contactHoursReport.get(indexCh).getTotalHours();
        if (durationList == duration) {
            contactHoursReport.remove(indexCh);
            contactHoursSet.remove(contactHours);
        } else {
            contactHoursReport.get(indexCh).setTotalHours(durationList.minus(duration));
        }
    }


}
