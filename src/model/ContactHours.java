package model;

import java.time.Duration;
import java.time.Month;
import java.util.Objects;

/**
 * Models a ContactHours which describes the total hours scheduled for appointments for a contact in a month.
 */
public class ContactHours {
    private Contact contact;
    private Month month;
    private Duration totalHours;

    /**
     * Constructor for ContactHours
     *
     * @param contact the contact to set
     * @param month the month to set
     * @param totalHours the totalHours to set
     */
    public ContactHours(Contact contact, Month month, Duration totalHours) {
        this.contact = contact;
        this.month = month;
        this.totalHours = totalHours;
    }

    /**
     * Gets the contact name.
     *
     * @return the contact name.
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * Sets the contact name.
     *
     * @param contact the contact name to set
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /**
     * Gets the month.
     *
     * @return the month
     */
    public Month getMonth() {
        return month;
    }

    /**
     * Sets the month.
     *
     * @param month the month to set
     */
    public void setMonth(Month month) {
        this.month = month;
    }

    /**
     * Gets the totalHours
     *
     * @return the totalHours
     */
    public Duration getTotalHours() {
        return totalHours;
    }

    /**
     * Sets the total Hours
     *
     * @param totalHours the total hours to set
     */
    public void setTotalHours(Duration totalHours) {
        this.totalHours = totalHours;
    }

    /**
     * Override of equals method: ContactHours are equal if the contact, month, and totalHours are the same.
     *
     * @param o the object to check for equality
     * @return true if the objects are equal, false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactHours that = (ContactHours) o;
        return contact.equals(that.contact) && month == that.month && totalHours.equals(that.totalHours);
    }

    /**
     * Override of the hashCode
     * @return the hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(contact, month, totalHours);
    }

}
