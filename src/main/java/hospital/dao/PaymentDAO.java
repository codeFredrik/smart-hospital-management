package hospital.dao;

import hospital.db.DBConnection;
import hospital.model.Payment;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for {@link Payment} entities.
 * <p>
 * Supports inserting payments within an existing transaction and retrieving
 * payments by bill.
 * </p>
 */
public class PaymentDAO {

    /**
     * Inserts a new payment record using the supplied connection.
     * <p>
     * The caller is responsible for transaction management (commit / rollback).
     * </p>
     *
     * @param p    the payment to insert
     * @param conn the JDBC connection to use (must be managed by the caller)
     * @throws SQLException if a database error occurs
     */
    public void insertPayment(Payment p, Connection conn) throws SQLException {
        String sql = "INSERT INTO Payments (bill_id, payment_amount, payment_method, "
                + "payment_status, payment_time) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, p.getBillId());
            ps.setDouble(2, p.getPaymentAmount());
            ps.setString(3, p.getPaymentMethod());
            ps.setString(4, p.getPaymentStatus());
            ps.setTimestamp(5, Timestamp.valueOf(
                    p.getPaymentTime() != null ? p.getPaymentTime() : LocalDateTime.now()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setPaymentId(rs.getInt(1));
                }
            }
        }
    }

    /**
     * Retrieves all payments associated with the given bill.
     *
     * @param billId the bill identifier
     * @return a list of {@link Payment} records for the bill
     * @throws SQLException           if a database error occurs
     * @throws ClassNotFoundException if the JDBC driver is not found
     */
    public List<Payment> getPaymentsByBill(int billId)
            throws SQLException, ClassNotFoundException {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM Payments WHERE bill_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, billId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    payments.add(new Payment(
                            rs.getInt("payment_id"),
                            rs.getInt("bill_id"),
                            rs.getDouble("payment_amount"),
                            rs.getString("payment_method"),
                            rs.getString("payment_status"),
                            rs.getTimestamp("payment_time").toLocalDateTime()
                    ));
                }
            }
        }
        return payments;
    }
}
