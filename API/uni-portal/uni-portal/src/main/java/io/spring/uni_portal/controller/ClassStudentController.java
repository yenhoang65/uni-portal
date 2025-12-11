package io.spring.uni_portal.controller;

import io.spring.uni_portal.dto.ClassStudent.*;
import io.spring.uni_portal.dto.ClassSubjectStudent.ClassStudentSubjectClassDTO;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.ClassStudentService.ClassStudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/class-student")
public class ClassStudentController {

    @Autowired
    private ClassStudentServiceImpl classStudentService;

    @GetMapping("/paging")
    public ResponseEntity<Response<Page<ClassStudentDTO>>> getClassStudentsPaging(
            @RequestParam int currentPage,
            @RequestParam int perPage,
            @RequestParam String searchValue) {
        // Tạo Pageable từ các tham số truyền vào
        Pageable pageable = PageRequest.of(currentPage, perPage, Sort.by("status").ascending());

        Response<Page<ClassStudentDTO>> response = classStudentService.getClassStudentsWithSearch(searchValue, pageable);

        Page<ClassStudentDTO> result = response.getData();

        return ResponseEntity.ok(Response.success("Danh sách ClassStudent phân trang", result));
    }

//    @GetMapping("/opened-classes/{subjectId}")
//    public ResponseEntity<Response<List<OpenedClassFullDTO>>> getOpenedClassesBySubject(@PathVariable Long subjectId) {
//        List<OpenedClassFullDTO> openedClasses = classStudentService.getOpenedClassesBySubject(subjectId);
//        return ResponseEntity.ok(Response.success("Danh sách lớp học đang mở của môn học", openedClasses));
//    }

    @GetMapping("/opened-classes/{subjectId}")
    public ResponseEntity<Response<List<OpenedClassFullDTO>>> getOpenedClassesBySubject(
            @PathVariable Long subjectId,
            @RequestParam(required = false) String semester,
            @RequestParam(required = false) String schoolyear
    ) {
        List<OpenedClassFullDTO> openedClasses = classStudentService.getOpenedClassesBySubject(subjectId, semester, schoolyear);
        return ResponseEntity.ok(Response.success("Danh sách lớp học đang mở của môn học", openedClasses));
    }



    @GetMapping("/students/{classStudentId}")
    public ResponseEntity<Response<List<ClassStudentResponse>>> getStudentsByClass(
            @PathVariable Long classStudentId) {
        Response<List<ClassStudentResponse>> response = classStudentService.getStudentsByClass(classStudentId);
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/successful")
//    public ResponseEntity<List<ClassStudentRequestDTO>> getAllSuccessfulClasses() {
//        List<ClassStudentRequestDTO> classes = classStudentService.getAllSuccessfulClasses();
//        return ResponseEntity.ok(classes);
//    }

    @GetMapping("/successful")
    public ResponseEntity<List<ClassStudentRequestDTO>> getAllSuccessfulClasses(
            @RequestParam(required = false) String semester,
            @RequestParam(required = false) String schoolyear
    ) {
        List<ClassStudentRequestDTO> classes = classStudentService.getAllSuccessfulClasses(semester, schoolyear);
        return ResponseEntity.ok(classes);
    }


    @GetMapping("/success-classes-subject")
    public ResponseEntity<Response<List<SuccessfulClassWithSubjectDTO>>> getAllSuccessClassesWithSubject() {
        Response<List<SuccessfulClassWithSubjectDTO>> response = classStudentService.getAllSuccessClassWithSubject();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/opened-classes")
    public ResponseEntity<Response<List<OpenedClassResponseDTO>>> getOpenedClassesForCurrentStudent(
            @RequestParam(required = false) String semester,
            @RequestParam(required = false) String schoolYear
    ) {
        Response<List<OpenedClassResponseDTO>> response =
                classStudentService.getOpenedClassForCurrentStudent(semester, schoolYear);
        return ResponseEntity.ok(response);
    }




}
