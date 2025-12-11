package io.spring.uni_portal.service.GradeEventService;

import io.spring.uni_portal.dto.GradeEvent.*;
import io.spring.uni_portal.response.Response;

import java.util.List;

public interface IGradeEventService {
    Response<GradeEventResponse> createMidtermGradeEvent(GradeEventRequest request);

    List<GradeEventResponseDto> getGradeEventsByClass(Long classStudentId);

    Response<GradeEventResponse> updateGradeEvent(Long gradeEventId, GradeEventRequest request);

    Response<GradeEventResponse> getGradeEventById(Long gradeEventId);

}
