package io.spring.uni_portal.service.GradeTypeService;

import io.spring.uni_portal.dto.GradeType.GradeTypeRequestDTO;
import io.spring.uni_portal.dto.GradeType.GradeTypeResponseDTO;
import io.spring.uni_portal.response.Response;

import java.util.List;

public interface IGradeTypeService {
    Response<GradeTypeResponseDTO> create(GradeTypeRequestDTO dto);
    Response<GradeTypeResponseDTO> update(Long id, GradeTypeRequestDTO dto);
    Response<String> delete(Long id);
    Response<GradeTypeResponseDTO> getById(Long id);
    Response<List<GradeTypeResponseDTO>> getAll();
}
