package hospital.service;

import hospital.dao.AppointmentDAO;
import hospital.dao.DoctorDAO;
import hospital.dao.PatientDAO;
import hospital.model.Appointment;
import hospital.model.Doctor;
import hospital.model.Patient;

import java.sql.SQLException;

/**
 * Service class responsible for scheduling appointments.
 * <p>
 * Validates that both the patient and doctor exist before persisting the
 * appointment through {@link AppointmentDAO}.
 * </p>
 */
public class AppointmentService {

    private final PatientDAO     patientDAO;
    private final DoctorDAO      doctorDAO;
    private final AppointmentDAO appointmentDAO;

    /**
     * Constructs an {@code AppointmentService} with the required DAOs.
     *
     * @param patientDAO     DAO for patient operations
     * @param doctorDAO      DAO for doctor operations
     * @param appointmentDAO DAO for appointment operations
     */
    public AppointmentService(PatientDAO patientDAO, DoctorDAO doctorDAO,
                               AppointmentDAO appointmentDAO) {
        this.patientDAO     = patientDAO;
        this.doctorDAO      = doctorDAO;
        this.appointmentDAO = appointmentDAO;
    }

    /**
     * Validates that the patient and doctor exist, then schedules a new appointment.
     *
     * @param patientId the patient's identifier
     * @param doctorId  the doctor's identifier
     * @param date      appointment date in {@code YYYY-MM-DD} format
     * @throws SQLException           if a database error occurs
     * @throws ClassNotFoundException if the JDBC driver is not found
     * @throws IllegalArgumentException if the patient or doctor is not found
     */
    public void scheduleAppointment(int patientId, int doctorId, String date)
            throws SQLException, ClassNotFoundException {

        Patient patient = patientDAO.getPatientById(patientId);
        if (patient == null) {
            throw new IllegalArgumentException("Patient not found with ID: " + patientId);
        }

        Doctor doctor = doctorDAO.getDoctorById(doctorId);
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor not found with ID: " + doctorId);
        }

        Appointment appointment = new Appointment(0, patientId, doctorId, date, "SCHEDULED");
        appointmentDAO.scheduleAppointment(appointment);
        System.out.println("Appointment scheduled successfully for patient '"
                + patient.getName() + "' with Dr. " + doctor.getName()
                + " on " + date + ".");
    }
}
