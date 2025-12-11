package io.spring.uni_portal.service.TeachingSlideService;

import io.spring.uni_portal.dto.TeachingSlide.*;
import io.spring.uni_portal.model.*;
import io.spring.uni_portal.repository.AttendanceSessionRepository;
import io.spring.uni_portal.repository.ClassStudentRepository;
import io.spring.uni_portal.repository.TeachingSlideRepository;
import io.spring.uni_portal.response.Response;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeachingSlideServiceImpl implements ITeachingSlideService {

    private final TeachingSlideRepository slideRepo;
    private final AttendanceSessionRepository sessionRepo;
    private final ClassStudentRepository classStudentRepository;
    private final AttendanceSessionRepository sessionRepository;

    public TeachingSlideServiceImpl(
            TeachingSlideRepository slideRepo,
            AttendanceSessionRepository sessionRepo,
            ClassStudentRepository classStudentRepository,
            AttendanceSessionRepository sessionRepository) {
        this.slideRepo = slideRepo;
        this.sessionRepo = sessionRepo;
        this.classStudentRepository = classStudentRepository;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Response<TeachingSlideResponseDTO> createSlide(TeachingSlideRequestDTO dto) {
        AttendanceSession session = sessionRepo.findById(dto.getSessionId())
                .orElseThrow(() -> new EntityNotFoundException("Session not found"));

        Optional<TeachingSlide> existing = slideRepo.findBySession_SessionId(dto.getSessionId());
        if (existing.isPresent()) {
            return Response.failure("Buổi học này đã có slide. Không thể thêm nhiều hơn một slide.");
        }

        TeachingSlide slide = new TeachingSlide();
        slide.setTitle(dto.getTitle());
        slide.setFileUrl(dto.getFileUrl());
        slide.setSession(session);

        TeachingSlide saved = slideRepo.save(slide);
        return Response.success("Thêm slide thành công", toDTO(saved));
    }

    @Override
    public Response<TeachingSlideResponseDTO> updateSlide(Long id, TeachingSlideRequestDTO dto) {
        TeachingSlide slide = slideRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Slide not found"));

        slide.setTitle(dto.getTitle());
        slide.setFileUrl(dto.getFileUrl());

        TeachingSlide updated = slideRepo.save(slide);
        return Response.success("Cập nhật slide thành công", toDTO(updated));
    }

    @Override
    public Response<Void> deleteSlide(Long id) {
        if (!slideRepo.existsById(id)) {
            return Response.failure("Không tìm thấy slide để xoá");
        }
        slideRepo.deleteById(id);
        return Response.success("Xoá slide thành công", null);
    }

    @Override
    public Response<TeachingSlideResponseDTO> getSlide(Long id) {
        TeachingSlide slide = slideRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Slide not found"));
        return Response.success("Lấy slide thành công", toDTO(slide));
    }

    @Override
    public Response<ClassSessionWithSlidesResponseDTO> getSlidesWithClassInfo(Long classStudentId) {
        ClassStudent cs = classStudentRepository.findById(classStudentId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy classStudent"));

        if (!"success".equalsIgnoreCase(cs.getStatus())) {
            return Response.failure("Lớp chưa được xác nhận thành công");
        }

        TeachingAssignment assignment = cs.getTeachingScheduleRequest().getAssignment();
        TermClass termClass = assignment.getTermClass();
        Subject subject = assignment.getSubject();
        Lecturer lecturer = assignment.getLecturer();

        ClassInfoDTO classInfo = new ClassInfoDTO(
                cs.getClassStudentId(),
                subject.getSubjectName(),
                lecturer.getUser().getUserName(),
                termClass.getSchoolyears(),
                termClass.getSemester(),
                termClass.getClassname()
        );


        List<AttendanceSessionWithSlideResponseDTO> sessions = sessionRepository
                .findSessionsWithSlideByClassStudentId(classStudentId);

        ClassSessionWithSlidesResponseDTO response = new ClassSessionWithSlidesResponseDTO(classInfo, sessions);
        return Response.success("Thông tin lớp và danh sách buổi học", response);
    }

    private TeachingSlideResponseDTO toDTO(TeachingSlide slide) {
        return new TeachingSlideResponseDTO(
                slide.getTeachingSlideId(),
                slide.getTitle(),
                slide.getFileUrl(),
                slide.getSession().getSessionId()
        );
    }
}
