package io.spring.uni_portal.controller;

import com.amazonaws.util.IOUtils;
import io.spring.uni_portal.dto.Student.StudentRequestDTO;
import io.spring.uni_portal.dto.Student.StudentResponseDTO;
import io.spring.uni_portal.model.Student;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.StudentService.IStudentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private IStudentService studentService;

    @PostMapping
    public Response<StudentResponseDTO> create(@RequestBody StudentRequestDTO dto) {
        Student student = studentService.createStudent(dto);
        return Response.success("Tạo sinh viên thành công", new StudentResponseDTO(student));
    }

    @GetMapping
    public Response<Page<StudentResponseDTO>> getAll(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        return Response.success("Danh sách sinh viên", studentService.getAllStudents(pageable));
    }

    @GetMapping("/{id}")
    public Response<StudentResponseDTO> getById(@PathVariable Long id) {
        return Response.success("Thông tin sinh viên", studentService.getStudentById(id));
    }

    @PutMapping("/{id}")
    public Response<StudentResponseDTO> update(@PathVariable Long id, @RequestBody StudentRequestDTO dto) {
        Student student = studentService.updateStudent(id, dto);
        return Response.success("Cập nhật sinh viên thành công", new StudentResponseDTO(student));
    }

    @DeleteMapping("/{id}")
    public Response<Void> delete(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return Response.success("Xoá sinh viên thành công", null);
    }

    @PostMapping("/import")
    public Response<List<StudentResponseDTO>> importExcel(@RequestParam MultipartFile file) throws Exception {
        return Response.success("Import danh sách sinh viên thành công", studentService.importFromExcel(file));
    }

    @GetMapping("/search")
    public Response<List<StudentResponseDTO>> searchByName(@RequestParam String name) {
        return Response.success("Tìm kiếm sinh viên theo tên thành công", studentService.searchByName(name));
    }

    @GetMapping("/filter")
    public Response<List<StudentResponseDTO>> filterByClass(@RequestParam Long classId) {
        return Response.success("Lọc sinh viên theo lớp thành công", studentService.filterByClass(classId));
    }

    @GetMapping("/export/class/{classId}")
    public ResponseEntity<byte[]> exportToExcelByClass(@PathVariable Long classId) {
        ByteArrayInputStream in = studentService.exportToExcelByClass(classId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=students.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(in.readAllBytes());
    }

    @GetMapping("/paging")
    public Response<Page<StudentResponseDTO>> getStudentsPaging(
            @RequestParam int currentPage,      // bắt buộc truyền
            @RequestParam int perPage,          // bắt buộc truyền
            @RequestParam String searchValue    // FE vẫn gửi xuống, có thể là rỗng ""
    ) {
        Pageable pageable = PageRequest.of(currentPage, perPage, Sort.by("user.userName").ascending());

        Page<StudentResponseDTO> result = studentService.getStudentsWithSearch(searchValue, pageable);

        return Response.success("Danh sách sinh viên phân trang", result);
    }

}

