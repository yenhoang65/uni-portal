package io.spring.uni_portal.service.StudentService;

import io.spring.uni_portal.dto.Student.StudentRequestDTO;
import io.spring.uni_portal.dto.Student.StudentResponseDTO;
import io.spring.uni_portal.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;



import java.io.ByteArrayInputStream;
import java.util.List;

public interface IStudentService {
    Student createStudent(StudentRequestDTO dto);
    Page<StudentResponseDTO> getAllStudents(Pageable pageable);
    StudentResponseDTO getStudentById(Long id);
    Student updateStudent(Long id, StudentRequestDTO dto);
    void deleteStudent(Long id);
    List<StudentResponseDTO> importFromExcel(MultipartFile file) throws Exception;
    List<StudentResponseDTO> searchByName(String name);
    List<StudentResponseDTO> filterByClass(Long classId);
    ByteArrayInputStream exportToExcelByClass(Long classId);
    Page<StudentResponseDTO> getStudentsWithSearch(String keyword, Pageable pageable);
}
