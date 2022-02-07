package model;

public class Country {
    private int countryId;
    private String country;

    /**
     * Constructor for Country
     *
     * @param countryId countryId to set
     * @param country country name to set
     */
    public Country(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }
}
