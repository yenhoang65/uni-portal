package io.spring.uni_portal.dto.Registration;

import io.spring.uni_portal.dto.ClassStudent.ClassStudentDTO;
import io.spring.uni_portal.dto.ClassSubjectStudent.ClassSubjectStudentDTO;
import io.spring.uni_portal.dto.TeachingScheduleRequest.TeachingScheduleRequestClassDTO;

public class RegistrationFullDTO {
    private TeachingScheduleRequestClassDTO teachingScheduleRequest;
    private ClassStudentDTO classStudent;
    private ClassSubjectStudentDTO classSubjectStudent;

    public RegistrationFullDTO() {}

    public RegistrationFullDTO(TeachingScheduleRequestClassDTO teachingScheduleRequest,
                               ClassStudentDTO classStudent,
                               ClassSubjectStudentDTO classSubjectStudent) {
        this.teachingScheduleRequest = teachingScheduleRequest;
        this.classStudent = classStudent;
        this.classSubjectStudent = classSubjectStudent;
    }

    public TeachingScheduleRequestClassDTO getTeachingScheduleRequest() {
        return teachingScheduleRequest;
    }

    public void setTeachingScheduleRequest(TeachingScheduleRequestClassDTO teachingScheduleRequest) {
        this.teachingScheduleRequest = teachingScheduleRequest;
    }

    public ClassStudentDTO getClassStudent() {
        return classStudent;
    }

    public void setClassStudent(ClassStudentDTO classStudent) {
        this.classStudent = classStudent;
    }

    public ClassSubjectStudentDTO getClassSubjectStudent() {
        return classSubjectStudent;
    }

    public void setClassSubjectStudent(ClassSubjectStudentDTO classSubjectStudent) {
        this.classSubjectStudent = classSubjectStudent;
    }
}