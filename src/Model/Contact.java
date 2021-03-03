package Model;

import Database.DBQuery;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Contact {

    private int contactId;
    private String contactName;
    private String email;

    public Contact() { }

    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    // Setters
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    // Getters
    public int getContactId() {
        return contactId;
    }
    public String getContactName() {
        return contactName;
    }
    public String getEmail() {
        return email;
    }

    /** Gets the email corresponding to the passed-in contactId
     *
     * @param contactId - the primary key of the contact
     * @return - the String containing the email that corresponds to the passed-in contactId
     */
    public static String getEmail(int contactId) {
        String email = "";
        try {
            DBQuery.makeQuery("SELECT Email from contacts WHERE Contact_ID=" + contactId);
            ResultSet rs = DBQuery.getResult();
            while (rs.next()) {
                email = rs.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return email;
    }

}
