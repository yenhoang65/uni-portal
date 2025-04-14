package io.spring.uni_portal.service.IntermediaryService;

import io.spring.uni_portal.dto.Intermediary.IntermediaryDTO;
import io.spring.uni_portal.dto.Intermediary.IntermediaryResponse;
import io.spring.uni_portal.model.Intermediary;
import io.spring.uni_portal.model.IntermediaryId;
import io.spring.uni_portal.model.Subject;
import io.spring.uni_portal.model.TrainingProgram;
import io.spring.uni_portal.repository.IntermediaryRepository;
import io.spring.uni_portal.repository.SubjectRepository;
import io.spring.uni_portal.repository.TrainingProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IntermediaryServiceImpl implements IIntermediaryService {

    @Autowired
    private IntermediaryRepository intermediaryRepository;

    @Autowired
    private TrainingProgramRepository trainingProgramRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    private IntermediaryResponse toResponse(Intermediary entity) {
        return new IntermediaryResponse(
                entity.getTrainingProgram().getTrainingProgramId(),
                entity.getTrainingProgram().getTrainingProgramName(),
                entity.getSubject().getSubjectId(),
                entity.getSubject().getSubjectName(),
                entity.getSchoolYear()
        );
    }

    @Override
    public IntermediaryResponse create(IntermediaryDTO dto) {
        TrainingProgram trainingProgram = trainingProgramRepository.findById(dto.getTrainingProgramId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chương trình"));

        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy môn học"));

        IntermediaryId id = new IntermediaryId(dto.getTrainingProgramId(), dto.getSubjectId());

        if (intermediaryRepository.existsById(id)) {
            throw new RuntimeException("Bản ghi đã tồn tại");
        }

        Intermediary intermediary = new Intermediary(trainingProgram, subject, dto.getSchoolYear());
        return toResponse(intermediaryRepository.save(intermediary));
    }

    @Override
    public List<IntermediaryResponse> getAll() {
        return intermediaryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long trainingProgramId, Long subjectId) {
        intermediaryRepository.deleteById(new IntermediaryId(trainingProgramId, subjectId));
    }

    @Override
    public List<IntermediaryResponse> getByTrainingProgram(Long trainingProgramId) {
        return intermediaryRepository.findByTrainingProgram_TrainingProgramId(trainingProgramId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<IntermediaryResponse> searchByProgramName(String trainingProgramName) {
        return intermediaryRepository.findByTrainingProgram_TrainingProgramNameContainingIgnoreCase(trainingProgramName)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllSubjectsOfProgram(Long trainingProgramId) {
        if (!trainingProgramRepository.existsById(trainingProgramId)) {
            throw new RuntimeException("Chương trình đào tạo không tồn tại");
        }
        intermediaryRepository.deleteByTrainingProgram_TrainingProgramId(trainingProgramId);
    }
}
