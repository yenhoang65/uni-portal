package io.spring.uni_portal.service.AttendanceSessionService;

import io.spring.uni_portal.dto.AttendanceSession.DailyClassScheduleDTO;
import io.spring.uni_portal.model.AttendanceSession;
import io.spring.uni_portal.model.TeachingAssignment;
import io.spring.uni_portal.model.TeachingScheduleRequest;
import io.spring.uni_portal.model.TermClass;
import io.spring.uni_portal.repository.AttendanceSessionRepository;
import io.spring.uni_portal.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceSessionServiceImpl implements IAttendanceSessionService {

    @Autowired
    private AttendanceSessionRepository attendanceSessionRepository;

    @Override
    public Response<List<DailyClassScheduleDTO>> getClassesByScheduledDate(LocalDate date) {
        // Xác định khoảng thời gian trong ngày
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();

        // Lọc theo khoảng LocalDateTime
        List<AttendanceSession> sessions = attendanceSessionRepository
                .findByScheduledDateBetween(startOfDay, endOfDay);

        // Chuyển sang DTO
        List<DailyClassScheduleDTO> result = sessions.stream().map(session -> {
            TeachingScheduleRequest tsr = session.getClassStudent().getTeachingScheduleRequest();
            TeachingAssignment assignment = tsr.getAssignment();
            TermClass termClass = assignment.getTermClass();

            return new DailyClassScheduleDTO(
                    session.getSessionId(),
                    termClass.getClassname(),
                    assignment.getSubject().getSubjectName(),
                    tsr.getClassType(),
                    assignment.getLecturer().getUser().getUserName(),
                    tsr.getClassroom().getClassroomName(),
                    tsr.getLesson(),
                    session.getScheduledDate().toLocalDate(),
                    session.getStatus()
            );
        }).collect(Collectors.toList());

        return Response.success("Danh sách lớp học theo ngày " + date, result);
    }
}
