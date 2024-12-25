package dao;

import Config.DatabaseConfig;
import model.MedicalRecord;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecordDAO {
    public void addMedicalRecord(MedicalRecord record) throws SQLException {
        String sql = "INSERT INTO medical_records (patient_id, diagnosis, treatment, date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, record.getPatientId());
            pstmt.setString(2, record.getDiagnosis());
            pstmt.setString(3, record.getTreatment());
            pstmt.setDate(4, Date.valueOf(record.getDate()));
            pstmt.executeUpdate();
        }
    }

    public MedicalRecord getMedicalRecord(int recordId) throws SQLException {
        String sql = "SELECT * FROM medical_records WHERE record_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, recordId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new MedicalRecord(
                        rs.getInt("record_id"),
                        rs.getInt("patient_id"),
                        rs.getString("diagnosis"),
                        rs.getString("treatment"),
                        rs.getDate("date").toLocalDate()
                );
            }
        }
        return null;
    }

    public void updateMedicalRecord(MedicalRecord record) throws SQLException {
        String sql = "UPDATE medical_records SET diagnosis = ?, treatment = ?, date = ? WHERE record_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, record.getDiagnosis());
            pstmt.setString(2, record.getTreatment());
            pstmt.setDate(3, Date.valueOf(record.getDate()));
            pstmt.setInt(4, record.getRecordId());
            pstmt.executeUpdate();
        }
    }

    public void deleteMedicalRecord(int recordId) throws SQLException {
        String sql = "DELETE FROM medical_records WHERE record_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, recordId);
            pstmt.executeUpdate();
        }
    }

    public List<MedicalRecord> getPatientMedicalRecords(int patientId) throws SQLException {
        List<MedicalRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM medical_records WHERE patient_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, patientId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                records.add(new MedicalRecord(
                        rs.getInt("record_id"),
                        rs.getInt("patient_id"),
                        rs.getString("diagnosis"),
                        rs.getString("treatment"),
                        rs.getDate("date").toLocalDate()
                ));
            }
        }
        return records;
    }
}
