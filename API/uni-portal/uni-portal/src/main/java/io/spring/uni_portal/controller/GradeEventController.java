package io.spring.uni_portal.controller;

import io.spring.uni_portal.dto.GradeEvent.*;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.GradeEventService.IGradeEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grade-event")
public class GradeEventController {

    @Autowired
    private IGradeEventService gradeEventService;

    @PostMapping("/midterm")
    public Response<?> createMidtermGradeEvent(@RequestBody GradeEventRequest request) {
        return gradeEventService.createMidtermGradeEvent(request);
    }

    @GetMapping("/by-class/{classStudentId}")
    public ResponseEntity<List<GradeEventResponseDto>> getGradeEventsByClass(@PathVariable Long classStudentId) {
        List<GradeEventResponseDto> response = gradeEventService.getGradeEventsByClass(classStudentId);
        return ResponseEntity.ok(response);
    }


    // API cập nhật bài tập
    @PutMapping("/{gradeEventId}")
    public Response<?> updateGradeEvent(@PathVariable Long gradeEventId, @RequestBody GradeEventRequest request) {
        return gradeEventService.updateGradeEvent(gradeEventId, request);
    }

    // API xem chi tiết bài tập
    @GetMapping("/{gradeEventId}")
    public Response<GradeEventResponse> getGradeEventById(@PathVariable Long gradeEventId) {
        return gradeEventService.getGradeEventById(gradeEventId);
    }




}
