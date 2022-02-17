package model;

import java.time.Duration;
import java.time.Month;
import java.util.Objects;

public class ContactHours {
    private Contact contact;
    private Month month;
    private Duration totalHours;

    public ContactHours(Contact contact, Month month, Duration totalHours) {
        this.contact = contact;
        this.month = month;
        this.totalHours = totalHours;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Duration getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Duration totalHours) {
        this.totalHours = totalHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactHours that = (ContactHours) o;
        return contact.equals(that.contact) && month == that.month && totalHours.equals(that.totalHours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contact, month, totalHours);
    }

}
