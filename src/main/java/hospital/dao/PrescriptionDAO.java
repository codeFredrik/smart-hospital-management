package hospital.dao;

import hospital.db.DBConnection;
import hospital.model.Prescription;

import java.sql.*;
import java.util.List;

/**
 * Data Access Object for {@link Prescription} entities.
 * <p>
 * Supports single inserts, batch inserts, and retrieval by appointment.
 * </p>
 */
public class PrescriptionDAO {

    /**
     * Inserts a single prescription record into the database.
     *
     * @param p the prescription to add
     * @throws SQLException           if a database error occurs
     * @throws ClassNotFoundException if the JDBC driver is not found
     */
    public void addPrescription(Prescription p)
            throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Prescriptions (appointment_id, medicines, notes) "
                + "VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, p.getAppointmentId());
            ps.setString(2, p.getMedicines());
            ps.setString(3, p.getNotes());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setPrescriptionId(rs.getInt(1));
                }
            }
            System.out.println("Prescription added with ID: " + p.getPrescriptionId());
        }
    }

    /**
     * Inserts a list of prescriptions using JDBC batch processing.
     * <p>
     * Uses {@link PreparedStatement#addBatch()} and
     * {@link PreparedStatement#executeBatch()} for efficient bulk inserts.
     * </p>
     *
     * @param prescriptions the list of prescriptions to insert
     * @throws SQLException           if a database error occurs
     * @throws ClassNotFoundException if the JDBC driver is not found
     */
    public void addPrescriptionsBatch(List<Prescription> prescriptions)
            throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Prescriptions (appointment_id, medicines, notes) "
                + "VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Prescription p : prescriptions) {
                ps.setInt(1, p.getAppointmentId());
                ps.setString(2, p.getMedicines());
                ps.setString(3, p.getNotes());
                ps.addBatch();
            }
            int[] results = ps.executeBatch();
            System.out.println("Batch insert complete. Inserted "
                    + results.length + " prescription(s).");
        }
    }

    /**
     * Retrieves the prescription associated with the given appointment.
     *
     * @param appointmentId the appointment identifier
     * @return the matching {@link Prescription}, or {@code null} if not found
     * @throws SQLException           if a database error occurs
     * @throws ClassNotFoundException if the JDBC driver is not found
     */
    public Prescription getPrescriptionByAppointment(int appointmentId)
            throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Prescriptions WHERE appointment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appointmentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Prescription(
                            rs.getInt("prescription_id"),
                            rs.getInt("appointment_id"),
                            rs.getString("medicines"),
                            rs.getString("notes")
                    );
                }
            }
        }
        return null;
    }
}
