package io.spring.uni_portal.service.MajorService;

import io.spring.uni_portal.dto.Major.MajorDTO;

import java.util.List;

public interface IMajorService {
    MajorDTO createMajor(MajorDTO dto);
    MajorDTO getMajorById(Long id);
    List<MajorDTO> getAllMajors();
    MajorDTO updateMajor(Long id, MajorDTO dto);
    void deleteMajor(Long id);
    List<MajorDTO> searchMajorsByName(String name);
}
