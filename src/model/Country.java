package model;

/**
 * Models a country
 */
public class Country {
    private int countryId;
    private String country;

    /**
     * No-arg constructor for Country
     */
    public Country() {
        countryId = 0;
        country = "";
    }

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

    /**
     * Gets the countryId
     *
     * @return the countryId
     */
    public int getCountryId(){
        return countryId;
    }

    /**
     * Override of toString: returns the country name.
     *
     * @return the name of the country
     */
    @Override
    public String toString() {
        return country;
    }
}
