package io.spring.uni_portal.service.TrainingProgramService;

import io.spring.uni_portal.dto.Intermediary.IntermediaryDTO;
import io.spring.uni_portal.dto.Subject.SubjectResponseDTO;
import io.spring.uni_portal.dto.TrainingProgram.SubjectInTrainingProgramResponse;
import io.spring.uni_portal.dto.TrainingProgram.TrainingProgramDTO;
import io.spring.uni_portal.dto.TrainingProgram.TrainingProgramResponse;
import io.spring.uni_portal.dto.TrainingProgram.TrainingProgramSimpleResponse;
import io.spring.uni_portal.model.*;
import io.spring.uni_portal.model.Class;
import io.spring.uni_portal.repository.*;
import io.spring.uni_portal.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
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

    @Autowired
    private ClassSubjectStudentRepository classSubjectStudentRepository;

    @Autowired
    private StudentGradeRepository studentGradeRepository;

    @Autowired
    private TrainingProgramRepository trainingProgramRepository;

    private TrainingProgramResponse toResponse(TrainingProgram entity) {
        return new TrainingProgramResponse(
                entity.getTrainingProgramId(),
                entity.getTrainingCode(),
                entity.getTrainingProgramName(),
                entity.getSpecialization().getSpecializationId(),
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

    if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
        throw new RuntimeException("Người dùng chưa xác thực hoặc token không hợp lệ.");
    }

    User currentUser = (User) authentication.getPrincipal();

    Student student = studentRepository.findById(currentUser.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sinh viên"));

    Class studentClass = student.getClassEntity();
    if (studentClass == null || studentClass.getTrainingProgram() == null) {
        return Response.failure("Sinh viên chưa được gán lớp hoặc chương trình đào tạo.");
    }

    TrainingProgram trainingProgram = studentClass.getTrainingProgram();

    // Tạo DTO cho training program
    TrainingProgramDTO trainingProgramDTO = new TrainingProgramDTO(
            trainingProgram.getTrainingProgramId(),
            trainingProgram.getTrainingCode(),
            trainingProgram.getTrainingProgramName(),
            trainingProgram.getSpecialization() != null ? trainingProgram.getSpecialization().getSpecializationId() : null
    );

    List<Intermediary> intermediarySubjects = intermediaryRepository.findByTrainingProgram(trainingProgram);
    List<ClassSubjectStudent> registeredSubjects = classSubjectStudentRepository.findByStudent_UserId(student.getUserId());

    Set<Long> learnedSubjectIds = registeredSubjects.stream()
            .map(css -> css.getClassStudent().getTeachingScheduleRequest().getAssignment().getSubject().getSubjectId())
            .collect(Collectors.toSet());

    List<SubjectInTrainingProgramResponse> resultSubjects = new ArrayList<>();

    for (Intermediary intermediary : intermediarySubjects) {
        Subject subject = intermediary.getSubject();
        Long subjectId = subject.getSubjectId();

        if (!learnedSubjectIds.contains(subjectId)) {

            IntermediaryDTO intermediaryDTO = new IntermediaryDTO();
            intermediaryDTO.setTrainingProgramId(intermediary.getTrainingProgram().getTrainingProgramId());
            intermediaryDTO.setSubjectId(intermediary.getSubject().getSubjectId());
            intermediaryDTO.setSubjectType(intermediary.getSubjectType());
            intermediaryDTO.setSchoolYear(intermediary.getSchoolYear());

            if (intermediary.getPrerequisiteFor() != null && !intermediary.getPrerequisiteFor().isBlank()) {
                List<Long> prerequisiteIds = Arrays.stream(intermediary.getPrerequisiteFor()
                                .replaceAll("[\\[\\]\"]", "") // loại bỏ [, ], "
                                .split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .map(Long::parseLong)
                        .collect(Collectors.toList());

                intermediaryDTO.setPrerequisiteForList(prerequisiteIds);
            } else {
                intermediaryDTO.setPrerequisiteForList(Collections.emptyList());
            }

            resultSubjects.add(new SubjectInTrainingProgramResponse(
                    subject,
                    intermediary.getSubjectType(),
                    intermediary.getSchoolYear(),
                    trainingProgramDTO,
                    intermediaryDTO
            ));
        }
    }


    return Response.success("Danh sách môn học chưa đăng ký trong CTĐT", resultSubjects);
}


    @Override
    public Response<List<SubjectInTrainingProgramResponse>> getAllSubjectsInProgramForCurrentStudent() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            throw new RuntimeException("Người dùng chưa xác thực hoặc token không hợp lệ.");
        }

        User currentUser = (User) authentication.getPrincipal();

        Student student = studentRepository.findById(currentUser.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sinh viên."));

        Class studentClass = student.getClassEntity();
        if (studentClass == null || studentClass.getTrainingProgram() == null) {
            return Response.failure("Sinh viên chưa được gán lớp hoặc chương trình đào tạo.");
        }

        TrainingProgram trainingProgram = studentClass.getTrainingProgram();

        // Tạo TrainingProgramDTO từ entity
        TrainingProgramDTO trainingProgramDTO = new TrainingProgramDTO(
                trainingProgram.getTrainingProgramId(),
                trainingProgram.getTrainingCode(),
                trainingProgram.getTrainingProgramName(),
                trainingProgram.getSpecialization() != null ? trainingProgram.getSpecialization().getSpecializationId() : null
        );

        List<Intermediary> intermediarySubjects = intermediaryRepository.findByTrainingProgram(trainingProgram);

        List<SubjectInTrainingProgramResponse> allSubjects = intermediarySubjects.stream()
                .map(i -> {
                    IntermediaryDTO intermediaryDTO = new IntermediaryDTO();
                    intermediaryDTO.setTrainingProgramId(i.getTrainingProgram().getTrainingProgramId());
                    intermediaryDTO.setSubjectId(i.getSubject().getSubjectId());
                    intermediaryDTO.setSubjectType(i.getSubjectType());
                    intermediaryDTO.setSchoolYear(i.getSchoolYear());

                    // ✅ Parse prerequisiteFor từ chuỗi "1,2,3" sang List<Long>
                    if (i.getPrerequisiteFor() != null && !i.getPrerequisiteFor().isBlank()) {
                        List<Long> prerequisiteIds = Arrays.stream(i.getPrerequisiteFor()
                                        .replaceAll("[\\[\\]\"]", "") // xử lý chuỗi JSON bị bao bởi []
                                        .split(","))
                                .map(String::trim)
                                .filter(s -> !s.isEmpty())
                                .map(Long::parseLong)
                                .collect(Collectors.toList());
                        intermediaryDTO.setPrerequisiteForList(prerequisiteIds);
                    } else {
                        intermediaryDTO.setPrerequisiteForList(Collections.emptyList());
                    }


                    return new SubjectInTrainingProgramResponse(
                            i.getSubject(),
                            i.getSubjectType(),
                            i.getSchoolYear(),
                            trainingProgramDTO,
                            intermediaryDTO
                    );
                })
                .collect(Collectors.toList());


        return Response.success("Tất cả môn học trong chương trình đào tạo", allSubjects);
    }




    @Override
    public Response<List<TrainingProgramSimpleResponse>> getProgramsBySpecialization(Long specializationId) {
        List<TrainingProgram> programs = trainingProgramRepository.findBySpecialization_SpecializationId(specializationId);
        List<TrainingProgramSimpleResponse> response = programs.stream()
                .map(p -> new TrainingProgramSimpleResponse(
                        p.getTrainingProgramId(),
                        p.getTrainingProgramName(),
                        p.getTrainingCode()
                )).collect(Collectors.toList());

        return Response.success("Danh sách chương trình đào tạo theo chuyên ngành", response);
    }
}
