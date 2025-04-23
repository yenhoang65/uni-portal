package io.spring.uni_portal.service.MajorService;

import io.spring.uni_portal.dto.Major.MajorDTO;
import io.spring.uni_portal.model.Faculty;
import io.spring.uni_portal.model.Major;
import io.spring.uni_portal.repository.FacultyRepository;
import io.spring.uni_portal.repository.MajorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MajorServiceImpl implements IMajorService {

    @Autowired
    private MajorRepository majorRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    private MajorDTO toDTO(Major major) {
        MajorDTO dto = new MajorDTO();
        dto.setMajorId(major.getMajorId());
        dto.setMajorName(major.getMajorName());
        dto.setFacultyId(major.getFaculty().getFacultyId());
        dto.setFacultyName(major.getFaculty().getFacultyName());
        dto.setMajorDateOfEstablishment(major.getMajorDateOfEstablishment());
        dto.setMajorDescription(major.getMajorDescription());
        dto.setMajorStatus(major.getMajorStatus());
        return dto;
    }

    private Major toEntity(MajorDTO dto) {
        Faculty faculty = facultyRepository.findById(dto.getFacultyId())
                .orElseThrow(() -> new RuntimeException("Faculty not found with ID: " + dto.getFacultyId()));
        Major major = new Major();
        major.setMajorName(dto.getMajorName());
        major.setFaculty(faculty);
        major.setMajorDateOfEstablishment(dto.getMajorDateOfEstablishment());
        major.setMajorDescription(dto.getMajorDescription());
        major.setMajorStatus(dto.getMajorStatus());
        return major;
    }

    public MajorDTO createMajor(MajorDTO dto) {
        if (majorRepository.existsById(dto.getMajorId())) {
            throw new RuntimeException("Mã ngành học đã tồn tại. Vui lòng chọn mã khác.");
        }
        Major major = toEntity(dto);
        major.setMajorId(dto.getMajorId());
        return toDTO(majorRepository.save(major));
    }

    @Override
    public MajorDTO getMajorById(Long id) {
        Major major = majorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Major not found with ID: " + id));
        return toDTO(major);
    }

    @Override
    public List<MajorDTO> getAllMajors() {
        return majorRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MajorDTO updateMajor(Long id, MajorDTO dto) {
        Major major = majorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Major not found with ID: " + id));

        major.setMajorName(dto.getMajorName());
        major.setMajorDateOfEstablishment(dto.getMajorDateOfEstablishment());
        major.setMajorDescription(dto.getMajorDescription());

        if (!major.getFaculty().getFacultyId().equals(dto.getFacultyId())) {
            Faculty faculty = facultyRepository.findById(dto.getFacultyId())
                    .orElseThrow(() -> new RuntimeException("Faculty not found with ID: " + dto.getFacultyId()));
            major.setFaculty(faculty);
        }

        return toDTO(majorRepository.save(major));
    }

    @Override
    public List<MajorDTO> searchMajorsByName(String name) {
        return majorRepository.findAll().stream()
                .filter(major -> name == null || major.getMajorName().toLowerCase().contains(name.toLowerCase()))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMajor(Long id) {
        majorRepository.deleteById(id);
    }
}
