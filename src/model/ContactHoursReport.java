package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.Duration;
import java.time.Month;
import java.util.Optional;

/**
 * Models the contact-hours report which lists the number of hours each contact has scheduled for each month.
 */
public class ContactHoursReport {
    private static ObservableList<ContactHours> contactHoursReport = FXCollections.observableArrayList();

    public static ObservableList<ContactHours> get() {
        return contactHoursReport;
    }

    /**
     * Updates contactHoursReport with the addition of an appointment. If a ContactHours already exists with matching
     * contact and month in contactHoursReport, adds the new appointment's duration to the existing ContactHours'
     * duration. If a ContactHours with matching contact and month cannot be found, adds a new ContactHours using the
     * appointment's contact, month, and duration.
     *
     * @param appointment the appointment to add to contactHoursReport
     */
    public static void add(Appointment appointment) {
        Contact contact = ContactList.getContactFromId(appointment.getContactId());
        Month month = appointment.getStartTime().getMonth();
        Duration duration = Duration.between(appointment.getStartTime(),appointment.getEndTime());

        ContactHours chList = new ContactHours(contact, month, duration);

        Optional<ContactHours> chMatch = contactHoursReport.stream()
                .filter(ch -> ch.getContact() == contact)
                .filter(ch -> ch.getMonth() == month)
                .findFirst();

        if (chMatch.isPresent()) {
            int indexCh = contactHoursReport.indexOf(chMatch.get());
            Duration chHours = contactHoursReport.get(indexCh).getTotalHours();
            contactHoursReport.get(indexCh).setTotalHours(chHours.plus(duration));
        } else {
            contactHoursReport.add(chList);
        }

    }

    /**
     * Updates the contactHoursReport with the addition of the updated appointment and the removal of the original
     * appointment.
     *
     * @param original the original appointment to be edited
     * @param updated the updated appointment to replace the original
     */
    public static void edit(Appointment original,Appointment updated) {
       add(updated);
       remove(original);
    }

    /**
     * Updates contactHoursReport with the deletion of an appointment. If the ContactHours with matching contact and
     * month has the same duration as the appointment's duration, the ContactHours is removed from the ContactHours
     * report. If the ContactHours with matching contact and month does not have the same duration as the
     * appointment's duration, the appointment's duration is subtracted from the ContactHours' duration.
     *
     * @param appointment the appointment to remove from contactHoursReport
     */
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
        Duration durationCh = contactHoursReport.get(indexCh).getTotalHours();

        if (durationCh == duration) {
            contactHoursReport.remove(indexCh);
        } else {
            contactHoursReport.get(indexCh).setTotalHours(durationCh.minus(duration));
        }
    }

}
