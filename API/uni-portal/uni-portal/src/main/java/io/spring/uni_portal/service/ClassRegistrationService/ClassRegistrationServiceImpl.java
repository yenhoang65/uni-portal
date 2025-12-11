package io.spring.uni_portal.service.ClassRegistrationService;

import io.spring.uni_portal.dto.ClassStudent.ClassStudentDTO;
import io.spring.uni_portal.dto.ClassSubjectStudent.ClassSubjectStudentDTO;
import io.spring.uni_portal.dto.ClassSubjectStudent.RegisterClassRequestDTO;
import io.spring.uni_portal.dto.ClassSubjectStudent.RegisteredClassDTO;
import io.spring.uni_portal.dto.ClassSubjectStudent.UnregisterClassRequestDTO;
import io.spring.uni_portal.dto.Registration.RegistrationFullDTO;
import io.spring.uni_portal.dto.TeachingScheduleRequest.TeachingScheduleRequestClassDTO;
import io.spring.uni_portal.model.*;
import io.spring.uni_portal.model.Class;
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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    @Autowired
    private IntermediaryRepository intermediaryRepo;

    @Autowired
    private SubjectRepository subjectRepo;

    @Autowired
    private ClassRepository classRepo;

    private Set<Integer> parseLessonRange(String lessonRange) {
        String[] parts = lessonRange.split("-");
        int start = Integer.parseInt(parts[0].trim());
        int end = Integer.parseInt(parts[1].trim());
        return IntStream.rangeClosed(start, end).boxed().collect(Collectors.toSet());
    }


    @Override
    @Transactional
    public ClassSubjectStudentDTO registerStudentToClass(RegisterClassRequestDTO dto) {
        LocalDateTime now = LocalDateTime.now();

        // Kiểm tra đợt đăng ký hiện tại
        StudentRegistrationPeriod currentPeriod = studentRegistrationPeriodRepository.findActivePeriod(now)
                .orElseThrow(() -> new IllegalStateException("Hiện tại không có đợt đăng ký sinh viên nào đang mở"));

        if (now.isBefore(currentPeriod.getStartDate()) || now.isAfter(currentPeriod.getEndDate())) {
            throw new IllegalStateException(String.format(
                    "Đăng ký lớp chỉ được thực hiện trong khoảng từ %s đến %s",
                    currentPeriod.getStartDate(), currentPeriod.getEndDate()));
        }

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            throw new RuntimeException("Người dùng chưa xác thực hoặc token không hợp lệ.");
        }

        User currentUser = (User) authentication.getPrincipal();
        Student student = studentRepository.findById(currentUser.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sinh viên."));

        // Kiểm tra class_id của sinh viên
        if (student.getClassEntity().getClassId() == null) {
            throw new IllegalStateException("Sinh viên chưa được gán vào lớp học.");
        }

        // Lấy thông tin lớp cần đăng ký
        ClassStudent classStudent = classStudentRepo.findById(dto.getClassStudentId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp học."));

        // Gán period nếu lớp chưa có
        if (classStudent.getRegistrationPeriod() == null) {
            classStudent.setRegistrationPeriod(currentPeriod);
            classStudentRepo.save(classStudent);

            // Bổ sung dòng này để cập nhật lại object sau khi gán period
            classStudent = classStudentRepo.findById(classStudent.getClassStudentId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy classStudent sau khi gán period"));
        }



        // Kiểm tra đã đăng ký lớp này chưa
        boolean exists = classSubjectRepo.existsByClassStudent_ClassStudentIdAndStudent_UserId(
                dto.getClassStudentId(), student.getUserId());
        if (exists) {
            throw new IllegalStateException("Sinh viên đã đăng ký lớp học này rồi.");
        }


        // 1. Lấy danh sách tất cả lớp sinh viên đã đăng ký
        List<ClassSubjectStudent> registeredClasses = classSubjectRepo.findByStudent_UserId(student.getUserId());

// 2. Lấy lịch học của lớp mà sinh viên đang muốn đăng ký
        List<TeachingScheduleRequest.ScheduleDetail> newScheduleDetails =
                classStudent.getTeachingScheduleRequest().getScheduleDetails();

        for (ClassSubjectStudent registered : registeredClasses) {
            List<TeachingScheduleRequest.ScheduleDetail> existingScheduleDetails =
                    registered.getClassStudent().getTeachingScheduleRequest().getScheduleDetails();

            for (TeachingScheduleRequest.ScheduleDetail newDetail : newScheduleDetails) {
                LocalDate newDate = LocalDateTime.parse(newDetail.getDateTime()).toLocalDate();
                Set<Integer> newLessons = parseLessonRange(newDetail.getLesson());

                for (TeachingScheduleRequest.ScheduleDetail existingDetail : existingScheduleDetails) {
                    LocalDate existingDate = LocalDateTime.parse(existingDetail.getDateTime()).toLocalDate();
                    Set<Integer> existingLessons = parseLessonRange(existingDetail.getLesson());

                    boolean sameDate = newDate.equals(existingDate);
                    boolean overlapLessons = !Collections.disjoint(newLessons, existingLessons);

                    // Chỉ báo lỗi khi cùng ngày và trùng tiết
                    if (sameDate && overlapLessons) {
                        throw new IllegalStateException(String.format(
                                "Lịch học bị trùng vào ngày %s, tiết %s với lớp đã đăng ký.",
                                newDate, newDetail.getLesson()
                        ));
                    }
                }
            }
        }


        // Kiểm tra đã đăng ký lớp khác của cùng môn trong đợt này chưa
        Long subjectId = classStudent.getTeachingScheduleRequest().getAssignment().getSubject().getSubjectId();
        boolean subjectExistsInPeriod = classSubjectRepo
                .existsByStudent_UserIdAndClassStudent_TeachingScheduleRequest_Assignment_Subject_SubjectIdAndRegistrationTimeBetween(
                        student.getUserId(), subjectId, currentPeriod.getStartDate(), currentPeriod.getEndDate());
        if (subjectExistsInPeriod) {
            throw new IllegalStateException(String.format(
                    "Sinh viên đã đăng ký một lớp khác cho môn học (subject_id: %d) trong khoảng thời gian này.",
                    subjectId));
        }

        // ========================== KIỂM TRA GIỚI HẠN 25 TÍN CHỈ ==========================
        TeachingAssignment assignment = classStudent.getTeachingScheduleRequest().getAssignment();
        TermClass termClass = assignment.getTermClass();

        String progress = termClass.getProgress();
        String semester = termClass.getSemester();
        String schoolYears = termClass.getSchoolyears();

        List<ClassSubjectStudent> registeredClassesInSameTerm =
                classSubjectRepo.findByStudent_UserIdAndClassStudent_TeachingScheduleRequest_Assignment_TermClass_ProgressAndTermClass_SemesterAndTermClass_Schoolyears(
                        student.getUserId(), progress, semester, schoolYears);

        int totalCredits = registeredClassesInSameTerm.stream()
                .map(css -> css.getClassStudent().getTeachingScheduleRequest().getAssignment().getSubject())
                .distinct()
                .mapToInt(sub -> (sub.getLtCredits() != null ? sub.getLtCredits() : 0) +
                        (sub.getThCredits() != null ? sub.getThCredits() : 0))
                .sum();

        Subject currentSubject = assignment.getSubject();
        int newCredits = (currentSubject.getLtCredits() != null ? currentSubject.getLtCredits() : 0) +
                (currentSubject.getThCredits() != null ? currentSubject.getThCredits() : 0);

        if (totalCredits + newCredits > 25) {
            throw new IllegalStateException("Bạn chỉ được đăng ký tối đa 25 tín chỉ trong mỗi kỳ. "
                    + "Hiện đã đăng ký " + totalCredits + " tín chỉ, thêm lớp này sẽ vượt quá giới hạn.");
        }

        // ========================== KIỂM TRA MÔN HỌC TIÊN QUYẾT ==========================
        Subject currentSubjectCheck = assignment.getSubject();

        // Lấy class của sinh viên để lấy trainingProgramId
        Class studentClass = classRepo.findById(student.getClassEntity().getClassId())
                .orElseThrow(() -> new IllegalStateException("Không tìm thấy lớp của sinh viên."));
        Long trainingProgramId = studentClass.getTrainingProgram().getTrainingProgramId();



        // Tìm tất cả các môn tiên quyết của môn học hiện tại trong chương trình đào tạo
        List<Intermediary> prerequisites = intermediaryRepo.findByTrainingProgram_TrainingProgramIdAndSubject_SubjectId(
                trainingProgramId, currentSubjectCheck.getSubjectId());

        if (!prerequisites.isEmpty()) {
            // Lấy danh sách các môn tiên quyết từ chuỗi [1056,1057,1058]
            List<Long> prerequisiteSubjectIds = prerequisites.stream()
                    .map(intermediary -> {
                        String prereqStr = intermediary.getPrerequisiteFor();
                        if (prereqStr != null && !prereqStr.isEmpty() && prereqStr.startsWith("[")) {
                            prereqStr = prereqStr.replaceAll("[\\[\\]]", "");
                            return Arrays.stream(prereqStr.split(","))
                                    .filter(s -> !s.trim().isEmpty())
                                    .map(String::trim)
                                    .map(Long::valueOf)
                                    .toArray(Long[]::new);
                        }
                        return new Long[0]; // Trả về mảng rỗng nếu không có giá trị hợp lệ
                    })
                    .flatMap(Arrays::stream)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            if (!prerequisiteSubjectIds.isEmpty()) {
                // Kiểm tra xem sinh viên đã hoàn thành tất cả các môn tiên quyết chưa
                List<ClassSubjectStudent> completedClasses = classSubjectRepo.findByStudent_UserId(student.getUserId());

                // Lấy danh sách subject_id của các lớp sinh viên đã hoàn thành
                Set<Long> completedSubjectIds = completedClasses.stream()
                        .filter(css -> "completed".equals(css.getStatus())) // Giả sử trạng thái "completed" nghĩa là đã hoàn thành
                        .map(css -> css.getClassStudent().getTeachingScheduleRequest().getAssignment().getSubject().getSubjectId())
                        .collect(Collectors.toSet());

                // Kiểm tra xem tất cả môn tiên quyết có trong danh sách đã hoàn thành không
                List<Long> missingPrerequisites = prerequisiteSubjectIds.stream()
                        .filter(prereqId -> !completedSubjectIds.contains(prereqId))
                        .collect(Collectors.toList());

                if (!missingPrerequisites.isEmpty()) {
                    // Lấy tên các môn tiên quyết còn thiếu
                    List<Subject> missingSubjects = subjectRepo.findAllById(missingPrerequisites);
                    String missingSubjectNames = missingSubjects.stream()
                            .map(Subject::getSubjectName)
                            .collect(Collectors.joining(", "));

                    throw new IllegalStateException(String.format(
                            "Bạn cần hoàn thành các môn tiên quyết sau trước khi đăng ký môn %s: %s",
                            currentSubjectCheck.getSubjectName(), missingSubjectNames));
                }
            }
        }

        // Lưu thông tin đăng ký
        ClassSubjectStudent css = new ClassSubjectStudent();
        css.setClassStudent(classStudent);
        css.setStudent(student);
        css.setStatus("pending");
        css.setRegistrationTime(now);
        classSubjectRepo.save(css);

        return new ClassSubjectStudentDTO(css);
    }

    @Override
    @Transactional
    public void finalizeRegistrationPeriod(Long periodId) {
        StudentRegistrationPeriod period = studentRegistrationPeriodRepository.findById(periodId)
                .orElseThrow(() -> new IllegalStateException("Không tìm thấy đợt đăng ký với ID: " + periodId));

        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(period.getEndDate())) {
            throw new IllegalStateException("Đợt đăng ký chưa kết thúc. Không thể hoàn tất xử lý.");
        }

        List<ClassStudent> classes = classStudentRepo.findAllByRegistrationPeriodId(periodId);
        for (ClassStudent classStudent : classes) {
            long pendingCount = classSubjectRepo.countByClassStudent_ClassStudentIdAndStatus(
                    classStudent.getClassStudentId(), "pending");

            if (pendingCount >= 6) {
                classStudent.setStatus("success");
                classStudentRepo.save(classStudent);

                List<ClassSubjectStudent> pendingRegistrations = classSubjectRepo
                        .findByClassStudent_ClassStudentIdAndStatus(classStudent.getClassStudentId(), "pending");
                for (ClassSubjectStudent css : pendingRegistrations) {
                    css.setStatus("success");
                    classSubjectRepo.save(css);
                }

                TeachingScheduleRequest schedule = classStudent.getTeachingScheduleRequest();
                if (schedule == null || schedule.getScheduleDetails() == null || schedule.getScheduleDetails().isEmpty()) {
                    throw new IllegalStateException("Không có lịch giảng dạy để tạo phiên học.");
                }

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
            } else {
                // Không đủ số lượng -> huỷ lớp
                classStudent.setStatus("cancel");
                classStudentRepo.save(classStudent);

                List<ClassSubjectStudent> pendingRegistrations = classSubjectRepo
                        .findByClassStudent_ClassStudentIdAndStatus(classStudent.getClassStudentId(), "pending");
                for (ClassSubjectStudent css : pendingRegistrations) {
                    css.setStatus("cancel");
                    classSubjectRepo.save(css);
                }
            }
        }
    }






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

    @Override
    public Response<List<RegistrationFullDTO>> getMyRegisteredClasses() {
        // 1. Lấy user hiện tại
        UsernamePasswordAuthenticationToken auth =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof io.spring.uni_portal.model.User)) {
            throw new RuntimeException("Người dùng chưa xác thực hoặc token không hợp lệ.");
        }
        io.spring.uni_portal.model.User currentUser = (io.spring.uni_portal.model.User) auth.getPrincipal();
        Long userId = currentUser.getUserId();

        // 2. Query về tất cả classSubjectStudent của user
        List<ClassSubjectStudent> regs = classSubjectRepo.findByStudentUserId(userId);

        // 3. Map sang DTO
        List<RegistrationFullDTO> dtoList = regs.stream().map(css -> {
            // Entity ClassStudent và TeachingScheduleRequest liên quan
            ClassStudent cs = css.getClassStudent();
            TeachingScheduleRequest ts = cs.getTeachingScheduleRequest();

            // --- 3.1 DTO cho TeachingScheduleRequest ---
            var tsDto = new TeachingScheduleRequestClassDTO(
                    ts.getScheduleId(),
                    ts.getClassroom() != null ? ts.getClassroom().getClassroomId() : null,
                    ts.getLesson(),
                    ts.getStartDate(),
                    ts.getEndDate(),
                    ts.getDateTime(),
                    ts.getStatus(),
                    ts.getCreatedAt(),
                    ts.getAssignment() != null ? ts.getAssignment().getAssignmentId() : null,
                    ts.getClassType(),
                    // material
                    ts.getMaterials().stream()
                            .map(m -> new TeachingScheduleRequestClassDTO.MaterialDTO(m.getLt(), m.getTh()))
                            .collect(Collectors.toList()),
                    // scheduleDetail
                    ts.getScheduleDetails().stream()
                            .map(sd -> new TeachingScheduleRequestClassDTO.ScheduleDetailDTO(
                                    sd.getClassroomId(), sd.getLesson(), sd.getDateTime(), sd.getEndDate(), sd.getClassType()))
                            .collect(Collectors.toList())
            );

            // --- 3.2 DTO cho ClassStudent ---
            // Lưu ý: DTO bạn định nghĩa nhận vào studentId và classId là ID của classroom
            var csDto = new ClassStudentDTO(
                    cs.getClassStudentId(),
                    cs.getStatus(),
                    ts.getScheduleId(),
                    cs.getCreatedAt(),
                    cs.getEndDate(),
                    cs.getMaterials(),
                    // studentId lấy từ css (ClassSubjectStudent) chứ không phải từ ClassStudent
                    css.getStudent().getUserId(),
                    // classId là classroomId
                    ts.getClassroom() != null ? ts.getClassroom().getClassroomId() : null
            );

            // --- 3.3 DTO cho ClassSubjectStudent ---
            // Bạn đã có constructor nhận thẳng entity
            var cssDto = new ClassSubjectStudentDTO(css);

            return new RegistrationFullDTO(tsDto, csDto, cssDto);
        }).collect(Collectors.toList());

        return Response.success("Lấy danh sách lớp đăng ký thành công", dtoList);
    }





}
