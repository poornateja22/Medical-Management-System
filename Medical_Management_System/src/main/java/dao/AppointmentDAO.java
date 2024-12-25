package dao;

import Config.DatabaseConfig;
import model.Appointment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {
    public void addAppointment(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO appointments (patient_id, appointment_date, doctor_name, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, appointment.getPatientId());
            pstmt.setTimestamp(2, Timestamp.valueOf(appointment.getAppointmentDate()));
            pstmt.setString(3, appointment.getDoctorName());
            pstmt.setString(4, appointment.getStatus());
            pstmt.executeUpdate();
        }
    }

    public Appointment getAppointment(int appointmentId) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE appointment_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, appointmentId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Appointment(
                        rs.getInt("appointment_id"),
                        rs.getInt("patient_id"),
                        rs.getTimestamp("appointment_date").toLocalDateTime(),
                        rs.getString("doctor_name"),
                        rs.getString("status")
                );
            }
        }
        return null;
    }

    public void updateAppointment(Appointment appointment) throws SQLException {
        String sql = "UPDATE appointments SET patient_id = ?, appointment_date = ?, doctor_name = ?, status = ? WHERE appointment_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, appointment.getPatientId());
            pstmt.setTimestamp(2, Timestamp.valueOf(appointment.getAppointmentDate()));
            pstmt.setString(3, appointment.getDoctorName());
            pstmt.setString(4, appointment.getStatus());
            pstmt.setInt(5, appointment.getAppointmentId());
            pstmt.executeUpdate();
        }
    }

    public void cancelAppointment(int appointmentId) throws SQLException {
        String sql = "UPDATE appointments SET status = 'cancelled' WHERE appointment_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, appointmentId);
            pstmt.executeUpdate();
        }
    }

    public List<Appointment> getPatientAppointments(int patientId) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE patient_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, patientId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                appointments.add(new Appointment(
                        rs.getInt("appointment_id"),
                        rs.getInt("patient_id"),
                        rs.getTimestamp("appointment_date").toLocalDateTime(),
                        rs.getString("doctor_name"),
                        rs.getString("status")
                ));
            }
        }
        return appointments;
    }
}
