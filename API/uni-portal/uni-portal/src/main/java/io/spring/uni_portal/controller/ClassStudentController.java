package io.spring.uni_portal.controller;

import io.spring.uni_portal.dto.ClassStudent.ClassStudentDTO;
import io.spring.uni_portal.dto.ClassStudent.OpenedClassFullDTO;
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

    @GetMapping("/opened-classes/{subjectId}")
    public ResponseEntity<Response<List<OpenedClassFullDTO>>> getOpenedClassesBySubject(@PathVariable Long subjectId) {
        List<OpenedClassFullDTO> openedClasses = classStudentService.getOpenedClassesBySubject(subjectId);
        return ResponseEntity.ok(Response.success("Danh sách lớp học đang mở của môn học", openedClasses));
    }



}
