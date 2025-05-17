package io.spring.uni_portal.service.ClassRegistrationService;

import io.spring.uni_portal.dto.ClassSubjectStudent.ClassSubjectStudentDTO;
import io.spring.uni_portal.dto.ClassSubjectStudent.RegisterClassRequestDTO;
import io.spring.uni_portal.dto.ClassSubjectStudent.RegisteredClassDTO;
import io.spring.uni_portal.dto.ClassSubjectStudent.UnregisterClassRequestDTO;
import io.spring.uni_portal.model.*;
import io.spring.uni_portal.repository.*;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.security.JwtService;
import io.spring.uni_portal.service.ClassStudentService.IClassStudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassRegistrationServiceImpl implements IClassRegistrationService  {
    @Autowired
    private ClassStudentRepository classStudentRepo;

    @Autowired
    private ClassSubjectStudentRepository classSubjectRepo;

    @Autowired
    private AttendanceSessionRepository attendanceSessionRepo;

    @Autowired
    private AttendanceRepository attendanceRepo;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentRegistrationPeriodRepository studentRegistrationPeriodRepository;

    @Override
    @Transactional
    public ClassSubjectStudentDTO registerStudentToClass(RegisterClassRequestDTO dto) {
        // 🔐 Check thời gian đăng ký sinh viên hợp lệ
        LocalDateTime now = LocalDateTime.now();
        StudentRegistrationPeriod currentPeriod = studentRegistrationPeriodRepository.findActivePeriod(now)
                .orElseThrow(() -> new IllegalStateException("Hiện tại không có đợt đăng ký sinh viên nào đang mở"));

        if (now.isBefore(currentPeriod.getStartDate()) || now.isAfter(currentPeriod.getEndDate())) {
            throw new IllegalStateException(String.format(
                    "Đăng ký lớp chỉ được thực hiện trong khoảng từ %s đến %s",
                    currentPeriod.getStartDate(), currentPeriod.getEndDate()
            ));
        }

        // Xác thực người dùng
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            throw new RuntimeException("Người dùng chưa xác thực hoặc token không hợp lệ.");
        }

        User currentUser = (User) authentication.getPrincipal();

        Student student = studentRepository.findById(currentUser.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sinh viên."));

        ClassStudent classStudent = classStudentRepo.findById(dto.getClassStudentId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp học."));

        // Kiểm tra trùng lớp học cụ thể
        boolean exists = classSubjectRepo.existsByClassStudent_ClassStudentIdAndStudent_UserId(
                dto.getClassStudentId(), student.getUserId());
        if (exists) {
            throw new IllegalStateException("Sinh viên đã đăng ký lớp học này rồi.");
        }

        // Kiểm tra xem sinh viên đã đăng ký lớp nào khác cho cùng subject_id trong khoảng thời gian đăng ký
        Long subjectId = classStudent.getTeachingScheduleRequest().getAssignment().getSubject().getSubjectId();
        boolean subjectExistsInPeriod = classSubjectRepo.existsByStudent_UserIdAndClassStudent_TeachingScheduleRequest_Assignment_Subject_SubjectIdAndRegistrationTimeBetween(
                student.getUserId(), subjectId, currentPeriod.getStartDate(), currentPeriod.getEndDate());
        if (subjectExistsInPeriod) {
            throw new IllegalStateException(String.format(
                    "Sinh viên đã đăng ký một lớp học khác cho môn học (subject_id: %d) trong khoảng thời gian này (%s đến %s).",
                    subjectId, currentPeriod.getStartDate(), currentPeriod.getEndDate()
            ));
        }

        // Ghi danh
        ClassSubjectStudent css = new ClassSubjectStudent();
        css.setClassStudent(classStudent);
        css.setStudent(student);
        css.setStatus(dto.getStatus());
        css.setRegistrationTime(now); // Lưu thời gian đăng ký
        classSubjectRepo.save(css);

        // Kiểm tra số lượng sinh viên đã đăng ký
        long count = classSubjectRepo.countByClassStudent_ClassStudentId(dto.getClassStudentId());

        if (count >= 6 && !"success".equalsIgnoreCase(classStudent.getStatus())) {
            // Cập nhật trạng thái lớp
            classStudent.setStatus("success");
            classStudentRepo.save(classStudent);

            // Lấy lịch giảng dạy
            TeachingScheduleRequest schedule = classStudent.getTeachingScheduleRequest();
            if (schedule == null || schedule.getScheduleDetails() == null || schedule.getScheduleDetails().isEmpty()) {
                throw new IllegalStateException("Không có lịch giảng dạy để tạo phiên học.");
            }

            // Sinh các buổi học theo tuần từ mỗi scheduleDetail
            for (TeachingScheduleRequest.ScheduleDetail detail : schedule.getScheduleDetails()) {
                try {
                    LocalDateTime start = LocalDateTime.parse(detail.getDateTime());
                    LocalDateTime end = LocalDateTime.parse(detail.getEndDate());

                    LocalDateTime current = start;
                    while (!current.isAfter(end)) {
                        AttendanceSession session = new AttendanceSession();
                        session.setClassStudent(classStudent);
                        session.setScheduledDate(current);
                        session.setStatus("created");
                        attendanceSessionRepo.save(session);

                        current = current.plusWeeks(1);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Lỗi khi xử lý ngày tháng cho tiết học: " + e.getMessage());
                }
            }
        }

        return new ClassSubjectStudentDTO(css);
    }


//    @Override
//    @Transactional
//    public ClassSubjectStudentDTO registerStudentToClass(RegisterClassRequestDTO dto) {
//        LocalDateTime now = LocalDateTime.now();
//
//        StudentRegistrationPeriod currentPeriod = studentRegistrationPeriodRepository.findActivePeriod(now)
//                .orElseThrow(() -> new IllegalStateException("Hiện tại không có đợt đăng ký sinh viên nào đang mở"));
//
//        boolean isInRegistrationPeriod = !now.isBefore(currentPeriod.getStartDate()) && !now.isAfter(currentPeriod.getEndDate());
//
//        UsernamePasswordAuthenticationToken authentication =
//                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
//            throw new RuntimeException("Người dùng chưa xác thực hoặc token không hợp lệ.");
//        }
//
//        User currentUser = (User) authentication.getPrincipal();
//
//        Student student = studentRepository.findById(currentUser.getUserId())
//                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sinh viên."));
//
//        ClassStudent classStudent = classStudentRepo.findById(dto.getClassStudentId())
//                .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp học."));
//
//        boolean exists = classSubjectRepo.existsByClassStudent_ClassStudentIdAndStudent_UserId(
//                dto.getClassStudentId(), student.getUserId());
//        if (exists) {
//            throw new IllegalStateException("Sinh viên đã đăng ký lớp học này rồi.");
//        }
//
//        // Ghi danh với trạng thái luôn là pending
//        ClassSubjectStudent css = new ClassSubjectStudent();
//        css.setClassStudent(classStudent);
//        css.setStudent(student);
//        css.setStatus("pending");
//        classSubjectRepo.save(css);
//
//        if (now.isAfter(currentPeriod.getEndDate())) {
//            // Lấy danh sách sinh viên có status = "pending"
//            List<ClassSubjectStudent> pendingStudents =
//                    classSubjectRepo.findByClassStudent_ClassStudentIdAndStatus(dto.getClassStudentId(), "pending");
//
//            // Nếu có đủ ≥ 6 người pending thì mở lớp, bất kể có ai reject hay không
//            if (pendingStudents.size() >= 6) {
//                classStudent.setStatus("success");
//                classStudentRepo.save(classStudent);
//
//                for (ClassSubjectStudent studentItem : pendingStudents) {
//                    studentItem.setStatus("success");
//                }
//                classSubjectRepo.saveAll(pendingStudents);
//
//                // Sinh buổi học
//                TeachingScheduleRequest schedule = classStudent.getTeachingScheduleRequest();
//                if (schedule == null || schedule.getScheduleDetails() == null || schedule.getScheduleDetails().isEmpty()) {
//                    throw new IllegalStateException("Không có lịch giảng dạy để tạo phiên học.");
//                }
//
//                for (TeachingScheduleRequest.ScheduleDetail detail : schedule.getScheduleDetails()) {
//                    LocalDateTime start = LocalDateTime.parse(detail.getDateTime());
//                    LocalDateTime end = LocalDateTime.parse(detail.getEndDate());
//
//                    LocalDateTime current = start;
//                    while (!current.isAfter(end)) {
//                        AttendanceSession session = new AttendanceSession();
//                        session.setClassStudent(classStudent);
//                        session.setScheduledDate(current);
//                        session.setStatus("created");
//                        attendanceSessionRepo.save(session);
//                        current = current.plusWeeks(1);
//                    }
//                }
//            } else {
//                // Không đủ người → cancel lớp
//                classStudent.setStatus("cancel");
//                classStudentRepo.save(classStudent);
//
//                for (ClassSubjectStudent studentItem : pendingStudents) {
//                    studentItem.setStatus("cancel");
//                }
//                classSubjectRepo.saveAll(pendingStudents);
//            }
//        }
//
//        return new ClassSubjectStudentDTO(css);
//    }





    @Override
    public List<RegisteredClassDTO> getRegisteredClasses(Long userId) {
        return classSubjectRepo.findRegisteredClassesByUserId(userId).stream().map(css -> {
            var cs = css.getClassStudent();
            var tsr = cs.getTeachingScheduleRequest();
            var assignment = tsr.getAssignment();
            var subject = assignment.getSubject();
            var termClass = assignment.getTermClass();
            var classroom = tsr.getClassroom();

            return new RegisteredClassDTO(
                    css.getClassSubjectStudentId(),
                    css.getStatus(),
                    cs.getClassStudentId(),
                    cs.getStatus(),
                    cs.getCreatedAt(),
                    cs.getEndDate(),
                    cs.getMaterials(),
                    tsr.getScheduleId(),
                    tsr.getClassType(),
                    tsr.getLesson(),
                    tsr.getStartDate(),
                    tsr.getEndDate(),
                    tsr.getDateTime(),
                    tsr.getStatus(),
                    tsr.getScheduleDetails(),
                    tsr.getMaterials(),
                    assignment.getAssignmentId(),
                    assignment.getAssignmentType() != null ? assignment.getAssignmentType().name() : null,
                    subject.getSubjectId(),
                    subject.getSubjectName(),
                    subject.getSubjectDescription(),
                    subject.getLtCredits(),
                    subject.getThCredits(),
                    subject.getSubjectCoefficient(),
                    termClass.getTermclassId(),
                    termClass.getClassname(),
                    termClass.getSchoolyears(),
                    termClass.getSemester(),
                    termClass.getProgress(),
                    classroom.getClassroomId(),
                    classroom.getClassroomName(),
                    classroom.getNumberOfSeats() != null ? classroom.getNumberOfSeats().intValue() : null,
                    classroom.getDevice(),
                    classroom.getStatus()
            );
        }).collect(Collectors.toList());
    }



    @Override
    @Transactional
    public void unregisterStudentFromClass(UnregisterClassRequestDTO dto) {
        LocalDateTime now = LocalDateTime.now();
        StudentRegistrationPeriod currentPeriod = studentRegistrationPeriodRepository.findActivePeriod(now)
                .orElseThrow(() -> new IllegalStateException("Hiện tại không có đợt đăng ký sinh viên nào đang mở"));

        if (now.isBefore(currentPeriod.getStartDate()) || now.isAfter(currentPeriod.getEndDate())) {
            throw new IllegalStateException(String.format(
                    "Huỷ đăng ký lớp chỉ được thực hiện trong khoảng từ %s đến %s",
                    currentPeriod.getStartDate(), currentPeriod.getEndDate()
            ));
        }

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            throw new RuntimeException("Người dùng chưa xác thực hoặc token không hợp lệ.");
        }

        User currentUser = (User) authentication.getPrincipal();

        ClassSubjectStudent css = classSubjectRepo.findById(dto.getClassSubjectStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy bản ghi đăng ký lớp học."));

        if (!css.getStudent().getUserId().equals(currentUser.getUserId())) {
            throw new SecurityException("Không có quyền huỷ đăng ký lớp học này.");
        }

        classSubjectRepo.delete(css);
    }

//    @Override
//    @Transactional
//    public ClassSubjectStudentDTO unregisterStudentFromClass(UnregisterClassRequestDTO dto) {
//        LocalDateTime now = LocalDateTime.now();
//        StudentRegistrationPeriod currentPeriod = studentRegistrationPeriodRepository.findActivePeriod(now)
//                .orElseThrow(() -> new IllegalStateException("Hiện tại không có đợt đăng ký sinh viên nào đang mở"));
//
//        UsernamePasswordAuthenticationToken authentication =
//                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
//            throw new RuntimeException("Người dùng chưa xác thực hoặc token không hợp lệ.");
//        }
//
//        User currentUser = (User) authentication.getPrincipal();
//
//        Student student = studentRepository.findById(currentUser.getUserId())
//                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sinh viên."));
//
//        // Tìm bản ghi đăng ký lớp học (class_subject_student) dựa vào classStudentId + userId
//        ClassSubjectStudent css = classSubjectRepo.findByClassStudent_ClassStudentIdAndStudent_UserId(
//                dto.getClassStudentId(), student.getUserId()
//        ).orElseThrow(() -> new IllegalArgumentException("Không tìm thấy bản ghi đăng ký lớp học."));
//
//        // Cập nhật trạng thái thành "reject"
//        css.setStatus("reject");
//        classSubjectRepo.save(css);
//
//        return new ClassSubjectStudentDTO(css);
//    }





    @Override
    @Transactional
    public void markAttendance(Long sessionId, Long classStudentId, String status, String note) {
        Attendance attendance = attendanceRepo.findAll().stream()
                .filter(a -> a.getAttendanceSession().getSessionId().equals(sessionId)
                        && a.getClassStudent().getClassStudentId().equals(classStudentId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bản ghi điểm danh."));

        attendance.setStatus(status);
        attendance.setNote(note);
        attendanceRepo.save(attendance);
    }

}
