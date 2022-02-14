package model;

import dbaccess.DBContact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Optional;

public class ContactList {
    private static ObservableList<Contact> list = FXCollections.observableArrayList();

    public static void set() {
        list = DBContact.getAll();
    }

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
