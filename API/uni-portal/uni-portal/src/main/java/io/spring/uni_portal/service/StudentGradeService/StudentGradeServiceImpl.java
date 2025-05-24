package io.spring.uni_portal.service.StudentGradeService;

import io.spring.uni_portal.dto.StudentGrade.*;
import io.spring.uni_portal.model.*;
import io.spring.uni_portal.repository.*;
import io.spring.uni_portal.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentGradeServiceImpl implements IStudentGradeService {
    private static final Logger logger = LoggerFactory.getLogger(StudentGradeServiceImpl.class);
    @Autowired
    private StudentGradeRepository studentGradeRepository;

    @Autowired
    private ClassSubjectStudentRepository classSubjectStudentRepository;

    @Autowired
    private GradeEventRepository gradeEventRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeachingAssignmentRepository teachingAssignmentRepository;

    @Override
    public Response<StudentGradeSubmissionResponse> submitGrade(StudentGradeSubmissionRequest request) {
        try {
            // Xác thực người dùng
            UsernamePasswordAuthenticationToken authentication =
                    (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
                throw new RuntimeException("Người dùng chưa xác thực hoặc token không hợp lệ.");
            }

            User currentUser = (User) authentication.getPrincipal();
            Student student = studentRepository.findById(currentUser.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sinh viên."));
            logger.info("User authenticated: userId = {}", currentUser.getUserId());

            // Kiểm tra các trường bắt buộc
            if (request.getClassStudentId() == null || request.getGradeEventId() == null) {
                return Response.failure("Mã lớp học phần và mã bài tập là bắt buộc");
            }

            if (request.getFileUrl() == null || request.getFileUrl().isEmpty()) {
                return Response.failure("File bài nộp là bắt buộc");
            }

            // Tìm ClassSubjectStudent dựa trên classStudentId và userId
            Long classStudentId = request.getClassStudentId();
            Long userId = student.getUserId();
            ClassSubjectStudent classSubjectStudent = classSubjectStudentRepository
                    .findByClassStudentIdAndUserId(classStudentId, userId)
                    .orElse(null);
            if (classSubjectStudent == null) {
                logger.error("No ClassSubjectStudent found for classStudentId: {} and userId: {}", classStudentId, userId);
                return Response.failure("Bạn chưa đăng ký lớp học phần này hoặc lớp không tồn tại");
            }
            logger.info("Found ClassSubjectStudent: id = {}, studentId = {}, status = {}",
                    classSubjectStudent.getClassSubjectStudentId(),
                    classSubjectStudent.getStudent().getUserId(),
                    classSubjectStudent.getStatus());

            // Kiểm tra trạng thái đăng ký của class_subject_student
            if (!"SUCCESS".equalsIgnoreCase(classSubjectStudent.getStatus())) {
                return Response.failure("Lớp học phần này chưa được mở hoặc bạn không đủ điều kiện nộp bài.");
            }

            // Kiểm tra grade_event có tồn tại không
            GradeEvent gradeEvent = gradeEventRepository
                    .findById(request.getGradeEventId())
                    .orElse(null);
            if (gradeEvent == null) {
                return Response.failure("Không tìm thấy bài tập");
            }

            // Kiểm tra grade_event có thuộc lớp học phần không
            if (!gradeEvent.getClassStudent().getClassStudentId().equals(classSubjectStudent.getClassStudent().getClassStudentId())) {
                return Response.failure("Bài tập này không thuộc lớp học phần mà bạn đã đăng ký.");
            }


            // Kiểm tra xem sinh viên đã nộp bài tập này chưa
            boolean exists = studentGradeRepository
                    .existsByClassSubjectStudent_ClassSubjectStudentIdAndGradeEvent_GradeEventId(
                            classSubjectStudent.getClassSubjectStudentId(), request.getGradeEventId());
            if (exists) {
                return Response.failure("Sinh viên đã nộp bài tập này rồi");
            }

            // Tạo mới StudentGrade để lưu bài nộp
            StudentGrade studentGrade = new StudentGrade();
            studentGrade.setClassSubjectStudent(classSubjectStudent);
            studentGrade.setGradeEvent(gradeEvent);
            studentGrade.setFileUrl(request.getFileUrl());
            studentGrade.setSubmittedAt(LocalDateTime.now());

            // Lưu bài nộp vào database
            StudentGrade savedStudentGrade = studentGradeRepository.save(studentGrade);

            // Tạo response
            StudentGradeSubmissionResponse response = new StudentGradeSubmissionResponse();
            response.setStudentGradeId(savedStudentGrade.getStudentGradeId());
            response.setClassSubjectStudentId(savedStudentGrade.getClassSubjectStudent().getClassSubjectStudentId());
            response.setGradeEventId(savedStudentGrade.getGradeEvent().getGradeEventId());
            response.setFileUrl(savedStudentGrade.getFileUrl());
            response.setSubmittedAt(savedStudentGrade.getSubmittedAt());

            return Response.success("Nộp bài tập thành công", response);
        } catch (Exception e) {
            logger.error("Error submitting grade: {}", e.getMessage(), e);
            return Response.failure("Không nộp được bài tập: " + e.getMessage());
        }
    }

    @Override
    public Response<StudentGradeGradingResponse> gradeSubmission(Long studentGradeId, Double score, String feedback) {
        try {
            // Tìm StudentGrade dựa trên studentGradeId
            StudentGrade studentGrade = studentGradeRepository.findById(studentGradeId)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy bài nộp với ID: " + studentGradeId));
            logger.info("Found StudentGrade for grading: id = {}", studentGradeId);

            // Kiểm tra xem bài nộp đã được chấm điểm hay chưa
            if (studentGrade.getScore() != null || studentGrade.getFeedback() != null) {
                logger.warn("Attempt to re-grade StudentGrade: id = {}", studentGradeId);
                return Response.failure("Bài nộp với ID " + studentGradeId + " đã được chấm điểm trước đó");
            }

            // Cập nhật điểm và feedback
            studentGrade.setScore(score);
            studentGrade.setFeedback(feedback);

            // Xác định isPassed dựa trên score
            studentGrade.setIsPassed(score != null && score >= 5.0);

            // Lưu lại vào database
            StudentGrade updatedStudentGrade = studentGradeRepository.save(studentGrade);

            // Tạo response
            StudentGradeGradingResponse response = new StudentGradeGradingResponse();
            response.setStudentGradeId(updatedStudentGrade.getStudentGradeId());
            response.setScore(updatedStudentGrade.getScore());
            response.setIsPassed(updatedStudentGrade.getIsPassed());
            response.setFeedback(updatedStudentGrade.getFeedback());

            return Response.success("Chấm điểm thành công", response);
        } catch (Exception e) {
            logger.error("Error grading submission: {}", e.getMessage(), e);
            return Response.failure("Không thể chấm điểm: " + e.getMessage());
        }
    }

    @Override
    public Response<List<StudentSubmissionStatusResponse>> getSubmissionStatus(Long classStudentId, Long gradeEventId) {
        try {
            // Kiểm tra gradeEvent có tồn tại không
            GradeEvent gradeEvent = gradeEventRepository.findById(gradeEventId)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy bài tập với ID: " + gradeEventId));
            logger.info("Found GradeEvent: id = {}", gradeEventId);

            // Kiểm tra gradeEvent có thuộc classStudentId không
            if (!gradeEvent.getClassStudent().getClassStudentId().equals(classStudentId)) {
                return Response.failure("Bài tập không thuộc lớp học này");
            }

            // Lấy danh sách sinh viên trong lớp
            List<ClassSubjectStudent> classSubjectStudents = classSubjectStudentRepository.findByClassStudent_ClassStudentId(classStudentId);
            if (classSubjectStudents.isEmpty()) {
                return Response.failure("Không tìm thấy sinh viên nào trong lớp này");
            }

            // Lấy danh sách bài nộp cho gradeEvent
            List<StudentGrade> studentGrades = studentGradeRepository.findByGradeEvent_GradeEventId(gradeEventId);

            // Lấy thông tin TeachingAssignment
            TeachingAssignment teachingAssignment = teachingAssignmentRepository
                    .findByTermClass_TermclassId(gradeEvent.getClassStudent().getTeachingScheduleRequest().getAssignment().getTermClass().getTermclassId())
                    .orElse(null);

            List<StudentSubmissionStatusResponse> responses = new ArrayList<>();

            // Xử lý từng sinh viên
            for (ClassSubjectStudent css : classSubjectStudents) {
                StudentSubmissionStatusResponse response = new StudentSubmissionStatusResponse();
                Student student = css.getStudent();
                ClassStudent classStudent = css.getClassStudent();

                // Student fields
                response.setStudentId(student.getUserId());
                response.setStudentName(student.getUser().getUserName());

                // ClassSubjectStudent fields
                response.setClassSubjectStudentId(css.getClassSubjectStudentId());
                response.setClassSubjectStudentStatus(css.getStatus());
                response.setRegistrationTime(css.getRegistrationTime());

                // ClassStudent fields
                response.setClassStudentId(classStudent.getClassStudentId());
                response.setClassStudentStatus(classStudent.getStatus());
                response.setClassStudentCreatedAt(classStudent.getCreatedAt());
                response.setClassStudentEndDate(classStudent.getEndDate());
                response.setClassStudentMaterials(classStudent.getMaterials());

                // GradeEvent fields
                response.setGradeEventId(gradeEvent.getGradeEventId());
                response.setGradeEventTitle(gradeEvent.getTitle());
                response.setEventDate(gradeEvent.getEventDate());
                response.setMaxScore(gradeEvent.getMaxScore());
                response.setGradeEventDescription(gradeEvent.getDescription());
                response.setGradeEventCreatedAt(gradeEvent.getCreatedAt());

                // TeachingAssignment fields
                if (teachingAssignment != null) {
                    response.setAssignmentId(teachingAssignment.getAssignmentId());
                    response.setAssignmentType(teachingAssignment.getAssignmentType());
                }

                // StudentGrade fields
                StudentGrade studentGrade = studentGrades.stream()
                        .filter(sg -> sg.getClassSubjectStudent().getClassSubjectStudentId().equals(css.getClassSubjectStudentId()))
                        .findFirst()
                        .orElse(null);

                if (studentGrade != null) {
                    response.setStudentGradeId(studentGrade.getStudentGradeId());
                    response.setScore(studentGrade.getScore());
                    response.setIsPassed(studentGrade.getIsPassed());
                    response.setFeedback(studentGrade.getFeedback());
                    response.setFileUrl(studentGrade.getFileUrl());
                    response.setSubmittedAt(studentGrade.getSubmittedAt());

                    // Xác định trạng thái nộp bài
                    LocalDateTime deadline = gradeEvent.getEventDate().atTime(23, 59, 59);
                    if (studentGrade.getSubmittedAt().isBefore(deadline) || studentGrade.getSubmittedAt().isEqual(deadline)) {
                        response.setSubmissionStatus(StudentSubmissionStatusResponse.SubmissionStatus.ON_TIME);
                    } else {
                        response.setSubmissionStatus(StudentSubmissionStatusResponse.SubmissionStatus.LATE);
                    }
                } else {
                    response.setSubmissionStatus(StudentSubmissionStatusResponse.SubmissionStatus.NOT_SUBMITTED);
                }

                responses.add(response);
            }

            return Response.success("Lấy danh sách trạng thái nộp bài thành công", responses);
        } catch (Exception e) {
            logger.error("Error retrieving submission status: {}", e.getMessage(), e);
            return Response.failure("Không thể lấy danh sách trạng thái nộp bài: " + e.getMessage());
        }
    }

    @Override
    public Response<List<StudentAssignmentResponse>> getAssignmentsByStudent() {
        try {
            // Xác thực người dùng qua token
            UsernamePasswordAuthenticationToken authentication =
                    (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
                throw new RuntimeException("Người dùng chưa xác thực hoặc token không hợp lệ.");
            }

            User currentUser = (User) authentication.getPrincipal();
            Student student = studentRepository.findById(currentUser.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sinh viên."));
            logger.info("User authenticated: userId = {}", currentUser.getUserId());

            // Lấy danh sách ClassSubjectStudent của sinh viên
            List<ClassSubjectStudent> classSubjectStudents = classSubjectStudentRepository.findByStudent_UserId(student.getUserId());
            if (classSubjectStudents.isEmpty()) {
                return Response.success("Sinh viên chưa đăng ký lớp học phần nào", new ArrayList<>());
            }

            List<StudentAssignmentResponse> responses = new ArrayList<>();

            // Duyệt qua từng ClassSubjectStudent
            for (ClassSubjectStudent css : classSubjectStudents) {
                ClassStudent classStudent = css.getClassStudent();
                // Lấy tất cả GradeEvent của ClassStudent
                List<GradeEvent> gradeEvents = gradeEventRepository.findByClassStudent_ClassStudentId(classStudent.getClassStudentId());

                // Lấy TermClass từ TeachingScheduleRequest
                TermClass termClass = classStudent.getTeachingScheduleRequest() != null
                        ? classStudent.getTeachingScheduleRequest().getAssignment().getTermClass()
                        : null;

                for (GradeEvent gradeEvent : gradeEvents) {
                    StudentAssignmentResponse response = new StudentAssignmentResponse();

                    // GradeEvent fields
                    response.setGradeEventId(gradeEvent.getGradeEventId());
                    response.setTitle(gradeEvent.getTitle());
                    response.setEventDate(gradeEvent.getEventDate());
                    response.setMaxScore(gradeEvent.getMaxScore());
                    response.setDescription(gradeEvent.getDescription());
                    response.setCreatedAt(gradeEvent.getCreatedAt());

                    // ClassStudent fields
                    response.setClassStudentId(classStudent.getClassStudentId());
                    response.setClassStudentStatus(classStudent.getStatus());
                    response.setClassStudentMaterials(classStudent.getMaterials());

                    // TermClass fields
                    if (termClass != null) {
                        response.setTermclassId(termClass.getTermclassId());
                        response.setClassname(termClass.getClassname());
                        response.setSemester(termClass.getSemester());
                        response.setSchoolyears(termClass.getSchoolyears());
                    }

                    // StudentGrade fields
                    StudentGrade studentGrade = studentGradeRepository
                            .findByClassSubjectStudent_ClassSubjectStudentIdAndGradeEvent_GradeEventId(
                                    css.getClassSubjectStudentId(), gradeEvent.getGradeEventId())
                            .orElse(null);

                    if (studentGrade != null) {
                        response.setStudentGradeId(studentGrade.getStudentGradeId());
                        response.setFileUrl(studentGrade.getFileUrl());
                        response.setSubmittedAt(studentGrade.getSubmittedAt());
                        response.setScore(studentGrade.getScore());
                        response.setIsPassed(studentGrade.getIsPassed());
                        response.setFeedback(studentGrade.getFeedback());

                        // Xác định trạng thái nộp bài
                        LocalDateTime deadline = gradeEvent.getEventDate().atTime(23, 59, 59);
                        if (studentGrade.getSubmittedAt().isBefore(deadline) || studentGrade.getSubmittedAt().isEqual(deadline)) {
                            response.setSubmissionStatus(StudentAssignmentResponse.SubmissionStatus.ON_TIME);
                        } else {
                            response.setSubmissionStatus(StudentAssignmentResponse.SubmissionStatus.LATE);
                        }
                    } else {
                        response.setSubmissionStatus(StudentAssignmentResponse.SubmissionStatus.NOT_SUBMITTED);
                    }

                    responses.add(response);
                }
            }

            return Response.success("Lấy danh sách bài tập thành công", responses);
        } catch (Exception e) {
            logger.error("Error retrieving assignments for student: {}", e.getMessage(), e);
            return Response.failure("Không thể lấy danh sách bài tập: " + e.getMessage());
        }
    }
}
