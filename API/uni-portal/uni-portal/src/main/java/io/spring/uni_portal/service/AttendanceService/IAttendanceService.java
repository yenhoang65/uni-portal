package io.spring.uni_portal.service.AttendanceService;

import io.spring.uni_portal.dto.Attendance.*;
import io.spring.uni_portal.response.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAttendanceService {
    Response<String> markAttendance(AttendanceRequestDTO dto);
    Response<String> updateAttendance(Long attendanceId, AttendanceRequestDTO dto);
    Response<List<AttendanceViewDTO>> getAttendanceByClassSubjectStudent(Long classSubjectStudentId);
    Response<List<SessionViewDTO>> getSessionsByClassStudent(Long classStudentId);
    Response<List<StudentInClassDTO>> getStudentsInClass(Long classStudentId);
    Response<List<SuccessfulClassDTO>> getSuccessfulClasses();

    Response<List<SuccessfulClassDTO>> getSuccessfulClassesByCurrentStudent();

    Response<List<AttendanceResponseDTO>> getAttendanceBySession(Long sessionId);

    Response<List<AttendanceViewDTO>> getAttendanceByClassStudent(Long classStudentId);
}
