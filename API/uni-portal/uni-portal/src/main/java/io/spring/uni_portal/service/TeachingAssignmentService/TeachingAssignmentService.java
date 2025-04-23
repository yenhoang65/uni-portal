package io.spring.uni_portal.service.TeachingAssignmentService;

import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentDTO;
import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentResponse;
import io.spring.uni_portal.model.Lecturer;
import io.spring.uni_portal.model.Subject;
import io.spring.uni_portal.model.TeachingAssignment;
import io.spring.uni_portal.repository.LecturerRepository;
import io.spring.uni_portal.repository.SubjectRepository;
import io.spring.uni_portal.repository.TeachingAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeachingAssignmentService implements ITeachingAssignmentService {

    @Autowired
    private TeachingAssignmentRepository assignmentRepository;

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    public List<TeachingAssignmentResponse> getAll() {
        return assignmentRepository.findAll().stream()
                .map(TeachingAssignmentResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public TeachingAssignmentResponse create(TeachingAssignmentDTO dto) {
        Lecturer lecturer = lecturerRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giảng viên"));

        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy môn học"));

        TeachingAssignment a = new TeachingAssignment();
        a.setLecturer(lecturer);
        a.setSubject(subject);
        a.setSemester(dto.getSemester());
        a.setSchoolYear(dto.getSchoolYear());

        TeachingAssignment saved = assignmentRepository.save(a);
        return new TeachingAssignmentResponse(saved); // Trả về DTO
    }


    @Override
    public TeachingAssignment update(Long id, TeachingAssignmentDTO dto) {
        TeachingAssignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bản ghi"));

        Lecturer lecturer = lecturerRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giảng viên"));
        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy môn học"));

        assignment.setLecturer(lecturer);
        assignment.setSubject(subject);
        assignment.setSchoolYear(dto.getSchoolYear());
        assignment.setSemester(dto.getSemester());

        return assignmentRepository.save(assignment);
    }

    @Override
    public void delete(Long id) {
        assignmentRepository.deleteById(id);
    }

    @Override
    public List<TeachingAssignment> getByLecturer(Long userId, Long schoolYear, Long semester) {
        return assignmentRepository.findByLecturerUserIdAndSchoolYearAndSemester(userId, schoolYear, semester);
    }
}
