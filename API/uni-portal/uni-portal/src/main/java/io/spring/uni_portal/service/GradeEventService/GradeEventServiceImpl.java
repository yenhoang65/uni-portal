package io.spring.uni_portal.service.GradeEventService;

import io.spring.uni_portal.dto.ClassStudent.ClassStudentDTO;
import io.spring.uni_portal.dto.ExamSchedule.ExamScheduleResponse;
import io.spring.uni_portal.dto.GradeEvent.*;
import io.spring.uni_portal.dto.TeachingScheduleRequest.TeachingScheduleRequestDTO;
import io.spring.uni_portal.model.*;
import io.spring.uni_portal.repository.*;
import io.spring.uni_portal.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GradeEventServiceImpl implements IGradeEventService {

    @Autowired
    private GradeEventRepository gradeEventRepository;

    @Autowired
    private GradeTypeRepository gradeTypeRepository;

    @Autowired
    private ClassStudentRepository classStudentRepository;

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private ExamScheduleRepository examScheduleRepository;

    @Override
    public Response<GradeEventResponse> createMidtermGradeEvent(GradeEventRequest request) {
        try {
            if (request.getClassStudentId() == null || request.getGradeTypeId() == null) {
                return Response.failure("Mã lớp học phần và mã loại bài tập là bắt buộc");
            }

            GradeType gradeType = gradeTypeRepository.findById(request.getGradeTypeId()).orElse(null);
            if (gradeType == null) {
                return Response.failure("Không tìm thấy loại bài tập");
            }

            String code = gradeType.getCode();
            if ("MID".equalsIgnoreCase(code) || "FINAL".equalsIgnoreCase(code)) {
                boolean exists = gradeEventRepository.existsByClassStudent_ClassStudentIdAndGradeType_Code(
                        request.getClassStudentId(), code
                );
                if (exists) {
                    return Response.failure("Bài tập " + code + " đã tồn tại trong lớp này");
                }
            }

            ClassStudent classStudent = new ClassStudent();
            classStudent.setClassStudentId(request.getClassStudentId());
            GradeEvent gradeEvent = new GradeEvent();
            gradeEvent.setClassStudent(classStudent);
            gradeEvent.setGradeType(gradeType);
            gradeEvent.setTitle(request.getTitle());
            gradeEvent.setEventDate(request.getEventDate());
            gradeEvent.setMaxScore(request.getMaxScore());
            gradeEvent.setDescription(request.getDescription());
            gradeEvent.setCreatedAt(LocalDateTime.now());

            GradeEvent savedEvent = gradeEventRepository.save(gradeEvent);

            GradeEventResponse response = new GradeEventResponse();
            response.setGradeEventId(savedEvent.getGradeEventId());
            response.setClassStudentId(request.getClassStudentId());
            response.setGradeTypeId(request.getGradeTypeId());
            response.setTitle(request.getTitle());
            response.setEventDate(request.getEventDate());
            response.setMaxScore(request.getMaxScore());
            response.setDescription(request.getDescription());
            response.setCreatedAt(savedEvent.getCreatedAt());

            return Response.success("Tạo bài tập thành công", response);
        } catch (Exception e) {
            return Response.failure("Không tạo được bài tập: " + e.getMessage());
        }
    }


    @Autowired
    public GradeEventServiceImpl(GradeEventRepository gradeEventRepository) {
        this.gradeEventRepository = gradeEventRepository;
    }

//    @Override
//    public List<GradeEventResponseDto> getGradeEventsByClass(Long classStudentId) {
//
//        List<GradeEvent> gradeEvents = gradeEventRepository.findGradeEventsByClassStudentId(classStudentId);
//        Map<Long, GradeEventResponseDto> classMap = new HashMap<>();
//
//        for (GradeEvent gradeEvent : gradeEvents) {
//            // Lấy TermClass từ ClassStudent.teachingScheduleRequest.termClass
//            Long termClassId = gradeEvent.getClassStudent().getTeachingScheduleRequest().getAssignment().getTermClass().getTermclassId();
//            TermClass termClass = gradeEvent.getClassStudent().getTeachingScheduleRequest().getAssignment().getTermClass();
//
//            GradeEventResponseDto responseDto = classMap.computeIfAbsent(
//                    termClassId,
//                    k -> new GradeEventResponseDto(
//                            termClassId,
//                            termClass.getClassname(),
//                            termClass.getSemester(),
//                            termClass.getSchoolyears(),
//                            new ArrayList<>()
//                    )
//            );
//
//            GradeEventResponseDto.AssignmentDto assignmentDto = new GradeEventResponseDto.AssignmentDto(
//                    gradeEvent.getGradeEventId(),
//                    gradeEvent.getTitle(),
//                    gradeEvent.getEventDate(),
//                    gradeEvent.getMaxScore(),
//                    gradeEvent.getDescription()
//            );
//
//            responseDto.getAssignments().add(assignmentDto);
//        }
//
//        return new ArrayList<>(classMap.values());
//    }

    @Override
    public List<GradeEventResponseDto> getGradeEventsByClass(Long classStudentId) {
        List<GradeEvent> gradeEvents = gradeEventRepository.findGradeEventsByClassStudentId(classStudentId);

        Map<Long, GradeEventResponseDto.GradeTypeEventDTO> gradeTypeMap = gradeTypeRepository.findAll()
                .stream()
                .collect(Collectors.toMap(
                        GradeType::getGradeTypeId,
                        gt -> new GradeEventResponseDto.GradeTypeEventDTO(
                                gt.getGradeTypeId(),
                                gt.getCode(),
                                gt.getName(),
                                gt.getCoefficient()
                        )
                ));

        Map<Long, GradeEventResponseDto> classMap = new HashMap<>();

        for (GradeEvent gradeEvent : gradeEvents) {
            TermClass termClass = gradeEvent.getClassStudent()
                    .getTeachingScheduleRequest()
                    .getAssignment()
                    .getTermClass();
            Long termClassId = termClass.getTermclassId();

            GradeEventResponseDto responseDto = classMap.computeIfAbsent(
                    termClassId,
                    id -> new GradeEventResponseDto(
                            termClassId,
                            termClass.getClassname(),
                            termClass.getSemester(),
                            termClass.getSchoolyears(),
                            new ArrayList<>()
                    )
            );

            GradeType gradeType = gradeEvent.getGradeType();
            GradeEventResponseDto.GradeTypeEventDTO gradeTypeDto = gradeTypeMap.getOrDefault(
                    gradeType != null ? gradeType.getGradeTypeId() : null, null
            );

            // Gán examSchedule (nếu có)
            ExamScheduleResponse examScheduleDto = null;
            if (gradeType != null) {
                ExamSchedule examSchedule = examScheduleRepository
                        .findByClassStudentIdAndGradeTypeId(classStudentId, gradeType.getGradeTypeId());

                if (examSchedule != null) {
                    examScheduleDto = new ExamScheduleResponse();
                    examScheduleDto.setExamScheduleId(examSchedule.getExamScheduleId());
                    examScheduleDto.setClassStudentId(classStudentId);
                    examScheduleDto.setClassroomId(examSchedule.getClassroom().getClassroomId());
                    examScheduleDto.setStartDate(examSchedule.getStartDate());
                    examScheduleDto.setEndDate(examSchedule.getEndDate());
                    examScheduleDto.setStartTime(examSchedule.getStartTime());
                    examScheduleDto.setEndTime(examSchedule.getEndTime());
                    examScheduleDto.setExamForm(examSchedule.getExamForm());
                    examScheduleDto.setGradeTypeId(gradeType.getGradeTypeId());
                }
            }

            GradeEventResponseDto.AssignmentDto assignmentDto = new GradeEventResponseDto.AssignmentDto(
                    gradeEvent.getGradeEventId(),
                    gradeEvent.getTitle(),
                    gradeEvent.getEventDate(),
                    gradeEvent.getMaxScore(),
                    gradeEvent.getDescription(),
                    gradeTypeDto,
                    examScheduleDto
            );

            responseDto.getAssignments().add(assignmentDto);
        }

        return new ArrayList<>(classMap.values());
    }






    @Override
    public Response<GradeEventResponse> updateGradeEvent(Long gradeEventId, GradeEventRequest request) {
        try {
            // Tìm bài tập theo ID
            GradeEvent gradeEvent = gradeEventRepository.findById(gradeEventId)
                    .orElse(null);
            if (gradeEvent == null) {
                return Response.failure("Không tìm thấy bài tập với ID: " + gradeEventId);
            }

            // Kiểm tra các trường bắt buộc
            if (request.getClassStudentId() == null || request.getGradeTypeId() == null) {
                return Response.failure("Mã lớp học phần và mã loại bài tập là bắt buộc");
            }

            // Kiểm tra loại bài tập
            GradeType gradeType = gradeTypeRepository.findById(request.getGradeTypeId()).orElse(null);
            if (gradeType == null) {
                return Response.failure("Không tìm thấy loại bài tập");
            }

            // Kiểm tra sự tồn tại của bài tập MID hoặc FINAL trong lớp
            String code = gradeType.getCode();
            if ("MID".equalsIgnoreCase(code) || "FINAL".equalsIgnoreCase(code)) {
                boolean exists = gradeEventRepository.existsByClassStudent_ClassStudentIdAndGradeType_CodeAndGradeEventIdNot(
                        request.getClassStudentId(), code, gradeEventId
                );
                if (exists) {
                    return Response.failure("Bài tập " + code + " đã tồn tại trong lớp này");
                }
            }

            // Cập nhật thông tin bài tập
            ClassStudent classStudent = new ClassStudent();
            classStudent.setClassStudentId(request.getClassStudentId());

            gradeEvent.setClassStudent(classStudent);
            gradeEvent.setGradeType(gradeType);
            gradeEvent.setTitle(request.getTitle());
            gradeEvent.setEventDate(request.getEventDate());
            gradeEvent.setMaxScore(request.getMaxScore());
            gradeEvent.setDescription(request.getDescription());
//            gradeEvent.getEventDate(LocalDateTime.now());

            // Lưu bài tập đã cập nhật
            GradeEvent updatedEvent = gradeEventRepository.save(gradeEvent);

            // Tạo response
            GradeEventResponse response = new GradeEventResponse();
            response.setGradeEventId(updatedEvent.getGradeEventId());
            response.setClassStudentId(request.getClassStudentId());
            response.setGradeTypeId(request.getGradeTypeId());
            response.setTitle(request.getTitle());
            response.setEventDate(request.getEventDate());
            response.setMaxScore(request.getMaxScore());
            response.setDescription(request.getDescription());
            response.setCreatedAt(updatedEvent.getCreatedAt());
//            response.setUpdatedAt(updatedEvent.getUpdatedAt());

            return Response.success("Cập nhật bài tập thành công", response);
        } catch (Exception e) {
            return Response.failure("Không cập nhật được bài tập: " + e.getMessage());
        }
    }

    @Override
    public Response<GradeEventResponse> getGradeEventById(Long gradeEventId) {
        try {
            // Tìm bài tập theo ID
            GradeEvent gradeEvent = gradeEventRepository.findById(gradeEventId).orElse(null);
            if (gradeEvent == null) {
                return Response.failure("Không tìm thấy bài tập với ID: " + gradeEventId);
            }

            // Tạo response từ thông tin bài tập
            GradeEventResponse response = new GradeEventResponse();
            response.setGradeEventId(gradeEvent.getGradeEventId());
            response.setClassStudentId(gradeEvent.getClassStudent().getClassStudentId());
            response.setGradeTypeId(gradeEvent.getGradeType().getGradeTypeId());
            response.setTitle(gradeEvent.getTitle());
            response.setEventDate(gradeEvent.getEventDate());
            response.setMaxScore(gradeEvent.getMaxScore());
            response.setDescription(gradeEvent.getDescription());
            response.setCreatedAt(gradeEvent.getCreatedAt());
            return Response.success("Lấy chi tiết bài tập thành công", response);
        } catch (Exception e) {
            return Response.failure("Không lấy được chi tiết bài tập: " + e.getMessage());
        }
    }



}
