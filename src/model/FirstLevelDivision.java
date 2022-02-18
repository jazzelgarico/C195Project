package model;

/**
 * Models First Level Divisions
 */
public class FirstLevelDivision {
    private int divisionId;
    private String division;
    private int countryId;

    /**
     * No-arg Constructor for FirstLevelDivision
     */
    public FirstLevelDivision() {
        divisionId = 0;
        division = "";
        countryId = 0;
    }

    /**
     * Constructor for FirstLevelDivision
     *
     * @param divisionId divisionId to set
     * @param division division name to set
     * @param countryId countryId to set
     */
    public FirstLevelDivision(int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }

    /**
     * Gets the divisionId
     *
     * @return the divisionId to get
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets the divisionId
     *
     * @param divisionId the divisionId to set
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Gets the division name
     *
     * @return the division name to get
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets the division name
     *
     * @param division the division name to set
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Gets the countryId
     *
     * @return the countryId to get
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets the countryId
     *
     * @param countryId the countryId to set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Returns the division name.
     *
     * @return
     */
    @Override
    public String toString() {
        return division;
    }

}
