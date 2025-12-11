package io.spring.uni_portal.service.TeachingSlideService;

import io.spring.uni_portal.dto.TeachingSlide.ClassSessionWithSlidesResponseDTO;
import io.spring.uni_portal.dto.TeachingSlide.TeachingSlideRequestDTO;
import io.spring.uni_portal.dto.TeachingSlide.TeachingSlideResponseDTO;
import io.spring.uni_portal.response.Response;

import java.util.List;

public interface ITeachingSlideService {
    Response<TeachingSlideResponseDTO> createSlide(TeachingSlideRequestDTO dto);
    Response<TeachingSlideResponseDTO> updateSlide(Long id, TeachingSlideRequestDTO dto);
    Response<Void> deleteSlide(Long id);
    Response<TeachingSlideResponseDTO> getSlide(Long id);

    Response<ClassSessionWithSlidesResponseDTO> getSlidesWithClassInfo(Long classStudentId);
}
