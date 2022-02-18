package dbaccess;

import dbconnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contains static methods to access the database contacts table.
 */
public class DBContact {
    /**
     * Provides an ObservableList which contain all contacts from the database's contacts table. Queries the database's
     * contacts table for all columns, creates a new Contact for each row, and adds each Contact to the ObservableList.
     *
     * @return an ObservableList containing all rows and columns from the database's contacts table
     */
    public static ObservableList<Contact> getAll() {
        ObservableList<Contact> list = FXCollections.observableArrayList();
        String query = "SELECT * from contacts;";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int contactId = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contact contact = new Contact(contactId, name, email);
                list.add(contact);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
