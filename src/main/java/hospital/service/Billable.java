package hospital.service;

/**
 * Strategy interface for billing calculations.
 * <p>
 * Implementations define how a final bill amount is computed given a base fee.
 * </p>
 */
public interface Billable {

    /**
     * Calculates the final bill amount based on the given base fee.
     *
     * @param baseFee the original consultation / bill amount
     * @return the final amount after applying any adjustments (e.g., discounts)
     */
    double calculateBill(double baseFee);
}
