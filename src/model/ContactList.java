package model;

import dbaccess.DBContact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Optional;

/**
 * Models the contact list. ContactList should match the rows of the contacts table in the database.
 */
public class ContactList {
    private static ObservableList<Contact> list = FXCollections.observableArrayList();

    /**
     * Sets the list with the rows of contacts in the database
     */
    public static void set() {
        list = DBContact.getAll();
    }

    /**
     * Gets the ObservableList of Contacts.
     *
     * @return the list of contacts
     */
    public static ObservableList<Contact> get() {
        return list;
    }

    /**
     * Given a contactId, returns a Contact with information from associated contact from the database.
     *
     * @param contactId contactId to find
     * @return Contact with associated contactId
     */
    public static Contact getContactFromId(int contactId) {
        Optional<Contact> optionalContact = list.stream().filter(c -> c.getContactId() == contactId).findFirst();
        return optionalContact.get();
    }
}
