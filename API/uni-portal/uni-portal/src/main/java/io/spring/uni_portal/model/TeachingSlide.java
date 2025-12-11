package io.spring.uni_portal.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "teaching_slide")
public class TeachingSlide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teaching_slide_id")
    private Long teachingSlideId;

    @Column(name = "title")
    private String title;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "uploaded_by")
    private Long uploadedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id")
    private AttendanceSession session;

    public TeachingSlide() {
    }

    public TeachingSlide(Long teachingSlideId, String fileUrl, String title, Long uploadedBy, AttendanceSession session) {
        this.teachingSlideId = teachingSlideId;
        this.fileUrl = fileUrl;
        this.title = title;
        this.uploadedBy = uploadedBy;
        this.session = session;
    }

    public Long getTeachingSlideId() {
        return teachingSlideId;
    }

    public void setTeachingSlideId(Long teachingSlideId) {
        this.teachingSlideId = teachingSlideId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(Long uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public AttendanceSession getSession() {
        return session;
    }

    public void setSession(AttendanceSession session) {
        this.session = session;
    }
}