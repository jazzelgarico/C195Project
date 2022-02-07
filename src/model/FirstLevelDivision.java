package model;

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

    public int getDivisionId() {
        return divisionId;
    }

    public int getCountryId() {
        return countryId;
    }
    @Override
    public String toString() {
        return division;
    }
}
