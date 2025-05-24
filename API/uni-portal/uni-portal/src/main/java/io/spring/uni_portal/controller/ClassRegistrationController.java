package io.spring.uni_portal.controller;

import io.spring.uni_portal.dto.ClassSubjectStudent.ClassSubjectStudentDTO;
import io.spring.uni_portal.dto.ClassSubjectStudent.RegisterClassRequestDTO;
import io.spring.uni_portal.dto.ClassSubjectStudent.RegisteredClassDTO;
import io.spring.uni_portal.dto.ClassSubjectStudent.UnregisterClassRequestDTO;
import io.spring.uni_portal.model.ClassSubjectStudent;
import io.spring.uni_portal.model.User;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.ClassRegistrationService.IClassRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/class-registration")
public class ClassRegistrationController {

    @Autowired
    private IClassRegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<Response<ClassSubjectStudentDTO>> registerToClass(@RequestBody RegisterClassRequestDTO dto) {
        ClassSubjectStudentDTO result = registrationService.registerStudentToClass(dto);
        return ResponseEntity.ok(Response.success("Đăng ký lớp học thành công", result));
    }

    @PostMapping("/finalize-registration-period/{periodId}")
    public ResponseEntity<String> finalizeRegistrationPeriod(@PathVariable Long periodId) {
        registrationService.finalizeRegistrationPeriod(periodId);
        return ResponseEntity.ok("Đợt đăng ký ID " + periodId + " đã được xử lý thành công.");
    }



    @GetMapping("/student")
    public ResponseEntity<Response<List<RegisteredClassDTO>>> getUserRegisteredClasses(Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getUserId();
        List<RegisteredClassDTO> classes = registrationService.getRegisteredClasses(userId);
        return ResponseEntity.ok(Response.success("Lấy danh sách lớp đã đăng ký thành công", classes));
    }


    @PostMapping("/unregister")
    public ResponseEntity<Response<String>> unregisterFromClass(@RequestBody UnregisterClassRequestDTO dto) {
        registrationService.unregisterStudentFromClass(dto);
        return ResponseEntity.ok(Response.success("Huỷ đăng ký lớp học thành công", null));
    }

//    @PostMapping("/unregister")
//    public ResponseEntity<Response<ClassSubjectStudentDTO>> unregisterFromClass(@RequestBody UnregisterClassRequestDTO dto) {
//        ClassSubjectStudentDTO result = registrationService.unregisterStudentFromClass(dto);
//        return ResponseEntity.ok(Response.success("Huỷ đăng ký lớp học thành công", result));
//    }
}

