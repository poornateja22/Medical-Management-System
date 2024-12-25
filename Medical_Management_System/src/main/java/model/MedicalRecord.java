package model;

import java.time.LocalDate;

public class MedicalRecord {
    private int recordId;
    private int patientId;
    private String diagnosis;
    private String treatment;
    private LocalDate date;

    // Constructors, getters, and setters
    public MedicalRecord(int recordId, int patientId, String diagnosis, String treatment, LocalDate date) {
        this.recordId = recordId;
        this.patientId = patientId;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.date = date;
    }

    // Getters and Setters
    public int getRecordId() { return recordId; }
    public void setRecordId(int recordId) { this.recordId = recordId; }
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public String getTreatment() { return treatment; }
    public void setTreatment(String treatment) { this.treatment = treatment; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
