package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * EventBookingBean represents booking information of an event.
 * It contains details such as event name, booking date, and seats booked.
 * This class extends {@link BaseBean} to include common audit fields.
 * 
 * @author Ankit Rawat
 * @version 1.0
 */
public class EventBookingBean extends BaseBean {

    /** Name of the event. */
    private String eventName;

    /** Date when booking is made. */
    private Date bookingDate;

    /** Number of seats booked. */
    private Integer seats;

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
     * Gets the booking date.
     * 
     * @return bookingDate
     */
    public Date getBookingDate() {
        return bookingDate;
    }

    /**
     * Sets the booking date.
     * 
     * @param bookingDate the booking date
     */
    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    /**
     * Gets number of seats booked.
     * 
     * @return seats
     */
    public Integer getSeats() {
        return seats;
    }

    /**
     * Sets number of seats booked.
     * 
     * @param seats the number of seats
     */
    public void setSeats(Integer seats) {
        this.seats = seats;
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
     * Returns the display value of the booking,
     * typically the event name.
     * 
     * @return eventName
     */
    @Override
    public String getValue() {
        return eventName;
    }
}