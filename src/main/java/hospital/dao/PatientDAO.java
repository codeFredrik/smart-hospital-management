package hospital.dao;

import hospital.db.DBConnection;
import hospital.model.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for {@link Patient} entities.
 * <p>
 * Provides full CRUD operations using {@link PreparedStatement} and JDBC.
 * </p>
 */
public class PatientDAO {

    /**
     * Inserts a new patient record into the database.
     *
     * @param p the patient to add
     * @throws SQLException           if a database error occurs
     * @throws ClassNotFoundException if the JDBC driver is not found
     */
    public void addPatient(Patient p) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Patients (name, age, gender, phone, "
                + "insurance_provider, insurance_number) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getName());
            ps.setInt(2, p.getAge());
            ps.setString(3, p.getGender());
            ps.setString(4, p.getPhone());
            ps.setString(5, p.getInsuranceProvider());
            ps.setString(6, p.getInsuranceNumber());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setId(rs.getInt(1));
                }
            }
            System.out.println("Patient added successfully with ID: " + p.getId());
        }
    }

    /**
     * Retrieves a patient by their unique identifier.
     *
     * @param id the patient id to look up
     * @return the matching {@link Patient}, or {@code null} if not found
     * @throws SQLException           if a database error occurs
     * @throws ClassNotFoundException if the JDBC driver is not found
     */
    public Patient getPatientById(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Patients WHERE patient_id = ?";
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
     * Retrieves all patients from the database.
     *
     * @return a list of all {@link Patient} records
     * @throws SQLException           if a database error occurs
     * @throws ClassNotFoundException if the JDBC driver is not found
     */
    public List<Patient> getAllPatients() throws SQLException, ClassNotFoundException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM Patients";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                patients.add(mapRow(rs));
            }
        }
        return patients;
    }

    /**
     * Updates an existing patient record in the database.
     *
     * @param p the patient with updated fields (identified by {@code patient_id})
     * @throws SQLException           if a database error occurs
     * @throws ClassNotFoundException if the JDBC driver is not found
     */
    public void updatePatient(Patient p) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Patients SET name=?, age=?, gender=?, phone=?, "
                + "insurance_provider=?, insurance_number=? WHERE patient_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setInt(2, p.getAge());
            ps.setString(3, p.getGender());
            ps.setString(4, p.getPhone());
            ps.setString(5, p.getInsuranceProvider());
            ps.setString(6, p.getInsuranceNumber());
            ps.setInt(7, p.getId());
            int rows = ps.executeUpdate();
            System.out.println(rows + " patient record(s) updated.");
        }
    }

    /**
     * Deletes a patient record from the database.
     *
     * @param id the id of the patient to delete
     * @throws SQLException           if a database error occurs
     * @throws ClassNotFoundException if the JDBC driver is not found
     */
    public void deletePatient(int id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Patients WHERE patient_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            System.out.println(rows + " patient record(s) deleted.");
        }
    }

    /**
     * Maps the current row of the given {@link ResultSet} to a {@link Patient} object.
     *
     * @param rs the result set positioned at the row to map
     * @return a populated {@link Patient}
     * @throws SQLException if a column value cannot be retrieved
     */
    private Patient mapRow(ResultSet rs) throws SQLException {
        return new Patient(
                rs.getInt("patient_id"),
                rs.getString("name"),
                rs.getInt("age"),
                rs.getString("gender"),
                rs.getString("phone"),
                rs.getString("insurance_provider"),
                rs.getString("insurance_number")
        );
    }
}
