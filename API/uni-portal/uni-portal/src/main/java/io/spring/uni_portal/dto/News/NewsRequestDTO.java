package io.spring.uni_portal.dto.News;

import org.springframework.web.multipart.MultipartFile;

public class NewsRequestDTO {
    private Long cateNewsId;
    private String title;
    private String content;

    private MultipartFile newLogoFile;
    private MultipartFile contentImageFile;

    // Getter & Setter
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

    public MultipartFile getNewLogoFile() {
        return newLogoFile;
    }

    public void setNewLogoFile(MultipartFile newLogoFile) {
        this.newLogoFile = newLogoFile;
    }

    public MultipartFile getContentImageFile() {
        return contentImageFile;
    }

    public void setContentImageFile(MultipartFile contentImageFile) {
        this.contentImageFile = contentImageFile;
    }
}
