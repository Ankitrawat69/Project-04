package in.co.rays.proj4.bean;

/**
 * SecurityStaffBean represents security staff information in the system.
 * It includes staff details such as name, shift, and salary.
 * This class extends BaseBean to include common audit fields.
 */
public class SecurityStaffBean extends BaseBean {

    /** Name of the security staff */
    private String staffName;

    /** Shift of the security staff */
    private String shift;

    /** Salary of the security staff */
    private String salary;

    /**
     * Gets the staff name
     * @return staffName
     */
    public String getStaffName() {
        return staffName;
    }

    /**
     * Sets the staff name
     * @param staffName
     */
    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    /**
     * Gets the shift
     * @return shift
     */
    public String getShift() {
        return shift;
    }

    /**
     * Sets the shift
     * @param shift
     */
    public void setShift(String shift) {
        this.shift = shift;
    }

    /**
     * Gets the salary
     * @return salary
     */
    public String getSalary() {
        return salary;
    }

    /**
     * Sets the salary
     * @param salary
     */
    public void setSalary(String salary) {
        this.salary = salary;
    }

    @Override
    public String getKey() {
        return id + "";
    }

    @Override
    public String getValue() {
        return staffName;
    }
}