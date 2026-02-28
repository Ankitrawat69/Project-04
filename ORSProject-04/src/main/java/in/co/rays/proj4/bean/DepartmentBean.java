package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * DepartmentBean represents department information within the system.
 * It includes details such as department code, name, head, location,
 * and status of the department.
 * This class extends {@link BaseBean} to include common audit fields.
 *
 * @author Ankit Rawat
 * @version 1.0
 */
public class DepartmentBean extends BaseBean {

    /** Unique code of the department. */
    private String departmentCode;

    /** Name of the department. */
    private String departmentName;

    /** Head of the department. */
    private String departmentHead;

    /** Location of the department. */
    private String location;

    /** Current status of the department. */
    private String status;

    /**
     * Gets the department code.
     *
     * @return departmentCode
     */
    public String getDepartmentCode() {
        return departmentCode;
    }

    /**
     * Sets the department code.
     *
     * @param departmentCode the department code
     */
    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    /**
     * Gets the department name.
     *
     * @return departmentName
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * Sets the department name.
     *
     * @param departmentName the department name
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * Gets the department head.
     *
     * @return departmentHead
     */
    public String getDepartmentHead() {
        return departmentHead;
    }

    /**
     * Sets the department head.
     *
     * @param departmentHead the department head
     */
    public void setDepartmentHead(String departmentHead) {
        this.departmentHead = departmentHead;
    }

    /**
     * Gets the location of the department.
     *
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the department.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the status of the department.
     *
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the department.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
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
     * Returns the display value of the department,
     * typically the department name.
     *
     * @return departmentName
     */
    @Override
    public String getValue() {
        return departmentName;
    }
}