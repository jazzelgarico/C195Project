package model;

/**
 *
 * @author Jazzme Nadine N. Elgarico
 */

public class Appointment {
    //Types of Appointments for Organization
    enum AppointmentType {
        RETIREMENT, LIFEINSURANCE, ESTATEPLANNING, WEALTHMANAGEMENT
    }

    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private AppointmentType type;
    private int customerId;
    private int userID;
    private int contactId;

}

