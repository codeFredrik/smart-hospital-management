package hospital.service;

import hospital.dao.BillingDAO;
import hospital.dao.PatientDAO;
import hospital.dao.PaymentDAO;
import hospital.db.DBConnection;
import hospital.exception.PaymentException;
import hospital.model.Bill;
import hospital.model.Patient;
import hospital.model.Payment;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Service class responsible for processing payments against bills.
 * <p>
 * Payment and bill-status update are executed atomically within a single
 * JDBC transaction. A {@link PaymentException} is thrown if anything goes
 * wrong, rolling back all changes.
 * </p>
 */
public class BillingService {

    private final BillingDAO billingDAO;
    private final PatientDAO patientDAO;
    private final PaymentDAO paymentDAO;

    /**
     * Constructs a {@code BillingService} with the required DAOs.
     *
     * @param billingDAO DAO for billing operations
     * @param patientDAO DAO for patient operations
     * @param paymentDAO DAO for payment operations
     */
    public BillingService(BillingDAO billingDAO, PatientDAO patientDAO,
                          PaymentDAO paymentDAO) {
        this.billingDAO = billingDAO;
        this.patientDAO = patientDAO;
        this.paymentDAO = paymentDAO;
    }

    /**
     * Processes a payment for the pending bill of the given patient.
     * <p>
     * Steps:
     * <ol>
     *   <li>Fetch the pending bill for the patient.</li>
     *   <li>Fetch the patient record to determine billing strategy.</li>
     *   <li>Select billing strategy: {@link InsuranceBilling} if insured,
     *       otherwise {@link StandardBilling}.</li>
     *   <li>Calculate the final amount via the chosen strategy.</li>
     *   <li>Open a JDBC transaction and insert the payment record.</li>
     *   <li>Update the bill status to {@code PAID}.</li>
     *   <li>Commit on success or rollback on any error.</li>
     * </ol>
     * </p>
     *
     * @param patientId     the patient's identifier
     * @param paymentAmount the amount tendered
     * @param paymentMethod the payment method (e.g., CASH, CARD)
     * @throws PaymentException if the payment cannot be processed
     */
    public void processPayment(int patientId, double paymentAmount, String paymentMethod)
            throws PaymentException {

        Connection conn = null;
        try {
            Bill bill = billingDAO.getBillByPatient(patientId);
            if (bill == null) {
                throw new PaymentException(
                        "No pending bill found for patient ID: " + patientId, null);
            }

            Patient patient = patientDAO.getPatientById(patientId);
            if (patient == null) {
                throw new PaymentException(
                        "Patient not found with ID: " + patientId, null);
            }

            // Choose billing strategy using polymorphism
            Billable billable = patient.hasInsurance()
                    ? new InsuranceBilling()
                    : new StandardBilling();

            double finalAmount = billable.calculateBill(bill.getTotalAmount());
            System.out.println("Final amount after billing strategy ("
                    + (patient.hasInsurance() ? "Insurance 20% discount" : "Standard")
                    + "): " + finalAmount);

            // Open transaction
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            Payment payment = new Payment(
                    0,
                    bill.getBillId(),
                    finalAmount,
                    paymentMethod,
                    "SUCCESS",
                    LocalDateTime.now()
            );

            paymentDAO.insertPayment(payment, conn);
            billingDAO.updateBillStatus(bill.getBillId(), "PAID", conn);

            conn.commit();
            System.out.println("Payment processed successfully. Bill ID "
                    + bill.getBillId() + " marked as PAID.");

        } catch (PaymentException pe) {
            rollback(conn);
            throw pe;
        } catch (Exception e) {
            rollback(conn);
            throw new PaymentException("Payment processing failed: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.err.println("Failed to close connection: " + ex.getMessage());
                }
            }
        }
    }

    /**
     * Rolls back the given connection silently, printing an error if rollback fails.
     *
     * @param conn the JDBC connection to roll back (may be {@code null})
     */
    private void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
                System.err.println("Transaction rolled back.");
            } catch (SQLException ex) {
                System.err.println("Rollback failed: " + ex.getMessage());
            }
        }
    }
}
