package hospital.app;

import hospital.dao.*;
import hospital.exception.PaymentException;
import hospital.model.*;
import hospital.service.AppointmentService;
import hospital.service.BillingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Entry point for the Smart Hospital Management &amp; Billing System.
 * <p>
 * Provides an interactive console menu for managing patients, doctors,
 * appointments, prescriptions, bills, and payments.
 * </p>
 */
public class Main {

    private static final PatientDAO      patientDAO      = new PatientDAO();
    private static final DoctorDAO       doctorDAO       = new DoctorDAO();
    private static final AppointmentDAO  appointmentDAO  = new AppointmentDAO();
    private static final PrescriptionDAO prescriptionDAO = new PrescriptionDAO();
    private static final BillingDAO      billingDAO      = new BillingDAO();
    private static final PaymentDAO      paymentDAO      = new PaymentDAO();

    private static final AppointmentService appointmentService =
            new AppointmentService(patientDAO, doctorDAO, appointmentDAO);
    private static final BillingService billingService =
            new BillingService(billingDAO, patientDAO, paymentDAO);

    /**
     * Application entry point.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Enter choice: ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    registerPatient(scanner);
                    break;
                case "2":
                    registerDoctor(scanner);
                    break;
                case "3":
                    scheduleAppointment(scanner);
                    break;
                case "4":
                    addPrescriptionBatch(scanner);
                    break;
                case "5":
                    generateBill(scanner);
                    break;
                case "6":
                    makePayment(scanner);
                    break;
                case "7":
                    viewPatientDetails(scanner);
                    break;
                case "8":
                    System.out.println("Exiting... Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 8.");
            }
        }

        scanner.close();
    }

    /**
     * Prints the main menu to the standard output.
     */
    private static void printMenu() {
        System.out.println();
        System.out.println("==== Smart Hospital Management System ====");
        System.out.println("1. Register Patient");
        System.out.println("2. Register Doctor");
        System.out.println("3. Schedule Appointment");
        System.out.println("4. Add Prescription (Batch)");
        System.out.println("5. Generate Bill (Stored Procedure)");
        System.out.println("6. Make Payment (Transaction)");
        System.out.println("7. View Patient Details");
        System.out.println("8. Exit");
        System.out.println("==========================================");
    }

    /**
     * Prompts the user for patient details and registers a new patient.
     *
     * @param scanner the input scanner
     */
    private static void registerPatient(Scanner scanner) {
        try {
            System.out.print("Enter patient name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter age: ");
            int age = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter gender (Male/Female/Other): ");
            String gender = scanner.nextLine().trim();

            System.out.print("Enter phone number: ");
            String phone = scanner.nextLine().trim();

            System.out.print("Enter insurance provider (leave blank if none): ");
            String insProvider = scanner.nextLine().trim();
            if (insProvider.isEmpty()) {
                insProvider = null;
            }

            System.out.print("Enter insurance number (leave blank if none): ");
            String insNumber = scanner.nextLine().trim();
            if (insNumber.isEmpty()) {
                insNumber = null;
            }

            Patient patient = new Patient(0, name, age, gender, phone, insProvider, insNumber);
            patientDAO.addPatient(patient);

        } catch (Exception e) {
            System.err.println("Error registering patient: " + e.getMessage());
        }
    }

    /**
     * Prompts the user for doctor details and registers a new doctor.
     *
     * @param scanner the input scanner
     */
    private static void registerDoctor(Scanner scanner) {
        try {
            System.out.print("Enter doctor name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter phone number: ");
            String phone = scanner.nextLine().trim();

            System.out.print("Enter specialization: ");
            String specialization = scanner.nextLine().trim();

            System.out.print("Enter consultation fee: ");
            double fee = Double.parseDouble(scanner.nextLine().trim());

            Doctor doctor = new Doctor(0, name, phone, specialization, fee);
            doctorDAO.addDoctor(doctor);

        } catch (Exception e) {
            System.err.println("Error registering doctor: " + e.getMessage());
        }
    }

    /**
     * Prompts the user for appointment details and schedules an appointment.
     *
     * @param scanner the input scanner
     */
    private static void scheduleAppointment(Scanner scanner) {
        try {
            System.out.print("Enter patient ID: ");
            int patientId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter doctor ID: ");
            int doctorId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter appointment date (YYYY-MM-DD): ");
            String date = scanner.nextLine().trim();

            appointmentService.scheduleAppointment(patientId, doctorId, date);

        } catch (Exception e) {
            System.err.println("Error scheduling appointment: " + e.getMessage());
        }
    }

    /**
     * Prompts the user for prescription details (multiple entries) and performs
     * a batch insert.
     *
     * @param scanner the input scanner
     */
    private static void addPrescriptionBatch(Scanner scanner) {
        try {
            System.out.print("How many prescriptions to add? ");
            int count = Integer.parseInt(scanner.nextLine().trim());

            List<Prescription> prescriptions = new ArrayList<>();
            for (int i = 1; i <= count; i++) {
                System.out.println("--- Prescription " + i + " ---");

                System.out.print("Enter appointment ID: ");
                int appointmentId = Integer.parseInt(scanner.nextLine().trim());

                System.out.print("Enter medicines (comma-separated): ");
                String medicines = scanner.nextLine().trim();

                System.out.print("Enter notes: ");
                String notes = scanner.nextLine().trim();

                prescriptions.add(new Prescription(0, appointmentId, medicines, notes));
            }

            prescriptionDAO.addPrescriptionsBatch(prescriptions);

        } catch (Exception e) {
            System.err.println("Error adding prescriptions: " + e.getMessage());
        }
    }

    /**
     * Prompts for a patient ID and generates a bill via the stored procedure.
     *
     * @param scanner the input scanner
     */
    private static void generateBill(Scanner scanner) {
        try {
            System.out.print("Enter patient ID to generate bill: ");
            int patientId = Integer.parseInt(scanner.nextLine().trim());

            billingDAO.generateBillViaProcedure(patientId);

        } catch (Exception e) {
            System.err.println("Error generating bill: " + e.getMessage());
        }
    }

    /**
     * Prompts for payment details and processes the payment within a transaction.
     *
     * @param scanner the input scanner
     */
    private static void makePayment(Scanner scanner) {
        try {
            System.out.print("Enter patient ID: ");
            int patientId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter payment amount: ");
            double amount = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Enter payment method (CASH/CARD/INSURANCE): ");
            String method = scanner.nextLine().trim();

            billingService.processPayment(patientId, amount, method);

        } catch (PaymentException pe) {
            System.err.println("Payment failed: " + pe.getMessage());
        } catch (Exception e) {
            System.err.println("Error processing payment: " + e.getMessage());
        }
    }

    /**
     * Prompts for a patient ID and displays their details.
     *
     * @param scanner the input scanner
     */
    private static void viewPatientDetails(Scanner scanner) {
        try {
            System.out.print("Enter patient ID: ");
            int patientId = Integer.parseInt(scanner.nextLine().trim());

            Patient patient = patientDAO.getPatientById(patientId);
            if (patient != null) {
                patient.display();

                List<Appointment> appointments =
                        appointmentDAO.getAppointmentsByPatient(patientId);
                if (appointments.isEmpty()) {
                    System.out.println("No appointments found for this patient.");
                } else {
                    System.out.println("--- Appointments ---");
                    for (Appointment a : appointments) {
                        System.out.println(a);
                    }
                }
            } else {
                System.out.println("No patient found with ID: " + patientId);
            }

        } catch (Exception e) {
            System.err.println("Error retrieving patient details: " + e.getMessage());
        }
    }
}
