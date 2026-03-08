package hospital.dao;

import hospital.db.DBConnection;
import hospital.model.Doctor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for {@link Doctor} entities.
 * <p>
 * Provides operations to add and retrieve doctor records using JDBC.
 * </p>
 */
public class DoctorDAO {

    /**
     * Inserts a new doctor record into the database.
     *
     * @param d the doctor to add
     * @throws SQLException           if a database error occurs
     * @throws ClassNotFoundException if the JDBC driver is not found
     */
    public void addDoctor(Doctor d) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Doctors (name, specialization, consultation_fee) "
                + "VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, d.getName());
            ps.setString(2, d.getSpecialization());
            ps.setDouble(3, d.getConsultationFee());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    d.setId(rs.getInt(1));
                }
            }
            System.out.println("Doctor added successfully with ID: " + d.getId());
        }
    }

    /**
     * Retrieves a doctor by their unique identifier.
     *
     * @param id the doctor id to look up
     * @return the matching {@link Doctor}, or {@code null} if not found
     * @throws SQLException           if a database error occurs
     * @throws ClassNotFoundException if the JDBC driver is not found
     */
    public Doctor getDoctorById(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Doctors WHERE doctor_id = ?";
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
     * Retrieves all doctors from the database.
     *
     * @return a list of all {@link Doctor} records
     * @throws SQLException           if a database error occurs
     * @throws ClassNotFoundException if the JDBC driver is not found
     */
    public List<Doctor> getAllDoctors() throws SQLException, ClassNotFoundException {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM Doctors";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                doctors.add(mapRow(rs));
            }
        }
        return doctors;
    }

    /**
     * Maps the current row of the given {@link ResultSet} to a {@link Doctor} object.
     *
     * @param rs the result set positioned at the row to map
     * @return a populated {@link Doctor}
     * @throws SQLException if a column value cannot be retrieved
     */
    private Doctor mapRow(ResultSet rs) throws SQLException {
        return new Doctor(
                rs.getInt("doctor_id"),
                rs.getString("name"),
                rs.getString("phone"),
                rs.getString("specialization"),
                rs.getDouble("consultation_fee")
        );
    }
}
