package io.spring.uni_portal.service.GradeEventService;

import io.spring.uni_portal.dto.GradeEvent.GradeEventRequest;
import io.spring.uni_portal.dto.GradeEvent.GradeEventResponse;
import io.spring.uni_portal.dto.GradeEvent.GradeEventResponseDto;
import io.spring.uni_portal.model.*;
import io.spring.uni_portal.repository.GradeEventRepository;
import io.spring.uni_portal.repository.GradeTypeRepository;
import io.spring.uni_portal.repository.LecturerRepository;
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

@Service
public class GradeEventServiceImpl implements IGradeEventService {

    @Autowired
    private GradeEventRepository gradeEventRepository;

    @Autowired
    private GradeTypeRepository gradeTypeRepository;

    @Autowired
    private LecturerRepository lecturerRepository;

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

    @Override
    public List<GradeEventResponseDto> getGradeEventsByClass() {

        // Xác thực người dùng
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            throw new RuntimeException("Người dùng chưa xác thực hoặc token không hợp lệ.");
        }

        User currentUser = (User) authentication.getPrincipal();
        Lecturer lecturer = lecturerRepository.findById(currentUser.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sinh viên."));


        List<Object[]> results = gradeEventRepository.findGradeEventsWithTermClass();
        Map<Long, GradeEventResponseDto> classMap = new HashMap<>();

        for (Object[] result : results) {
            GradeEvent gradeEvent = (GradeEvent) result[0];
            TermClass termClass = (TermClass) result[1];

            GradeEventResponseDto responseDto = classMap.computeIfAbsent(
                    termClass.getTermclassId(),
                    k -> new GradeEventResponseDto(
                            termClass.getTermclassId(),
                            termClass.getClassname(),
                            termClass.getSemester(),
                            termClass.getSchoolyears(),
                            new ArrayList<>()
                    )
            );

            GradeEventResponseDto.AssignmentDto assignmentDto = new GradeEventResponseDto.AssignmentDto(
                    gradeEvent.getGradeEventId(),
                    gradeEvent.getTitle(),
                    gradeEvent.getEventDate(),
                    gradeEvent.getMaxScore(),
                    gradeEvent.getDescription()
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
