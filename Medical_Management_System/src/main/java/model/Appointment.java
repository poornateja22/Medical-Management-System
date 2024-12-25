package model;

import java.time.LocalDateTime;

public class Appointment {
    private int appointmentId;
    private int patientId;
    private LocalDateTime appointmentDate;
    private String doctorName;
    private String status;

    // Constructors, getters, and setters
    public Appointment(int appointmentId, int patientId, LocalDateTime appointmentDate,
                       String doctorName, String status) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.appointmentDate = appointmentDate;
        this.doctorName = doctorName;
        this.status = status;
    }

    // Getters and Setters
    public int getAppointmentId() { return appointmentId; }
    public void setAppointmentId(int appointmentId) { this.appointmentId = appointmentId; }
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public LocalDateTime getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDateTime appointmentDate) { this.appointmentDate = appointmentDate; }
    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}