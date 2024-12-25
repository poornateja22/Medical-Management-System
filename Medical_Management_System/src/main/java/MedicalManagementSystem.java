

import dao.*;
import model.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MedicalManagementSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final PatientDAO patientDAO = new PatientDAO();
    private static final AppointmentDAO appointmentDAO = new AppointmentDAO();
    private static final MedicalRecordDAO medicalRecordDAO = new MedicalRecordDAO();
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        while (true) {
            displayMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                switch (choice) {
                    case 1:
                        handlePatientMenu();
                        break;
                    case 2:
                        handleAppointmentMenu();
                        break;
                    case 3:
                        handleMedicalRecordMenu();
                        break;
                    case 4:
                        System.out.println("Exiting system...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n=== Clinic Management System ===");
        System.out.println("1. Patient Management");
        System.out.println("2. Appointment Management");
        System.out.println("3. Medical Record Management");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void handlePatientMenu() throws SQLException {
        while (true) {
            System.out.println("\n=== Patient Management ===");
            System.out.println("1. Add Patient");
            System.out.println("2. View Patient");
            System.out.println("3. Update Patient");
            System.out.println("4. Delete Patient");
            System.out.println("5. View All Patients");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addPatient();
                    break;
                case 2:
                    viewPatient();
                    break;
                case 3:
                    updatePatient();
                    break;
                case 4:
                    deletePatient();
                    break;
                case 5:
                    viewAllPatients();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void handleAppointmentMenu() throws SQLException {
        while (true) {
            System.out.println("\n=== Appointment Management ===");
            System.out.println("1. Schedule Appointment");
            System.out.println("2. View Appointment");
            System.out.println("3. Update Appointment");
            System.out.println("4. Cancel Appointment");
            System.out.println("5. View Patient Appointments");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    scheduleAppointment();
                    break;
                case 2:
                    viewAppointment();
                    break;
                case 3:
                    updateAppointment();
                    break;
                case 4:
                    cancelAppointment();
                    break;
                case 5:
                    viewPatientAppointments();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void handleMedicalRecordMenu() throws SQLException {
        while (true) {
            System.out.println("\n=== Medical Record Management ===");
            System.out.println("1. Add Medical Record");
            System.out.println("2. View Medical Record");
            System.out.println("3. Update Medical Record");
            System.out.println("4. Delete Medical Record");
            System.out.println("5. View Patient Medical Records");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addMedicalRecord();
                    break;
                case 2:
                    viewMedicalRecord();
                    break;
                case 3:
                    updateMedicalRecord();
                    break;
                case 4:
                    deleteMedicalRecord();
                    break;
                case 5:
                    viewPatientMedicalRecords();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // Patient Management Methods
    private static void addPatient() throws SQLException {
        System.out.println("\n=== Add New Patient ===");
        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter date of birth (YYYY-MM-DD): ");
        LocalDate dob = LocalDate.parse(scanner.nextLine(), dateFormatter);

        System.out.print("Enter gender: ");
        String gender = scanner.nextLine();

        System.out.print("Enter contact info: ");
        String contactInfo = scanner.nextLine();

        Patient patient = new Patient(0, name, dob, gender, contactInfo);
        patientDAO.addPatient(patient);
        System.out.println("Patient added successfully!");
    }

    private static void viewPatient() throws SQLException {
        System.out.print("Enter patient ID: ");
        int patientId = scanner.nextInt();
        Patient patient = patientDAO.getPatient(patientId);

        if (patient != null) {
            System.out.println("\nPatient Details:");
            System.out.println("ID: " + patient.getPatientId());
            System.out.println("Name: " + patient.getName());
            System.out.println("Date of Birth: " + patient.getDateOfBirth());
            System.out.println("Gender: " + patient.getGender());
            System.out.println("Contact Info: " + patient.getContactInfo());
        } else {
            System.out.println("Patient not found.");
        }
    }

    private static void updatePatient() throws SQLException {
        System.out.print("Enter patient ID to update: ");
        int patientId = scanner.nextInt();
        scanner.nextLine();

        Patient patient = patientDAO.getPatient(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.print("Enter new name (or press enter to skip): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            patient.setName(name);
        }

        System.out.print("Enter new date of birth (YYYY-MM-DD) (or press enter to skip): ");
        String dobStr = scanner.nextLine();
        if (!dobStr.isEmpty()) {
            patient.setDateOfBirth(LocalDate.parse(dobStr, dateFormatter));
        }

        System.out.print("Enter new gender (or press enter to skip): ");
        String gender = scanner.nextLine();
        if (!gender.isEmpty()) {
            patient.setGender(gender);
        }

        System.out.print("Enter new contact info (or press enter to skip): ");
        String contactInfo = scanner.nextLine();
        if (!contactInfo.isEmpty()) {
            patient.setContactInfo(contactInfo);
        }

        patientDAO.updatePatient(patient);
        System.out.println("Patient updated successfully!");
    }

    private static void deletePatient() throws SQLException {
        System.out.print("Enter patient ID to delete: ");
        int patientId = scanner.nextInt();
        patientDAO.deletePatient(patientId);
        System.out.println("Patient deleted successfully!");
    }

    private static void viewAllPatients() throws SQLException {
        List<Patient> patients = patientDAO.getAllPatients();
        if (patients.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }

        System.out.println("\nAll Patients:");
        for (Patient patient : patients) {
            System.out.println("ID: " + patient.getPatientId() +
                    ", Name: " + patient.getName() +
                    ", DOB: " + patient.getDateOfBirth() +
                    ", Gender: " + patient.getGender());
        }
    }

    // Appointment Management Methods
    private static void scheduleAppointment() throws SQLException {
        System.out.println("\n=== Schedule New Appointment ===");
        System.out.print("Enter patient ID: ");
        int patientId = scanner.nextInt();
        scanner.nextLine();

        if (patientDAO.getPatient(patientId) == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.print("Enter appointment date and time (YYYY-MM-DD HH:mm): ");
        LocalDateTime appointmentDate = LocalDateTime.parse(scanner.nextLine(), dateTimeFormatter);

        System.out.print("Enter doctor name: ");
        String doctorName = scanner.nextLine();

        Appointment appointment = new Appointment(0, patientId, appointmentDate, doctorName, "scheduled");
        appointmentDAO.addAppointment(appointment);
        System.out.println("Appointment scheduled successfully!");
    }


    private static void viewAppointment() throws SQLException {
        System.out.print("Enter appointment ID: ");
        int appointmentId = scanner.nextInt();
        Appointment appointment = appointmentDAO.getAppointment(appointmentId);

        if (appointment != null) {
            System.out.println("\nAppointment Details:");
            System.out.println("ID: " + appointment.getAppointmentId());
            System.out.println("Patient ID: " + appointment.getPatientId());
            System.out.println("Date: " + appointment.getAppointmentDate());
            System.out.println("Doctor: " + appointment.getDoctorName());
            System.out.println("Status: " + appointment.getStatus());
        } else {
            System.out.println("Appointment not found.");
        }
    }

    private static void updateAppointment() throws SQLException {
        System.out.print("Enter appointment ID to update: ");
        int appointmentId = scanner.nextInt();
        scanner.nextLine();

        Appointment appointment = appointmentDAO.getAppointment(appointmentId);
        if (appointment == null) {
            System.out.println("Appointment not found.");
            return;
        }

        System.out.print("Enter new date and time (YYYY-MM-DD HH:mm) (or press enter to skip): ");
        String dateStr = scanner.nextLine();
        if (!dateStr.isEmpty()) {
            appointment.setAppointmentDate(LocalDateTime.parse(dateStr, dateTimeFormatter));
        }

        System.out.print("Enter new doctor name (or press enter to skip): ");
        String doctorName = scanner.nextLine();
        if (!doctorName.isEmpty()) {
            appointment.setDoctorName(doctorName);
        }

        System.out.print("Enter new status (scheduled/completed/cancelled) (or press enter to skip): ");
        String status = scanner.nextLine();
        if (!status.isEmpty()) {
            appointment.setStatus(status);
        }

        appointmentDAO.updateAppointment(appointment);
        System.out.println("Appointment updated successfully!");
    }

    private static void cancelAppointment() throws SQLException {
        System.out.print("Enter appointment ID to cancel: ");
        int appointmentId = scanner.nextInt();
        appointmentDAO.cancelAppointment(appointmentId);
        System.out.println("Appointment cancelled successfully!");
    }

    private static void viewPatientAppointments() throws SQLException {
        System.out.print("Enter patient ID: ");
        int patientId = scanner.nextInt();

        List<Appointment> appointments = appointmentDAO.getPatientAppointments(patientId);
        if (appointments.isEmpty()) {
            System.out.println("No appointments found for this patient.");
            return;
        }

        System.out.println("\nPatient Appointments:");
        for (Appointment appointment : appointments) {
            System.out.println("ID: " + appointment.getAppointmentId() +
                    ", Date: " + appointment.getAppointmentDate() +
                    ", Doctor: " + appointment.getDoctorName() +
                    ", Status: " + appointment.getStatus());
        }
    }

    private static void addMedicalRecord() throws SQLException {
        System.out.println("\n=== Add New Medical Record ===");
        System.out.print("Enter patient ID: ");
        int patientId = scanner.nextInt();
        scanner.nextLine();

        if (patientDAO.getPatient(patientId) == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.print("Enter diagnosis: ");
        String diagnosis = scanner.nextLine();

        System.out.print("Enter treatment: ");
        String treatment = scanner.nextLine();

        System.out.print("Enter date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine(), dateFormatter);

        MedicalRecord record = new MedicalRecord(0, patientId, diagnosis, treatment, date);
        medicalRecordDAO.addMedicalRecord(record);
        System.out.println("Medical record added successfully!");
    }

    private static void viewMedicalRecord() throws SQLException {
        System.out.print("Enter record ID: ");
        int recordId = scanner.nextInt();
        MedicalRecord record = medicalRecordDAO.getMedicalRecord(recordId);

        if (record != null) {
            System.out.println("\nMedical Record Details:");
            System.out.println("Record ID: " + record.getRecordId());
            System.out.println("Patient ID: " + record.getPatientId());
            System.out.println("Diagnosis: " + record.getDiagnosis());
            System.out.println("Treatment: " + record.getTreatment());
            System.out.println("Date: " + record.getDate());
        } else {
            System.out.println("Medical record not found.");
        }
    }

    private static void updateMedicalRecord() throws SQLException {
        System.out.print("Enter record ID to update: ");
        int recordId = scanner.nextInt();
        scanner.nextLine();

        MedicalRecord record = medicalRecordDAO.getMedicalRecord(recordId);
        if (record == null) {
            System.out.println("Medical record not found.");
            return;
        }

        System.out.print("Enter new diagnosis (or press enter to skip): ");
        String diagnosis = scanner.nextLine();
        if (!diagnosis.isEmpty()) {
            record.setDiagnosis(diagnosis);
        }

        System.out.print("Enter new treatment (or press enter to skip): ");
        String treatment = scanner.nextLine();
        if (!treatment.isEmpty()) {
            record.setTreatment(treatment);
        }

        System.out.print("Enter new date (YYYY-MM-DD) (or press enter to skip): ");
        String dateStr = scanner.nextLine();
        if (!dateStr.isEmpty()) {
            record.setDate(LocalDate.parse(dateStr, dateFormatter));
        }

        medicalRecordDAO.updateMedicalRecord(record);
        System.out.println("Medical record updated successfully!");
    }

    private static void deleteMedicalRecord() throws SQLException {
        System.out.print("Enter record ID to delete: ");
        int recordId = scanner.nextInt();
        medicalRecordDAO.deleteMedicalRecord(recordId);
        System.out.println("Medical record deleted successfully!");
    }

    private static void viewPatientMedicalRecords() throws SQLException {
        System.out.print("Enter patient ID: ");
        int patientId = scanner.nextInt();

        List<MedicalRecord> records = medicalRecordDAO.getPatientMedicalRecords(patientId);
        if (records.isEmpty()) {
            System.out.println("No medical records found for this patient.");
            return;
        }

        System.out.println("\nPatient Medical Records:");
        for (MedicalRecord record : records) {
            System.out.println("Record ID: " + record.getRecordId() +
                    ", Date: " + record.getDate() +
                    ", Diagnosis: " + record.getDiagnosis());
        }
    }
}
