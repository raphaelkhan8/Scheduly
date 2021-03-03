package Utils;

import Database.DBQuery;

import java.sql.ResultSet;

/** Commonly used methods to query the database */
public class DataRetriever {

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
