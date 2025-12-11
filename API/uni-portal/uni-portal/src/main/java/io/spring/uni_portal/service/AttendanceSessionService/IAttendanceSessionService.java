package io.spring.uni_portal.service.AttendanceSessionService;

import io.spring.uni_portal.dto.AttendanceSession.DailyClassScheduleDTO;
import io.spring.uni_portal.response.Response;

import java.time.LocalDate;
import java.util.List;

public interface IAttendanceSessionService {
    Response<List<DailyClassScheduleDTO>> getClassesByScheduledDate(LocalDate date);
}
