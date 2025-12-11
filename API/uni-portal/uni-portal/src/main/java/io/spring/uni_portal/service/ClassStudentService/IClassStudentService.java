package io.spring.uni_portal.service.ClassStudentService;
import io.spring.uni_portal.dto.ClassStudent.*;
import io.spring.uni_portal.dto.ClassSubjectStudent.ClassStudentSubjectClassDTO;
import io.spring.uni_portal.dto.TeachingScheduleRequest.TeachingScheduleDetailResponseDTO;
import io.spring.uni_portal.model.ClassStudent;
import io.spring.uni_portal.response.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClassStudentService {
    Response<Page<ClassStudentDTO>> getClassStudentsWithSearch(String searchValue, Pageable pageable);

//    List<OpenedClassFullDTO> getOpenedClassesBySubject(Long subjectId);
List<OpenedClassFullDTO> getOpenedClassesBySubject(Long subjectId, String semester, String schoolyear);

//    Page<ClassStudentResponseDTO> getRegisteredClasses(Long userId, String status, String searchValue, Pageable pageable);
Response<List<ClassStudentResponse>> getStudentsByClass(Long classStudentId);

//    List<ClassStudentRequestDTO> getAllSuccessfulClasses();
    List<ClassStudentRequestDTO> getAllSuccessfulClasses(String semester, String schoolyear);


    List<TeachingScheduleDetailResponseDTO> getSuccessClassesByLecturer(Long lecturerUserId);

    Response<List<SuccessfulClassWithSubjectDTO>> getAllSuccessClassWithSubject();

    // Thêm tham số semester và schoolYear
    Response<List<OpenedClassResponseDTO>> getOpenedClassForCurrentStudent(String semester, String schoolYear);



}
