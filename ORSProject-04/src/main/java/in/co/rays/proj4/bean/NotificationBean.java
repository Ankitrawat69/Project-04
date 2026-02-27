package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * NotificationBean represents notification information within the system.
 * It includes details such as notification code, message, recipient,
 * sent time, and notification status.
 * This class extends {@link BaseBean} to include common audit fields.
 *
 * @author Ankit Rawat
 * @version 1.0
 */
public class NotificationBean extends BaseBean {

    /** Unique code of the notification. */
    private String notificationCode;

    /** Message content of the notification. */
    private String message;

    /** Recipient of the notification. */
    private String sentTo;

    /** Date and time when notification was sent. */
    private Date sentTime;

    /** Status of the notification (e.g., Sent, Pending, Failed). */
    private String notificationStatus;

    /**
     * Gets the notification code.
     *
     * @return notificationCode
     */
    public String getNotificationCode() {
        return notificationCode;
    }

    /**
     * Sets the notification code.
     *
     * @param notificationCode the unique notification code
     */
    public void setNotificationCode(String notificationCode) {
        this.notificationCode = notificationCode;
    }

    /**
     * Gets the message content.
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message content.
     *
     * @param message the notification message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the recipient of the notification.
     *
     * @return sentTo
     */
    public String getSentTo() {
        return sentTo;
    }

    /**
     * Sets the recipient of the notification.
     *
     * @param sentTo the recipient
     */
    public void setSentTo(String sentTo) {
        this.sentTo = sentTo;
    }

    /**
     * Gets the sent date and time.
     *
     * @return sentTime
     */
    public Date getSentTime() {
        return sentTime;
    }

    /**
     * Sets the sent date and time.
     *
     * @param sentTime the date and time when notification was sent
     */
    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }

    /**
     * Gets the notification status.
     *
     * @return notificationStatus
     */
    public String getNotificationStatus() {
        return notificationStatus;
    }

    /**
     * Sets the notification status.
     *
     * @param notificationStatus the status of notification
     */
    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
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
     * Returns the display value of the notification,
     * typically the notification code.
     *
     * @return notificationCode
     */
    @Override
    public String getValue() {
        return notificationCode;
    }
}