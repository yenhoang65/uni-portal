package io.spring.uni_portal.service.TeachingAssignmentService;

import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentDTO;
import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentResponse;
import io.spring.uni_portal.dto.TermClass.TermClassResponse;
import io.spring.uni_portal.dto.TermClass.TermClassResponseUnassignedClasses;
import io.spring.uni_portal.model.Lecturer;
import io.spring.uni_portal.model.Subject;
import io.spring.uni_portal.model.TeachingAssignment;
import io.spring.uni_portal.model.TermClass;
import io.spring.uni_portal.repository.LecturerRepository;
import io.spring.uni_portal.repository.SubjectRepository;
import io.spring.uni_portal.repository.TeachingAssignmentRepository;
import io.spring.uni_portal.repository.TermClassRepository;
import io.spring.uni_portal.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeachingAssignmentService implements ITeachingAssignmentService {

    @Autowired
    private TeachingAssignmentRepository teachingAssignmentRepository;

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TermClassRepository termClassRepository;



    @Override
    public Response<TeachingAssignmentResponse> assignClassToLecturer(TeachingAssignmentDTO dto) {
        // Kiểm tra giảng viên và môn học có tồn tại không
        Lecturer lecturer = lecturerRepository.findById(dto.getLecturerId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giảng viên"));
        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy môn học"));
        TermClass termClass = termClassRepository.findById(dto.getTermClassId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp học"));

        // Kiểm tra xem lớp học đã có giảng viên phân công chưa
        TeachingAssignment existingAssignment = teachingAssignmentRepository.findByTermClass(termClass);
        if (existingAssignment != null) {
            throw new RuntimeException("This term class already has an assigned lecturer.");
        }

        // Tạo phân công giảng viên
        TeachingAssignment teachingAssignment = new TeachingAssignment();
        teachingAssignment.setLecturer(lecturer);
        teachingAssignment.setSubject(subject);
        teachingAssignment.setTermClass(termClass);

        // Lưu vào database
        TeachingAssignment savedAssignment = teachingAssignmentRepository.save(teachingAssignment);

        // Tạo và trả về response
        TeachingAssignmentResponse response = new TeachingAssignmentResponse(
                savedAssignment.getAssignmentId(),
                savedAssignment.getLecturer().getUserId(),
                savedAssignment.getLecturer().getUser().getUserName(), // Tên giảng viên từ bảng User
                savedAssignment.getSubject().getSubjectId(),
                savedAssignment.getSubject().getSubjectName(),
                savedAssignment.getTermClass().getTermclassId(),
                savedAssignment.getTermClass().getClassname(),
                savedAssignment.getTermClass().getProgress(),
                savedAssignment.getTermClass().getSemester(),
                savedAssignment.getTermClass().getSchoolyears()
        );

        return Response.success("Phân công giảng dạy đã được tạo thành công", response);
    }

    @Override
    public Response<List<TeachingAssignmentResponse>> getAllAssignments() {
        List<TeachingAssignmentResponse> assignments = teachingAssignmentRepository.findAll().stream()
                .map(assignment -> new TeachingAssignmentResponse(
                        assignment.getAssignmentId(),
                        assignment.getLecturer().getUserId(),
                        assignment.getLecturer().getUser().getUserName(), // Tên giảng viên từ bảng User
                        assignment.getSubject().getSubjectId(),
                        assignment.getSubject().getSubjectName(),
                        assignment.getTermClass().getTermclassId(),
                        assignment.getTermClass().getClassname(),
                        assignment.getTermClass().getProgress(),
                        assignment.getTermClass().getSemester(),
                        assignment.getTermClass().getSchoolyears()
                ))
                .collect(Collectors.toList());

        return Response.success("Danh sách bài tập giảng dạy", assignments);
    }

    @Override
    public Response<TeachingAssignmentResponse> getAssignmentById(Long assignmentId) {
        TeachingAssignment teachingAssignment = teachingAssignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Phân công giảng dạy không tìm thấy"));

        TeachingAssignmentResponse response = new TeachingAssignmentResponse(
                teachingAssignment.getAssignmentId(),
                teachingAssignment.getLecturer().getUserId(),
                teachingAssignment.getLecturer().getUser().getUserName(), // Tên giảng viên từ bảng User
                teachingAssignment.getSubject().getSubjectId(),
                teachingAssignment.getSubject().getSubjectName(),
                teachingAssignment.getTermClass().getTermclassId(),
                teachingAssignment.getTermClass().getClassname(),
                teachingAssignment.getTermClass().getProgress(),
                teachingAssignment.getTermClass().getSemester(),
                teachingAssignment.getTermClass().getSchoolyears()
        );

        return Response.success("Phân công giảng dạy đã tìm thấy", response);
    }

    @Override
    public Response<TeachingAssignmentResponse> updateAssignment(Long assignmentId, TeachingAssignmentDTO dto) {
        TeachingAssignment teachingAssignment = teachingAssignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Phân công giảng dạy không tìm thấy"));

        Lecturer lecturer = lecturerRepository.findById(dto.getLecturerId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giảng viên"));
        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy môn học"));
        TermClass termClass = termClassRepository.findById(dto.getTermClassId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp học"));

        // Cập nhật phân công giảng viên
        teachingAssignment.setLecturer(lecturer);
        teachingAssignment.setSubject(subject);
        teachingAssignment.setTermClass(termClass);

        // Lưu lại vào database
        TeachingAssignment savedAssignment = teachingAssignmentRepository.save(teachingAssignment);

        TeachingAssignmentResponse response = new TeachingAssignmentResponse(
                savedAssignment.getAssignmentId(),
                savedAssignment.getLecturer().getUserId(),
                savedAssignment.getLecturer().getUser().getUserName(),
                savedAssignment.getSubject().getSubjectId(),
                savedAssignment.getSubject().getSubjectName(),
                savedAssignment.getTermClass().getTermclassId(),
                savedAssignment.getTermClass().getClassname(),
                savedAssignment.getTermClass().getProgress(),
                savedAssignment.getTermClass().getSemester(),
                savedAssignment.getTermClass().getSchoolyears()
        );

        return Response.success("Phân công giảng dạy đã được cập nhật thành công", response);
    }

    @Override
    public Response<Void> deleteAssignment(Long assignmentId) {
        teachingAssignmentRepository.deleteById(assignmentId);
        return Response.success("Phân công giảng dạy đã được xóa thành công", null);
    }


    @Override
    public Response<List<TeachingAssignmentResponse>> getAssignedClasses() {
        List<TeachingAssignment> assignments = teachingAssignmentRepository.findByLecturerIsNotNull();

        List<TeachingAssignmentResponse> responseList = assignments.stream()
                .map(assignment -> new TeachingAssignmentResponse(
                        assignment.getAssignmentId(),
                        assignment.getLecturer().getUserId(),
                        assignment.getLecturer().getUser().getUserName(), // Tên giảng viên từ bảng User
                        assignment.getSubject().getSubjectId(),
                        assignment.getSubject().getSubjectName(),
                        assignment.getTermClass().getTermclassId(),
                        assignment.getTermClass().getClassname(),
                        assignment.getTermClass().getProgress(),
                        assignment.getTermClass().getSemester(),
                        assignment.getTermClass().getSchoolyears()
                ))
                .collect(Collectors.toList());

        return Response.success("Danh sách các lớp đã được phân công", responseList);
    }

    @Override
    public Response<List<TermClassResponseUnassignedClasses>> getUnassignedClasses() {
        // Lấy danh sách tất cả các lớp học
        List<TermClass> allTermClasses = termClassRepository.findAll();

        // Lấy danh sách các termClassId đã có giảng viên phân công
        List<Long> assignedTermClassIds = teachingAssignmentRepository.findByLecturerIsNotNull()
                .stream()
                .map(teachingAssignment -> teachingAssignment.getTermClass().getTermclassId())
                .collect(Collectors.toList());

        // Lọc ra các lớp học chưa có giảng viên
        List<TermClassResponseUnassignedClasses> unassignedClasses = allTermClasses.stream()
                .filter(termClass -> !assignedTermClassIds.contains(termClass.getTermclassId()))
                .map(termClass -> new TermClassResponseUnassignedClasses(
                        termClass.getTermclassId(),
                        termClass.getClassname(),
                        termClass.getProgress(),
                        termClass.getSemester(),
                        termClass.getSchoolyears()
                ))
                .collect(Collectors.toList());

        // Trả về Response
        return Response.success("Danh sách các lớp chưa phân công giảng viên", unassignedClasses);
    }




}
