package model;

/**
 *
 * @author Jazzme Nadine N. Elgarico
 */

public class Customer {
    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private int divisionId;

    /**
     * No-args constructor for Customer
     */
    public Customer() {
        customerId = 0;
        customerName = "";
        address = "";
        postalCode = "";
        phoneNumber = "";
        divisionId = 0;
    }

    /**
     * Constructor for Customer
     *
     * @param customerId customerId to set
     * @param customerName customerName to set
     * @param address address to set
     * @param postalCode postalCode to set
     * @param divisionId divisionId to set
     */
    public Customer (int customerId, String customerName, String address, String postalCode, String phoneNumber, int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
    }


    /**
     * Gets the customerId.
     *
     * @return the customerId
     */
    public int getCustomerId() { return customerId; }

    /**
     * Sets the customerId.
     *
     * @param customerId the customerId to set
     */
    public void setCustomerId(int customerId) { this.customerId = customerId;}

    /**
     * Gets the customerName.
     *
     * @return the customerName
     */
    public String getCustomerName() { return customerName; }

    /**
     * Sets the customerName.
     *
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) { this.customerName = customerName;}

    /**
     * Gets the address.
     *
     * @return the address
     */
    public String getAddress() { return address; }

    /**
     * Sets the address.
     *
     * @param address the address to set
     */
    public void setAddress(String address) { this.address = address;}

    /**
     * Gets the postalCode.
     *
     * @return the postalCode
     */
    public String getPostalCode() { return postalCode; }

    /**
     * Sets the postalCode.
     *
     * @param postalCode the postalCode to set
     */
    public void setPostalCode(String postalCode) { this.postalCode = postalCode;}

    /**
     * Gets the phoneNumber.
     *
     * @return the phoneNumber
     */
    public String getPhoneNumber() { return phoneNumber; }

    /**
     * Sets the phoneNumber.
     *
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber;}

    /**
     * Gets the divisionId.
     *
     * @return the divisionId
     */
    public int getDivisionId() { return divisionId; }

    /**
     * Sets the divisionId.
     *
     * @param divisionId the divisionId to set
     */
    public void setdivisionId(int divisionId) { this.divisionId = divisionId;}



}
