package io.spring.uni_portal.service.TranscriptService;

import io.spring.uni_portal.dto.Transcript.TranscriptResponseDTO;
import io.spring.uni_portal.dto.Transcript.TranscriptSubjectDTO;
import io.spring.uni_portal.model.*;
import io.spring.uni_portal.model.Class;
import io.spring.uni_portal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TranscriptServiceImpl implements ITranscriptService {

@Autowired private ClassSubjectStudentRepository classSubjectStudentRepository;
    @Autowired private StudentGradeRepository studentGradeRepository;
    @Autowired private StudentRepository studentRepository;
    @Autowired private IntermediaryRepository intermediaryRepository;
    @Autowired private SubjectRepository subjectRepository;

    @Override
    public TranscriptResponseDTO getStudentTranscript(Long studentId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (!studentOpt.isPresent()) {
            // Xử lý khi không tìm thấy sinh viên
            throw new RuntimeException("Student not found with id " + studentId);
        }
        Student student = studentOpt.get();

        // Lấy class của sinh viên
        Class clazz = student.getClassEntity();
        if (clazz == null) {
            throw new RuntimeException("Student has no class assigned");
        }

        // Lấy chương trình đào tạo của lớp và niên khóa lớp
        TrainingProgram trainingProgram = clazz.getTrainingProgram();
        Long schoolYear = clazz.getSchoolYear();

        if (trainingProgram == null || schoolYear == null) {
            throw new RuntimeException("Class has no training program or school year");
        }

        // Lấy danh sách intermediary (các môn trong chương trình đào tạo) theo chương trình đào tạo và niên khóa lớp
        List<Intermediary> intermediaries = intermediaryRepository
                .findByTrainingProgramAndSchoolYear(trainingProgram, schoolYear.toString());

        // Tính tổng tín chỉ chương trình đào tạo (tổng tín chỉ các môn trong intermediary)
        int totalProgramCredits = 0;
        for (Intermediary inter : intermediaries) {
            Subject subject = inter.getSubject();
            if (subject != null) {
                int credits = (subject.getLtCredits() != null ? subject.getLtCredits() : 0)
                        + (subject.getThCredits() != null ? subject.getThCredits() : 0);
                totalProgramCredits += credits;
            }
        }

        // Lấy danh sách các môn sinh viên đã đăng ký (classSubjectStudent)
        List<ClassSubjectStudent> enrolled = classSubjectStudentRepository.findAllEnrolledSubjects(studentId);

        // Lấy danh sách điểm sinh viên
        List<StudentGrade> grades = studentGradeRepository.findAllByStudentId(studentId);

        List<TranscriptSubjectDTO> subjectDTOs = new ArrayList<>();
        int totalCredits = 0; // tổng tín chỉ đã học (có điểm)

        for (ClassSubjectStudent css : enrolled) {
            Subject subject = css.getClassStudent().getTeachingScheduleRequest().getAssignment().getSubject();

            TranscriptSubjectDTO dto = new TranscriptSubjectDTO();
            dto.setSubjectId(subject.getSubjectId());
            dto.setSubjectName(subject.getSubjectName());
            dto.setLtCredits(subject.getLtCredits());
            dto.setThCredits(subject.getThCredits());
            dto.setSubjectDescription(subject.getSubjectDescription());
            dto.setSubjectCoefficient(subject.getSubjectCoefficient());

            // Tính tổng tín chỉ môn học đã học
            int credits = (subject.getLtCredits() != null ? subject.getLtCredits() : 0)
                    + (subject.getThCredits() != null ? subject.getThCredits() : 0);
            totalCredits += credits;

            // Map loại điểm -> danh sách điểm
            Map<String, List<Double>> scoreMap = new HashMap<>();
            Double finalScore = null;

            for (StudentGrade g : grades) {
                if (g.getClassSubjectStudent() == null || g.getGradeEvent() == null ||
                        g.getGradeEvent().getGradeType() == null || g.getScore() == null) continue;

                if (!g.getClassSubjectStudent()
                        .getClassStudent()
                        .getTeachingScheduleRequest()
                        .getAssignment()
                        .getSubject().getSubjectId().equals(subject.getSubjectId())) continue;

                String type = g.getGradeEvent().getGradeType().getCode();
                scoreMap.putIfAbsent(type, new ArrayList<>());
                scoreMap.get(type).add(g.getScore());

                if ("FINAL".equals(type)) {
                    finalScore = g.getScore();
                }
            }

            Float thScore = averageNonNull(scoreMap.get("TH"));
            Float p15Score = averageNonNull(scoreMap.get("15P"));
            Float midScore = averageNonNull(scoreMap.get("MID"));
            Float finalScoreF = finalScore != null ? finalScore.floatValue() : null;

            float thVal = thScore != null ? thScore : 0f;
            float p15Val = p15Score != null ? p15Score : 0f;
            float midVal = midScore != null ? midScore : 0f;
            float finalVal = finalScoreF != null ? finalScoreF : 0f;

            float avgScore = 0f;
            if (finalScoreF == null) {
                if (thScore != null && p15Score != null) {
                    avgScore = (thVal + p15Val) * 0.25f + midVal * 0.5f;
                } else if (thScore != null) {
                    avgScore = thVal * 0.25f + midVal * 0.75f;
                } else if (p15Score != null) {
                    avgScore = p15Val * 0.25f + midVal * 0.75f;
                } else {
                    avgScore = midVal;
                }
            } else {
                if (thScore != null && p15Score != null) {
                    avgScore = (thVal + p15Val) * 0.25f + midVal * 0.25f + finalVal * 0.5f;
                } else if (thScore != null) {
                    avgScore = thVal * 0.25f + midVal * 0.25f + finalVal * 0.5f;
                } else if (p15Score != null) {
                    avgScore = p15Val * 0.25f + midVal * 0.25f + finalVal * 0.5f;
                } else {
                    avgScore = midVal * 0.5f + finalVal * 0.5f;
                }
            }

            Map<String, Float> componentScores = new HashMap<>();
            if (p15Score != null) componentScores.put("15P", p15Score);
            if (thScore != null) componentScores.put("TH", thScore);
            if (midScore != null) componentScores.put("MID", midScore);

            dto.setComponentScores(componentScores);
            dto.setFinalScore(finalScoreF);
            dto.setAverageScore(avgScore);

            float gpa4 = convertGPA4(avgScore);
            dto.setGpa4(gpa4);
            dto.setLetter(convertLetter(gpa4));

            subjectDTOs.add(dto);
        }

        TranscriptResponseDTO response = new TranscriptResponseDTO();
        response.setTotalCredits(totalProgramCredits);   // Tổng tín chỉ chương trình đào tạo
        response.setAccumulatedCredits(totalCredits);    // Tổng tín chỉ đã học
        response.setGpa10(subjectDTOs.isEmpty() ? 0f : (float) subjectDTOs.stream()
                .mapToDouble(TranscriptSubjectDTO::getAverageScore)
                .average()
                .orElse(0));

        response.setGpa4(subjectDTOs.isEmpty() ? 0f : (float) subjectDTOs.stream()
                .mapToDouble(TranscriptSubjectDTO::getGpa4)
                .average()
                .orElse(0));

        response.setClassification10(classifyGPA10(response.getGpa10()));
        response.setClassification4(classifyGPA4(response.getGpa4()));
        response.setSubjects(subjectDTOs);

        return response;
    }


    private Float averageNonNull(List<Double> list) {
        if (list == null) return null;
        List<Double> filtered = list.stream().filter(Objects::nonNull).collect(Collectors.toList());
        if (filtered.isEmpty()) return null;
        return (float) filtered.stream().mapToDouble(Double::doubleValue).average().orElse(0);
    }

    private float convertGPA4(float score) {
        if (score >= 9) return 4.0f;
        else if (score >= 8) return 3.5f;
        else if (score >= 7) return 3.0f;
        else if (score >= 5) return 2.0f;
        else if (score >= 4) return 1.0f;
        return 0f;
    }

    private String convertLetter(float gpa4) {
        if (gpa4 == 4.0) return "A+";
        else if (gpa4 == 3.5) return "B+";
        else if (gpa4 == 3.0) return "B";
        else if (gpa4 == 2.5) return "C+";
        else if (gpa4 == 2.0) return "C";
        else if (gpa4 == 1.5) return "D+";
        else if (gpa4 == 1.0) return "D";
        else return "F";
    }

    private String classifyGPA10(float gpa10) {
        if (gpa10 >= 9.0) return "Xuất sắc";
        else if (gpa10 >= 8.0) return "Giỏi";
        else if (gpa10 >= 7.0) return "Khá";
        else if (gpa10 >= 5.0) return "Trung bình";
        else if (gpa10 >= 4.0) return "Yếu";
        else return "Kém";
    }

    private String classifyGPA4(float gpa4) {
        if (gpa4 >= 3.6) return "Xuất sắc";
        else if (gpa4 >= 3.2) return "Giỏi";
        else if (gpa4 >= 2.5) return "Khá";
        else if (gpa4 >= 2.0) return "Trung bình";
        else if (gpa4 >= 1.0) return "Yếu";
        else return "Kém";
    }
}
