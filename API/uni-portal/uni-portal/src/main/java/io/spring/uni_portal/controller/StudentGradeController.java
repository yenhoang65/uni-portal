package io.spring.uni_portal.controller;
import io.spring.uni_portal.dto.StudentGrade.*;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.StudentGradeService.IStudentGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student-grade")
public class StudentGradeController {

    @Autowired
    private IStudentGradeService studentGradeService;

    @PostMapping("/submit")
    public Response<StudentGradeSubmissionResponse> submitGrade(@RequestBody StudentGradeSubmissionRequest request) {
        return studentGradeService.submitGrade(request);
    }

    @PostMapping("/grade/{studentGradeId}")
    public Response<StudentGradeGradingResponse> gradeSubmission(
            @PathVariable Long studentGradeId,
            @RequestBody StudentGradeGradingRequest request) {
        return studentGradeService.gradeSubmission(
                studentGradeId,
                request.getScore(),
                request.getFeedback()
        );
    }

    @GetMapping("/submission-status")
    public Response<List<StudentSubmissionStatusResponse>> getSubmissionStatus(
            @RequestParam Long classStudentId,
            @RequestParam Long gradeEventId) {
        return studentGradeService.getSubmissionStatus(classStudentId, gradeEventId);
    }

    @GetMapping("/assignments")
    public ResponseEntity<Response<List<StudentAssignmentResponse>>> getAssignmentsByStudent() {
        Response<List<StudentAssignmentResponse>> response = studentGradeService.getAssignmentsByStudent();
        return ResponseEntity.ok(response);
    }
}
