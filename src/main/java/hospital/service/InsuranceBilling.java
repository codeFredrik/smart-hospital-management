package hospital.service;

/**
 * Insurance billing strategy that applies a 20% discount to the base fee.
 * <p>
 * Used for patients who have an insurance provider on file.
 * </p>
 */
public class InsuranceBilling implements Billable {

    /** The discount rate applied to insured patients (20%). */
    private static final double DISCOUNT_RATE = 0.20;

    /**
     * Returns the base fee reduced by a 20% insurance discount.
     *
     * @param baseFee the original bill amount
     * @return {@code baseFee * (1 - 0.20)}
     */
    @Override
    public double calculateBill(double baseFee) {
        return baseFee * (1 - DISCOUNT_RATE);
    }
}
