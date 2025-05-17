package io.spring.uni_portal.service.TrainingProgramService;

import io.spring.uni_portal.dto.Subject.SubjectResponseDTO;
import io.spring.uni_portal.dto.TrainingProgram.SubjectInTrainingProgramResponse;
import io.spring.uni_portal.dto.TrainingProgram.TrainingProgramDTO;
import io.spring.uni_portal.dto.TrainingProgram.TrainingProgramResponse;
import io.spring.uni_portal.response.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITrainingProgramService {
    TrainingProgramResponse create(TrainingProgramDTO dto);
    TrainingProgramResponse getById(Long id);
    List<TrainingProgramResponse> getAll();
    TrainingProgramResponse update(Long id, TrainingProgramDTO dto);
    void delete(Long id);
    List<TrainingProgramResponse> searchByCode(String code);

    Response<List<SubjectInTrainingProgramResponse>> getSubjectsForCurrentStudent();
}
