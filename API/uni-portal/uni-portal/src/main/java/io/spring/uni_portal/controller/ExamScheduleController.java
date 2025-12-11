package io.spring.uni_portal.controller;

import io.spring.uni_portal.dto.ExamSchedule.ExamScheduleAdminResponse;
import io.spring.uni_portal.dto.ExamSchedule.ExamScheduleRequest;
import io.spring.uni_portal.dto.ExamSchedule.ExamScheduleResponse;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.ExamScheduleService.IExamScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exam-schedule")
public class ExamScheduleController {

    @Autowired
    private IExamScheduleService examScheduleService;

    @PostMapping("/midterm")
    public Response<?> createMidtermExamSchedule(@RequestBody ExamScheduleRequest request) {
        return examScheduleService.createMidtermExamSchedule(request);
    }

    //lịch thi cho sv
    @GetMapping("/student/all")
    public Response<?> getExamSchedulesForStudent(
            @RequestParam(required = false) String semester,
            @RequestParam(required = false) String schoolyear
    ) {
        return examScheduleService.getExamSchedulesForCurrentStudent(semester, schoolyear);
    }

    //lấy tất cả lịch thi
    @GetMapping("/all")
    public ResponseEntity<Response<List<ExamScheduleAdminResponse>>> getAllExamSchedules(
            @RequestParam(required = false) String semester,
            @RequestParam(required = false) String schoolyear
    ) {
        Response<List<ExamScheduleAdminResponse>> response = examScheduleService.getAllExamSchedules(semester, schoolyear);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{examScheduleId}")
    public ResponseEntity<Response<ExamScheduleResponse>> updateExamSchedule(
            @PathVariable Long examScheduleId,
            @RequestBody ExamScheduleRequest request) {
        return ResponseEntity.ok(examScheduleService.updateExamSchedule(examScheduleId, request));
    }

    @GetMapping("/{examScheduleId}")
    public ResponseEntity<Response<ExamScheduleResponse>> getExamScheduleById(@PathVariable Long examScheduleId) {
        Response<ExamScheduleResponse> response = examScheduleService.getExamScheduleById(examScheduleId);
        return ResponseEntity.ok(response);
    }


}
