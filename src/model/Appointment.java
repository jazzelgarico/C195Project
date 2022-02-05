package model;

import java.time.LocalDate;
import java.time.LocalTime;

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
    private int contactId;
    private AppointmentType type;
    private LocalDate appointmentDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private int customerId;
    private int userId;


    /**
     * Constructor for Appointment
     * @param appointmentId appointmentID to set
     * @param title title to set
     * @param description description to set
     * @param location location to set
     * @param contactId contactId to set
     * @param type appointment type to set
     * @param appointmentDate appointment date to set
     * @param startTime start time to set
     * @param endTime end time to set
     * @param customerId customerId to set
     * @param userId userId to set
     */
    public Appointment(int appointmentId, String title, String description, String location, int contactId,
                       AppointmentType type, LocalDate appointmentDate, LocalTime startTime, LocalTime endTime,
                       int customerId, int userId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactId = contactId;
        this.type = type;
        this.appointmentDate = appointmentDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerId = customerId;
        this.userId = userId;
    }



}

