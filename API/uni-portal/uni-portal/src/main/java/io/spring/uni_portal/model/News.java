package io.spring.uni_portal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "news")
public class News {

    @Id
    @Column(name = "news_id")
    private Long newId;

    @ManyToOne
    @JoinColumn(name = "cate_news_id")
    private CateNews cateNews;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "new_logo")
    private String newLogo;

    @Column(name = "content_image")
    private String contentImage;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Constructor không tham số
    public News() {
    }

    // Constructor có tham số
    public News(Long newId, CateNews cateNews, String title, String content, String newLogo, String contentImage, LocalDateTime createdAt) {
        this.newId = newId;
        this.cateNews = cateNews;
        this.title = title;
        this.content = content;
        this.newLogo = newLogo;
        this.contentImage = contentImage;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getNewId() {
        return newId;
    }

    public void setNewId(Long newId) {
        this.newId = newId;
    }

    public CateNews getCateNews() {
        return cateNews;
    }

    public void setCateNews(CateNews cateNews) {
        this.cateNews = cateNews;
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
