# Smart Hospital Management & Billing System — Sample Output

This document shows representative console output for each menu option.

---

## Prerequisites

1. Install MySQL and create the database by running `schema.sql`:
   ```bash
   mysql -u root -p < src/main/resources/schema.sql
   ```
2. Update `src/main/resources/config.properties` with your MySQL credentials.
3. Build the project:
   ```bash
   mvn clean package
   ```
4. Run the application:
   ```bash
   java -jar target/smart-hospital-1.0-SNAPSHOT.jar
   ```

---

## Sample Session

### Main Menu
```
==== Smart Hospital Management System ====
1. Register Patient
2. Register Doctor
3. Schedule Appointment
4. Add Prescription (Batch)
5. Generate Bill (Stored Procedure)
6. Make Payment (Transaction)
7. View Patient Details
8. Exit
==========================================
Enter choice:
```

---

### 1. Register Patient
```
Enter choice: 1
Enter patient name: Alice Johnson
Enter age: 34
Enter gender (Male/Female/Other): Female
Enter phone number: 555-1234
Enter insurance provider (leave blank if none): BlueCross
Enter insurance number (leave blank if none): BC-9876543
Patient added successfully with ID: 1
```

### Register Uninsured Patient
```
Enter choice: 1
Enter patient name: Bob Smith
Enter age: 52
Enter gender (Male/Female/Other): Male
Enter phone number: 555-5678
Enter insurance provider (leave blank if none):
Enter insurance number (leave blank if none):
Patient added successfully with ID: 2
```

---

### 2. Register Doctor
```
Enter choice: 2
Enter doctor name: Dr. Sarah Chen
Enter phone number: 555-9000
Enter specialization: Cardiology
Enter consultation fee: 250.00
Doctor added successfully with ID: 1
```

```
Enter choice: 2
Enter doctor name: Dr. Mark Rivera
Enter phone number: 555-9001
Enter specialization: General Practice
Enter consultation fee: 100.00
Doctor added successfully with ID: 2
```

---

### 3. Schedule Appointment
```
Enter choice: 3
Enter patient ID: 1
Enter doctor ID: 1
Enter appointment date (YYYY-MM-DD): 2025-08-15
Appointment scheduled with ID: 1
Appointment scheduled successfully for patient 'Alice Johnson' with Dr. Dr. Sarah Chen on 2025-08-15.
```

```
Enter choice: 3
Enter patient ID: 2
Enter doctor ID: 2
Enter appointment date (YYYY-MM-DD): 2025-08-16
Appointment scheduled with ID: 2
Appointment scheduled successfully for patient 'Bob Smith' with Dr. Dr. Mark Rivera on 2025-08-16.
```

---

### 4. Add Prescription (Batch)
```
Enter choice: 4
How many prescriptions to add? 2
--- Prescription 1 ---
Enter appointment ID: 1
Enter medicines (comma-separated): Atorvastatin 40mg, Aspirin 81mg
Enter notes: Take with food. Follow-up in 4 weeks.
--- Prescription 2 ---
Enter appointment ID: 2
Enter medicines (comma-separated): Amoxicillin 500mg, Ibuprofen 400mg
Enter notes: Complete the full antibiotic course.
Batch insert complete. Inserted 2 prescription(s).
```

---

### 5. Generate Bill (Stored Procedure)
```
Enter choice: 5
Enter patient ID to generate bill: 1
Bill generated successfully for patient ID: 1
```

```
Enter choice: 5
Enter patient ID to generate bill: 2
Bill generated successfully for patient ID: 2
```

> The `GenerateBill` stored procedure sums consultation fees for all `SCHEDULED`
> appointments and inserts a `PENDING` bill. Patient 1 (Alice) has one Cardiology
> appointment at $250.00, so her bill total is $250.00.

---

### 6. Make Payment (Transaction) — Insured Patient

Alice has BlueCross insurance, so a **20% discount** is applied.

```
Enter choice: 6
Enter patient ID: 1
Enter payment amount: 200.00
Enter payment method (CASH/CARD/INSURANCE): INSURANCE
Final amount after billing strategy (Insurance 20% discount): 200.0
Payment processed successfully. Bill ID 1 marked as PAID.
```

---

### 6. Make Payment (Transaction) — Standard Patient

Bob has no insurance, so the **full fee** is charged.

```
Enter choice: 6
Enter patient ID: 2
Enter payment amount: 100.00
Enter payment method (CASH/CARD/INSURANCE): CASH
Final amount after billing strategy (Standard): 100.0
Payment processed successfully. Bill ID 2 marked as PAID.
```

---

### 7. View Patient Details
```
Enter choice: 7
Enter patient ID: 1
------ Patient Details ------
ID               : 1
Name             : Alice Johnson
Age              : 34
Gender           : Female
Phone            : 555-1234
Insurance Prov.  : BlueCross
Insurance No.    : BC-9876543
-----------------------------
--- Appointments ---
Appointment{id=1, patientId=1, doctorId=1, date='2025-08-15', status='SCHEDULED'}
```

---

### 8. Exit
```
Enter choice: 8
Exiting... Goodbye!
```

---

## Key JDBC Features Demonstrated

| Feature | Where Used |
|---|---|
| **Transaction Management** | `BillingService.processPayment()` — `setAutoCommit(false)`, `commit()`, `rollback()` |
| **Batch Processing** | `PrescriptionDAO.addPrescriptionsBatch()` — `addBatch()`, `executeBatch()` |
| **Stored Procedure** | `BillingDAO.generateBillViaProcedure()` — `CallableStatement` |
| **PreparedStatement** | All DAO CRUD operations |
| **Polymorphism** | `StandardBilling` vs `InsuranceBilling` via `Billable` interface |
| **OOP Inheritance** | `Patient` and `Doctor` extend abstract `Person` |
