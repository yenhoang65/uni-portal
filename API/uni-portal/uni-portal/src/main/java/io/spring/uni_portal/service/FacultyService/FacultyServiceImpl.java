package io.spring.uni_portal.service.FacultyService;
import io.spring.uni_portal.config.AwsS3Config;
import io.spring.uni_portal.dto.Faculty.FacultyDTO;
import io.spring.uni_portal.exception.OurException;
import io.spring.uni_portal.model.Faculty;
import io.spring.uni_portal.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class FacultyServiceImpl implements IFacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private AwsS3Config awsS3Config;

    private Long generateNextFacultyId() {
        Long maxId = facultyRepository.findMaxFacultyId();
        return maxId + 1;
    }

    private FacultyDTO convertToDTO(Faculty faculty) {
        FacultyDTO dto = new FacultyDTO();
        dto.setFacultyId(faculty.getFacultyId());
        dto.setFacultyName(faculty.getFacultyName());
        dto.setFacultyDateOfEstablishment(faculty.getFacultyDateOfEstablishment());
        dto.setFacultyEmail(faculty.getFacultyEmail());
        dto.setFacultyPhoneNumber(faculty.getFacultyPhoneNumber());
        dto.setFacultyAddress(faculty.getFacultyAddress());
        dto.setFacultyDescription(faculty.getFacultyDescription());
        dto.setFacultyLogo(faculty.getFacultyLogo());
        dto.setFacultyStatus(faculty.getFacultyStatus());
        return dto;
    }

    private Faculty convertToEntity(FacultyDTO dto) {
        Faculty faculty = new Faculty();
        faculty.setFacultyId(dto.getFacultyId());
        faculty.setFacultyName(dto.getFacultyName());
        faculty.setFacultyDateOfEstablishment(dto.getFacultyDateOfEstablishment());
        faculty.setFacultyEmail(dto.getFacultyEmail());
        faculty.setFacultyPhoneNumber(dto.getFacultyPhoneNumber());
        faculty.setFacultyAddress(dto.getFacultyAddress());
        faculty.setFacultyDescription(dto.getFacultyDescription());
        faculty.setFacultyStatus(dto.getFacultyStatus());
        if (dto.getFacultyLogoFile() != null && !dto.getFacultyLogoFile().isEmpty()) {
            String uploadedUrl = awsS3Config.saveImageToS3(dto.getFacultyLogoFile());
            faculty.setFacultyLogo(uploadedUrl);
        } else {
            faculty.setFacultyLogo(dto.getFacultyLogo());
        }

        return faculty;
    }


//    @Override
//    public FacultyDTO createFaculty(FacultyDTO facultyDTO) {
//        facultyDTO.setFacultyId(generateNextFacultyId());
//        Faculty faculty = convertToEntity(facultyDTO);
//        faculty.setFacultyId(generateNextFacultyId());
//        Faculty saved = facultyRepository.save(faculty);
//        return convertToDTO(saved);
//    }

    @Override
    public FacultyDTO createFaculty(FacultyDTO facultyDTO) {
        // Kiểm tra nếu không nhập facultyId thì báo lỗi
        if (facultyDTO.getFacultyId() == null) {
            throw new OurException("Mã khoa không được để trống!");
        }

        // Kiểm tra xem mã đã tồn tại chưa
        if (facultyRepository.existsById(facultyDTO.getFacultyId())) {
            throw new OurException("Mã khoa đã tồn tại!");
        }

        // Không cần gọi generateNextFacultyId nữa
        Faculty faculty = convertToEntity(facultyDTO);
        Faculty saved = facultyRepository.save(faculty);
        return convertToDTO(saved);
    }


    @Override
    public FacultyDTO getFacultyById(Long id) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Faculty not found with ID: " + id));
        return convertToDTO(faculty);
    }

    @Override
    public List<FacultyDTO> getAllFaculties() {
        return facultyRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FacultyDTO updateFaculty(Long id, FacultyDTO dto) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Faculty not found with ID: " + id));

        faculty.setFacultyName(dto.getFacultyName());
        faculty.setFacultyDateOfEstablishment(dto.getFacultyDateOfEstablishment());
        faculty.setFacultyEmail(dto.getFacultyEmail());
        faculty.setFacultyPhoneNumber(dto.getFacultyPhoneNumber());
        faculty.setFacultyAddress(dto.getFacultyAddress());
        faculty.setFacultyDescription(dto.getFacultyDescription());
        faculty.setFacultyStatus(dto.getFacultyStatus());
        if (dto.getFacultyLogoFile() != null && !dto.getFacultyLogoFile().isEmpty()) {
            String uploadedUrl = awsS3Config.saveImageToS3(dto.getFacultyLogoFile());
            faculty.setFacultyLogo(uploadedUrl);
        }

        return convertToDTO(facultyRepository.save(faculty));
    }

    @Override
    public List<FacultyDTO> searchFacultiesByName(String name) {
        return facultyRepository.findAll().stream()
                .filter(faculty -> faculty.getFacultyName().toLowerCase().contains(name.toLowerCase()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }
}