package Model;

public class Country {

    private static int countryId;
    private static String countryName;

    // Setters
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    // Getters
    public int getCountryId() {
        return countryId;
    }
    public String getCountryName() {
        return countryName;
    }

}
