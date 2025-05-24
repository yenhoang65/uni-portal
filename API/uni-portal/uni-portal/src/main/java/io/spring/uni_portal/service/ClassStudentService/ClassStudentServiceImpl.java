package io.spring.uni_portal.service.ClassStudentService;

import io.spring.uni_portal.dto.ClassStudent.*;
import io.spring.uni_portal.model.ClassStudent;
import io.spring.uni_portal.model.ClassSubjectStudent;
import io.spring.uni_portal.model.Lecturer;
import io.spring.uni_portal.model.User;
import io.spring.uni_portal.repository.ClassStudentRepository;
import io.spring.uni_portal.repository.ClassSubjectStudentRepository;
import io.spring.uni_portal.repository.LecturerRepository;
import io.spring.uni_portal.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassStudentServiceImpl implements IClassStudentService{
    @Autowired
    private ClassStudentRepository classStudentRepository;

    @Autowired
    private ClassSubjectStudentRepository classSubjectStudentRepository;

    @Autowired
    private LecturerRepository lecturerRepository;

    private static final Logger logger = LoggerFactory.getLogger(ClassStudentServiceImpl.class);
    @Override
    public Response<Page<ClassStudentDTO>> getClassStudentsWithSearch(String searchValue, Pageable pageable) {
        // Lấy user đang đăng nhập từ SecurityContextHolder
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        // Kiểm tra nếu người dùng có role là admin
        boolean isAdmin = currentUser.getRole().contains("admin");

        Page<ClassStudent> classStudentPage;

        if (isAdmin) {
            // Nếu là admin, trả về tất cả bản ghi với phân trang và tìm kiếm
            classStudentPage = classStudentRepository.findAllBySearchValue(searchValue, pageable);
        } else {
            // Nếu không phải admin, chỉ trả về bản ghi của student hiện tại với phân trang và tìm kiếm
            Long userId = currentUser.getUserId();
            classStudentPage = classStudentRepository.findClassStudentsForStudent(userId, searchValue, pageable);
        }

        // Chuyển đổi từ Page<ClassStudent> thành Page<ClassStudentDTO>
        Page<ClassStudentDTO> responsePage = classStudentPage.map(this::convertToDTO);

        return Response.success("Danh sách ClassStudent với phân trang", responsePage);
    }



    // Phương thức chuyển đổi từ entity sang DTO
    private ClassStudentDTO convertToDTO(ClassStudent classStudent) {
        ClassStudentDTO dto = new ClassStudentDTO();
        dto.setClassStudentId(classStudent.getClassStudentId());
        dto.setStatus(classStudent.getStatus());
        dto.setScheduleId(classStudent.getTeachingScheduleRequest() != null
                ? classStudent.getTeachingScheduleRequest().getScheduleId()
                : null);
        dto.setCreatedAt(classStudent.getCreatedAt());
        dto.setEndDate(classStudent.getEndDate());
        dto.setMaterials(classStudent.getMaterials());
        // Giả định có cột student_id và class_id trong bảng class_student
        dto.setStudentId(null);
        dto.setClassId(null);
        return dto;
    }

    @Override
    public List<OpenedClassFullDTO> getOpenedClassesBySubject(Long subjectId) {
        return classStudentRepository.findFullOpenedClassesBySubjectId(subjectId)
                .stream()
                .map(OpenedClassFullDTO::new)
                .collect(Collectors.toList());
    }
//
@Override
public Response<List<ClassStudentResponse>> getStudentsByClass(Long classStudentId) {
    try {
        // Kiểm tra classStudentId có tồn tại không
        ClassStudent classStudent = classStudentRepository.findById(classStudentId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy lớp học với ID: " + classStudentId));
        logger.info("Found ClassStudent: id = {}, status = {}", classStudentId, classStudent.getStatus());

        // Lấy danh sách ClassSubjectStudent với status = "success"
        List<ClassSubjectStudent> classSubjectStudents = classSubjectStudentRepository
                .findByClassStudent_ClassStudentIdAndStatus(classStudentId, "success");
        logger.info("Found {} ClassSubjectStudent records for classStudentId = {}", classSubjectStudents.size(), classStudentId);

        if (classSubjectStudents.isEmpty()) {
            return Response.success("Không có sinh viên nào đăng ký thành công trong lớp này", new ArrayList<>());
        }

        List<ClassStudentResponse> responses = new ArrayList<>();
        for (ClassSubjectStudent css : classSubjectStudents) {
            ClassStudentResponse response = new ClassStudentResponse();
            response.setClassStudentId(css.getClassStudent().getClassStudentId());
            response.setClassSubjectStudentId(css.getClassSubjectStudentId());
            response.setStudentId(css.getStudent().getUserId());
            response.setName(css.getStudent().getUser().getUserName());
            response.setRegistrationTime(css.getRegistrationTime());
            response.setStatus(css.getStatus());
            responses.add(response);
            logger.debug("Added student: studentId = {}, name = {}, status = {}", css.getStudent().getUserId(), css.getStudent().getUser().getUserName(), css.getStatus());
        }

        return Response.success("Lấy danh sách sinh viên thành công", responses);
    } catch (Exception e) {
        logger.error("Error retrieving students for class: classStudentId = {}, error = {}", classStudentId, e.getMessage(), e);
        return Response.failure("Không thể lấy danh sách sinh viên: " + e.getMessage());
    }
}

    @Override
    public List<ClassStudentRequestDTO> getAllSuccessfulClasses() {
        // Xác thực người dùng
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            throw new RuntimeException("Người dùng chưa xác thực hoặc token không hợp lệ.");
        }

        User currentUser = (User) authentication.getPrincipal();
        Lecturer lecturer = lecturerRepository.findById(currentUser.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy giảng viên."));

        return classStudentRepository.findAllSuccessfulClassesByLecturerId(lecturer.getUserId());}
}
