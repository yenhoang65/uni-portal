package io.spring.uni_portal.dto.ClassStudent;

import io.spring.uni_portal.dto.Subject.SubjectDTO;
import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentDTO;
import io.spring.uni_portal.dto.TeachingScheduleRequest.TeachingScheduleRequestDTO;
import io.spring.uni_portal.dto.TermClass.TermClassDTO;
import io.spring.uni_portal.model.*;

import java.time.LocalDateTime;
import java.util.List;

public class OpenedClassFullDTO {
    private Long classStudentId;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime endDate;
    private String materials;
    private TeachingScheduleRequestDTO teachingScheduleRequest;
    private TeachingAssignmentDTO teachingAssignment;
    private TermClassDTO termClass;
    private SubjectDTO subject;

    public OpenedClassFullDTO(ClassStudent cs) {
        this.classStudentId = cs.getClassStudentId();
        this.status = cs.getStatus();
        this.createdAt = cs.getCreatedAt();
        this.endDate = cs.getEndDate();
        this.materials = cs.getMaterials();

        TeachingScheduleRequest tsr = cs.getTeachingScheduleRequest();
        TeachingAssignment assignment = tsr.getAssignment();
        Subject subj = assignment.getSubject();
        TermClass tc = assignment.getTermClass();

        // Teaching Schedule DTO
        this.teachingScheduleRequest = new TeachingScheduleRequestDTO();
        this.teachingScheduleRequest.setAssignmentId(assignment.getAssignmentId());
        this.teachingScheduleRequest.setStatus(tsr.getStatus());

        if (tsr.getMaterials() != null) {
            List<TeachingScheduleRequestDTO.MaterialDTO> materialDTOs = tsr.getMaterials().stream().map(material -> {
                TeachingScheduleRequestDTO.MaterialDTO dto = new TeachingScheduleRequestDTO.MaterialDTO();
                dto.setLt(material.getLt());
                dto.setTh(material.getTh());
                return dto;
            }).toList();
            this.teachingScheduleRequest.setMaterials(materialDTOs);
        }

        if (tsr.getScheduleDetails() != null) {
            List<TeachingScheduleRequestDTO.ScheduleDetailDTO> detailDTOs = tsr.getScheduleDetails().stream().map(detail -> {
                TeachingScheduleRequestDTO.ScheduleDetailDTO dto = new TeachingScheduleRequestDTO.ScheduleDetailDTO();
                dto.setClassroomId(detail.getClassroomId());
                dto.setLesson(detail.getLesson());
                dto.setDateTime(detail.getDateTime());
                dto.setEndDate(detail.getEndDate());
                dto.setClassType(detail.getClassType());
                return dto;
            }).toList();
            this.teachingScheduleRequest.setScheduleDetails(detailDTOs);
        }

        // Teaching Assignment DTO
        this.teachingAssignment = new TeachingAssignmentDTO();
        this.teachingAssignment.setLecturerId(assignment.getLecturer().getUserId());
        this.teachingAssignment.setSubjectId(subj.getSubjectId());
        this.teachingAssignment.setTermClassId(tc.getTermclassId());

        // Subject DTO
        this.subject = new SubjectDTO();
        this.subject.setSubjectId(subj.getSubjectId());
        this.subject.setSubjectName(subj.getSubjectName());
        this.subject.setLtCredits(subj.getLtCredits());
        this.subject.setThCredits(subj.getThCredits());
        this.subject.setSubjectDescription(subj.getSubjectDescription());
        this.subject.setSubjectCoefficient(subj.getSubjectCoefficient());

        // Term Class DTO
        this.termClass = new TermClassDTO();
        this.termClass.setClassname(tc.getClassname());
        this.termClass.setProgress(tc.getProgress());
        this.termClass.setSemester(tc.getSemester());
        this.termClass.setSchoolyears(tc.getSchoolyears());
    }

    // Getters & Setters
    public Long getClassStudentId() {
        return classStudentId;
    }

    public void setClassStudentId(Long classStudentId) {
        this.classStudentId = classStudentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public TeachingScheduleRequestDTO getTeachingScheduleRequest() {
        return teachingScheduleRequest;
    }

    public void setTeachingScheduleRequest(TeachingScheduleRequestDTO teachingScheduleRequest) {
        this.teachingScheduleRequest = teachingScheduleRequest;
    }

    public TeachingAssignmentDTO getTeachingAssignment() {
        return teachingAssignment;
    }

    public void setTeachingAssignment(TeachingAssignmentDTO teachingAssignment) {
        this.teachingAssignment = teachingAssignment;
    }

    public TermClassDTO getTermClass() {
        return termClass;
    }

    public void setTermClass(TermClassDTO termClass) {
        this.termClass = termClass;
    }

    public SubjectDTO getSubject() {
        return subject;
    }

    public void setSubject(SubjectDTO subject) {
        this.subject = subject;
    }
}




