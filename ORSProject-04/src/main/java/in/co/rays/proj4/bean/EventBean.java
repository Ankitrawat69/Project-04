package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * EventBean represents event information within the system.
 * It includes event details such as event code, name, organizer,
 * and event date.
 * This class extends {@link BaseBean} to include common audit fields.
 * 
 * @author Ankit Rawat
 * @version 1.0
 */
public class EventBean extends BaseBean {

    /** Unique code of the event. */
    private String eventCode;

    /** Name of the event. */
    private String eventName;

    /** Organizer of the event. */
    private String organizer;

    /** Date of the event. */
    private Date eventDate;

    /**
     * Gets the event code.
     *
     * @return eventCode
     */
    public String getEventCode() {
        return eventCode;
    }

    /**
     * Sets the event code.
     *
     * @param eventCode the event code
     */
    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    /**
     * Gets the event name.
     *
     * @return eventName
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Sets the event name.
     *
     * @param eventName the event name
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * Gets the organizer name.
     *
     * @return organizer
     */
    public String getOrganizer() {
        return organizer;
    }

    /**
     * Sets the organizer name.
     *
     * @param organizer the organizer
     */
    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    /**
     * Gets the event date.
     *
     * @return eventDate
     */
    public Date getEventDate() {
        return eventDate;
    }

    /**
     * Sets the event date.
     *
     * @param eventDate the event date
     */
    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    /**
     * Returns the unique key (ID) as a string.
     *
     * @return the key
     */
    @Override
    public String getKey() {
        return id + "";
    }

    /**
     * Returns the display value of the event,
     * typically the event name.
     *
     * @return eventName
     */
    @Override
    public String getValue() {
        return eventName;
    }
}