package io.spring.uni_portal.service.ClassRegistrationService;

import io.spring.uni_portal.dto.ClassSubjectStudent.ClassSubjectStudentDTO;
import io.spring.uni_portal.dto.ClassSubjectStudent.RegisterClassRequestDTO;
import io.spring.uni_portal.dto.ClassSubjectStudent.RegisteredClassDTO;
import io.spring.uni_portal.dto.ClassSubjectStudent.UnregisterClassRequestDTO;
import io.spring.uni_portal.model.ClassSubjectStudent;

import java.util.List;

public interface IClassRegistrationService {
    public ClassSubjectStudentDTO registerStudentToClass(RegisterClassRequestDTO dto);
//    void markAttendance(Long sessionId, Long classStudentId, String status, String note);

    List<RegisteredClassDTO> getRegisteredClasses(Long userId);

    void unregisterStudentFromClass(UnregisterClassRequestDTO dto);

    void finalizeRegistrationPeriod(Long periodId);


}
