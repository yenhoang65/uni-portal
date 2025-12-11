package io.spring.uni_portal.service.CateNewsService;

import io.spring.uni_portal.dto.CateNews.CateNewsRequestDTO;
import io.spring.uni_portal.dto.CateNews.CateNewsResponseDTO;

import java.util.List;

public interface ICateNewsService {
    CateNewsResponseDTO create(CateNewsRequestDTO request);
    CateNewsResponseDTO update(Long id, CateNewsRequestDTO request);
    void delete(Long id);
    CateNewsResponseDTO getById(Long id);
    List<CateNewsResponseDTO> getAll();
}
