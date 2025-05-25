package io.spring.uni_portal.repository;

import io.spring.uni_portal.dto.Attendance.SessionViewDTO;
import io.spring.uni_portal.model.Attendance;
import io.spring.uni_portal.response.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    boolean existsByAttendanceSession_SessionIdAndClassSubjectStudent_ClassSubjectStudentId(Long sessionId, Long classSubjectStudentId);

    List<Attendance> findByClassSubjectStudent_ClassSubjectStudentId(Long classSubjectStudentId);


}
