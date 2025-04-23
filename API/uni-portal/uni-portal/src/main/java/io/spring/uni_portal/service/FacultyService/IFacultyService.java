package io.spring.uni_portal.service.FacultyService;


import io.spring.uni_portal.dto.Faculty.FacultyDTO;

import java.util.List;

public interface IFacultyService {
    FacultyDTO createFaculty(FacultyDTO facultyDTO);
    FacultyDTO getFacultyById(Long id);
    List<FacultyDTO> getAllFaculties();
    FacultyDTO updateFaculty(Long id, FacultyDTO facultyDTO);
    void deleteFaculty(Long id);
    List<FacultyDTO> searchFacultiesByName(String name);
}
