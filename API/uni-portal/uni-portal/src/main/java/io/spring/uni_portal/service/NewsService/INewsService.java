package io.spring.uni_portal.service.NewsService;

import io.spring.uni_portal.dto.News.NewsDetailWithRelatedDTO;
import io.spring.uni_portal.dto.News.NewsRequestDTO;
import io.spring.uni_portal.dto.News.NewsResponseDTO;

import java.util.List;
import java.util.Map;

public interface INewsService {
    NewsResponseDTO create(NewsRequestDTO dto);
    NewsResponseDTO update(Long id, NewsRequestDTO dto);
    void delete(Long id);
    NewsResponseDTO getById(Long id);
    List<NewsResponseDTO> getAll();


    Map<String, Object> getNewsByCateId(Long cateNewsId);
    NewsDetailWithRelatedDTO getNewsDetailAndRelated(Long newsId);
}
