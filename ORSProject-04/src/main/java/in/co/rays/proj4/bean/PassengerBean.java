package in.co.rays.proj4.bean;

/**
 * PassengerBean represents passenger information within the system.
 * It includes personal details such as name, age, and passport number.
 * This class extends {@link BaseBean} to include common audit fields.
 *
 * @author 
 * @version 1.0
 */
public class PassengerBean extends BaseBean {

    /** Name of the passenger. */
    private String name;

    /** Age of the passenger. */
    private Integer age;

    /** Passport number of the passenger. */
    private String passportNumber;

    /**
     * Gets the name of the passenger.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the passenger.
     *
     * @param name the passenger name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the age of the passenger.
     *
     * @return age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Sets the age of the passenger.
     *
     * @param age the passenger age
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * Gets the passport number of the passenger.
     *
     * @return passportNumber
     */
    public String getPassportNumber() {
        return passportNumber;
    }

    /**
     * Sets the passport number of the passenger.
     *
     * @param passportNumber the passport number
     */
    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
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
     * Returns the display value of the passenger,
     * typically the name.
     *
     * @return name
     */
    @Override
    public String getValue() {
        return name;
    }
}