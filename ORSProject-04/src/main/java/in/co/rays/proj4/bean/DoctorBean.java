package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * DoctorBean represents a Doctor entity.
 * It stores doctor-related information like name,
 * date of birth, mobile number and expertise.
 * 
 * @author Ankit Rawat
 * 
 * @version 1.0
 */
public class DoctorBean extends BaseBean {

    /** Doctor Name */
    private String name;

    /** Doctor Date of Birth */
    private Date dob;

    /** Doctor Mobile Number */
    private String mobileNo;

    /** Doctor Expertise */
    private String expertise;

    /** Returns doctor name */
    public String getName() {
        return name;
    }

    /** Sets doctor name */
    public void setName(String name) {
        this.name = name;
    }

    /** Returns doctor date of birth */
    public Date getDob() {
        return dob;
    }

    /** Sets doctor date of birth */
    public void setDob(Date dob) {
        this.dob = dob;
    }

    /** Returns doctor mobile number */
    public String getMobileNo() {
        return mobileNo;
    }

    /** Sets doctor mobile number */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    /** Returns doctor expertise */
    public String getExpertise() {
        return expertise;
    }

    /** Sets doctor expertise */
    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    /**
     * Dropdown display value
     */
    @Override
    public String getKey() {
        return String.valueOf(getId());
    }

    @Override
    public String getValue() {
        return name;
    }
}
