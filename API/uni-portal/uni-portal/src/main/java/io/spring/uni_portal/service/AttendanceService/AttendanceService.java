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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public Response<String> updateAttendance(Long attendanceId, AttendanceRequestDTO dto) {
        // Tìm bản ghi điểm danh theo attendanceId
        Attendance attendance = attendanceRepo.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bản ghi điểm danh"));

        // Kiểm tra xem sessionId và classSubjectStudentId có hợp lệ không
        AttendanceSession session = sessionRepo.findById(dto.getSessionId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy buổi học"));

        ClassSubjectStudent css = classSubjectStudentRepo.findById(dto.getClassSubjectStudentId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên đăng ký lớp học"));

        // Kiểm tra xem bản ghi điểm danh có khớp với sessionId và classSubjectStudentId không
        if (!attendance.getAttendanceSession().getSessionId().equals(dto.getSessionId()) ||
                !attendance.getClassSubjectStudent().getClassSubjectStudentId().equals(dto.getClassSubjectStudentId())) {
            return Response.failure("Thông tin buổi học hoặc sinh viên không khớp với bản ghi điểm danh");
        }

        // Cập nhật trạng thái và ghi chú
        attendance.setStatus(dto.getStatus());
        attendance.setNote(dto.getNote());

        // Lưu bản ghi đã cập nhật
        attendanceRepo.save(attendance);

        return Response.success("Cập nhật điểm danh thành công", null);
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


    @Override
    public Response<List<AttendanceResponseDTO>> getAttendanceBySession(Long sessionId) {
        // Tìm lịch học theo sessionId
        AttendanceSession session = sessionRepo.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy buổi học"));

        // Lấy classStudentId từ lịch học
        Long classStudentId = session.getClassStudent().getClassStudentId();

        // Lấy danh sách sinh viên trong lớp
        List<ClassSubjectStudent> students = classSubjectStudentRepo.findByClassStudent_ClassStudentId(classStudentId);
        if (students.isEmpty()) {
            return Response.failure("Không có sinh viên nào trong lớp");
        }

        // Lấy tất cả bản ghi điểm danh cho sessionId
        List<Attendance> attendances = attendanceRepo.findByAttendanceSession_SessionId(sessionId);

        // Map classSubjectStudentId => Attendance
        Map<Long, Attendance> attendanceMap = attendances.stream()
                .collect(Collectors.toMap(
                        att -> att.getClassSubjectStudent().getClassSubjectStudentId(),
                        att -> att
                ));

        // Tạo danh sách DTO
        List<AttendanceResponseDTO> result = new ArrayList<>();
        for (ClassSubjectStudent student : students) {
            Student std = student.getStudent();
            if (std != null && std.getUser() != null) {
                Long userId = std.getUserId();
                String studentName = std.getUser().getUserName();

                Attendance attendance = attendanceMap.get(student.getClassSubjectStudentId());
                Long attendanceId = attendance != null ? attendance.getAttendanceId() : null;
                String status = attendance != null ? attendance.getStatus() : "Vắng";

                result.add(new AttendanceResponseDTO(attendanceId, userId, studentName, status));
            }
        }

        return Response.success("Lấy thông tin điểm danh thành công", result);
    }

    @Override
    public Response<List<AttendanceViewDTO>> getAttendanceByClassStudent(Long classStudentId) {
        // Lấy studentId từ token
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            return Response.failure("Người dùng chưa xác thực hoặc token không hợp lệ.");
        }

        User currentUser = (User) authentication.getPrincipal();
        Long studentId = currentUser.getUserId();

        // Kiểm tra xem sinh viên có thuộc lớp này không
        ClassSubjectStudent css = classSubjectStudentRepo.findByClassStudent_ClassStudentIdAndStudent_UserId(
                        classStudentId, studentId)
                .orElseThrow(() -> new RuntimeException("Sinh viên không thuộc lớp này"));

        // Lấy tất cả các buổi học của lớp (không lọc theo status)
        List<AttendanceSession> sessions = sessionRepo.findByClassStudent_ClassStudentId(classStudentId);

        if (sessions.isEmpty()) {
            return Response.failure("Không có buổi học nào cho lớp này.");
        }

        // Lấy tất cả bản ghi điểm danh của sinh viên cho các buổi học này
        List<Attendance> attendances = attendanceRepo.findByClassSubjectStudent_ClassSubjectStudentIdAndAttendanceSession_In(
                css.getClassSubjectStudentId(), sessions);

        // Map các bản ghi điểm danh theo sessionId
        Map<Long, Attendance> attendanceMap = attendances.stream()
                .collect(Collectors.toMap(
                        att -> att.getAttendanceSession().getSessionId(),
                        att -> att
                ));

        // Chuyển đổi tất cả buổi học thành DTO, bao gồm cả các buổi chưa có điểm danh
        List<AttendanceViewDTO> dtos = sessions.stream().map(session -> {
            AttendanceViewDTO dto = new AttendanceViewDTO();
            dto.setClassStudentId(classStudentId);
            dto.setUserId(studentId);
            dto.setScheduledDate(session.getScheduledDate());

            // Kiểm tra xem có bản ghi điểm danh cho buổi học này không
            Attendance attendance = attendanceMap.get(session.getSessionId());
            if (attendance != null) {
                dto.setAttendanceId(attendance.getAttendanceId());
                dto.setStatus(attendance.getStatus());
                dto.setNote(attendance.getNote());
            } else {
                dto.setStatus("Chưa điểm danh");
                dto.setNote("");
            }

            return dto;
        }).collect(Collectors.toList());

        return Response.success("Danh sách lịch học và điểm danh của sinh viên", dtos);
    }
}
