package Model;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

}
