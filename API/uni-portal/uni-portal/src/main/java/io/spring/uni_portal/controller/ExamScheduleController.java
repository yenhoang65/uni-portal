package io.spring.uni_portal.controller;

import io.spring.uni_portal.dto.ExamSchedule.ExamScheduleRequest;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.ExamScheduleService.IExamScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exam-schedule")
public class ExamScheduleController {

    @Autowired
    private IExamScheduleService examScheduleService;

    @PostMapping("/midterm")
    public Response<?> createMidtermExamSchedule(@RequestBody ExamScheduleRequest request) {
        return examScheduleService.createMidtermExamSchedule(request);
    }
}
