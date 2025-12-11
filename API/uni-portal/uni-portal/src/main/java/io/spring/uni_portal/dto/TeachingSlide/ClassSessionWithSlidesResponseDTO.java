package io.spring.uni_portal.dto.TeachingSlide;

import java.util.List;

public class ClassSessionWithSlidesResponseDTO {
    private ClassInfoDTO classInfo;
    private List<AttendanceSessionWithSlideResponseDTO> sessions;

    public ClassSessionWithSlidesResponseDTO() {}

    public ClassSessionWithSlidesResponseDTO(ClassInfoDTO classInfo, List<AttendanceSessionWithSlideResponseDTO> sessions) {
        this.classInfo = classInfo;
        this.sessions = sessions;
    }

    public ClassInfoDTO getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(ClassInfoDTO classInfo) {
        this.classInfo = classInfo;
    }

    public List<AttendanceSessionWithSlideResponseDTO> getSessions() {
        return sessions;
    }

    public void setSessions(List<AttendanceSessionWithSlideResponseDTO> sessions) {
        this.sessions = sessions;
    }
}
