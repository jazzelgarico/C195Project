package model;

/**
 *
 * @author Jazzme Nadine N. Elgarico
 */

public class Contact {
    private int contactId;
    private String contactName;
    private String emailAddress;

    /**
     * No-args constructor for Contact
     */
    public Contact() {
        contactId = 0;
        contactName = "";
        emailAddress = "";
    }

    /**
     * Constructor for Contact
     *
     * @param contactId contactId to set
     * @param contactName contactName to set
     * @param emailAddress emailAddress to set
     */
    public Contact(int contactId, String contactName, String emailAddress) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.emailAddress = emailAddress;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getContactId() { return contactId; }

    /**
     * Sets the contactId.
     *
     * @param contactId the contactId to set
     */
    public void setContactId(int contactId) { this.contactId = contactId;}

    /**
     * Gets the contactName.
     *
     * @return the contactName
     */
    public String getContactName() { return contactName; }

    /**
     * Sets the contactName.
     *
     * @param contactName the contactName to set
     */
    public void setContactName(String contactName) { this.contactName = contactName;}

    /**
     * Gets the emailAddress.
     *
     * @return the emailAddress
     */
    public String getEmailAddress() { return emailAddress; }

    /**
     * Sets the emailAddress.
     *
     * @param emailAddress the contactName to set
     */
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress;}

    /**
     * Override of toString: returns contactName.
     *
     * @return Contact's contactName
     */
    @Override
    public String toString() {
        return contactName;
    }

}
