package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * DispatchBean represents dispatch information in the system.
 * It includes details such as dispatch date, status, and courier name.
 * This class extends {@link BaseBean} to include common audit fields.
 * 
 * @author Ankit Rawat
 * @version 1.0
 */
public class DispatchBean extends BaseBean {

    /** Unique ID for dispatch */
    private Long dispatchId;

    /** Date when the order is dispatched */
    private Date dispatchDate;

    /** Current status of the dispatch */
    private String status;

    /** Name of the courier service */
    private String courierName;

    /**
     * Gets the dispatch ID.
     * 
     * @return dispatchId
     */
    public Long getDispatchId() {
        return dispatchId;
    }

    /**
     * Sets the dispatch ID.
     * 
     * @param dispatchId the dispatch ID
     */
    public void setDispatchId(Long dispatchId) {
        this.dispatchId = dispatchId;
    }

    /**
     * Gets the dispatch date.
     * 
     * @return dispatchDate
     */
    public Date getDispatchDate() {
        return dispatchDate;
    }

    /**
     * Sets the dispatch date.
     * 
     * @param dispatchDate the dispatch date
     */
    public void setDispatchDate(Date dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    /**
     * Gets the dispatch status.
     * 
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the dispatch status.
     * 
     * @param status the dispatch status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the courier name.
     * 
     * @return courierName
     */
    public String getCourierName() {
        return courierName;
    }

    /**
     * Sets the courier name.
     * 
     * @param courierName the courier name
     */
    public void setCourierName(String courierName) {
        this.courierName = courierName;
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
     * Returns the display value of the dispatch,
     * typically the courier name.
     * 
     * @return courierName
     */
    @Override
    public String getValue() {
        return courierName;
    }
}