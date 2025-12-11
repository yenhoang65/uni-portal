package io.spring.uni_portal.service.NewsService;

import io.spring.uni_portal.config.AwsS3Config;
import io.spring.uni_portal.dto.CateNews.CateNewsResponseDTO;
import io.spring.uni_portal.dto.News.NewsDetailWithRelatedDTO;
import io.spring.uni_portal.dto.News.NewsRequestDTO;
import io.spring.uni_portal.dto.News.NewsResponseDTO;
import io.spring.uni_portal.model.CateNews;
import io.spring.uni_portal.model.News;
import io.spring.uni_portal.repository.CateNewsRepository;
import io.spring.uni_portal.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements INewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private CateNewsRepository cateNewsRepository;

    @Autowired
    private AwsS3Config awsS3Config;

    @Override
    public NewsResponseDTO create(NewsRequestDTO dto) {
        News news = new News();
        news.setNewId(generateNextId());
        news.setTitle(dto.getTitle());
        news.setContent(dto.getContent());
        news.setCreatedAt(LocalDateTime.now());

        CateNews cateNews = cateNewsRepository.findById(dto.getCateNewsId())
                .orElseThrow(() -> new RuntimeException("Danh mục không tồn tại"));
        news.setCateNews(cateNews);

        if (dto.getNewLogoFile() != null && !dto.getNewLogoFile().isEmpty()) {
            String logoUrl = awsS3Config.saveImageToS3(dto.getNewLogoFile());
            news.setNewLogo(logoUrl);
        }

        if (dto.getContentImageFile() != null && !dto.getContentImageFile().isEmpty()) {
            String imageUrl = awsS3Config.saveImageToS3(dto.getContentImageFile());
            news.setContentImage(imageUrl);
        }

        return convertToResponse(newsRepository.save(news));
    }

    @Override
    public NewsResponseDTO update(Long id, NewsRequestDTO dto) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bản ghi"));

        news.setTitle(dto.getTitle());
        news.setContent(dto.getContent());

        if (dto.getNewLogoFile() != null && !dto.getNewLogoFile().isEmpty()) {
            String logoUrl = awsS3Config.saveImageToS3(dto.getNewLogoFile());
            news.setNewLogo(logoUrl);
        }

        if (dto.getContentImageFile() != null && !dto.getContentImageFile().isEmpty()) {
            String imageUrl = awsS3Config.saveImageToS3(dto.getContentImageFile());
            news.setContentImage(imageUrl);
        }

        return convertToResponse(newsRepository.save(news));
    }

    @Override
    public void delete(Long id) {
        newsRepository.deleteById(id);
    }

    @Override
    public NewsResponseDTO getById(Long id) {
        return convertToResponse(newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bản ghi")));
    }

    @Override
    public List<NewsResponseDTO> getAll() {
        return newsRepository.findAll().stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    private NewsResponseDTO convertToResponse(News news) {
        NewsResponseDTO dto = new NewsResponseDTO();
        dto.setNewsId(news.getNewId());
        dto.setTitle(news.getTitle());
        dto.setContent(news.getContent());
        dto.setCateNewsId(news.getCateNews().getCateNewsId());
        dto.setNewLogo(news.getNewLogo());
        dto.setContentImage(news.getContentImage());
        dto.setCreatedAt(news.getCreatedAt());
        return dto;
    }

    private Long generateNextId() {
        return newsRepository.findAll().stream()
                .mapToLong(News::getNewId)
                .max().orElse(0L) + 1;
    }


    @Override
    public Map<String, Object> getNewsByCateId(Long cateNewsId) {
        CateNews cateNews = cateNewsRepository.findById(cateNewsId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyên mục"));

        List<NewsResponseDTO> newsList = newsRepository.findByCateNews_CateNewsId(cateNewsId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("category", new CateNewsResponseDTO(cateNews.getCateNewsId(), cateNews.getTitle()));
        result.put("newsList", newsList);
        return result;
    }

    @Override
    public NewsDetailWithRelatedDTO getNewsDetailAndRelated(Long newsId) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tin tức"));

        NewsResponseDTO detail = convertToDTO(news);

        List<NewsResponseDTO> relatedNews = newsRepository.findByCateNews_CateNewsId(news.getCateNews().getCateNewsId())
                .stream()
                .filter(n -> !n.getNewId().equals(newsId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        NewsDetailWithRelatedDTO dto = new NewsDetailWithRelatedDTO();
        dto.setNewsDetail(detail);
        dto.setRelatedNews(relatedNews);

        return dto;
    }

    private NewsResponseDTO convertToDTO(News news) {
        NewsResponseDTO dto = new NewsResponseDTO();
        dto.setNewsId(news.getNewId());
        dto.setCateNewsId(news.getCateNews().getCateNewsId());
        dto.setTitle(news.getTitle());
        dto.setContent(news.getContent());
        dto.setNewLogo(news.getNewLogo());
        dto.setContentImage(news.getContentImage());
        dto.setCreatedAt(news.getCreatedAt());
        return dto;
    }
}