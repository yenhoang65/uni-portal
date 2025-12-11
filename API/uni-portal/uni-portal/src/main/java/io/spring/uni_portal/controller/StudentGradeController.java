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


    @PostMapping("/grade/offline")
    public Response<List<StudentGradeOfflineResponse>> gradeOfflineSubmissionsV2(@RequestBody List<GradeOfflineRequest> requests) {
        return studentGradeService.gradeOfflineSubmissionBatch(requests);
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

    @PostMapping("/grade/batch")
    public ResponseEntity<Response<List<BatchGradeResponse>>> gradeMultiple(@RequestBody BatchGradeRequest request) {
        return ResponseEntity.ok(studentGradeService.gradeMultipleSubmissions(request.getGrades()));
    }

    @GetMapping("/assignments/by-class-student/{classStudentId}")
    public ResponseEntity<Response<List<StudentAssignmentResponse>>> getAssignmentsByClassStudentId(
            @PathVariable Long classStudentId) {
        Response<List<StudentAssignmentResponse>> response = studentGradeService.getAssignmentsByClassStudentId(classStudentId);
        return ResponseEntity.ok(response);
    }

}
