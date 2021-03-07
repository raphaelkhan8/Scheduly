package Utils;

import Controller.LoginController;
import Database.DBQuery;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.TimeZone;

/** Commonly used properties and methods */
public class DataRetriever {

    /** containers containing static data (for combo boxes)) */
    private static ObservableList<String> contactTypes = FXCollections.observableArrayList("Primary", "Work", "School");
    private static ObservableList<String> appointmentTypes = FXCollections.observableArrayList("Virtual", "In-Person", "Telephone", "Whatever works");
    private static ObservableList<String> times = FXCollections.observableArrayList("08:00:00", "09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00", "14:00:00", "15:00:00", "16:00:00", "17:00:00", "18:00:00", "19:00:00", "20:00:00", "21:00:00", "22:00:00");
    private static ObservableList<String> monthOptions = FXCollections.observableArrayList("January","February","March","April","May","June","July","August","September","October","November","December");
    private static ObservableList<String> weekOptions = FXCollections.observableArrayList("Last Week", "This Week", "Next Week");
    private static ObservableList<String> reportTypes = FXCollections.observableArrayList("Number of appointment types by month", "Schedule of each contact type", "Customers ranked by number of appointments");

    /** Getters for above properties */
    public static ObservableList<String> getContactTypes() { return contactTypes; }
    public static ObservableList<String> getAppointmentTypes() {
        return appointmentTypes;
    }
    public static ObservableList<String> getTimes() {
        return times;
    }
    public static ObservableList<String> getMonthOptions() { return monthOptions; }
    public static ObservableList<String> getWeekOptions() { return weekOptions; }
    public static ObservableList<String> getReportTypes() { return reportTypes; }

    /**
     * Gets divisionID and divisionName for the passed-in country from the database
     *
     * @param countryId - the int corresponding to the selected country
     * @return - the ResultSet contains the country's divisionId (int) and divisionName (String)
     */
    public static ResultSet getDivisionInfo(int countryId) {
        DBQuery.makeQuery("SELECT Division_ID, Division from first_level_divisions WHERE Country_ID = " + countryId);
        return DBQuery.getResult();
    }

    /** returns sorted Appointments by week or month
     *
     * @param durationType - the int corresponding to week or month (0 for week; 1 for month)
     * @param durationPeriod - the String value corresponding to the week or month to filter by
     * @return
     */
    public static ObservableList<Appointment> getAppointmentsByDuration (int durationType, String durationPeriod) {
        ObservableList<Appointment> appointmentsByDuration = FXCollections.observableArrayList();

        try {
            ArrayList<Integer> selectedAppointmentIDsByDuration = new ArrayList<>();
            String dbQuery = "";

            if (durationType == 0) {
                int week = Integer.parseInt(durationPeriod);
                dbQuery = "SELECT appointment_ID from appointments where year(start) = YEAR(date_add(curdate(), interval " + week + " WEEK)) and weekofyear(start) = weekofyear(date_add(curdate(),interval " + week + " WEEK));";
            } else {
                dbQuery = "SELECT appointment_ID FROM appointments WHERE monthname(start) = '" + durationPeriod + "' ORDER BY EXTRACT(DAY FROM start)";
            }

            DBQuery.makeQuery(dbQuery);
            ResultSet monthlyAppointments = DBQuery.getResult();

            while (monthlyAppointments.next()) {
                selectedAppointmentIDsByDuration.add(monthlyAppointments.getInt(1));
            }

            for (int aptId : selectedAppointmentIDsByDuration) {
                DBQuery.makeQuery("SELECT a.*, cust.Customer_Name, c.Email FROM appointments a INNER JOIN customers cust ON " +
                        "a.Customer_ID=cust.Customer_ID LEFT JOIN contacts c ON a.Contact_ID=c.Contact_ID WHERE appointment_ID =" + aptId +
                        " GROUP BY a.Contact_ID, MONTH(start), start");
                ResultSet selectAppointment = DBQuery.getResult();
                while (selectAppointment.next()) {

                    int appointmentId = selectAppointment.getInt(1);
                    String title = selectAppointment.getString(2);
                    String description = selectAppointment.getString(3);
                    String location = selectAppointment.getString(4);
                    String type = selectAppointment.getString(5);
                    String start = selectAppointment.getString(6);
                    String end = selectAppointment.getString(7);
                    int customerId = selectAppointment.getInt("Customer_ID");
                    int userId = LoginController.getCurrentUser();
                    int contactId = selectAppointment.getInt("Contact_ID");
                    String customerName = selectAppointment.getString("cust.Customer_Name");
                    String email = selectAppointment.getString("c.Email");

                    Appointment apt = new Appointment(appointmentId, title, description, location, type, start, end, customerId,
                            userId, contactId, customerName, email);
                    appointmentsByDuration.add(apt);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentsByDuration;
    }

    /** Converts the passed-in Local time to UTC for database standardization
     *
     * @param time - the String corresponding to a DateTime in Local Time
     * @return - a String value in UTC time
     */
    public static String convertLocalTimeToUTC(String time) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        ZoneId UTCZoneID = ZoneId.of("UTC");

        LocalDateTime dateTime = LocalDateTime.parse(time, format);
        ZonedDateTime zonedTimeLocal = dateTime.atZone(localZoneId);
        ZonedDateTime convertedTime = zonedTimeLocal.withZoneSameInstant(UTCZoneID);

        String finalTime = convertedTime.toLocalDateTime().toString().replace("T", " ");

        return finalTime;
    }

    /** Converts the passed-in UTC time to the user's local Time Zone equivalent
     *
     * @param time - the String corresponding to a DateTime in UTC
     * @return - a String value in Local time
     */
    public static String convertUTCTimeToLocal(String time) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        ZoneId UTCZoneID = ZoneId.of("UTC");

        LocalDateTime dateTime = LocalDateTime.parse(time, format);
        ZonedDateTime zonedTimeLocal = dateTime.atZone(UTCZoneID);
        ZonedDateTime convertedTime = zonedTimeLocal.withZoneSameInstant(localZoneId);

        String finalTime = convertedTime.toLocalDateTime().toString().replace("T", " ");

        return finalTime;
    }

}
