package io.spring.uni_portal.service.ClassSubjectStudentService;

import io.spring.uni_portal.dto.ClassStudent.ClassStudentRequestDTO;
import io.spring.uni_portal.dto.ClassStudent.ClassStudentResponseDTO;
import io.spring.uni_portal.dto.ClassSubjectStudent.ClassSubjectStudentRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClassSubjectStudentService {
    List<ClassSubjectStudentRequestDTO> getSuccessfulRegisteredClasses(String semester, String schoolYear);
}

