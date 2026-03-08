package hospital.dao;

import hospital.db.DBConnection;
import hospital.model.Bill;

import java.sql.*;

/**
 * Data Access Object for {@link Bill} entities.
 * <p>
 * Supports bill generation via a stored procedure, retrieval, and status updates.
 * </p>
 */
public class BillingDAO {

    /**
     * Calls the {@code GenerateBill} stored procedure to calculate and insert
     * a bill for the given patient.
     * <p>
     * Uses {@link CallableStatement} to invoke the stored procedure.
     * </p>
     *
     * @param patientId the patient's identifier
     * @throws SQLException           if a database error occurs
     * @throws ClassNotFoundException if the JDBC driver is not found
     */
    public void generateBillViaProcedure(int patientId)
            throws SQLException, ClassNotFoundException {
        String call = "{CALL GenerateBill(?)}";
        try (Connection conn = DBConnection.getConnection();
             CallableStatement cs = conn.prepareCall(call)) {
            cs.setInt(1, patientId);
            cs.execute();
            System.out.println("Bill generated successfully for patient ID: " + patientId);
        }
    }

    /**
     * Retrieves the most recent pending bill for the given patient.
     *
     * @param patientId the patient's identifier
     * @return the matching {@link Bill}, or {@code null} if none found
     * @throws SQLException           if a database error occurs
     * @throws ClassNotFoundException if the JDBC driver is not found
     */
    public Bill getBillByPatient(int patientId)
            throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Bills WHERE patient_id = ? "
                + "AND bill_status = 'PENDING' ORDER BY bill_id DESC LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    /**
     * Updates the status of a bill using the supplied connection.
     * <p>
     * The caller is responsible for transaction management (commit / rollback).
     * </p>
     *
     * @param billId the bill to update
     * @param status the new status string
     * @param conn   the JDBC connection to use (must be managed by the caller)
     * @throws SQLException if a database error occurs
     */
    public void updateBillStatus(int billId, String status, Connection conn)
            throws SQLException {
        String sql = "UPDATE Bills SET bill_status = ? WHERE bill_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, billId);
            ps.executeUpdate();
        }
    }

    /**
     * Maps the current row of the given {@link ResultSet} to a {@link Bill} object.
     *
     * @param rs the result set positioned at the row to map
     * @return a populated {@link Bill}
     * @throws SQLException if a column value cannot be retrieved
     */
    private Bill mapRow(ResultSet rs) throws SQLException {
        return new Bill(
                rs.getInt("bill_id"),
                rs.getInt("patient_id"),
                rs.getInt("appointment_id"),
                rs.getDouble("total_amount"),
                rs.getString("bill_status")
        );
    }
}
