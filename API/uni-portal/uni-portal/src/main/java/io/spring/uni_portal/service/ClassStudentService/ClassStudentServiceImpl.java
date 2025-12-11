package io.spring.uni_portal.service.ClassStudentService;

import io.spring.uni_portal.dto.Attendance.SuccessfulClassDTO;
import io.spring.uni_portal.dto.ClassStudent.*;
import io.spring.uni_portal.dto.Classroom.ClassroomDTO;
import io.spring.uni_portal.dto.Subject.SubjectDTO;
import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentDTO;
import io.spring.uni_portal.dto.TeachingScheduleRequest.TeachingScheduleDetailResponseDTO;
import io.spring.uni_portal.dto.TeachingScheduleRequest.TeachingScheduleRequestDTO;
import io.spring.uni_portal.dto.TermClass.TermClassDTO;
import io.spring.uni_portal.model.*;
import io.spring.uni_portal.model.Class;
import io.spring.uni_portal.repository.*;
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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClassStudentServiceImpl implements IClassStudentService{
    @Autowired
    private ClassStudentRepository classStudentRepository;

    @Autowired
    private ClassSubjectStudentRepository classSubjectStudentRepository;

    @Autowired
    private LecturerRepository lecturerRepository;


    @Autowired
    private IntermediaryRepository intermediaryRepository;

    @Autowired
    private StudentRepository studentRepository;



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
    public List<OpenedClassFullDTO> getOpenedClassesBySubject(Long subjectId, String semester, String schoolyear) {
        return classStudentRepository
                .findFullOpenedClassesBySubjectIdAndTerm(subjectId, semester, schoolyear)
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
    public List<ClassStudentRequestDTO> getAllSuccessfulClasses(String semester, String schoolyear) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            throw new RuntimeException("Người dùng chưa xác thực hoặc token không hợp lệ.");
        }

        User currentUser = (User) authentication.getPrincipal();
        Lecturer lecturer = lecturerRepository.findById(currentUser.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy giảng viên."));

        List<ClassStudentRequestDTO> result;
        if (semester != null && schoolyear != null) {
            result = classStudentRepository.findSuccessfulClassesByLecturerAndTerm(
                    lecturer.getUserId(), semester, schoolyear
            );
        } else {
            result = classStudentRepository.findAllSuccessfulClassesByLecturerId(lecturer.getUserId());
        }

        return result;
    }


    @Override
    public List<TeachingScheduleDetailResponseDTO> getSuccessClassesByLecturer(Long lecturerUserId) {
        List<ClassStudent> classStudents = classStudentRepository.findSuccessClassesByLecturerUserId(lecturerUserId);
        return classStudents.stream()
                .map(cs -> new TeachingScheduleDetailResponseDTO(cs.getTeachingScheduleRequest()))
                .collect(Collectors.toList());
    }



    @Override
    public Response<List<SuccessfulClassWithSubjectDTO>> getAllSuccessClassWithSubject() {
        List<ClassStudent> successClasses = classStudentRepository.findAllSuccessClassStudentsWithSubject();

        List<SuccessfulClassWithSubjectDTO> result = successClasses.stream().map(cs -> {
            var assignment = cs.getTeachingScheduleRequest().getAssignment();

            // Subject
            var s = assignment.getSubject();
            SubjectDTO subjectDTO = new SubjectDTO();
            subjectDTO.setSubjectId(s.getSubjectId());
            subjectDTO.setSubjectName(s.getSubjectName());
            subjectDTO.setLtCredits(s.getLtCredits());
            subjectDTO.setThCredits(s.getThCredits());
            subjectDTO.setSubjectDescription(s.getSubjectDescription());
            subjectDTO.setSubjectCoefficient(s.getSubjectCoefficient());

            // TermClass
            var tc = assignment.getTermClass();
            TermClassDTO termClassDTO = new TermClassDTO();
            termClassDTO.setClassname(tc.getClassname());
            termClassDTO.setProgress(tc.getProgress());
            termClassDTO.setSemester(tc.getSemester());
            termClassDTO.setSchoolyears(tc.getSchoolyears());

            return new SuccessfulClassWithSubjectDTO(
                    cs.getClassStudentId(),
                    cs.getStatus(),
                    subjectDTO,
                    termClassDTO
            );
        }).collect(Collectors.toList());

        return Response.success("Danh sách lớp học thành công với môn học", result);
    }


    @Override
    public Response<List<OpenedClassResponseDTO>> getOpenedClassForCurrentStudent(String semester, String schoolYear) {
        UsernamePasswordAuthenticationToken auth =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof User)) {
            throw new RuntimeException("Người dùng chưa xác thực hoặc token không hợp lệ.");
        }

        User currentUser = (User) auth.getPrincipal();
        Student student = studentRepository.findById(currentUser.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sinh viên"));

        Class studentClass = student.getClassEntity();
        if (studentClass == null || studentClass.getTrainingProgram() == null) {
            return Response.failure("Sinh viên chưa được gán lớp hoặc chương trình đào tạo.");
        }

        TrainingProgram trainingProgram = studentClass.getTrainingProgram();
        List<Intermediary> intermediarySubjects = intermediaryRepository.findByTrainingProgram(trainingProgram);

        List<ClassSubjectStudent> registeredSubjects =
                classSubjectStudentRepository.findByStudent_UserId(student.getUserId());

        Set<Long> learnedSubjectIds = registeredSubjects.stream()
                .map(css -> css.getClassStudent().getTeachingScheduleRequest().getAssignment().getSubject().getSubjectId())
                .collect(Collectors.toSet());

        List<Long> subjectIdsToRegister = intermediarySubjects.stream()
                .map(i -> i.getSubject().getSubjectId())
                .filter(id -> !learnedSubjectIds.contains(id))
                .distinct()
                .toList();

        List<ClassStudent> openClasses = classStudentRepository.findOpenClassStudentsForSubjectsByTerm(
                student.getUserId(), subjectIdsToRegister, semester, schoolYear);

        List<OpenedClassResponseDTO> result = openClasses.stream().map(cs -> {
            Subject subject = cs.getTeachingScheduleRequest().getAssignment().getSubject();
            SubjectDTO subjectDTO = new SubjectDTO(
                    subject.getSubjectId(),
                    subject.getSubjectName(),
                    subject.getLtCredits(),
                    subject.getThCredits(),
                    subject.getSubjectDescription(),
                    subject.getSubjectCoefficient()
            );

            TermClass termClass = cs.getTeachingScheduleRequest().getAssignment().getTermClass();
            TermClassDTO termClassDTO = new TermClassDTO();
            termClassDTO.setClassname(termClass.getClassname());
            termClassDTO.setProgress(termClass.getProgress());
            termClassDTO.setSemester(termClass.getSemester());
            termClassDTO.setSchoolyears(termClass.getSchoolyears());

            Lecturer lecturer = cs.getTeachingScheduleRequest().getAssignment().getLecturer();
            SuccessfulClassDTO.TeachingAssignmentDTO.LecturerDTO lecturerDTO =
                    new SuccessfulClassDTO.TeachingAssignmentDTO.LecturerDTO(lecturer);
            lecturerDTO.setLecturerId(lecturer.getUser().getUserId());
            lecturerDTO.setLecturerName(lecturer.getUser().getUserName());

            TeachingScheduleRequest tsr = cs.getTeachingScheduleRequest();
            TeachingScheduleRequestDTO tsrDTO = new TeachingScheduleRequestDTO();
            tsrDTO.setAssignmentId(tsr.getAssignment().getAssignmentId());
            tsrDTO.setStatus(tsr.getStatus());

            List<TeachingScheduleRequestDTO.ScheduleDetailDTO> scheduleDetailDTOList = tsr.getScheduleDetails().stream().map(s -> {
                TeachingScheduleRequestDTO.ScheduleDetailDTO dto = new TeachingScheduleRequestDTO.ScheduleDetailDTO();
                dto.setClassroomId(s.getClassroomId());
                dto.setLesson(s.getLesson());
                dto.setDateTime(s.getDateTime());
                dto.setEndDate(s.getEndDate());
                dto.setClassType(s.getClassType());
                return dto;
            }).toList();
            tsrDTO.setScheduleDetails(scheduleDetailDTOList);

            if (tsr.getMaterials() != null) {
                List<TeachingScheduleRequestDTO.MaterialDTO> materialDTOList = tsr.getMaterials().stream().map(m -> {
                    TeachingScheduleRequestDTO.MaterialDTO dto = new TeachingScheduleRequestDTO.MaterialDTO();
                    dto.setLt(m.getLt());
                    dto.setTh(m.getTh());
                    return dto;
                }).toList();
                tsrDTO.setMaterials(materialDTOList);
            }

            OpenedClassResponseDTO dto = new OpenedClassResponseDTO();
            dto.setClassStudentId(cs.getClassStudentId());
            dto.setStatus(cs.getStatus());
            dto.setCreatedAt(cs.getCreatedAt());
            dto.setSubject(subjectDTO);
            dto.setTermClass(termClassDTO);
            dto.setLecturer(lecturerDTO);
            dto.setScheduleRequest(tsrDTO);
            return dto;
        }).toList();

        return Response.success("Danh sách lớp đang mở mà sinh viên chưa học", result);
    }





}
