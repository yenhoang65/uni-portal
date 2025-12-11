package io.spring.uni_portal.controller;

import io.spring.uni_portal.dto.ClassStudent.ClassStudentRequestDTO;
import io.spring.uni_portal.dto.ClassStudent.ClassStudentResponseDTO;
import io.spring.uni_portal.dto.ClassSubjectStudent.ClassSubjectStudentRequestDTO;
import io.spring.uni_portal.model.User;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.ClassSubjectStudentService.IClassSubjectStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/class-subject-student")
public class ClassSubjectStudentController {
    @Autowired
    private IClassSubjectStudentService classSubjectStudentService;

    @GetMapping("/student/successful")
    public ResponseEntity<List<ClassSubjectStudentRequestDTO>> getAllSuccessfulRegisteredClasses(
            @RequestParam(required = false) String semester,
            @RequestParam(required = false) String schoolYear
    ) {
        List<ClassSubjectStudentRequestDTO> result = classSubjectStudentService.getSuccessfulRegisteredClasses(semester, schoolYear);
        return ResponseEntity.ok(result);
    }


}
