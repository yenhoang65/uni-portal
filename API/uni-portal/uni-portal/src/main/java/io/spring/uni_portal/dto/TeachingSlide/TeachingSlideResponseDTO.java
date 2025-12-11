package io.spring.uni_portal.dto.TeachingSlide;

public class TeachingSlideResponseDTO {
    private Long id;
    private String title;
    private String fileUrl;
    private Long sessionId;

    public TeachingSlideResponseDTO() {
    }

    public TeachingSlideResponseDTO(Long id, String title, String fileUrl, Long sessionId) {
        this.id = id;
        this.title = title;
        this.fileUrl = fileUrl;
        this.sessionId = sessionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }
}
