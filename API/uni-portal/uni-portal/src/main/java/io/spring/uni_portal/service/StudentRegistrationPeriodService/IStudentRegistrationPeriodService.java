package io.spring.uni_portal.service.StudentRegistrationPeriodService;

import io.spring.uni_portal.dto.StudentRegistrationPeriod.StudentRegistrationPeriodDTO;
import io.spring.uni_portal.model.StudentRegistrationPeriod;

import java.util.List;

public interface IStudentRegistrationPeriodService {
    List<StudentRegistrationPeriod> getAll();
    StudentRegistrationPeriod create(StudentRegistrationPeriodDTO dto);
    StudentRegistrationPeriod update(Long id, StudentRegistrationPeriodDTO dto);
    void delete(Long id);
}
