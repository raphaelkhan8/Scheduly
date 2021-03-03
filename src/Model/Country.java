package Model;

import Database.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Country {

    private static int countryId;
    private static String countryName;

    public Country() { }

    public Country(int countryID, String countryName) {
        this.countryId = countryID;
        this.countryName = countryName;

    }

    // Setters
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
    public void setCountryName(String countryName) { this.countryName = countryName; }

    // Getters
    public int getCountryId() {
        return countryId;
    }
    public String getCountryName() {
        return countryName;
    }

    /** Gets all countries (Id and Name) from the database
     *
     * @return - the ResultSet contains all of the countries' CountryId (int) and CountryName (String)
     * @throws SQLException
     */
    public static ResultSet getAllCountries() throws SQLException {
        DBQuery.makeQuery("SELECT Country_ID, Country from countries");
        return DBQuery.getResult();
    }

    /** Gets countryId for the passed-in country from the database
     *
     * @param countryName - the String corresponding to the selected country's name
     * @return - the int corresponds to the passed in country's Id
     * @throws SQLException
     */
    public static int getCountryId(String countryName) throws SQLException {
        int countryId = 0;
        DBQuery.makeQuery("SELECT Country_ID from countries WHERE Country = '" + countryName + "'");
        ResultSet rs = DBQuery.getResult();
        if (rs.next()) {
            countryId = rs.getInt(1);
        }
        return countryId;
    }

}
