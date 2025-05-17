package io.spring.uni_portal.service.ClassStudentService;
import io.spring.uni_portal.dto.ClassStudent.ClassStudentDTO;
import io.spring.uni_portal.dto.ClassStudent.ClassStudentResponseDTO;
import io.spring.uni_portal.dto.ClassStudent.OpenedClassFullDTO;
import io.spring.uni_portal.response.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClassStudentService {
    Response<Page<ClassStudentDTO>> getClassStudentsWithSearch(String searchValue, Pageable pageable);

    List<OpenedClassFullDTO> getOpenedClassesBySubject(Long subjectId);

//    Page<ClassStudentResponseDTO> getRegisteredClasses(Long userId, String status, String searchValue, Pageable pageable);

}
