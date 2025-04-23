package io.spring.uni_portal.service.SpecializationService;

import io.spring.uni_portal.dto.Specialization.SpecializationDTO;
import io.spring.uni_portal.dto.Specialization.SpecializationResponse;

import java.util.List;

public interface ISpecializationService {
    SpecializationResponse create(SpecializationDTO dto);
    SpecializationResponse getById(Long id);
    List<SpecializationResponse> getAll();
    SpecializationResponse update(Long id, SpecializationDTO dto);
    void delete(Long id);
    List<SpecializationResponse> searchByName(String name);
}
