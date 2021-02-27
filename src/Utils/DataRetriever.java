package Utils;

import Database.DBQuery;

import java.sql.ResultSet;
import java.sql.SQLException;

/** Commonly used methods to query the database */
public class DataRetriever {

    /** Gets all countries (Id and Name) from the database
     *
     * @return - the ResultSet contains all of the countries' CountryId (int) and CountryName (String)
     * @throws SQLException
     */
    public static ResultSet getAllCountries() throws SQLException {
        DBQuery.makeQuery("SELECT Country_ID, Country from countries");
        return DBQuery.getResult();
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

    /** Gets countryId for the passed-in country from the database
     *
     * @param countryName - the String corresponding to the selected country's name
     * @return - the int corresponds to the passed in country's Id
     * @throws SQLException
     */
    public static int getCountryId(String countryName) throws SQLException {
        int countryId = 0;
        DBQuery.makeQuery("SELECT c.Country_ID from countries AS c WHERE c.Country = '" + countryName + "'");
        ResultSet rs = DBQuery.getResult();
        if (rs.next()) {
            countryId = rs.getInt(1);
        }
        return countryId;
    }

}
