package io.spring.uni_portal.service.StudentGradeService;

import io.spring.uni_portal.dto.StudentGrade.*;
import io.spring.uni_portal.response.Response;

import java.util.List;

public interface IStudentGradeService {
    Response<StudentGradeSubmissionResponse> submitGrade(StudentGradeSubmissionRequest request);
    Response<StudentGradeGradingResponse> gradeSubmission(Long studentGradeId, Double score, String feedback);

    Response<List<StudentSubmissionStatusResponse>> getSubmissionStatus(Long classStudentId, Long gradeEventId);
    Response<List<StudentAssignmentResponse>> getAssignmentsByStudent();
}
