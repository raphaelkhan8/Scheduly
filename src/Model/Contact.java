package Model;

import Database.DBQuery;

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

    /** Setter
     *
     * @param contactId - the int value which the Contact's Id will be set to
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
    /** Setter
     *
     * @param contactName - the String value which the Contact's name will be set to
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    /** Setter
     *
     * @param email - the String value which the Contact's email will be set to
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /** Getter
     *
     * @return - the int value corresponding to the Contact's id
     */
    public int getContactId() {
        return contactId;
    }
    /** Getter
     *
     * @return - the int value corresponding to the Contact's name
     */
    public String getContactName() {
        return contactName;
    }
    /** Getter
     *
     * @return - the int value corresponding to the Contact's email
     */
    public String getEmail() {
        return email;
    }

    /** Gets the Contact object corresponding to the passed-in contactId
     *
     * @param contactId - the primary key of the contact
     * @return - the Contact object corresponding to the passed-in contactId
     */
    public static Contact getEmail(int contactId) {
        Contact contact = new Contact();
        try {
            DBQuery.makeQuery("SELECT * from contacts WHERE Contact_ID=" + contactId);
            ResultSet rs = DBQuery.getResult();
            while (rs.next()) {
                String contactType = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                contact.setContactId(contactId);
                contact.setContactName(contactType);
                contact.setEmail(email);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contact;
    }

}
