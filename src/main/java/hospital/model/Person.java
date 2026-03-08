package hospital.model;

/**
 * Abstract base class representing a person in the hospital system.
 * <p>
 * Subclasses must implement {@link #display()} to print their details.
 * </p>
 */
public abstract class Person {

    /** Unique identifier for the person. */
    protected int id;

    /** Full name of the person. */
    protected String name;

    /** Contact phone number of the person. */
    protected String phone;

    /**
     * Constructs a {@code Person} with the given attributes.
     *
     * @param id    the unique identifier
     * @param name  the full name
     * @param phone the contact phone number
     */
    protected Person(int id, String name, String phone) {
        this.id    = id;
        this.name  = name;
        this.phone = phone;
    }

    /**
     * Returns the unique identifier of this person.
     *
     * @return the person's id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the full name of this person.
     *
     * @return the person's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the contact phone number of this person.
     *
     * @return the person's phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the unique identifier of this person.
     *
     * @param id the new identifier
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the full name of this person.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the contact phone number of this person.
     *
     * @param phone the new phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Displays the details of this person to the standard output.
     */
    public abstract void display();
}
