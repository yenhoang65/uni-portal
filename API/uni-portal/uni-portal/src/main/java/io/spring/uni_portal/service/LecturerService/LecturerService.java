package io.spring.uni_portal.service.LecturerService;

import io.spring.uni_portal.dto.Lecturer.LecturerRequestDTO;
import io.spring.uni_portal.dto.Lecturer.LecturerResponseDTO;
import io.spring.uni_portal.model.Lecturer;
import io.spring.uni_portal.model.Major;
import io.spring.uni_portal.model.User;
import io.spring.uni_portal.repository.LecturerRepository;
import io.spring.uni_portal.repository.MajorRepository;
import io.spring.uni_portal.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LecturerService implements ILecturerService {

    private final UserRepository userRepository;
    private final LecturerRepository lecturerRepository;
    private final MajorRepository majorRepository;
    private final PasswordEncoder passwordEncoder;

    public LecturerService(UserRepository userRepository,
                           LecturerRepository lecturerRepository,
                           MajorRepository majorRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.lecturerRepository = lecturerRepository;
        this.majorRepository = majorRepository;
        this.passwordEncoder = passwordEncoder;
    }

@Override
public Lecturer createLecturer(LecturerRequestDTO dto) {
    Major major = majorRepository.findById(dto.getMajorId())
            .orElseThrow(() -> new RuntimeException("Ngành học không tồn tại"));

    Long nextUserId = generateCustomUserId(major);

    if (userRepository.existsById(nextUserId)) {
        throw new RuntimeException("UserId đã tồn tại: " + nextUserId);
    }

    if (userRepository.existsByEmail(dto.getEmail())) {
        throw new RuntimeException("Email đã được sử dụng");
    }

    if (userRepository.existsByPhoneNumber(dto.getPhoneNumber())) {
        throw new RuntimeException("Số điện thoại đã được sử dụng");
    }

    User user = new User();
    user.setUserId(nextUserId);
    user.setUserName(dto.getUserName());
    user.setPassword(passwordEncoder.encode(String.valueOf(nextUserId)));
    user.setGender(dto.getGender());
    user.setPhoneNumber(dto.getPhoneNumber());
    user.setAddress(dto.getAddress());
    user.setEthnicGroup(dto.getEthnicGroup());
    user.setDateOfBirth(dto.getDateOfBirth());
    user.setReligion(dto.getReligion());
    user.setIdNumber(dto.getIdNumber());
    user.setEmail(dto.getEmail());
    user.setPlaceOfBirth(dto.getPlaceOfBirth());
    user.setPermanentResident(dto.getPermanentResident());
    user.setBank(dto.getBank());
    user.setBankAccountOwner(dto.getBankAccountOwner());
    user.setBankAccountNumber(dto.getBankAccountNumber());
    user.setAdmissionDate(dto.getAdmissionDate());
    user.setRole("lecturer");

    Lecturer lecturer = new Lecturer();
    lecturer.setUser(user); // chỉ cần gán, không save riêng
    lecturer.setAcademicDegree(dto.getAcademicDegree());
    lecturer.setGraduatedFrom(dto.getGraduatedFrom());
    lecturer.setPosition(dto.getPosition());
    lecturer.setMajor(major);

    // save cả lecturer + user 1 lần
    return lecturerRepository.save(lecturer);
}


    private Long generateCustomUserId(Major major) {
        String facultyIdStr = String.valueOf(major.getFaculty().getFacultyId());
        String majorIdStr = String.valueOf(major.getMajorId());

        String facultyPrefix = facultyIdStr.substring(0, 2);
        String majorSuffix = majorIdStr.length() >= 2
                ? majorIdStr.substring(majorIdStr.length() - 2)
                : String.format("%02d", Integer.parseInt(majorIdStr));

        String prefix = facultyPrefix + majorSuffix;
        String pattern = prefix + "%";

        List<User> users = userRepository.findByUserIdLikeCustom(pattern);

        int nextSequence = 1;
        if (!users.isEmpty()) {
            String lastUserIdStr = String.valueOf(users.get(0).getUserId());
            nextSequence = Integer.parseInt(lastUserIdStr.substring(4)) + 1;
        }

        String finalId = prefix + String.format("%04d", nextSequence);
        return Long.parseLong(finalId);
    }




    private Long getNextUserId() {
        return userRepository.findTopByOrderByUserIdDesc()
                .map(user -> user.getUserId() + 1)
                .orElse(10000000L);
    }

    @Override
    public List<LecturerResponseDTO> getAllLecturers() {
        return lecturerRepository.findAll().stream()
                .map(LecturerResponseDTO::new)
                .toList();
    }

    @Override
    public Lecturer getLecturerById(Long userId) {
        return lecturerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giảng viên với ID: " + userId));
    }

    @Override
    public Lecturer updateLecturer(Long userId, LecturerRequestDTO dto) {
        Lecturer lecturer = lecturerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giảng viên để cập nhật"));

        User user = lecturer.getUser();
        user.setUserName(dto.getUserName());
        user.setGender(dto.getGender());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setAddress(dto.getAddress());
        user.setEthnicGroup(dto.getEthnicGroup());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setReligion(dto.getReligion());
        user.setIdNumber(dto.getIdNumber());
        user.setEmail(dto.getEmail());
        user.setPlaceOfBirth(dto.getPlaceOfBirth());
        user.setPermanentResident(dto.getPermanentResident());
        user.setBank(dto.getBank());
        user.setBankAccountOwner(dto.getBankAccountOwner());
        user.setBankAccountNumber(dto.getBankAccountNumber());
        user.setAdmissionDate(dto.getAdmissionDate());

        Major major = majorRepository.findById(dto.getMajorId())
                .orElseThrow(() -> new RuntimeException("Ngành học không tồn tại"));

        lecturer.setAcademicDegree(dto.getAcademicDegree());
        lecturer.setGraduatedFrom(dto.getGraduatedFrom());
        lecturer.setPosition(dto.getPosition());
        lecturer.setMajor(major);

        userRepository.save(user);
        return lecturerRepository.save(lecturer);
    }

    @Override
    public List<LecturerResponseDTO> importFromExcel(MultipartFile file) throws Exception {
        List<LecturerResponseDTO> importedLecturers = new ArrayList<>();

        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                LecturerRequestDTO dto = new LecturerRequestDTO();
                dto.setUserName(getCellValue(row, 0));
                dto.setGender(getCellValue(row, 1));
                dto.setPhoneNumber(getCellValue(row, 2));
                dto.setDateOfBirth(LocalDate.parse(getCellValue(row, 3)));
                dto.setAdmissionDate(LocalDate.parse(getCellValue(row, 4)));
                dto.setAcademicDegree(getCellValue(row, 5));
                dto.setGraduatedFrom(getCellValue(row, 6));
                dto.setPosition(getCellValue(row, 7));
                dto.setMajorId((long) Double.parseDouble(getCellValue(row, 8)));

                Lecturer lecturer = createLecturer(dto);
                importedLecturers.add(new LecturerResponseDTO(lecturer));
            }
        }

        return importedLecturers;
    }

    private String getCellValue(Row row, int index) {
        Cell cell = row.getCell(index, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            return cell.getLocalDateTimeCellValue().toLocalDate().toString(); // yyyy-MM-dd
        }
        return cell.toString().trim();
    }

    @Override
    public void deleteLecturer(Long userId) {
        Lecturer lecturer = lecturerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giảng viên để xoá"));

        lecturerRepository.delete(lecturer);
        userRepository.deleteById(userId);
    }
}
