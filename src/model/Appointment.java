package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Appointment {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private int contactId;
    private String type;
    private LocalDate appointmentDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int customerId;
    private int userId;

    /**
     * Constructor for Appointment without appointmentId
     *
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
    public Appointment(String title, String description, String location, int contactId,
                       String type, LocalDate appointmentDate, LocalDateTime startTime, LocalDateTime endTime,
                       int customerId, int userId) {
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

    /**
     * Constructor for Appointment
     *
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
                       String type, LocalDate appointmentDate, LocalDateTime startTime, LocalDateTime endTime,
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

    /**
     * Gets the appointmentId.
     *
     * @return the appointmentId
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Sets the appointmentId.
     *
     * @param appointmentId appointmentId to set
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title
     *
     * @param title title to set
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description description to set
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Gets the location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location
     *
     * @param location location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the contactId
     *
     * @return the contactId
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets the contactId
     *
     * @param contactId contactId to set
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Gets the type
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type
     *
     * @param type type to set
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * Gets the appointmentDate
     *
     * @return the appointmentDate
     */
    public LocalDate getAppointmentDate() {
       return appointmentDate; 
    }

    /**
     * Sets the appointmentDate
     * 
     * @param appointmentDate appointmentDate to set
     */
    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    /**
     * Gets the startTime
     * 
     * @return the startTime
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time
     * 
     * @param startTime startTime to set
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the endTime
     *
     * @return the endTime
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time
     *
     * @param endTime endTime to set
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the customerId
     *
     * @return the customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the customerId
     *
     * @param customerId the customerId to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets the userId
     *
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the userId
     *
     * @param userId userId to set
     */
    public void getUserId(int userId) {
        this.userId = userId;
    }


}

