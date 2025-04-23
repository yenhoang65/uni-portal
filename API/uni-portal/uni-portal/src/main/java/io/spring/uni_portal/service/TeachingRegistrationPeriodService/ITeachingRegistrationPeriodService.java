package io.spring.uni_portal.service.TeachingRegistrationPeriodService;

import io.spring.uni_portal.dto.TeachingRegistrationPeriod.TeachingRegistrationPeriodDTO;
import io.spring.uni_portal.model.TeachingRegistrationPeriod;

import java.util.List;

public interface ITeachingRegistrationPeriodService {
    List<TeachingRegistrationPeriod> getAll();
    TeachingRegistrationPeriod create(TeachingRegistrationPeriodDTO dto);
    TeachingRegistrationPeriod update(Long id, TeachingRegistrationPeriodDTO dto);
    void delete(Long id);
}
