package hospital.dao;

import hospital.db.DBConnection;
import hospital.model.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for {@link Appointment} entities.
 * <p>
 * Provides operations to schedule, retrieve, and update appointments using JDBC.
 * </p>
 */
public class AppointmentDAO {

    /**
     * Inserts a new appointment record into the database.
     *
     * @param a the appointment to schedule
     * @throws SQLException           if a database error occurs
     * @throws ClassNotFoundException if the JDBC driver is not found
     */
    public void scheduleAppointment(Appointment a)
            throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Appointments (patient_id, doctor_id, "
                + "appointment_date, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, a.getPatientId());
            ps.setInt(2, a.getDoctorId());
            ps.setString(3, a.getAppointmentDate());
            ps.setString(4, a.getStatus());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    a.setAppointmentId(rs.getInt(1));
                }
            }
            System.out.println("Appointment scheduled with ID: " + a.getAppointmentId());
        }
    }

    /**
     * Retrieves an appointment by its unique identifier.
     *
     * @param id the appointment id to look up
     * @return the matching {@link Appointment}, or {@code null} if not found
     * @throws SQLException           if a database error occurs
     * @throws ClassNotFoundException if the JDBC driver is not found
     */
    public Appointment getAppointmentById(int id)
            throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Appointments WHERE appointment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    /**
     * Retrieves all appointments for a given patient.
     *
     * @param patientId the patient whose appointments to retrieve
     * @return a list of {@link Appointment} records for the patient
     * @throws SQLException           if a database error occurs
     * @throws ClassNotFoundException if the JDBC driver is not found
     */
    public List<Appointment> getAppointmentsByPatient(int patientId)
            throws SQLException, ClassNotFoundException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM Appointments WHERE patient_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    appointments.add(mapRow(rs));
                }
            }
        }
        return appointments;
    }

    /**
     * Updates the status of an existing appointment.
     *
     * @param appointmentId the appointment to update
     * @param status        the new status string
     * @throws SQLException           if a database error occurs
     * @throws ClassNotFoundException if the JDBC driver is not found
     */
    public void updateStatus(int appointmentId, String status)
            throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Appointments SET status = ? WHERE appointment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, appointmentId);
            int rows = ps.executeUpdate();
            System.out.println(rows + " appointment(s) status updated to '" + status + "'.");
        }
    }

    /**
     * Maps the current row of the given {@link ResultSet} to an {@link Appointment} object.
     *
     * @param rs the result set positioned at the row to map
     * @return a populated {@link Appointment}
     * @throws SQLException if a column value cannot be retrieved
     */
    private Appointment mapRow(ResultSet rs) throws SQLException {
        return new Appointment(
                rs.getInt("appointment_id"),
                rs.getInt("patient_id"),
                rs.getInt("doctor_id"),
                rs.getString("appointment_date"),
                rs.getString("status")
        );
    }
}
