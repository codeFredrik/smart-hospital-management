-- Smart Hospital Management & Billing System
-- Database Schema

-- Create database
CREATE DATABASE IF NOT EXISTS hospital_db;
USE hospital_db;

-- Patients table
CREATE TABLE IF NOT EXISTS Patients (
    patient_id        INT AUTO_INCREMENT PRIMARY KEY,
    name              VARCHAR(100) NOT NULL,
    age               INT NOT NULL,
    gender            VARCHAR(10) NOT NULL,
    phone             VARCHAR(20) NOT NULL,
    insurance_provider VARCHAR(100),
    insurance_number  VARCHAR(50)
);

-- Doctors table
CREATE TABLE IF NOT EXISTS Doctors (
    doctor_id         INT AUTO_INCREMENT PRIMARY KEY,
    name              VARCHAR(100) NOT NULL,
    specialization    VARCHAR(100) NOT NULL,
    consultation_fee  DOUBLE NOT NULL
);

-- Appointments table
CREATE TABLE IF NOT EXISTS Appointments (
    appointment_id    INT AUTO_INCREMENT PRIMARY KEY,
    patient_id        INT NOT NULL,
    doctor_id         INT NOT NULL,
    appointment_date  DATE NOT NULL,
    status            VARCHAR(20) NOT NULL DEFAULT 'SCHEDULED',
    FOREIGN KEY (patient_id) REFERENCES Patients(patient_id),
    FOREIGN KEY (doctor_id)  REFERENCES Doctors(doctor_id)
);

-- Prescriptions table
CREATE TABLE IF NOT EXISTS Prescriptions (
    prescription_id   INT AUTO_INCREMENT PRIMARY KEY,
    appointment_id    INT NOT NULL,
    medicines         VARCHAR(500) NOT NULL,
    notes             VARCHAR(500),
    FOREIGN KEY (appointment_id) REFERENCES Appointments(appointment_id)
);

-- Bills table
CREATE TABLE IF NOT EXISTS Bills (
    bill_id           INT AUTO_INCREMENT PRIMARY KEY,
    patient_id        INT NOT NULL,
    appointment_id    INT,
    total_amount      DOUBLE NOT NULL,
    bill_status       VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    FOREIGN KEY (patient_id)     REFERENCES Patients(patient_id),
    FOREIGN KEY (appointment_id) REFERENCES Appointments(appointment_id)
);

-- Payments table
CREATE TABLE IF NOT EXISTS Payments (
    payment_id        INT AUTO_INCREMENT PRIMARY KEY,
    bill_id           INT NOT NULL,
    payment_amount    DOUBLE NOT NULL,
    payment_method    VARCHAR(50) NOT NULL,
    payment_status    VARCHAR(20) NOT NULL DEFAULT 'SUCCESS',
    payment_time      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (bill_id) REFERENCES Bills(bill_id)
);

-- Stored Procedure: GenerateBill
-- Calculates total consultation fees from SCHEDULED appointments for a patient
-- and inserts a record into the Bills table.
DROP PROCEDURE IF EXISTS GenerateBill;

DELIMITER $$

CREATE PROCEDURE GenerateBill(IN p_patient_id INT)
BEGIN
    DECLARE v_total DOUBLE DEFAULT 0.0;
    DECLARE v_appointment_id INT DEFAULT NULL;

    -- Sum consultation fees for all SCHEDULED appointments for the patient
    SELECT COALESCE(SUM(d.consultation_fee), 0), MAX(a.appointment_id)
    INTO v_total, v_appointment_id
    FROM Appointments a
    JOIN Doctors d ON a.doctor_id = d.doctor_id
    WHERE a.patient_id = p_patient_id
      AND a.status = 'SCHEDULED';

    -- Insert bill record
    INSERT INTO Bills (patient_id, appointment_id, total_amount, bill_status)
    VALUES (p_patient_id, v_appointment_id, v_total, 'PENDING');
END$$

DELIMITER ;
