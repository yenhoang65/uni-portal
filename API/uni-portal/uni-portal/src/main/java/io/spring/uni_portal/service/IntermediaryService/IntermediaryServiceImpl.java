package io.spring.uni_portal.service.IntermediaryService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IntermediaryServiceImpl implements IIntermediaryService {

    @Autowired
    private IntermediaryRepository intermediaryRepository;

    @Autowired
    private TrainingProgramRepository trainingProgramRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Helper method to convert a String (JSON) into a List<PrerequisiteSubject>
    private List<IntermediaryResponse.PrerequisiteSubject> getPrerequisiteList(String prerequisiteFor) {
        try {
            // Chuyển chuỗi JSON thành List<Long>
            List<Long> subjectIds = objectMapper.readValue(prerequisiteFor, new TypeReference<List<Long>>() {});
            if (subjectIds == null || subjectIds.isEmpty()) {
                return new ArrayList<>();
            }

            // Truy vấn SubjectRepository để lấy danh sách môn học dựa trên subjectIds
            List<Subject> subjects = subjectRepository.findAllById(subjectIds);
            // Chuyển đổi thành List<PrerequisiteSubject>
            return subjects.stream()
                    .map(subject -> new IntermediaryResponse.PrerequisiteSubject(
                            subject.getSubjectId(),
                            subject.getSubjectName()
                    ))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    // Convert a list of Intermediary entities into a list of IntermediaryResponse
    private List<IntermediaryResponse> toResponseList(List<Intermediary> intermediaries) {
        // Group intermediaries by trainingProgramId and schoolYear
        Map<Long, Map<String, List<Intermediary>>> groupedByProgramAndYear = intermediaries.stream()
                .collect(Collectors.groupingBy(
                        intermediary -> intermediary.getTrainingProgram().getTrainingProgramId(),
                        Collectors.groupingBy(Intermediary::getSchoolYear)
                ));



        List<IntermediaryResponse> responses = new ArrayList<>();
        for (Long trainingProgramId : groupedByProgramAndYear.keySet()) {
            Map<String, List<Intermediary>> programEntries = groupedByProgramAndYear.get(trainingProgramId);
            for (String schoolYear : programEntries.keySet()) {
                List<Intermediary> programIntermediaries = programEntries.get(schoolYear);

                // Get the TrainingProgram (assuming all intermediaries in this group share the same program)
                TrainingProgram trainingProgram = programIntermediaries.get(0).getTrainingProgram();

                // Map the intermediaries to SubjectResponse
                List<IntermediaryResponse.SubjectResponse> subjectResponses = programIntermediaries.stream()
                        .map(intermediary -> new IntermediaryResponse.SubjectResponse(
                                intermediary.getSubject().getSubjectId(),
                                intermediary.getSubject().getSubjectName(),
                                intermediary.getSubjectType(),
                                getPrerequisiteList(intermediary.getPrerequisiteFor()) // Sử dụng phương thức mới
                        ))
                        .collect(Collectors.toList());

                // Create the IntermediaryResponse
                responses.add(new IntermediaryResponse(
                        trainingProgram.getTrainingProgramId(),
                        trainingProgram.getTrainingProgramName(),
                        schoolYear,
                        subjectResponses
                ));
            }
        }
        return responses;
    }

    @Override
    public IntermediaryResponse create(IntermediaryDTO dto) {
        // Fetch the TrainingProgram and Subject
        TrainingProgram trainingProgram = trainingProgramRepository.findById(dto.getTrainingProgramId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chương trình"));

        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy môn học"));

        // Check if the record already exists
        IntermediaryId id = new IntermediaryId(dto.getTrainingProgramId(), dto.getSubjectId());
        if (intermediaryRepository.existsById(id)) {
            throw new RuntimeException("Bản ghi đã tồn tại");
        }

        // Create a new Intermediary entity
        Intermediary intermediary = new Intermediary(trainingProgram, subject, dto.getSchoolYear(), dto.getSubjectType());
        try {
            intermediary.setPrerequisiteFor(objectMapper.writeValueAsString(dto.getPrerequisiteForList()));
        } catch (Exception e) {
            intermediary.setPrerequisiteFor("[]");
        }

        // Save the intermediary
        intermediaryRepository.save(intermediary);

        // Fetch all intermediaries for this training program and school year to build the response
        List<Intermediary> intermediaries = intermediaryRepository.findByTrainingProgram_TrainingProgramIdAndSchoolYear(
                dto.getTrainingProgramId(), dto.getSchoolYear());

        // Convert to IntermediaryResponse
        List<IntermediaryResponse> responses = toResponseList(intermediaries);
        return responses.isEmpty() ? null : responses.get(0); // Return the first (and only) response
    }

    @Override
    public List<IntermediaryResponse> getAll() {
        List<Intermediary> intermediaries = intermediaryRepository.findAll();
        return toResponseList(intermediaries);
    }

    @Override
    public void delete(Long trainingProgramId, Long subjectId) {
        intermediaryRepository.deleteById(new IntermediaryId(trainingProgramId, subjectId));
    }

    @Override
    public List<IntermediaryResponse> getByTrainingProgram(Long trainingProgramId) {
        List<Intermediary> intermediaries = intermediaryRepository.findByTrainingProgram_TrainingProgramId(trainingProgramId);
        return toResponseList(intermediaries);
    }

    @Override
    public List<IntermediaryResponse> searchByProgramName(String trainingProgramName) {
        List<Intermediary> intermediaries = intermediaryRepository.findByTrainingProgram_TrainingProgramNameContainingIgnoreCase(trainingProgramName);
        return toResponseList(intermediaries);
    }

    @Override
    public void deleteAllSubjectsOfProgram(Long trainingProgramId) {
        intermediaryRepository.deleteByTrainingProgram_TrainingProgramId(trainingProgramId);
    }
}