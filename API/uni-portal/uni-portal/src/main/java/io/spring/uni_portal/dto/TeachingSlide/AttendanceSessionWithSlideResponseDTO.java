package io.spring.uni_portal.dto.TeachingSlide;

import java.time.LocalDateTime;

public class AttendanceSessionWithSlideResponseDTO {
    private Long sessionId;
    private LocalDateTime scheduledDate;
    private String slideTitle;
    private String slideUrl;
    private Long teachingSlideId;

    public AttendanceSessionWithSlideResponseDTO() {}

    public AttendanceSessionWithSlideResponseDTO(Long sessionId, LocalDateTime scheduledDate,
                                                 String slideTitle, String slideUrl, Long teachingSlideId) {
        this.sessionId = sessionId;
        this.scheduledDate = scheduledDate;
        this.slideTitle = slideTitle;
        this.slideUrl = slideUrl;
        this.teachingSlideId = teachingSlideId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public LocalDateTime getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDateTime scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public String getSlideTitle() {
        return slideTitle;
    }

    public void setSlideTitle(String slideTitle) {
        this.slideTitle = slideTitle;
    }

    public String getSlideUrl() {
        return slideUrl;
    }

    public void setSlideUrl(String slideUrl) {
        this.slideUrl = slideUrl;
    }

    public Long getTeachingSlideId() {
        return teachingSlideId;
    }

    public void setTeachingSlideId(Long teachingSlideId) {
        this.teachingSlideId = teachingSlideId;
    }
}
