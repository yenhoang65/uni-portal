package io.spring.uni_portal.service.ClassService;

import io.spring.uni_portal.dto.Class.ClassRequestDTO;
import io.spring.uni_portal.dto.Class.ClassResponseDTO;
import io.spring.uni_portal.exception.OurException;
import io.spring.uni_portal.model.Lecturer;
import io.spring.uni_portal.model.Class;
import io.spring.uni_portal.model.TrainingProgram;
import io.spring.uni_portal.repository.ClassRepository;
import io.spring.uni_portal.repository.LecturerRepository;
import io.spring.uni_portal.repository.TrainingProgramRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassService implements IClassService {

    private final ClassRepository classRepository;
    private final LecturerRepository lecturerRepository;
    private final TrainingProgramRepository trainingProgramRepository;

    public ClassService(ClassRepository classRepository,
                        LecturerRepository lecturerRepository,
                        TrainingProgramRepository trainingProgramRepository) {
        this.classRepository = classRepository;
        this.lecturerRepository = lecturerRepository;
        this.trainingProgramRepository = trainingProgramRepository;
    }

    @Override
    public ClassResponseDTO create(ClassRequestDTO dto) {
        Lecturer lecturer = lecturerRepository.findById(dto.getLecturerId())
                .orElseThrow(() -> new RuntimeException("Giảng viên không tồn tại"));
        TrainingProgram trainingProgram = trainingProgramRepository.findById(dto.getTrainingProgramId())
                .orElseThrow(() -> new RuntimeException("Chương trình đào tạo không tồn tại"));
        if (classRepository.existsById(dto.getClassId())) {
            throw new OurException("Mã lớp đã tồn tại!");
        }

        Class c = new Class();
        c.setClassId(dto.getClassId());
        c.setLecturer(lecturer);
        c.setTrainingProgram(trainingProgram);
        c.setSchoolYear(dto.getSchoolYear());

        return new ClassResponseDTO(classRepository.save(c));
    }

    @Override
    public List<ClassResponseDTO> getAll() {
        return classRepository.findAll().stream()
                .map(ClassResponseDTO::new)
                .toList();
    }

    @Override
    public ClassResponseDTO getById(Long id) {
        return classRepository.findById(id)
                .map(ClassResponseDTO::new)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp học với ID: " + id));
    }

    @Override
    public ClassResponseDTO update(Long id, ClassRequestDTO dto) {
        Class c = classRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp để cập nhật"));

        Lecturer lecturer = lecturerRepository.findById(dto.getLecturerId())
                .orElseThrow(() -> new RuntimeException("Giảng viên không tồn tại"));
        TrainingProgram trainingProgram = trainingProgramRepository.findById(dto.getTrainingProgramId())
                .orElseThrow(() -> new RuntimeException("Chương trình đào tạo không tồn tại"));

        c.setLecturer(lecturer);
        c.setTrainingProgram(trainingProgram);
        c.setSchoolYear(dto.getSchoolYear());

        return new ClassResponseDTO(classRepository.save(c));
    }

    @Override
    public void delete(Long id) {
        classRepository.deleteById(id);
    }
}

