package io.spring.uni_portal.service.TrainingProgramService;

import io.spring.uni_portal.dto.Subject.SubjectResponseDTO;
import io.spring.uni_portal.dto.TrainingProgram.SubjectInTrainingProgramResponse;
import io.spring.uni_portal.dto.TrainingProgram.TrainingProgramDTO;
import io.spring.uni_portal.dto.TrainingProgram.TrainingProgramResponse;
import io.spring.uni_portal.model.Specialization;
import io.spring.uni_portal.model.Student;
import io.spring.uni_portal.model.Class;
import io.spring.uni_portal.model.TrainingProgram;
import io.spring.uni_portal.model.User;
import io.spring.uni_portal.repository.IntermediaryRepository;
import io.spring.uni_portal.repository.SpecializationRepository;
import io.spring.uni_portal.repository.StudentRepository;
import io.spring.uni_portal.repository.TrainingProgramRepository;
import io.spring.uni_portal.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainingProgramServiceImpl implements ITrainingProgramService {

    @Autowired
    private TrainingProgramRepository repository;

    @Autowired
    private SpecializationRepository specializationRepository;

    @Autowired
    private IntermediaryRepository intermediaryRepository;

    @Autowired
    private StudentRepository studentRepository;


    private TrainingProgramResponse toResponse(TrainingProgram entity) {
        return new TrainingProgramResponse(
                entity.getTrainingProgramId(),
                entity.getTrainingCode(),
                entity.getTrainingProgramName(),
                entity.getSpecialization().getSpecializationName(),
                entity.getCreatedAt()
        );
    }

    @Override
    public TrainingProgramResponse create(TrainingProgramDTO dto) {
        if (repository.existsById(dto.getTrainingProgramId())) {
            throw new RuntimeException("ID đã tồn tại");
        }

        Specialization spec = specializationRepository.findById(dto.getSpecializationId())
                .orElseThrow(() -> new RuntimeException("Ngành không tồn tại"));

        TrainingProgram program = new TrainingProgram();
        program.setTrainingProgramId(dto.getTrainingProgramId());
        program.setTrainingCode(dto.getTrainingCode());
        program.setTrainingProgramName(dto.getTrainingProgramName());
        program.setCreatedAt(LocalDateTime.now());
        program.setSpecialization(spec);

        return toResponse(repository.save(program));
    }

    @Override
    public TrainingProgramResponse getById(Long id) {
        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chương trình"));
    }

    @Override
    public List<TrainingProgramResponse> getAll() {
        return repository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public TrainingProgramResponse update(Long id, TrainingProgramDTO dto) {
        TrainingProgram entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chương trình"));

        entity.setTrainingCode(dto.getTrainingCode());
        entity.setTrainingProgramName(dto.getTrainingProgramName());

        if (!entity.getSpecialization().getSpecializationId().equals(dto.getSpecializationId())) {
            Specialization spec = specializationRepository.findById(dto.getSpecializationId())
                    .orElseThrow(() -> new RuntimeException("Ngành không tồn tại"));
            entity.setSpecialization(spec);
        }

        return toResponse(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<TrainingProgramResponse> searchByCode(String code) {
        return repository.findByTrainingCodeContainingIgnoreCase(code)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }


    @Override
    public Response<List<SubjectInTrainingProgramResponse>> getSubjectsForCurrentStudent() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        Student student = studentRepository.findById(currentUser.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sinh viên"));

        Class studentClass = student.getClassEntity();
        if (studentClass == null || studentClass.getTrainingProgram() == null) {
            return Response.failure("Sinh viên chưa được gán lớp hoặc chương trình đào tạo.");
        }

        TrainingProgram trainingProgram = studentClass.getTrainingProgram();

        List<SubjectInTrainingProgramResponse> subjects = intermediaryRepository
                .findByTrainingProgram(trainingProgram)
                .stream()
                .map(intermediary -> new SubjectInTrainingProgramResponse(
                        intermediary.getSubject(),
                        intermediary.getSubjectType(),
                        intermediary.getSchoolYear()
                ))
                .collect(Collectors.toList());


        return Response.success("Danh sách môn học trong chương trình đào tạo", subjects);
    }
}
