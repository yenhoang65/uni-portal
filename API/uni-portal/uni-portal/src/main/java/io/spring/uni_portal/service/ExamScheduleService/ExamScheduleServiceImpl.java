package io.spring.uni_portal.service.ExamScheduleService;

import io.spring.uni_portal.dto.ExamSchedule.ExamScheduleRequest;
import io.spring.uni_portal.dto.ExamSchedule.ExamScheduleResponse;
import io.spring.uni_portal.model.*;
import io.spring.uni_portal.repository.*;
import io.spring.uni_portal.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ExamScheduleServiceImpl implements IExamScheduleService{
//    @Autowired
//    private ExamScheduleRepository examScheduleRepository;
//    @Autowired
//    private GradeTypeRepository gradeTypeRepository;
//
//    @Override
//    public Response<ExamScheduleResponse> createMidtermExamSchedule(ExamScheduleRequest request) {
//        try {
//            // Kiểm tra dữ liệu đầu vào
//            if (request.getGradeTypeId() == null || request.getClassStudentId() == null) {
//                return Response.failure("ID của lớp và loại điểm là bắt buộc");
//            }
//
//            // Lấy thông tin GradeType từ cơ sở dữ liệu
//            GradeType gradeType = gradeTypeRepository.findById(request.getGradeTypeId())
//                    .orElseThrow(() -> new IllegalArgumentException("Loại điểm không tồn tại"));
//            String gradeTypeCode = gradeType.getCode();
//
//            // Kiểm tra xem lớp đã có lịch thi MID hoặc FINAL hay chưa
//            List<ExamSchedule> existingSchedules = examScheduleRepository.findByClassStudentId(request.getClassStudentId());
//            long midCount = existingSchedules.stream()
//                    .filter(schedule -> "MID".equals(schedule.getGradeType().getCode()))
//                    .count();
//            long finalCount = existingSchedules.stream()
//                    .filter(schedule -> "FINAL".equals(schedule.getGradeType().getCode()))
//                    .count();
//
//            if (midCount > 0 && "MID".equals(gradeTypeCode)) {
//                return Response.failure("Lớp này đã có lịch thi giữa kỳ rồi");
//            }
//            if (finalCount > 0 && "FINAL".equals(gradeTypeCode)) {
//                return Response.failure("Lớp này đã có lịch thi cuối kỳ rồi");
//            }
//
//            // Tạo các thực thể liên quan
//            ClassStudent classStudent = new ClassStudent();
//            classStudent.setClassStudentId(request.getClassStudentId());
//            Classroom classroom = new Classroom();
//            classroom.setClassroomId(request.getClassroomId());
//
//            // Sử dụng GradeType đã lấy từ cơ sở dữ liệu
//            ExamSchedule examSchedule = new ExamSchedule(
//                    classStudent,
//                    classroom,
//                    request.getStartDate(),
//                    request.getEndDate(),
//                    request.getStartTime(),
//                    request.getEndTime(),
//                    request.getExamForm(),
//                    gradeType
//            );
//
//            ExamSchedule savedSchedule = examScheduleRepository.save(examSchedule);
//
//            // Tạo response
//            ExamScheduleResponse response = new ExamScheduleResponse();
//            response.setExamScheduleId(savedSchedule.getExamScheduleId());
//            response.setClassStudentId(request.getClassStudentId());
//            response.setClassroomId(request.getClassroomId());
//            response.setStartDate(request.getStartDate());
//            response.setEndDate(request.getEndDate());
//            response.setStartTime(request.getStartTime());
//            response.setEndTime(request.getEndTime());
//            response.setExamForm(request.getExamForm());
//            response.setGradeTypeId(request.getGradeTypeId());
//
//            return Response.success("Tạo lịch thi giữa kỳ thành công", response);
//        } catch (IllegalArgumentException e) {
//            return Response.failure("Loại điểm không tồn tại");
//        } catch (Exception e) {
//            return Response.failure("Lỗi khi tạo lịch thi giữa kỳ: " + e.getMessage());
//        }
//    }

    @Autowired
    private ExamScheduleRepository examScheduleRepository;
    @Autowired
    private GradeTypeRepository gradeTypeRepository;
    @Autowired
    private ClassStudentRepository classStudentRepository;
    @Autowired
    private TeachingScheduleRequestRepository teachingScheduleRequestRepository;
    @Autowired
    private AttendanceSessionRepository attendanceSessionRepository;

    private static final LocalTime DAY_START = LocalTime.of(7, 30); // Tiết 1 bắt đầu từ 7:30 AM
    private static final int LESSON_DURATION_MINUTES = 50; // Mỗi tiết 50 phút
    private static final int BREAK_MINUTES = 5; // Cách nhau 5 phút
    private static final int TOTAL_LESSONS = 11; // Tổng số tiết trong ngày

    @Override
    public Response<ExamScheduleResponse> createMidtermExamSchedule(ExamScheduleRequest request) {
        try {
            // Kiểm tra dữ liệu đầu vào
            if (request.getGradeTypeId() == null || request.getClassStudentId() == null || request.getClassroomId() == null) {
                return Response.failure("ID của lớp, loại điểm và phòng học là bắt buộc");
            }

            // Lấy thông tin GradeType từ cơ sở dữ liệu
            GradeType gradeType = gradeTypeRepository.findById(request.getGradeTypeId())
                    .orElseThrow(() -> new IllegalArgumentException("Loại điểm không tồn tại"));
            String gradeTypeCode = gradeType.getCode();

            // Sử dụng startDate và endDate trực tiếp từ request
            LocalDate startDate = request.getStartDate();
            LocalDate endDate = request.getEndDate();
            if (startDate == null) {
                return Response.failure("Ngày bắt đầu là bắt buộc");
            }

            // Kiểm tra trùng MID hoặc FINAL
            List<ExamSchedule> existingSchedules = examScheduleRepository.findByClassStudentId(request.getClassStudentId());
            long midCount = existingSchedules.stream()
                    .filter(schedule -> "MID".equals(schedule.getGradeType().getCode()))
                    .count();
            long finalCount = existingSchedules.stream()
                    .filter(schedule -> "FINAL".equals(schedule.getGradeType().getCode()))
                    .count();

            if (midCount > 0 && "MID".equals(gradeTypeCode)) {
                return Response.failure("Lớp này đã có lịch thi giữa kỳ rồi");
            }
            if (finalCount > 0 && "FINAL".equals(gradeTypeCode)) {
                return Response.failure("Lớp này đã có lịch thi cuối kỳ rồi");
            }

            // Kiểm tra trùng ngày startDate giữa MID và FINAL
            boolean hasConflictingDate = existingSchedules.stream()
                    .anyMatch(schedule -> {
                        String existingGradeTypeCode = schedule.getGradeType().getCode();
                        LocalDate existingStartDate = schedule.getStartDate();
                        return ("MID".equals(gradeTypeCode) && "FINAL".equals(existingGradeTypeCode) ||
                                "FINAL".equals(gradeTypeCode) && "MID".equals(existingGradeTypeCode)) &&
                                existingStartDate.equals(startDate);
                    });
            if (hasConflictingDate) {
                return Response.failure("Ngày thi giữa kỳ không được trùng với ngày thi cuối kỳ");
            }

            // Kiểm tra phòng học đã được đăng ký để thi chưa (Trường hợp 1)
            List<ExamSchedule> conflictingExamSchedules = examScheduleRepository.findByClassroomIdAndStartDate(request.getClassroomId(), startDate);
            if (!conflictingExamSchedules.isEmpty()) {
                return Response.failure("Phòng học đã được đăng ký để thi vào ngày này");
            }

            // Kiểm tra phòng học đã được sử dụng để học chưa (Trường hợp 2)
            List<ClassStudent> successfulClassStudents = classStudentRepository.findSuccessByClassroomId(request.getClassroomId());
            Long currentClassStudentId = request.getClassStudentId();
            boolean classroomInUse = false;

            for (ClassStudent classStudent : successfulClassStudents) {
                if (!classStudent.getClassStudentId().equals(currentClassStudentId)) { // Chỉ kiểm tra lớp khác
                    TeachingScheduleRequest teachingSchedule = classStudent.getTeachingScheduleRequest();
                    if (teachingSchedule != null && teachingSchedule.getScheduleDetails() != null) {
                        for (TeachingScheduleRequest.ScheduleDetail detail : teachingSchedule.getScheduleDetails()) {
                            LocalDate detailDate = LocalDate.parse(detail.getDateTime());
//                            LocalDate detailDate = LocalDateTime.parse(detail.getDateTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME).toLocalDate();
                            if (detailDate.equals(startDate)) {
                                LocalTime startLessonTime = calculateLessonStartTime(detail.getLesson());
                                LocalTime endLessonTime = startLessonTime.plusMinutes(LESSON_DURATION_MINUTES * getLessonCount(detail.getLesson()));
                                LocalTime examStartTime = LocalTime.parse(request.getStartTime());
                                LocalTime examEndTime = LocalTime.parse(request.getEndTime());

                                if (isTimeOverlap(startLessonTime, endLessonTime, examStartTime, examEndTime)) {
                                    classroomInUse = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            if (classroomInUse) {
                return Response.failure("Phòng học đã được sử dụng để học vào thời gian này");
            }

            // Kiểm tra lịch học trong AttendanceSession
            List<AttendanceSession> attendanceSessions = attendanceSessionRepository.findByScheduledDateAndClassroomId(startDate, request.getClassroomId());
            for (AttendanceSession session : attendanceSessions) {
                Long sessionClassStudentId = session.getClassStudent().getClassStudentId();
                if (!sessionClassStudentId.equals(currentClassStudentId)) { // Chỉ kiểm tra lớp khác
                    TeachingScheduleRequest teachingSchedule = session.getClassStudent().getTeachingScheduleRequest();
                    if (teachingSchedule != null && teachingSchedule.getScheduleDetails() != null) {
                        for (TeachingScheduleRequest.ScheduleDetail detail : teachingSchedule.getScheduleDetails()) {
                            LocalDate detailDate = LocalDate.parse(detail.getDateTime());
//                            LocalDate detailDate = LocalDateTime.parse(detail.getDateTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME).toLocalDate();
                            if (detailDate.equals(startDate)) {
                                LocalTime startLessonTime = calculateLessonStartTime(detail.getLesson());
                                LocalTime endLessonTime = startLessonTime.plusMinutes(LESSON_DURATION_MINUTES * getLessonCount(detail.getLesson()));
                                LocalTime examStartTime = LocalTime.parse(request.getStartTime());
                                LocalTime examEndTime = LocalTime.parse(request.getEndTime());

                                if (isTimeOverlap(startLessonTime, endLessonTime, examStartTime, examEndTime)) {
                                    return Response.failure("Phòng học đã được sử dụng để học vào thời gian này");
                                }
                            }
                        }
                    }
                }
            }

            // Tạo các thực thể liên quan
            ClassStudent classStudent = new ClassStudent();
            classStudent.setClassStudentId(request.getClassStudentId());
            Classroom classroom = new Classroom();
            classroom.setClassroomId(request.getClassroomId());

            // Sử dụng GradeType đã lấy từ cơ sở dữ liệu
            ExamSchedule examSchedule = new ExamSchedule(
                    classStudent,
                    classroom,
                    startDate,
                    endDate,
                    request.getStartTime(),
                    request.getEndTime(),
                    request.getExamForm(),
                    gradeType
            );

            ExamSchedule savedSchedule = examScheduleRepository.save(examSchedule);

            // Tạo response
            ExamScheduleResponse response = new ExamScheduleResponse();
            response.setExamScheduleId(savedSchedule.getExamScheduleId());
            response.setClassStudentId(request.getClassStudentId());
            response.setClassroomId(request.getClassroomId());
            response.setStartDate(startDate);
            response.setEndDate(endDate);
            response.setStartTime(request.getStartTime());
            response.setEndTime(request.getEndTime());
            response.setExamForm(request.getExamForm());
            response.setGradeTypeId(request.getGradeTypeId());

            return Response.success("Tạo lịch thi giữa kỳ thành công", response);
        } catch (IllegalArgumentException e) {
            return Response.failure("Loại điểm không tồn tại");
        } catch (Exception e) {
            return Response.failure("Lỗi khi tạo lịch thi giữa kỳ: " + e.getMessage());
        }
    }
    // Hàm tính thời gian bắt đầu của tiết học
    private LocalTime calculateLessonStartTime(String lesson) {
        int lessonNumber = Integer.parseInt(lesson.split("-")[0]); // Lấy số tiết đầu tiên
        long totalMinutes = (lessonNumber - 1) * (LESSON_DURATION_MINUTES + BREAK_MINUTES);
        return DAY_START.plusMinutes(totalMinutes);
    }

    // Hàm đếm số tiết học
    private int getLessonCount(String lesson) {
        String[] parts = lesson.split("-");
        return Integer.parseInt(parts[1]) - Integer.parseInt(parts[0]) + 1;
    }

    // Hàm kiểm tra chồng lấn thời gian
    private boolean isTimeOverlap(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return start1.isBefore(end2) && end1.isAfter(start2);
    }
}
