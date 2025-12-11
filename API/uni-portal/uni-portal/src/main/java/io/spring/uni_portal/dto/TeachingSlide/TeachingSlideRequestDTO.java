package io.spring.uni_portal.dto.TeachingSlide;

public class TeachingSlideRequestDTO {
    private String title;
    private String fileUrl;
    private Long sessionId;

    public TeachingSlideRequestDTO() {
    }

    public TeachingSlideRequestDTO(String title, String fileUrl, Long sessionId) {
        this.title = title;
        this.fileUrl = fileUrl;
        this.sessionId = sessionId;
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