package io.spring.uni_portal.controller;

import io.spring.uni_portal.dto.StudentRegistrationPeriod.StudentRegistrationPeriodDTO;
import io.spring.uni_portal.dto.TeachingRegistrationPeriod.TeachingRegistrationPeriodDTO;
import io.spring.uni_portal.model.StudentRegistrationPeriod;
import io.spring.uni_portal.model.TeachingRegistrationPeriod;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.StudentRegistrationPeriodService.IStudentRegistrationPeriodService;
import io.spring.uni_portal.service.TeachingRegistrationPeriodService.ITeachingRegistrationPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registration-period")
public class RegistrationPeriodController {

    @Autowired
    private ITeachingRegistrationPeriodService teachingService;

    @Autowired
    private IStudentRegistrationPeriodService studentService;

    // === GIẢNG VIÊN ===

    @GetMapping("/teaching")
    public Response<List<TeachingRegistrationPeriod>> getAllTeaching() {
        List<TeachingRegistrationPeriod> list = teachingService.getAll();
        return Response.success("Lấy danh sách đăng ký lịch dạy thành công", list);
    }

    @PostMapping("/teaching")
    public Response<TeachingRegistrationPeriod> createTeaching(@RequestBody TeachingRegistrationPeriodDTO dto) {
        TeachingRegistrationPeriod created = teachingService.create(dto);
        return Response.success("Tạo mới đợt đăng ký lịch dạy thành công", created);
    }

    @PutMapping("/teaching/{id}")
    public Response<TeachingRegistrationPeriod> updateTeaching(@PathVariable Long id, @RequestBody TeachingRegistrationPeriodDTO dto) {
        TeachingRegistrationPeriod updated = teachingService.update(id, dto);
        return Response.success("Cập nhật đợt đăng ký lịch dạy thành công", updated);
    }

    @DeleteMapping("/teaching/{id}")
    public Response<String> deleteTeaching(@PathVariable Long id) {
        teachingService.delete(id);
        return Response.success("Xóa đợt đăng ký lịch dạy thành công", null);
    }

    // === SINH VIÊN ===

    @GetMapping("/student")
    public Response<List<StudentRegistrationPeriod>> getAllStudent() {
        List<StudentRegistrationPeriod> list = studentService.getAll();
        return Response.success("Lấy danh sách đăng ký học phần thành công", list);
    }

    @PostMapping("/student")
    public Response<StudentRegistrationPeriod> createStudent(@RequestBody StudentRegistrationPeriodDTO dto) {
        StudentRegistrationPeriod created = studentService.create(dto);
        return Response.success("Tạo mới đợt đăng ký học phần thành công", created);
    }

    @PutMapping("/student/{id}")
    public Response<StudentRegistrationPeriod> updateStudent(@PathVariable Long id, @RequestBody StudentRegistrationPeriodDTO dto) {
        StudentRegistrationPeriod updated = studentService.update(id, dto);
        return Response.success("Cập nhật đợt đăng ký học phần thành công", updated);
    }

    @DeleteMapping("/student/{id}")
    public Response<String> deleteStudent(@PathVariable Long id) {
        studentService.delete(id);
        return Response.success("Xóa đợt đăng ký học phần thành công", null);
    }
}
