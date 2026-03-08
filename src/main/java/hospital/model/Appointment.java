package hospital.model;

/**
 * Represents a scheduled appointment between a patient and a doctor.
 */
public class Appointment {

    /** Unique appointment identifier. */
    private int appointmentId;

    /** The identifier of the patient for this appointment. */
    private int patientId;

    /** The identifier of the doctor for this appointment. */
    private int doctorId;

    /** The date of the appointment in {@code YYYY-MM-DD} format. */
    private String appointmentDate;

    /** Current status of the appointment (e.g., SCHEDULED, COMPLETED, CANCELLED). */
    private String status;

    /**
     * Constructs an {@code Appointment} with all fields populated.
     *
     * @param appointmentId  unique appointment identifier
     * @param patientId      patient identifier
     * @param doctorId       doctor identifier
     * @param appointmentDate date in {@code YYYY-MM-DD} format
     * @param status         appointment status
     */
    public Appointment(int appointmentId, int patientId, int doctorId,
                       String appointmentDate, String status) {
        this.appointmentId   = appointmentId;
        this.patientId       = patientId;
        this.doctorId        = doctorId;
        this.appointmentDate = appointmentDate;
        this.status          = status;
    }

    /**
     * Returns the appointment identifier.
     *
     * @return appointment id
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Sets the appointment identifier.
     *
     * @param appointmentId the new appointment id
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Returns the patient identifier.
     *
     * @return patient id
     */
    public int getPatientId() {
        return patientId;
    }

    /**
     * Sets the patient identifier.
     *
     * @param patientId the new patient id
     */
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    /**
     * Returns the doctor identifier.
     *
     * @return doctor id
     */
    public int getDoctorId() {
        return doctorId;
    }

    /**
     * Sets the doctor identifier.
     *
     * @param doctorId the new doctor id
     */
    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    /**
     * Returns the appointment date.
     *
     * @return date string in {@code YYYY-MM-DD} format
     */
    public String getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * Sets the appointment date.
     *
     * @param appointmentDate date string in {@code YYYY-MM-DD} format
     */
    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    /**
     * Returns the current status of the appointment.
     *
     * @return status string
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the current status of the appointment.
     *
     * @param status the new status string
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns a readable string representation of this appointment.
     *
     * @return formatted string
     */
    @Override
    public String toString() {
        return "Appointment{id=" + appointmentId
                + ", patientId=" + patientId
                + ", doctorId=" + doctorId
                + ", date='" + appointmentDate + '\''
                + ", status='" + status + '\''
                + '}';
    }
}
