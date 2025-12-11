package io.spring.uni_portal.controller;

import io.spring.uni_portal.dto.AttendanceSession.DailyClassScheduleDTO;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.AttendanceSessionService.AttendanceSessionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance-sessions")
public class AttendanceSessionController {

    @Autowired
    private AttendanceSessionServiceImpl attendanceSessionService;


    @GetMapping("/by-date")
    public Response<List<DailyClassScheduleDTO>> getClassesByScheduledDate(
            @RequestParam("scheduledDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate scheduledDate) {
        return attendanceSessionService.getClassesByScheduledDate(scheduledDate);
    }

}