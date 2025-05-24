package io.spring.uni_portal.service.TeachingScheduleRequestService;

import io.spring.uni_portal.dto.TeachingScheduleRequest.*;
import io.spring.uni_portal.model.*;
import io.spring.uni_portal.repository.*;
import io.spring.uni_portal.service.ClassroomService.ClassroomServiceImpl;
import io.spring.uni_portal.service.TeachingAssignmentService.TeachingAssignmentService;
import io.spring.uni_portal.service.TermClassService.TermClassService;
import io.spring.uni_portal.utils.ScheduleDateUtils;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TeachingScheduleRequestService implements ITeachingScheduleRequestService{
    @Autowired
    private TeachingScheduleRequestRepository teachingScheduleRequestRepository;

    @Autowired
    private TeachingRegistrationPeriodRepository teachingRegistrationPeriodRepository;

    @Autowired
    private ClassStudentRepository classStudentRepository;

    @Autowired
    private TeachingAssignmentService teachingAssignmentService;

    @Autowired
    private ClassroomServiceImpl classroomService;

    @Autowired
    private TermClassService termClassService;

    @Autowired
    private StudentRegistrationPeriodRepository studentRegistrationPeriodRepository;


    private int calculateLessonsPerSession(String lesson) {
        String[] parts = lesson.split("-");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Định dạng lesson không hợp lệ: " + lesson);
        }
        try {
            int start = Integer.parseInt(parts[0]);
            int end = Integer.parseInt(parts[1]);
            return end - start + 1;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Số tiết không hợp lệ: " + lesson, e);
        }
    }

    private List<Integer> getLessonRange(String lesson) {
        String[] parts = lesson.split("-");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Định dạng lesson không hợp lệ: " + lesson);
        }
        try {
            int start = Integer.parseInt(parts[0]);
            int end = Integer.parseInt(parts[1]);
            if (start > end) {
                throw new IllegalArgumentException("Start lesson phải nhỏ hơn hoặc bằng end lesson: " + lesson);
            }
            List<Integer> range = new ArrayList<>();
            for (int i = start; i <= end; i++) {
                range.add(i);
            }
            return range;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Số tiết không hợp lệ: " + lesson, e);
        }
    }

    private LocalDateTime calculateEndDate(LocalDateTime dateTime, String lesson, String classType, TeachingAssignment assignment) {
        int lessonsPerSession = calculateLessonsPerSession(lesson);
        int totalLessons = 0;
        if ("LT".equalsIgnoreCase(classType)) {
            totalLessons = assignment.getSubject().getLtCredits() * 15;
        } else if ("TH".equalsIgnoreCase(classType)) {
            totalLessons = assignment.getSubject().getThCredits() * 30;
        } else {
            throw new IllegalArgumentException("Loại lớp không hợp lệ: " + classType);
        }

        int sessionsNeeded = (int) Math.ceil((double) totalLessons / lessonsPerSession);
        // Giả sử một buổi mỗi tuần
        return dateTime.plusWeeks(sessionsNeeded - 1);
    }

    private int calculateLessonDuration(String lesson) {
        int minutesPerLesson = 50;
        int breakTime = 5;

        if (lesson.equals("3-4") || lesson.equals("9-10")) {
            breakTime = 10;
        }

        String[] parts = lesson.split("-");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Định dạng lesson không hợp lệ: " + lesson);
        }

        try {
            int startLesson = Integer.parseInt(parts[0]);
            int endLesson = Integer.parseInt(parts[1]);

            int numberOfLessons = endLesson - startLesson + 1;
            // Tính tổng thời gian học cộng với thời gian ra chơi giữa các tiết
            int totalMinutes = (numberOfLessons * minutesPerLesson) + (numberOfLessons - 1) * breakTime;

            return totalMinutes;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Số tiết học không hợp lệ: " + lesson, e);
        }
    }


    private void checkClassroomAvailability(TeachingScheduleRequestDTO dto, TeachingAssignment assignment) {
        for (TeachingScheduleRequestDTO.ScheduleDetailDTO detail : dto.getScheduleDetails()) {
            Long classroomId = detail.getClassroomId();
            String lesson = detail.getLesson();
            LocalDateTime dateTime = LocalDateTime.parse(detail.getDateTime(), DateTimeFormatter.ISO_DATE_TIME);
            LocalDateTime endDate = calculateEndDate(dateTime, lesson, detail.getClassType(), assignment);
            List<Integer> newLessonRange = getLessonRange(lesson);

            List<TeachingScheduleRequest> conflictingSchedules = teachingScheduleRequestRepository.findConflictingSchedules(
                    classroomId, dateTime, endDate
            );

            for (TeachingScheduleRequest schedule : conflictingSchedules) {
                for (TeachingScheduleRequest.ScheduleDetail detailExisting : schedule.getScheduleDetails()) {
                    List<Integer> existingLessonRange = getLessonRange(detailExisting.getLesson());
                    if (newLessonRange.stream().anyMatch(existingLessonRange::contains)) {
                        throw new IllegalStateException(
                                String.format("Phòng học %d đã được đăng ký cho một hoặc nhiều tiết trong phạm vi %s, vào ngày trong tuần tương ứng, trong khoảng từ %s đến %s.",
                                        classroomId, lesson, dateTime, endDate)
                        );
                    }
                }
            }
        }
    }


    private void checkClassroomAndDateConflict(TeachingScheduleRequestDTO dto, TeachingScheduleRequest existingRequest) {
        for (TeachingScheduleRequestDTO.ScheduleDetailDTO newDetail : dto.getScheduleDetails()) {
            Long classroomId = newDetail.getClassroomId();
            String lesson = newDetail.getLesson();
            LocalDateTime dateTime = LocalDateTime.parse(newDetail.getDateTime(), DateTimeFormatter.ISO_DATE_TIME);
            int lessonDuration = calculateLessonDuration(lesson);
            LocalDateTime endDate = dateTime.plusMinutes(lessonDuration);

            List<TeachingScheduleRequest> conflictingSchedules = teachingScheduleRequestRepository.findConflictingSchedules(
                    classroomId, dateTime, endDate
            );

            for (TeachingScheduleRequest schedule : conflictingSchedules) {
                if (!schedule.getScheduleId().equals(existingRequest.getScheduleId())) {
                    throw new IllegalStateException(String.format(
                            "Phòng học %d đã được đăng ký vào ngày %s từ %s đến %s. Không thể cập nhật lịch giảng dạy.",
                            classroomId, dateTime, dateTime.plusMinutes(lessonDuration), endDate));
                }
            }
        }
    }


    @Override
    public Page<TeachingScheduleWithAssignmentResponseDTO> getAllTeachingSchedules(Pageable pageable, String searchValue) {
        // Kiểm tra nếu có searchValue, gọi phương thức findBySearchValue
        if (searchValue != null && !searchValue.isEmpty()) {
            return teachingScheduleRequestRepository.findBySearchValue(pageable, searchValue)
                    .map(TeachingScheduleWithAssignmentResponseDTO::new);
        }

        // Trả về tất cả các lịch giảng dạy nếu không có giá trị tìm kiếm
        return teachingScheduleRequestRepository.findAll(pageable)
                .map(TeachingScheduleWithAssignmentResponseDTO::new);
    }
    

    @Transactional
    public TeachingScheduleRequestResponseDTO registerTeachingSchedule(TeachingScheduleRequestDTO dto) {
        // Validate input
        if (dto == null || dto.getAssignmentId() == null || dto.getScheduleDetails() == null || dto.getScheduleDetails().isEmpty() || dto.getStatus() == null) {
            throw new IllegalArgumentException("TeachingScheduleRequestDTO không hợp lệ: Thiếu các trường bắt buộc");
        }

        // Check registration period
        LocalDateTime now = LocalDateTime.now();
        TeachingRegistrationPeriod currentPeriod = teachingRegistrationPeriodRepository.findActivePeriod(now)
                .orElseThrow(() -> new RuntimeException("Hiện tại không có đợt đăng ký giảng dạy nào đang mở"));

        if (now.isBefore(currentPeriod.getStartDate()) || now.isAfter(currentPeriod.getEndDate())) {
            throw new IllegalStateException(String.format(
                    "Yêu cầu đăng ký giảng dạy chỉ được thực hiện trong khoảng thời gian từ %s đến %s",
                    currentPeriod.getStartDate(), currentPeriod.getEndDate()));
        }

        // Check if assignment already registered
        if (teachingScheduleRequestRepository.existsByAssignment_AssignmentId(dto.getAssignmentId())) {
            throw new IllegalStateException("Phân công với ID " + dto.getAssignmentId() + " đã được đăng ký.");
        }

        // Get teaching assignment
        TeachingAssignment assignment = teachingAssignmentService.findById(dto.getAssignmentId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phân công có ID: " + dto.getAssignmentId()));

        // Check classroom availability
        checkClassroomAvailability(dto, assignment);

        // Check for lecturer schedule conflicts
        Long lecturerId = assignment.getLecturer().getUserId();
        LocalDateTime newDateTime = LocalDateTime.parse(dto.getScheduleDetails().get(0).getDateTime(), DateTimeFormatter.ISO_DATE_TIME);
        String newLesson = dto.getScheduleDetails().get(0).getLesson();

        LocalDateTime startOfDay = newDateTime.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = newDateTime.toLocalDate().atTime(LocalTime.MAX);

        List<TeachingScheduleRequest> sameDayRequests =
                teachingScheduleRequestRepository.findByLecturerIdAndDate(lecturerId, startOfDay, endOfDay);

        for (TeachingScheduleRequest existing : sameDayRequests) {
            if (isLessonOverlap(newLesson, existing.getLesson())) {
                throw new IllegalStateException(String.format(
                        "Trùng lịch giảng dạy: Giảng viên đã đăng ký lớp vào %s với tiết %s",
                        newDateTime.toLocalDate(), existing.getLesson()
                ));
            }
        }

        // Check for lesson overlap between LT and TH classes on the same day
        Map<LocalDate, List<TeachingScheduleRequestDTO.ScheduleDetailDTO>> detailsByDate = dto.getScheduleDetails().stream()
                .collect(Collectors.groupingBy(detail ->
                        LocalDateTime.parse(detail.getDateTime(), DateTimeFormatter.ISO_DATE_TIME).toLocalDate()));

        for (Map.Entry<LocalDate, List<TeachingScheduleRequestDTO.ScheduleDetailDTO>> entry : detailsByDate.entrySet()) {
            List<TeachingScheduleRequestDTO.ScheduleDetailDTO> dailyDetails = entry.getValue();
            List<TeachingScheduleRequestDTO.ScheduleDetailDTO> ltDetails = dailyDetails.stream()
                    .filter(d -> "LT".equals(d.getClassType()))
                    .collect(Collectors.toList());
            List<TeachingScheduleRequestDTO.ScheduleDetailDTO> thDetails = dailyDetails.stream()
                    .filter(d -> "TH".equals(d.getClassType()))
                    .collect(Collectors.toList());

            for (TeachingScheduleRequestDTO.ScheduleDetailDTO ltDetail : ltDetails) {
                for (TeachingScheduleRequestDTO.ScheduleDetailDTO thDetail : thDetails) {
                    if (isLessonOverlap(ltDetail.getLesson(), thDetail.getLesson())) {
                        throw new IllegalStateException(String.format(
                                "Lịch giảng dạy trùng tiết trong cùng ngày %s: Lớp lý thuyết (%s) trùng với lớp thực hành (%s)",
                                entry.getKey(), ltDetail.getLesson(), thDetail.getLesson()
                        ));
                    }
                }
            }
        }

        // Create TeachingScheduleRequest
        TeachingScheduleRequest request = new TeachingScheduleRequest();
        Long classroomId = dto.getScheduleDetails().get(0).getClassroomId();
        Classroom classroom = classroomService.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng học có ID: " + classroomId));

        request.setAssignment(assignment);
        request.setClassroom(classroom);

        String lessonStr = dto.getScheduleDetails().get(0).getLesson();
        if (lessonStr == null || lessonStr.isEmpty()) {
            throw new IllegalArgumentException("Lesson không được để trống");
        }
        request.setLesson(lessonStr);
        request.setDateTime(newDateTime);
        request.setStatus(dto.getStatus());
        request.setCreatedAt(LocalDateTime.now());
        request.setClassType(dto.getScheduleDetails().get(0).getClassType());

        // Map schedule details
        List<TeachingScheduleRequest.ScheduleDetail> scheduleDetails = dto.getScheduleDetails().stream()
                .map(dtoDetail -> {
                    TeachingScheduleRequest.ScheduleDetail detail = new TeachingScheduleRequest.ScheduleDetail();
                    detail.setClassroomId(dtoDetail.getClassroomId());
                    detail.setLesson(dtoDetail.getLesson());
                    detail.setDateTime(dtoDetail.getDateTime());
                    detail.setClassType(dtoDetail.getClassType());
                    LocalDateTime dateTime = LocalDateTime.parse(dtoDetail.getDateTime(), DateTimeFormatter.ISO_DATE_TIME);
                    LocalDateTime endDateTime = calculateEndDate(dateTime, dtoDetail.getLesson(), dtoDetail.getClassType(), assignment);
                    detail.setEndDate(endDateTime.format(DateTimeFormatter.ISO_DATE_TIME));
                    return detail;
                })
                .collect(Collectors.toList());
        request.setScheduleDetails(scheduleDetails);

        // Set end date
        LocalDateTime latestEndDate = scheduleDetails.stream()
                .map(detail -> LocalDateTime.parse(detail.getEndDate(), DateTimeFormatter.ISO_DATE_TIME))
                .max(LocalDateTime::compareTo)
                .orElse(null);
        request.setEndDate(latestEndDate);

        // Map materials
        List<TeachingScheduleRequest.Material> materials = null;
        if (dto.getMaterials() != null) {
            materials = dto.getMaterials().stream()
                    .map(dtoMaterial -> {
                        TeachingScheduleRequest.Material material = new TeachingScheduleRequest.Material();
                        material.setLt(dtoMaterial.getLt());
                        material.setTh(dtoMaterial.getTh());
                        return material;
                    })
                    .collect(Collectors.toList());
        }
        request.setMaterials(materials);

        // Save request
        TeachingScheduleRequest savedRequest = teachingScheduleRequestRepository.save(request);

        // Create ClassStudent if status is success
        if ("success".equalsIgnoreCase(dto.getStatus())) {
            ClassStudent classStudent = new ClassStudent();
            classStudent.setTeachingScheduleRequest(savedRequest);
            classStudent.setStatus("pending");

            classStudent.setCreatedAt(savedRequest.getCreatedAt());
            classStudent.setEndDate(savedRequest.getEndDate());
            classStudent.setMaterials(null);
            classStudentRepository.save(classStudent);
        }

        // Build response DTO
        TeachingScheduleRequestResponseDTO responseDTO = new TeachingScheduleRequestResponseDTO();
        responseDTO.setScheduleId(savedRequest.getScheduleId());
        responseDTO.setClassroomId(savedRequest.getClassroom().getClassroomId());
        responseDTO.setEndDate(savedRequest.getEndDate());
        responseDTO.setDateTime(savedRequest.getDateTime());
        responseDTO.setStatus(savedRequest.getStatus());
        responseDTO.setCreatedAt(savedRequest.getCreatedAt());

        TeachingScheduleRequestResponseDTO.TeachingAssignmentDTO assignmentDTO = new TeachingScheduleRequestResponseDTO.TeachingAssignmentDTO();
        assignmentDTO.setAssignmentId(savedRequest.getAssignment().getAssignmentId());
        assignmentDTO.setAssignmentType(savedRequest.getAssignment().getAssignmentType());

        TeachingScheduleRequestResponseDTO.TeachingAssignmentDTO.LecturerDTO lecturerDTO = new TeachingScheduleRequestResponseDTO.TeachingAssignmentDTO.LecturerDTO();
        lecturerDTO.setLecturerId(savedRequest.getAssignment().getLecturer().getUserId());
        lecturerDTO.setLecturerName(savedRequest.getAssignment().getLecturer().getUser().getUserName());
        assignmentDTO.setLecturer(lecturerDTO);

        TeachingScheduleRequestResponseDTO.TeachingAssignmentDTO.SubjectDTO subjectDTO = new TeachingScheduleRequestResponseDTO.TeachingAssignmentDTO.SubjectDTO();
        subjectDTO.setSubjectId(savedRequest.getAssignment().getSubject().getSubjectId());
        subjectDTO.setSubjectName(savedRequest.getAssignment().getSubject().getSubjectName());
        subjectDTO.setLtCredits(savedRequest.getAssignment().getSubject().getLtCredits());
        subjectDTO.setThCredits(savedRequest.getAssignment().getSubject().getThCredits());
        assignmentDTO.setSubject(subjectDTO);

        TeachingScheduleRequestResponseDTO.TeachingAssignmentDTO.TermClassDTO termClassDTO = new TeachingScheduleRequestResponseDTO.TeachingAssignmentDTO.TermClassDTO();
        termClassDTO.setTermClassId(savedRequest.getAssignment().getTermClass().getTermclassId());
        termClassDTO.setTermName(savedRequest.getAssignment().getTermClass().getClassname());
        assignmentDTO.setTermClass(termClassDTO);

        responseDTO.setAssignment(assignmentDTO);

        List<TeachingScheduleRequestResponseDTO.ScheduleDetailDTO> scheduleDetailDTOs = savedRequest.getScheduleDetails().stream()
                .map(detail -> {
                    TeachingScheduleRequestResponseDTO.ScheduleDetailDTO detailDTO = new TeachingScheduleRequestResponseDTO.ScheduleDetailDTO();
                    detailDTO.setClassroomId(detail.getClassroomId());
                    detailDTO.setLesson(detail.getLesson());
                    detailDTO.setDateTime(detail.getDateTime());
                    detailDTO.setEndDate(detail.getEndDate());
                    detailDTO.setClassType(detail.getClassType());

                    Classroom detailClassroom = classroomService.findById(detail.getClassroomId())
                            .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng học có ID: " + detail.getClassroomId()));
                    detailDTO.setNumberOfSeats(detailClassroom.getNumberOfSeats());

                    return detailDTO;
                })
                .collect(Collectors.toList());
        responseDTO.setScheduleDetails(scheduleDetailDTOs);

        List<TeachingScheduleRequestResponseDTO.MaterialDTO> materialDTOs = null;
        if (savedRequest.getMaterials() != null) {
            materialDTOs = savedRequest.getMaterials().stream()
                    .map(material -> {
                        TeachingScheduleRequestResponseDTO.MaterialDTO materialDTO = new TeachingScheduleRequestResponseDTO.MaterialDTO();
                        materialDTO.setLt(material.getLt());
                        materialDTO.setTh(material.getTh());
                        return materialDTO;
                    })
                    .collect(Collectors.toList());
        }
        responseDTO.setMaterials(materialDTOs);

        return responseDTO;
    }




    private boolean isLessonOverlap(String lesson1, String lesson2) {
    if (lesson1 == null || lesson2 == null) return false;

    String[] parts1 = lesson1.split("-");
    String[] parts2 = lesson2.split("-");

    if (parts1.length != 2 || parts2.length != 2) {
        throw new IllegalArgumentException("Định dạng tiết học không hợp lệ");
    }

    try {
        int start1 = Integer.parseInt(parts1[0]);
        int end1 = Integer.parseInt(parts1[1]);
        int start2 = Integer.parseInt(parts2[0]);
        int end2 = Integer.parseInt(parts2[1]);

        return start1 <= end2 && start2 <= end1;
    } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Định dạng tiết học không hợp lệ");
    }
}

    private int[] parseLessonRange(String lessonStr) {
        String[] parts = lessonStr.split("-");
        int start = Integer.parseInt(parts[0].trim());
        int end = Integer.parseInt(parts[1].trim());
        return new int[]{start, end};
    }



    @Transactional
    public TeachingScheduleRequestResponseDTO updateTeachingSchedule(TeachingScheduleRequestDTO dto) {
        if (dto == null || dto.getAssignmentId() == null || dto.getScheduleDetails() == null || dto.getScheduleDetails().isEmpty()) {
            throw new IllegalArgumentException("TeachingScheduleRequestDTO is invalid: Missing required fields.");
        }

        // Retrieve the existing teaching schedule request using assignmentId from the URL
        TeachingScheduleRequest existingRequest = teachingScheduleRequestRepository.findByAssignment_AssignmentId(dto.getAssignmentId())
                .orElseThrow(() -> new RuntimeException("Schedule with assignmentId " + dto.getAssignmentId() + " not found"));

        // Check if the updated schedule conflicts with any existing schedules
        checkClassroomAndDateConflict(dto, existingRequest);

        // Update the status
        existingRequest.setStatus(dto.getStatus());

        // Update materials (if provided)
        List<TeachingScheduleRequest.Material> updatedMaterials = new ArrayList<>();
        if (dto.getMaterials() != null) {
            updatedMaterials = dto.getMaterials().stream()
                    .map(dtoMaterial -> {
                        TeachingScheduleRequest.Material material = new TeachingScheduleRequest.Material();
                        material.setLt(dtoMaterial.getLt());
                        material.setTh(dtoMaterial.getTh());
                        return material;
                    })
                    .collect(Collectors.toList());
        }
        existingRequest.setMaterials(updatedMaterials);

        // Update schedule details
        List<TeachingScheduleRequest.ScheduleDetail> updatedScheduleDetails = dto.getScheduleDetails().stream()
                .map(dtoDetail -> {
                    TeachingScheduleRequest.ScheduleDetail detail = new TeachingScheduleRequest.ScheduleDetail();
                    detail.setClassroomId(dtoDetail.getClassroomId());
                    detail.setLesson(dtoDetail.getLesson());
                    detail.setDateTime(dtoDetail.getDateTime());
                    detail.setClassType(dtoDetail.getClassType());
                    // Recalculate end date for updated schedule details
                    LocalDateTime dateTime = LocalDateTime.parse(dtoDetail.getDateTime(), DateTimeFormatter.ISO_DATE_TIME);
                    LocalDateTime endDateTime = calculateEndDate(dateTime, dtoDetail.getLesson(), dtoDetail.getClassType(), existingRequest.getAssignment());
                    detail.setEndDate(endDateTime.format(DateTimeFormatter.ISO_DATE_TIME));
                    return detail;
                })
                .collect(Collectors.toList());

        existingRequest.setScheduleDetails(updatedScheduleDetails);

        // Recalculate the overall end date based on the updated schedule details
        LocalDateTime latestEndDate = updatedScheduleDetails.stream()
                .map(detail -> LocalDateTime.parse(detail.getEndDate(), DateTimeFormatter.ISO_DATE_TIME))
                .max(LocalDateTime::compareTo)
                .orElse(null);
        existingRequest.setEndDate(latestEndDate);

        // Save the updated schedule
        TeachingScheduleRequest updatedRequest = teachingScheduleRequestRepository.save(existingRequest);

        // Map the updated entity to a response DTO
        TeachingScheduleRequestResponseDTO responseDTO = new TeachingScheduleRequestResponseDTO();
        responseDTO.setScheduleId(updatedRequest.getScheduleId());
        responseDTO.setClassroomId(updatedRequest.getClassroom().getClassroomId());
        responseDTO.setStatus(updatedRequest.getStatus());
        responseDTO.setDateTime(updatedRequest.getDateTime());
        responseDTO.setEndDate(updatedRequest.getEndDate());
        responseDTO.setCreatedAt(updatedRequest.getCreatedAt());

        // Map assignment and lecturer info
        TeachingScheduleRequestResponseDTO.TeachingAssignmentDTO assignmentDTO = new TeachingScheduleRequestResponseDTO.TeachingAssignmentDTO();
        assignmentDTO.setAssignmentId(updatedRequest.getAssignment().getAssignmentId());

        // Lecturer information
        TeachingScheduleRequestResponseDTO.TeachingAssignmentDTO.LecturerDTO lecturerDTO = new TeachingScheduleRequestResponseDTO.TeachingAssignmentDTO.LecturerDTO();
        lecturerDTO.setLecturerId(updatedRequest.getAssignment().getLecturer().getUserId());
        lecturerDTO.setLecturerName(updatedRequest.getAssignment().getLecturer().getUser().getUserName());
        assignmentDTO.setLecturer(lecturerDTO);

        // Subject information
        TeachingScheduleRequestResponseDTO.TeachingAssignmentDTO.SubjectDTO subjectDTO = new TeachingScheduleRequestResponseDTO.TeachingAssignmentDTO.SubjectDTO();
        subjectDTO.setSubjectId(updatedRequest.getAssignment().getSubject().getSubjectId());
        subjectDTO.setSubjectName(updatedRequest.getAssignment().getSubject().getSubjectName());
        subjectDTO.setLtCredits(updatedRequest.getAssignment().getSubject().getLtCredits());
        subjectDTO.setThCredits(updatedRequest.getAssignment().getSubject().getThCredits());
        assignmentDTO.setSubject(subjectDTO);

        // Term Class information
        TeachingScheduleRequestResponseDTO.TeachingAssignmentDTO.TermClassDTO termClassDTO = new TeachingScheduleRequestResponseDTO.TeachingAssignmentDTO.TermClassDTO();
        termClassDTO.setTermClassId(updatedRequest.getAssignment().getTermClass().getTermclassId());
        termClassDTO.setTermName(updatedRequest.getAssignment().getTermClass().getClassname());
        assignmentDTO.setTermClass(termClassDTO);

        responseDTO.setAssignment(assignmentDTO);

        // Map schedule details to DTO
        List<TeachingScheduleRequestResponseDTO.ScheduleDetailDTO> scheduleDetailDTOs = updatedRequest.getScheduleDetails().stream()
                .map(detail -> {
                    TeachingScheduleRequestResponseDTO.ScheduleDetailDTO detailDTO = new TeachingScheduleRequestResponseDTO.ScheduleDetailDTO();
                    detailDTO.setClassroomId(detail.getClassroomId());
                    detailDTO.setLesson(detail.getLesson());
                    detailDTO.setDateTime(detail.getDateTime());
                    detailDTO.setEndDate(detail.getEndDate());
                    detailDTO.setClassType(detail.getClassType());
                    Classroom classroom = classroomService.findById(detail.getClassroomId())
                            .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng học có ID: " + detail.getClassroomId()));
                    detailDTO.setNumberOfSeats(classroom.getNumberOfSeats());
                    return detailDTO;
                })
                .collect(Collectors.toList());
        responseDTO.setScheduleDetails(scheduleDetailDTOs);

        // Map materials to DTO
        List<TeachingScheduleRequestResponseDTO.MaterialDTO> materialDTOs = updatedRequest.getMaterials().stream()
                .map(material -> {
                    TeachingScheduleRequestResponseDTO.MaterialDTO materialDTO = new TeachingScheduleRequestResponseDTO.MaterialDTO();
                    materialDTO.setLt(material.getLt());
                    materialDTO.setTh(material.getTh());
                    return materialDTO;
                })
                .collect(Collectors.toList());
        responseDTO.setMaterials(materialDTOs);

        return responseDTO;  // Return the updated schedule response
    }

    @Override
    public Page<TeachingScheduleDetailResponseDTO> getSchedulesByStatusAndUser(
            Long userId,
            List<String> statuses,
            Pageable pageable,
            String searchValue) {

        Page<TeachingScheduleRequest> filteredSchedules;

        if (searchValue != null && !searchValue.isEmpty()) {
            filteredSchedules = teachingScheduleRequestRepository.findByLecturerAndStatusAndSearchValue(
                    userId, statuses, searchValue, pageable);
        } else {
            filteredSchedules = teachingScheduleRequestRepository.findByLecturerAndStatus(
                    userId, statuses, pageable);
        }

        return filteredSchedules.map(TeachingScheduleDetailResponseDTO::new);
    }

    @Override
    public List<TeachingScheduleDetailResponseDTO> getSchedulesWithSuccessStatus(Long userId) {
        List<TeachingScheduleRequest> schedules =
                teachingScheduleRequestRepository.findByLecturerAndSuccessStatus(userId);

        return schedules.stream()
                .map(TeachingScheduleDetailResponseDTO::new)
                .collect(Collectors.toList());
    }





}
