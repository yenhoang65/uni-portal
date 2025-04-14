package io.spring.uni_portal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TeachingAssignmentId implements Serializable {

    @Column(name = "user_id") // userId thay vì lecturerId
    private Long userId;

    @Column(name = "subject_id")
    private Long subjectId;

    public TeachingAssignmentId() {}

    public TeachingAssignmentId(Long userId, Long subjectId) {
        this.userId = userId;
        this.subjectId = subjectId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeachingAssignmentId)) return false;
        TeachingAssignmentId that = (TeachingAssignmentId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(subjectId, that.subjectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, subjectId);
    }
}
