package io.spring.uni_portal.dto.News;

import java.util.List;

public class NewsDetailWithRelatedDTO {
    private NewsResponseDTO newsDetail;
    private List<NewsResponseDTO> relatedNews;

    public NewsDetailWithRelatedDTO() {
    }

    public NewsDetailWithRelatedDTO(NewsResponseDTO newsDetail, List<NewsResponseDTO> relatedNews) {
        this.newsDetail = newsDetail;
        this.relatedNews = relatedNews;
    }

    public NewsResponseDTO getNewsDetail() {
        return newsDetail;
    }

    public void setNewsDetail(NewsResponseDTO newsDetail) {
        this.newsDetail = newsDetail;
    }

    public List<NewsResponseDTO> getRelatedNews() {
        return relatedNews;
    }

    public void setRelatedNews(List<NewsResponseDTO> relatedNews) {
        this.relatedNews = relatedNews;
    }
}