package hospital.model;

/**
 * Represents a patient registered in the hospital system.
 * <p>
 * Extends {@link Person} with medical and insurance information.
 * </p>
 */
public class Patient extends Person {

    /** Age of the patient in years. */
    private int age;

    /** Gender of the patient (e.g., Male, Female, Other). */
    private String gender;

    /** Name of the patient's insurance provider, or {@code null} if uninsured. */
    private String insuranceProvider;

    /** Insurance policy number, or {@code null} if uninsured. */
    private String insuranceNumber;

    /**
     * Constructs a fully populated {@code Patient}.
     *
     * @param id                unique patient identifier
     * @param name              full name
     * @param age               age in years
     * @param gender            gender string
     * @param phone             contact phone number
     * @param insuranceProvider name of insurance provider (may be {@code null})
     * @param insuranceNumber   insurance policy number (may be {@code null})
     */
    public Patient(int id, String name, int age, String gender, String phone,
                   String insuranceProvider, String insuranceNumber) {
        super(id, name, phone);
        this.age               = age;
        this.gender            = gender;
        this.insuranceProvider = insuranceProvider;
        this.insuranceNumber   = insuranceNumber;
    }

    /**
     * Returns the age of this patient.
     *
     * @return age in years
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age of this patient.
     *
     * @param age age in years
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Returns the gender of this patient.
     *
     * @return gender string
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of this patient.
     *
     * @param gender gender string
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Returns the insurance provider name.
     *
     * @return insurance provider, or {@code null} if uninsured
     */
    public String getInsuranceProvider() {
        return insuranceProvider;
    }

    /**
     * Sets the insurance provider name.
     *
     * @param insuranceProvider insurance provider name
     */
    public void setInsuranceProvider(String insuranceProvider) {
        this.insuranceProvider = insuranceProvider;
    }

    /**
     * Returns the insurance policy number.
     *
     * @return insurance number, or {@code null} if uninsured
     */
    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    /**
     * Sets the insurance policy number.
     *
     * @param insuranceNumber insurance policy number
     */
    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    /**
     * Returns {@code true} if this patient has an insurance provider on file.
     *
     * @return {@code true} if insured
     */
    public boolean hasInsurance() {
        return insuranceProvider != null && !insuranceProvider.trim().isEmpty();
    }

    /**
     * Displays the patient's details to the standard output.
     */
    @Override
    public void display() {
        System.out.println("------ Patient Details ------");
        System.out.println("ID               : " + id);
        System.out.println("Name             : " + name);
        System.out.println("Age              : " + age);
        System.out.println("Gender           : " + gender);
        System.out.println("Phone            : " + phone);
        System.out.println("Insurance Prov.  : " + (insuranceProvider != null ? insuranceProvider : "N/A"));
        System.out.println("Insurance No.    : " + (insuranceNumber   != null ? insuranceNumber   : "N/A"));
        System.out.println("-----------------------------");
    }
}
