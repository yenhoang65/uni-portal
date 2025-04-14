package io.spring.uni_portal.service.IntermediaryService;

import io.spring.uni_portal.dto.Intermediary.IntermediaryDTO;
import io.spring.uni_portal.dto.Intermediary.IntermediaryResponse;
import io.spring.uni_portal.model.Intermediary;

import java.util.List;

public interface IIntermediaryService {
    IntermediaryResponse create(IntermediaryDTO dto);
    List<IntermediaryResponse> getAll();
    void delete(Long trainingProgramId, Long subjectId);
    List<IntermediaryResponse> getByTrainingProgram(Long trainingProgramId);
    List<IntermediaryResponse> searchByProgramName(String trainingProgramName);
    void deleteAllSubjectsOfProgram(Long trainingProgramId);
}
