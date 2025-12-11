package io.spring.uni_portal.controller;

import io.spring.uni_portal.dto.TeachingSlide.ClassSessionWithSlidesResponseDTO;
import io.spring.uni_portal.dto.TeachingSlide.TeachingSlideRequestDTO;
import io.spring.uni_portal.dto.TeachingSlide.TeachingSlideResponseDTO;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.TeachingSlideService.ITeachingSlideService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/slides")
public class TeachingSlideController {

    private final ITeachingSlideService slideService;

    public TeachingSlideController(ITeachingSlideService slideService) {
        this.slideService = slideService;
    }

    @PostMapping
    public Response<TeachingSlideResponseDTO> create(@RequestBody TeachingSlideRequestDTO dto) {
        return slideService.createSlide(dto);
    }

    @PutMapping("/{id}")
    public Response<TeachingSlideResponseDTO> update(@PathVariable Long id, @RequestBody TeachingSlideRequestDTO dto) {
        return slideService.updateSlide(id, dto);
    }

    @DeleteMapping("/{id}")
    public Response<Void> delete(@PathVariable Long id) {
        return slideService.deleteSlide(id);
    }

    @GetMapping("/{id}")
    public Response<TeachingSlideResponseDTO> get(@PathVariable Long id) {
        return slideService.getSlide(id);
    }

    @GetMapping("/class-student/full/{classStudentId}")
    public Response<ClassSessionWithSlidesResponseDTO> getSlidesAndClassInfo(@PathVariable Long classStudentId) {
        return slideService.getSlidesWithClassInfo(classStudentId);
    }



}
