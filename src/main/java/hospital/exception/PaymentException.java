package hospital.exception;

/**
 * Checked exception thrown when a payment cannot be processed successfully.
 */
public class PaymentException extends Exception {

    /**
     * Constructs a {@code PaymentException} with a descriptive message and root cause.
     *
     * @param message a human-readable description of the failure
     * @param cause   the underlying exception that caused this failure (may be {@code null})
     */
    public PaymentException(String message, Throwable cause) {
        super(message, cause);
    }
}
