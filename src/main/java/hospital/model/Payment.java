package hospital.model;

import java.time.LocalDateTime;

/**
 * Represents a payment made against a bill.
 */
public class Payment {

    /** Unique payment identifier. */
    private int paymentId;

    /** The identifier of the bill this payment applies to. */
    private int billId;

    /** Amount paid. */
    private double paymentAmount;

    /** Payment method used (e.g., CASH, CARD, INSURANCE). */
    private String paymentMethod;

    /** Current status of the payment (e.g., SUCCESS, FAILED). */
    private String paymentStatus;

    /** Timestamp when the payment was made. */
    private LocalDateTime paymentTime;

    /**
     * Constructs a {@code Payment} with all fields populated.
     *
     * @param paymentId     unique payment identifier
     * @param billId        bill identifier this payment applies to
     * @param paymentAmount amount paid
     * @param paymentMethod payment method used
     * @param paymentStatus current payment status
     * @param paymentTime   timestamp of payment
     */
    public Payment(int paymentId, int billId, double paymentAmount,
                   String paymentMethod, String paymentStatus,
                   LocalDateTime paymentTime) {
        this.paymentId     = paymentId;
        this.billId        = billId;
        this.paymentAmount = paymentAmount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.paymentTime   = paymentTime;
    }

    /**
     * Returns the payment identifier.
     *
     * @return payment id
     */
    public int getPaymentId() {
        return paymentId;
    }

    /**
     * Sets the payment identifier.
     *
     * @param paymentId the new payment id
     */
    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * Returns the bill identifier for this payment.
     *
     * @return bill id
     */
    public int getBillId() {
        return billId;
    }

    /**
     * Sets the bill identifier for this payment.
     *
     * @param billId the new bill id
     */
    public void setBillId(int billId) {
        this.billId = billId;
    }

    /**
     * Returns the amount paid.
     *
     * @return payment amount
     */
    public double getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * Sets the amount paid.
     *
     * @param paymentAmount the new payment amount
     */
    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    /**
     * Returns the payment method used.
     *
     * @return payment method string
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Sets the payment method.
     *
     * @param paymentMethod the new payment method
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Returns the current payment status.
     *
     * @return payment status string
     */
    public String getPaymentStatus() {
        return paymentStatus;
    }

    /**
     * Sets the current payment status.
     *
     * @param paymentStatus the new payment status
     */
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    /**
     * Returns the timestamp when this payment was made.
     *
     * @return payment timestamp
     */
    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    /**
     * Sets the timestamp for this payment.
     *
     * @param paymentTime the new payment timestamp
     */
    public void setPaymentTime(LocalDateTime paymentTime) {
        this.paymentTime = paymentTime;
    }

    /**
     * Returns a readable string representation of this payment.
     *
     * @return formatted string
     */
    @Override
    public String toString() {
        return "Payment{id=" + paymentId
                + ", billId=" + billId
                + ", amount=" + paymentAmount
                + ", method='" + paymentMethod + '\''
                + ", status='" + paymentStatus + '\''
                + ", time=" + paymentTime
                + '}';
    }
}
