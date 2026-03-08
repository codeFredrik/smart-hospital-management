package hospital.service;

/**
 * Standard billing strategy that returns the base fee without any modifications.
 * <p>
 * Used for patients who do not have insurance coverage.
 * </p>
 */
public class StandardBilling implements Billable {

    /**
     * Returns the base fee unchanged.
     *
     * @param baseFee the original bill amount
     * @return the same {@code baseFee} value
     */
    @Override
    public double calculateBill(double baseFee) {
        return baseFee;
    }
}
