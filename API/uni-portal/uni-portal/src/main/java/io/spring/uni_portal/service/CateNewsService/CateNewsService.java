package io.spring.uni_portal.service.CateNewsService;


import io.spring.uni_portal.dto.CateNews.CateNewsRequestDTO;
import io.spring.uni_portal.dto.CateNews.CateNewsResponseDTO;
import io.spring.uni_portal.model.CateNews;
import io.spring.uni_portal.repository.CateNewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CateNewsService implements ICateNewsService {

    @Autowired
    private CateNewsRepository cateNewsRepository;

    @Override
    public CateNewsResponseDTO create(CateNewsRequestDTO request) {
        CateNews cateNews = new CateNews();
        cateNews.setTitle(request.getTitle());
        cateNews = cateNewsRepository.save(cateNews);
        return mapToResponse(cateNews);
    }

    @Override
    public CateNewsResponseDTO update(Long id, CateNewsRequestDTO request) {
        Optional<CateNews> optional = cateNewsRepository.findById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("CateNews not found with id: " + id);
        }
        CateNews cateNews = optional.get();
        cateNews.setTitle(request.getTitle());
        cateNews = cateNewsRepository.save(cateNews);
        return mapToResponse(cateNews);
    }

    @Override
    public void delete(Long id) {
        if (!cateNewsRepository.existsById(id)) {
            throw new RuntimeException("CateNews not found with id: " + id);
        }
        cateNewsRepository.deleteById(id);
    }

    @Override
    public CateNewsResponseDTO getById(Long id) {
        CateNews cateNews = cateNewsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CateNews not found with id: " + id));
        return mapToResponse(cateNews);
    }

    @Override
    public List<CateNewsResponseDTO> getAll() {
        return cateNewsRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private CateNewsResponseDTO mapToResponse(CateNews cateNews) {
        return new CateNewsResponseDTO(cateNews.getCateNewsId(), cateNews.getTitle());
    }
}
