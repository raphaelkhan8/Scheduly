package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

}
