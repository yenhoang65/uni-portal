package io.spring.uni_portal.dto.News;

import java.time.LocalDateTime;

import java.time.LocalDateTime;

public class NewsResponseDTO {
    private Long newsId;
    private Long cateNewsId;
    private String title;
    private String content;
    private String newLogo;
    private String contentImage;
    private LocalDateTime createdAt;

    // Constructor không tham số
    public NewsResponseDTO() {
    }

    // Constructor đầy đủ tham số
    public NewsResponseDTO(Long newsId, Long cateNewsId, String title, String content, String newLogo, String contentImage, LocalDateTime createdAt) {
        this.newsId = newsId;
        this.cateNewsId = cateNewsId;
        this.title = title;
        this.content = content;
        this.newLogo = newLogo;
        this.contentImage = contentImage;
        this.createdAt = createdAt;
    }

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    public Long getCateNewsId() {
        return cateNewsId;
    }

    public void setCateNewsId(Long cateNewsId) {
        this.cateNewsId = cateNewsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNewLogo() {
        return newLogo;
    }

    public void setNewLogo(String newLogo) {
        this.newLogo = newLogo;
    }

    public String getContentImage() {
        return contentImage;
    }

    public void setContentImage(String contentImage) {
        this.contentImage = contentImage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}