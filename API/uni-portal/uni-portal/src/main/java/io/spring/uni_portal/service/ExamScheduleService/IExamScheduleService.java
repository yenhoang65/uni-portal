package io.spring.uni_portal.service.ExamScheduleService;

import io.spring.uni_portal.dto.ExamSchedule.ExamScheduleAdminResponse;
import io.spring.uni_portal.dto.ExamSchedule.ExamScheduleRequest;
import io.spring.uni_portal.dto.ExamSchedule.ExamScheduleResponse;
import io.spring.uni_portal.dto.ExamSchedule.ExamScheduleStudentResponse;
import io.spring.uni_portal.response.Response;

import java.util.List;

public interface IExamScheduleService {
    Response<ExamScheduleResponse> createMidtermExamSchedule(ExamScheduleRequest request);
    Response<List<ExamScheduleStudentResponse>> getExamSchedulesForCurrentStudent(String semester, String schoolyear);
    Response<List<ExamScheduleAdminResponse>> getAllExamSchedules(String semester, String schoolyear);
    Response<ExamScheduleResponse> updateExamSchedule(Long examScheduleId, ExamScheduleRequest request);
    Response<ExamScheduleResponse> getExamScheduleById(Long examScheduleId);


}
