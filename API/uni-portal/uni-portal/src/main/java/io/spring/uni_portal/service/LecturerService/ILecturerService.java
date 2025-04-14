package io.spring.uni_portal.service.LecturerService;


import io.spring.uni_portal.dto.Lecturer.LecturerRequestDTO;
import io.spring.uni_portal.dto.Lecturer.LecturerResponseDTO;
import io.spring.uni_portal.model.Lecturer;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ILecturerService {
    Lecturer createLecturer(LecturerRequestDTO dto);
    List<LecturerResponseDTO> getAllLecturers();
    Lecturer updateLecturer(Long userId, LecturerRequestDTO dto);
    Lecturer getLecturerById(Long userId);
    void deleteLecturer(Long userId);
    List<LecturerResponseDTO> importFromExcel(MultipartFile file) throws Exception;

}
