package hospital.model;

/**
 * Represents a doctor registered in the hospital system.
 * <p>
 * Extends {@link Person} with specialization and consultation fee information.
 * </p>
 */
public class Doctor extends Person {

    /** Medical specialization of the doctor (e.g., Cardiology, General Practice). */
    private String specialization;

    /** Consultation fee charged per appointment. */
    private double consultationFee;

    /**
     * Constructs a fully populated {@code Doctor}.
     *
     * @param id              unique doctor identifier
     * @param name            full name
     * @param phone           contact phone number
     * @param specialization  medical specialization
     * @param consultationFee fee per consultation
     */
    public Doctor(int id, String name, String phone,
                  String specialization, double consultationFee) {
        super(id, name, phone);
        this.specialization  = specialization;
        this.consultationFee = consultationFee;
    }

    /**
     * Returns the medical specialization of this doctor.
     *
     * @return specialization string
     */
    public String getSpecialization() {
        return specialization;
    }

    /**
     * Sets the medical specialization of this doctor.
     *
     * @param specialization specialization string
     */
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    /**
     * Returns the consultation fee charged by this doctor.
     *
     * @return consultation fee
     */
    public double getConsultationFee() {
        return consultationFee;
    }

    /**
     * Sets the consultation fee for this doctor.
     *
     * @param consultationFee the new consultation fee
     */
    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }

    /**
     * Displays the doctor's details to the standard output.
     */
    @Override
    public void display() {
        System.out.println("------ Doctor Details ------");
        System.out.println("ID               : " + id);
        System.out.println("Name             : " + name);
        System.out.println("Phone            : " + phone);
        System.out.println("Specialization   : " + specialization);
        System.out.println("Consultation Fee : " + consultationFee);
        System.out.println("----------------------------");
    }
}
