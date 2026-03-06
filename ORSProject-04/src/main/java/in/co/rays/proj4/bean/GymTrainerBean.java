package in.co.rays.proj4.bean;

import java.util.*;

/**
 * GymTrainerBean represents trainer information within the system.
 * It includes trainer details such as name, specialization,
 * and salary.
 * This class extends {@link BaseBean} to include common audit fields.
 *
 * @author Your Name
 * @version 1.0
 */
public class GymTrainerBean extends BaseBean {

    /** Name of the trainer. */
    private String trainerName;

    /** Specialization of the trainer (e.g., Yoga, Cardio, Weight Training). */
    private String specialization;

    /** Salary of the trainer. */
    private String salary;

    /**
     * Gets the trainer name.
     *
     * @return trainerName
     */
    public String getTrainerName() {
        return trainerName;
    }

    /**
     * Sets the trainer name.
     *
     * @param trainerName the trainer name
     */
    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    /**
     * Gets the specialization of the trainer.
     *
     * @return specialization
     */
    public String getSpecialization() {
        return specialization;
    }

    /**
     * Sets the specialization of the trainer.
     *
     * @param specialization the specialization
     */
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    /**
     * Gets the salary of the trainer.
     *
     * @return salary
     */
    public String getSalary() {
        return salary;
    }

    /**
     * Sets the salary of the trainer.
     *
     * @param salary the salary
     */
    public void setSalary(String salary) {
        this.salary = salary;
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
     * Returns the display value of the trainer,
     * typically the trainer name.
     *
     * @return trainerName
     */
    @Override
    public String getValue() {
        return trainerName;
    }
}