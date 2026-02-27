package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * InsuranceBean represents car insurance information within the system.
 * It includes details such as insurance code, car name, expiry date,
 * and provider name.
 * This class extends {@link BaseBean} to include common audit fields.
 *
 * @author Ankit Rawat
 * @version 1.0
 */
public class InsuranceBean extends BaseBean {

    /** Unique insurance code. */
    private String insuranceCode;

    /** Name of the car. */
    private String carName;

    /** Expiry date of the insurance. */
    private Date expiryDate;

    /** Name of the insurance provider. */
    private String providerName;

    /**
     * Gets the insurance code.
     *
     * @return insuranceCode
     */
    public String getInsuranceCode() {
        return insuranceCode;
    }

    /**
     * Sets the insurance code.
     *
     * @param insuranceCode the insurance code
     */
    public void setInsuranceCode(String insuranceCode) {
        this.insuranceCode = insuranceCode;
    }

    /**
     * Gets the car name.
     *
     * @return carName
     */
    public String getCarName() {
        return carName;
    }

    /**
     * Sets the car name.
     *
     * @param carName the car name
     */
    public void setCarName(String carName) {
        this.carName = carName;
    }

    /**
     * Gets the expiry date of the insurance.
     *
     * @return expiryDate
     */
    public Date getExpiryDate() {
        return expiryDate;
    }

    /**
     * Sets the expiry date of the insurance.
     *
     * @param expiryDate the expiry date
     */
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * Gets the provider name.
     *
     * @return providerName
     */
    public String getProviderName() {
        return providerName;
    }

    /**
     * Sets the provider name.
     *
     * @param providerName the provider name
     */
    public void setProviderName(String providerName) {
        this.providerName = providerName;
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
     * Returns the display value of the insurance,
     * typically the insurance code.
     *
     * @return insuranceCode
     */
    @Override
    public String getValue() {
        return insuranceCode;
    }
}