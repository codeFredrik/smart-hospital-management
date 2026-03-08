package hospital.model;

/**
 * Represents a billing record for a patient's appointment(s).
 */
public class Bill {

    /** Unique bill identifier. */
    private int billId;

    /** The identifier of the patient this bill belongs to. */
    private int patientId;

    /** The identifier of the appointment associated with this bill (may be 0 if not set). */
    private int appointmentId;

    /** Total amount due on this bill. */
    private double totalAmount;

    /** Current status of the bill (e.g., PENDING, PAID). */
    private String billStatus;

    /**
     * Constructs a {@code Bill} with all fields populated.
     *
     * @param billId        unique bill identifier
     * @param patientId     patient identifier
     * @param appointmentId appointment identifier
     * @param totalAmount   total amount due
     * @param billStatus    current bill status
     */
    public Bill(int billId, int patientId, int appointmentId,
                double totalAmount, String billStatus) {
        this.billId        = billId;
        this.patientId     = patientId;
        this.appointmentId = appointmentId;
        this.totalAmount   = totalAmount;
        this.billStatus    = billStatus;
    }

    /**
     * Returns the bill identifier.
     *
     * @return bill id
     */
    public int getBillId() {
        return billId;
    }

    /**
     * Sets the bill identifier.
     *
     * @param billId the new bill id
     */
    public void setBillId(int billId) {
        this.billId = billId;
    }

    /**
     * Returns the patient identifier for this bill.
     *
     * @return patient id
     */
    public int getPatientId() {
        return patientId;
    }

    /**
     * Sets the patient identifier for this bill.
     *
     * @param patientId the new patient id
     */
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    /**
     * Returns the appointment identifier for this bill.
     *
     * @return appointment id
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Sets the appointment identifier for this bill.
     *
     * @param appointmentId the new appointment id
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Returns the total amount due on this bill.
     *
     * @return total amount
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Sets the total amount due on this bill.
     *
     * @param totalAmount the new total amount
     */
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * Returns the current status of this bill.
     *
     * @return bill status string
     */
    public String getBillStatus() {
        return billStatus;
    }

    /**
     * Sets the current status of this bill.
     *
     * @param billStatus the new bill status
     */
    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    /**
     * Returns a readable string representation of this bill.
     *
     * @return formatted string
     */
    @Override
    public String toString() {
        return "Bill{id=" + billId
                + ", patientId=" + patientId
                + ", appointmentId=" + appointmentId
                + ", totalAmount=" + totalAmount
                + ", status='" + billStatus + '\''
                + '}';
    }
}
