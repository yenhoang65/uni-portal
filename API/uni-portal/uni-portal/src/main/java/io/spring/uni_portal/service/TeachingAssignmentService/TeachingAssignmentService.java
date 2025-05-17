package io.spring.uni_portal.service.TeachingAssignmentService;

import io.spring.uni_portal.dto.Subject.SubjectDTO;
import io.spring.uni_portal.dto.TeachingAssignment.*;
import io.spring.uni_portal.dto.TeachingScheduleRequest.TeachingScheduleRequestResponseDTO;
import io.spring.uni_portal.dto.TermClass.TermClassResponse;
import io.spring.uni_portal.dto.TermClass.TermClassResponseUnassignedClasses;
import io.spring.uni_portal.model.*;
import io.spring.uni_portal.repository.*;
import io.spring.uni_portal.response.Response;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Autowired
    private TeachingScheduleRequestRepository scheduleRepository;

    @Override
    @Transactional
    public Response<List<TeachingAssignmentResponse>> assignClassToLecturer(TeachingAssignmentDTO dto) {
        // Kiểm tra giảng viên, môn học và lớp học
        Lecturer lecturer = lecturerRepository.findById(dto.getLecturerId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy giảng viên với ID: " + dto.getLecturerId()));
        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy môn học với ID: " + dto.getSubjectId()));
        TermClass termClass = termClassRepository.findById(dto.getTermClassId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy lớp học với ID: " + dto.getTermClassId()));

        // Kiểm tra tín chỉ lý thuyết và thực hành
        Integer ltCredits = subject.getLtCredits();
        Integer thCredits = subject.getThCredits();

        // Nếu không có tín chỉ nào để phân công
        if ((ltCredits == null || ltCredits <= 0) && (thCredits == null || thCredits <= 0)) {
            return Response.failure("Môn học không có tín chỉ lý thuyết hoặc thực hành để phân công.");
        }

        // Kiểm tra xem lớp học đã có phân công chưa
        TeachingAssignment existingAssignment = teachingAssignmentRepository
                .findByTermClass(termClass);

        // Nếu lớp học đã có phân công giảng viên, trả về lỗi
        if (existingAssignment != null) {
            return Response.failure("Lớp học này đã được phân công giảng viên.");
        }

        // Tạo bản ghi phân công giảng viên cho lớp học, chỉ lưu một bản ghi cho lớp này
        TeachingAssignment assignment = new TeachingAssignment();
        assignment.setLecturer(lecturer);
        assignment.setSubject(subject);
        assignment.setTermClass(termClass);

        // Để assignmentType là null
        assignment.setAssignmentType(null);  // Không gán giá trị cho assignmentType, để nó null

        // Lưu vào cơ sở dữ liệu
        teachingAssignmentRepository.save(assignment);

        // Tạo phản hồi từ bản ghi đã lưu
        TeachingAssignmentResponse response = new TeachingAssignmentResponse(
                assignment.getAssignmentId(),
                assignment.getLecturer().getUserId(),
                assignment.getLecturer().getUser().getUserName(),
                assignment.getSubject().getSubjectId(),
                assignment.getSubject().getSubjectName(),
                assignment.getTermClass().getTermclassId(),
                assignment.getTermClass().getClassname(),
                assignment.getTermClass().getProgress(),
                assignment.getTermClass().getSemester(),
                assignment.getTermClass().getSchoolyears(),
                assignment.getAssignmentType() != null ? assignment.getAssignmentType().name() : "UNKNOWN"
        );

        return Response.success("Phân công giảng dạy đã được tạo thành công", List.of(response));
    }

    @Override
    public Response<List<TeachingAssignmentResponse>> getAllAssignments() {
        return null;
    }


    @Override
    public Response<List<TeachingAssignmentResponse>> getAllAssignmentsForAdmin() {
        List<TeachingAssignmentResponse> responseList = teachingAssignmentRepository.findAll()
                .stream()
                .map(assignment -> new TeachingAssignmentResponse(
                        assignment.getAssignmentId(),
                        assignment.getLecturer().getUserId(),
                        assignment.getLecturer().getUser().getUserName(),
                        assignment.getSubject().getSubjectId(),
                        assignment.getSubject().getSubjectName(),
                        assignment.getTermClass().getTermclassId(),
                        assignment.getTermClass().getClassname(),
                        assignment.getTermClass().getProgress(),
                        assignment.getTermClass().getSemester(),
                        assignment.getTermClass().getSchoolyears(),
                        assignment.getAssignmentType() != null
                                ? assignment.getAssignmentType().name()
                                : "UNKNOWN"
                ))
                .collect(Collectors.toList());

        return Response.success("Danh sách phân công giảng dạy (ADMIN)", responseList);
    }


@Override
public Response<Page<TeachingAssignmentResponse>> getAssignmentsWithSearch(String searchValue, Pageable pageable) {
    // Lấy user đang đăng nhập từ SecurityContextHolder
    UsernamePasswordAuthenticationToken authentication =
            (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    User currentUser = (User) authentication.getPrincipal();

    // Kiểm tra nếu người dùng có role là admin
    boolean isAdmin = currentUser.getRole().contains("admin");

    Page<TeachingAssignment> assignmentsPage;

    if (isAdmin) {
        // Nếu là admin, trả về tất cả bản ghi trong bảng TeachingAssignment với phân trang và tìm kiếm
        assignmentsPage = teachingAssignmentRepository.findAllBySearchValue(searchValue, pageable);
    } else {
        // Nếu không phải admin, chỉ trả về bản ghi của giảng viên hiện tại với phân trang và tìm kiếm
        Long userId = currentUser.getUserId();
        assignmentsPage = teachingAssignmentRepository.findByLecturerUserUserIdAndSearchValue(userId, searchValue, pageable);
    }

    // Chuyển đổi từ Page<TeachingAssignment> thành Page<TeachingAssignmentResponse>
    Page<TeachingAssignmentResponse> responsePage = assignmentsPage.map(assignment -> new TeachingAssignmentResponse(
            assignment.getAssignmentId(),
            assignment.getLecturer().getUserId(),
            assignment.getLecturer().getUser().getUserName(),
            assignment.getSubject().getSubjectId(),
            assignment.getSubject().getSubjectName(),
            assignment.getTermClass().getTermclassId(),
            assignment.getTermClass().getClassname(),
            assignment.getTermClass().getProgress(),
            assignment.getTermClass().getSemester(),
            assignment.getTermClass().getSchoolyears(),
            assignment.getAssignmentType() != null ? assignment.getAssignmentType().name() : "UNKNOWN"
    ));

    return Response.success("Danh sách phân công giảng dạy với phân trang", responsePage);
}



    @Override
    public Response<TeachingAssignmentDetailDTO> getAssignmentById(Long assignmentId) {
        TeachingAssignment ta = teachingAssignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("Phân công giảng dạy không tìm thấy với ID: " + assignmentId));

        Subject subject = ta.getSubject();
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setSubjectId(subject.getSubjectId());
        subjectDTO.setSubjectName(subject.getSubjectName());
        subjectDTO.setLtCredits(subject.getLtCredits());
        subjectDTO.setThCredits(subject.getThCredits());
        subjectDTO.setSubjectDescription(subject.getSubjectDescription());
        subjectDTO.setSubjectCoefficient(subject.getSubjectCoefficient());

        TeachingAssignmentDetailDTO dto = new TeachingAssignmentDetailDTO(
                ta.getAssignmentId(),
                ta.getLecturer().getUserId(),
                ta.getLecturer().getUser().getUserName(),
                subjectDTO,
                ta.getTermClass().getTermclassId(),
                ta.getTermClass().getClassname(),
                ta.getTermClass().getProgress(),
                ta.getTermClass().getSemester(),
                ta.getTermClass().getSchoolyears(),
                ta.getAssignmentType() != null ? ta.getAssignmentType().name() : "UNKNOWN"
        );

        return Response.success("Phân công giảng dạy đã tìm thấy", dto);
    }




    @Override
    public Response<TeachingAssignmentResponse> updateAssignment(Long assignmentId, TeachingAssignmentDTO dto) {
        // Lấy bản ghi phân công hiện tại
        TeachingAssignment teachingAssignment = teachingAssignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("Phân công giảng dạy không tìm thấy với ID: " + assignmentId));

        // Kiểm tra giảng viên, môn học và lớp học
        Lecturer lecturer = lecturerRepository.findById(dto.getLecturerId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy giảng viên với ID: " + dto.getLecturerId()));
        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy môn học với ID: " + dto.getSubjectId()));
        TermClass termClass = termClassRepository.findById(dto.getTermClassId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy lớp học với ID: " + dto.getTermClassId()));

        // Kiểm tra tín chỉ lý thuyết và thực hành
        Integer ltCredits = subject.getLtCredits();
        Integer thCredits = subject.getThCredits();

        // Nếu không có tín chỉ nào để phân công
        if ((ltCredits == null || ltCredits <= 0) && (thCredits == null || thCredits <= 0)) {
            return Response.failure("Môn học không có tín chỉ lý thuyết hoặc thực hành để phân công.");
        }

        // Cập nhật phân công giảng viên và các thông tin khác
        teachingAssignment.setLecturer(lecturer);
        teachingAssignment.setSubject(subject);
        teachingAssignment.setTermClass(termClass);

        // Để assignmentType là null
        teachingAssignment.setAssignmentType(null);  // Không gán giá trị cho assignmentType, để nó null

        // Lưu lại vào cơ sở dữ liệu
        TeachingAssignment savedAssignment = teachingAssignmentRepository.save(teachingAssignment);

        // Tạo phản hồi từ bản ghi đã lưu
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
                savedAssignment.getTermClass().getSchoolyears(),
                savedAssignment.getAssignmentType() != null ? savedAssignment.getAssignmentType().name() : "UNKNOWN"
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
                        assignment.getLecturer().getUser().getUserName(),
                        assignment.getSubject().getSubjectId(),
                        assignment.getSubject().getSubjectName(),
                        assignment.getTermClass().getTermclassId(),
                        assignment.getTermClass().getClassname(),
                        assignment.getTermClass().getProgress(),
                        assignment.getTermClass().getSemester(),
                        assignment.getTermClass().getSchoolyears(),
                        assignment.getAssignmentType() != null ? assignment.getAssignmentType().name() : "UNKNOWN" // Giá trị mặc định
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

        return Response.success("Danh sách các lớp chưa phân công giảng viên", unassignedClasses);
    }

    @Override
    public Optional<TeachingAssignment> findById(Long id) {
        return teachingAssignmentRepository.findById(id);
    }


    @Override
    public TeachingAssignmentWithSchedulesDTO getAssignmentWithSchedules(Long assignmentId) {
        // Tìm kiếm assignment theo ID
        TeachingAssignment assignment = teachingAssignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy assignment"));

        // Chuyển entity sang DTO
        TeachingAssignmentSimpleDTO assignmentDTO = new TeachingAssignmentSimpleDTO(assignment);

        // Lấy danh sách TeachingScheduleRequest liên quan
        List<TeachingScheduleRequest> schedules = scheduleRepository.findAllByAssignment_AssignmentId(assignmentId);

        // Chuyển danh sách TeachingScheduleRequest sang danh sách DTO
        List<TeachingScheduleSimpleDTO> scheduleDTOs = schedules.stream()
                .map(TeachingScheduleSimpleDTO::new)
                .toList();

        // Tạo response DTO tổng hợp
        TeachingAssignmentWithSchedulesDTO response = new TeachingAssignmentWithSchedulesDTO();
        response.setAssignment(assignmentDTO);
        response.setSchedules(scheduleDTOs);

        return response;
    }


}
