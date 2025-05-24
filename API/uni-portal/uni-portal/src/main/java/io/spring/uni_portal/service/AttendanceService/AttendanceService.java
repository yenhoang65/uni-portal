package io.spring.uni_portal.service.AttendanceService;

import io.spring.uni_portal.dto.Attendance.*;
import io.spring.uni_portal.model.*;
import io.spring.uni_portal.repository.AttendanceRepository;
import io.spring.uni_portal.repository.AttendanceSessionRepository;
import io.spring.uni_portal.repository.ClassStudentRepository;
import io.spring.uni_portal.repository.ClassSubjectStudentRepository;
import io.spring.uni_portal.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceService implements IAttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepo;

    @Autowired
    private AttendanceSessionRepository sessionRepo;

    @Autowired
    private ClassSubjectStudentRepository classSubjectStudentRepo;

    @Autowired
    private ClassStudentRepository classStudentRepo;

    @Override
    public Response<String> markAttendance(AttendanceRequestDTO dto) {
        AttendanceSession session = sessionRepo.findById(dto.getSessionId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy buổi học"));

        ClassSubjectStudent css = classSubjectStudentRepo.findById(dto.getClassSubjectStudentId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên đăng ký lớp học"));

//        LocalDate today = LocalDate.now();
//        LocalDate sessionDate = session.getScheduledDate().toLocalDate();
//
//        if (!today.equals(sessionDate)) {
//            return Response.failure("Chỉ được điểm danh đúng ngày học hôm nay");
//        }

        boolean exists = attendanceRepo
                .existsByAttendanceSession_SessionIdAndClassSubjectStudent_ClassSubjectStudentId(
                        dto.getSessionId(), dto.getClassSubjectStudentId());

        if (exists) {
            return Response.failure("Đã điểm danh rồi");
        }

        Attendance attendance = new Attendance();
        attendance.setAttendanceSession(session);
        attendance.setClassSubjectStudent(css);
        attendance.setStatus(dto.getStatus());
        attendance.setNote(dto.getNote());

        attendanceRepo.save(attendance);

        return Response.success("Điểm danh thành công", null);
    }

//    thừa
    @Override
    public Response<List<AttendanceViewDTO>> getAttendanceByClassSubjectStudent(Long classSubjectStudentId) {
        List<Attendance> records = attendanceRepo.findByClassSubjectStudent_ClassSubjectStudentId(classSubjectStudentId);
        List<AttendanceViewDTO> dtos = records.stream().map(att -> {
            AttendanceViewDTO dto = new AttendanceViewDTO();
            dto.setAttendanceId(att.getAttendanceId());
            dto.setScheduledDate(att.getAttendanceSession().getScheduledDate());
            dto.setStatus(att.getStatus());
            dto.setNote(att.getNote());
            return dto;
        }).collect(Collectors.toList());
        return Response.success("Danh sách điểm danh", dtos);
    }



    @Override
    public Response<List<SessionViewDTO>> getSessionsByClassStudent(Long classStudentId) {
        List<AttendanceSession> sessions = sessionRepo.findByClassStudent_ClassStudentId(classStudentId);
        List<SessionViewDTO> dtos = sessions.stream().map(s -> {
            SessionViewDTO dto = new SessionViewDTO();
            dto.setSessionId(s.getSessionId());
            dto.setScheduledDate(s.getScheduledDate());
            dto.setStatus(s.getStatus());
            dto.setClassStudentId(s.getClassStudent().getClassStudentId());
            return dto;
        }).collect(Collectors.toList());
        return Response.success("Danh sách buổi học của lớp", dtos);
    }

    @Override
    public Response<List<StudentInClassDTO>> getStudentsInClass(Long classStudentId) {
        List<ClassSubjectStudent> records = classSubjectStudentRepo.findByClassStudent_ClassStudentId(classStudentId);
        List<StudentInClassDTO> dtos = records.stream().map(item -> {
            StudentInClassDTO dto = new StudentInClassDTO();
            dto.setClassSubjectStudentId(item.getClassSubjectStudentId());
            dto.setUserId(item.getStudent().getUserId());
            dto.setStatus(item.getStatus());
            dto.setFullName(item.getStudent().getUser().getUserName());
            return dto;
        }).collect(Collectors.toList());

        return Response.success("Danh sách sinh viên trong lớp", dtos);
    }

    @Override
    public Response<List<SuccessfulClassDTO>> getSuccessfulClasses() {
        List<ClassStudent> classes = classStudentRepo.findByTeachingScheduleRequest_Status("success");

        List<SuccessfulClassDTO> dtos = classes.stream()
                .map(SuccessfulClassDTO::new)
                .collect(Collectors.toList());

        return Response.success("Danh sách lớp đã mở thành công", dtos);
    }

    @Override
    public Response<List<SuccessfulClassDTO>> getSuccessfulClassesByCurrentStudent() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            throw new RuntimeException("Người dùng chưa xác thực hoặc token không hợp lệ.");
        }

        User currentUser = (User) authentication.getPrincipal();
        Long userId = currentUser.getUserId();

        List<ClassSubjectStudent> classSubjectStudents = classSubjectStudentRepo
                .findByStudent_UserId(userId);

        List<SuccessfulClassDTO> dtos = classSubjectStudents.stream()
                .filter(css -> "success".equalsIgnoreCase(css.getClassStudent().getStatus()))
                .map(css -> new SuccessfulClassDTO(css.getClassStudent()))
                .collect(Collectors.toList());

        return Response.success("Lớp học thành công của sinh viên", dtos);
    }

}
