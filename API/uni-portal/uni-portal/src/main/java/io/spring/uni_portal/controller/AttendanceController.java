package io.spring.uni_portal.controller;

import io.spring.uni_portal.dto.Attendance.*;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.AttendanceService.IAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private IAttendanceService attendanceService;

//    điểm danh
    @PostMapping("/mark")
    public Response<String> markAttendance(@RequestBody AttendanceRequestDTO dto) {
        return attendanceService.markAttendance(dto);
    }

    @GetMapping("/by-class-subject-student/{classSubjectStudentId}")
    public Response<List<AttendanceViewDTO>> getByClassSubjectStudent(@PathVariable Long classSubjectStudentId) {
        return attendanceService.getAttendanceByClassSubjectStudent(classSubjectStudentId);
    }

//    get số tất cả các lịch học của mỗi lớp
    @GetMapping("/sessions/by-class-student/{classStudentId}")
    public Response<List<SessionViewDTO>> getSessionsByClassStudent(@PathVariable Long classStudentId) {
        return attendanceService.getSessionsByClassStudent(classStudentId);
    }


//    lấy tât cả các sinh vien trong 1 lớp đã đăng ký
    @GetMapping("/students/by-class-student/{classStudentId}")
    public Response<List<StudentInClassDTO>> getStudentsInClass(@PathVariable Long classStudentId) {
        return attendanceService.getStudentsInClass(classStudentId);
    }

// tất cả các lớp được tạo thành công
        @GetMapping("/classes/success")
    public Response<List<SuccessfulClassDTO>> getSuccessfulClasses() {
        return attendanceService.getSuccessfulClasses();
    }

// lấy ra caác lớp student đăng ký thành công
    @GetMapping("/classes/success/by-student")
    public Response<List<SuccessfulClassDTO>> getSuccessfulClassesByCurrentStudent() {
        return attendanceService.getSuccessfulClassesByCurrentStudent();
    }


}
