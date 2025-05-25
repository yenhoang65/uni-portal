package io.spring.uni_portal.service.GradeEventService;

import io.spring.uni_portal.dto.GradeEvent.GradeEventRequest;
import io.spring.uni_portal.dto.GradeEvent.GradeEventResponse;
import io.spring.uni_portal.dto.GradeEvent.GradeEventResponseDto;
import io.spring.uni_portal.response.Response;

import java.util.List;

public interface IGradeEventService {
    Response<GradeEventResponse> createMidtermGradeEvent(GradeEventRequest request);

    List<GradeEventResponseDto> getGradeEventsByClass();

    Response<GradeEventResponse> updateGradeEvent(Long gradeEventId, GradeEventRequest request);

    Response<GradeEventResponse> getGradeEventById(Long gradeEventId);

}
