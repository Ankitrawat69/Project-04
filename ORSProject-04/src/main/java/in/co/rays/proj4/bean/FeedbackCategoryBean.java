package in.co.rays.proj4.bean;

/**
 * FeedbackCategoryBean represents feedback category information within the system.
 * It includes category details such as code, name, description and status.
 * This class extends {@link BaseBean} to include common audit fields.
 *
 * @author Ankit Rawat
 * @version 1.0
 */
public class FeedbackCategoryBean extends BaseBean {

    /** Unique code of the category. */
    private String categoryCode;

    /** Name of the category. */
    private String categoryName;

    /** Description of the category. */
    private String description;

    /** Status of the category (Active/Inactive). */
    private String categoryStatus;

    /**
     * Gets the category code.
     *
     * @return categoryCode
     */
    public String getCategoryCode() {
        return categoryCode;
    }

    /**
     * Sets the category code.
     *
     * @param categoryCode the category code
     */
    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    /**
     * Gets the category name.
     *
     * @return categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Sets the category name.
     *
     * @param categoryName the category name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Gets the description.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the category status.
     *
     * @return categoryStatus
     */
    public String getCategoryStatus() {
        return categoryStatus;
    }

    /**
     * Sets the category status.
     *
     * @param categoryStatus the category status
     */
    public void setCategoryStatus(String categoryStatus) {
        this.categoryStatus = categoryStatus;
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
     * Returns the display value of the category,
     * typically the category name.
     *
     * @return categoryName
     */
    @Override
    public String getValue() {
        return categoryName;
    }
}