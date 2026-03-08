package hospital.model;

/**
 * Represents a prescription issued during an appointment.
 */
public class Prescription {

    /** Unique prescription identifier. */
    private int prescriptionId;

    /** The identifier of the appointment this prescription belongs to. */
    private int appointmentId;

    /** Comma-separated list of medicines prescribed. */
    private String medicines;

    /** Additional notes or instructions from the doctor. */
    private String notes;

    /**
     * Constructs a {@code Prescription} with all fields populated.
     *
     * @param prescriptionId unique prescription identifier
     * @param appointmentId  related appointment identifier
     * @param medicines      comma-separated medicine names
     * @param notes          doctor's notes or instructions
     */
    public Prescription(int prescriptionId, int appointmentId,
                        String medicines, String notes) {
        this.prescriptionId = prescriptionId;
        this.appointmentId  = appointmentId;
        this.medicines      = medicines;
        this.notes          = notes;
    }

    /**
     * Returns the prescription identifier.
     *
     * @return prescription id
     */
    public int getPrescriptionId() {
        return prescriptionId;
    }

    /**
     * Sets the prescription identifier.
     *
     * @param prescriptionId the new prescription id
     */
    public void setPrescriptionId(int prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    /**
     * Returns the appointment identifier for this prescription.
     *
     * @return appointment id
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Sets the appointment identifier for this prescription.
     *
     * @param appointmentId the new appointment id
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Returns the medicines prescribed.
     *
     * @return comma-separated medicine names
     */
    public String getMedicines() {
        return medicines;
    }

    /**
     * Sets the medicines for this prescription.
     *
     * @param medicines comma-separated medicine names
     */
    public void setMedicines(String medicines) {
        this.medicines = medicines;
    }

    /**
     * Returns the doctor's notes for this prescription.
     *
     * @return notes string
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the doctor's notes for this prescription.
     *
     * @param notes notes string
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Returns a readable string representation of this prescription.
     *
     * @return formatted string
     */
    @Override
    public String toString() {
        return "Prescription{id=" + prescriptionId
                + ", appointmentId=" + appointmentId
                + ", medicines='" + medicines + '\''
                + ", notes='" + notes + '\''
                + '}';
    }
}
