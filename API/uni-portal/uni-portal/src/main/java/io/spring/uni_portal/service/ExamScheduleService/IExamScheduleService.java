package io.spring.uni_portal.service.ExamScheduleService;

import io.spring.uni_portal.dto.ExamSchedule.ExamScheduleRequest;
import io.spring.uni_portal.dto.ExamSchedule.ExamScheduleResponse;
import io.spring.uni_portal.response.Response;

public interface IExamScheduleService {
    Response<ExamScheduleResponse> createMidtermExamSchedule(ExamScheduleRequest request);
}
