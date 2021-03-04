package Utils;

import Database.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

/** Commonly used properties and methods */
public class DataRetriever {

    /** containers used to populate comboboxes */
    private static ObservableList<String> contactTypes = FXCollections.observableArrayList("Primary", "Work", "School");
    private static ObservableList<String> appointmentTypes = FXCollections.observableArrayList("Virtual", "In-Person", "Telephone", "Whatever works");
    private static ObservableList<String> times = FXCollections.observableArrayList("08:00:00","09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00", "14:00:00", "15:00:00", "16:00:00", "17:00:00", "18:00:00", "19:00:00", "20:00:00", "21:00:00", "22:00:00");

    /** gets all of the Contact Types to populate ContactType combo-boxes
     *
     * @return - an ObservableList of Strings
     */
    public static ObservableList<String> getContactTypes() {
        return contactTypes;
    }

    /** gets all of the Appointment Types to populate AppointmentType combo-boxes
     *
     * @return - an ObservableList of Strings
     */
    public static ObservableList<String> getAppointmentTypes() {
        return appointmentTypes;
    }

    /** gets all of the Times to populate Start/End combo-boxes
     *
     * @return - an ObservableList of Strings
     */
    public static ObservableList<String> getTimes() {
        return times;
    }

    /** Gets divisionID and divisionName for the passed-in country from the database
     *
     * @param countryId - the int corresponding to the selected country
     * @return - the ResultSet contains the country's divisionId (int) and divisionName (String)
     */
    public static ResultSet getDivisionInfo(int countryId) {
        DBQuery.makeQuery("SELECT Division_ID, Division from first_level_divisions WHERE Country_ID = " + countryId);
        return DBQuery.getResult();
    }

}
