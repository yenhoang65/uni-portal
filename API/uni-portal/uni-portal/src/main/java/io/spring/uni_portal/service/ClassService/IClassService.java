package io.spring.uni_portal.service.ClassService;

import io.spring.uni_portal.dto.Class.ClassRequestDTO;
import io.spring.uni_portal.dto.Class.ClassResponseDTO;

import java.util.List;

public interface IClassService {
    ClassResponseDTO create(ClassRequestDTO dto);
    List<ClassResponseDTO> getAll();
    ClassResponseDTO getById(Long id);
    ClassResponseDTO update(Long id, ClassRequestDTO dto);
    void delete(Long id);
}
