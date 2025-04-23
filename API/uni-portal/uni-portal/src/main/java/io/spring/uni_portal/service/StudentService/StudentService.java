package io.spring.uni_portal.service.StudentService;

import io.spring.uni_portal.dto.Student.StudentRequestDTO;
import io.spring.uni_portal.dto.Student.StudentResponseDTO;
import io.spring.uni_portal.model.*;
import io.spring.uni_portal.model.Class;
import io.spring.uni_portal.repository.ClassRepository;
import io.spring.uni_portal.repository.SpecializationRepository;
import io.spring.uni_portal.repository.StudentRepository;
import io.spring.uni_portal.repository.UserRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class StudentService implements IStudentService {

    @Autowired
    private UserRepository userRepository;
    @Autowired private StudentRepository studentRepository;
    @Autowired private SpecializationRepository specializationRepository;
    @Autowired private ClassRepository classRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public Student createStudent(StudentRequestDTO dto) {
        // Lấy class và specialization
        Class classEntity = classRepository.findById(dto.getClassId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp"));

        Specialization specialization = specializationRepository.findById(dto.getSpecializationId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyên ngành"));

        Major major = specialization.getMajor();
        Integer schoolYear = classEntity.getSchoolYear().intValue();

        Long userId = generateCustomStudentUserId(major.getMajorId(), classEntity.getClassId(), schoolYear);

        if (userRepository.existsById(userId)) {
            throw new RuntimeException("UserId đã tồn tại: " + userId);
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email đã được sử dụng");
        }

        if (userRepository.existsByPhoneNumber(dto.getPhoneNumber())) {
            throw new RuntimeException("Số điện thoại đã được sử dụng");
        }

        // Tạo User
        User user = new User();
        user.setUserId(userId);
        user.setUserName(dto.getUserName());
        user.setPassword(passwordEncoder.encode(String.valueOf(userId)));
        user.setGender(dto.getGender());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setAddress(dto.getAddress());
        user.setEthnicGroup(dto.getEthnicGroup());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setReligion(dto.getReligion());
        user.setIdNumber(dto.getIdNumber());
        user.setEmail(dto.getEmail());
        user.setStatus(dto.getStatus());
        user.setPlaceOfBirth(dto.getPlaceOfBirth());
        user.setPermanentResident(dto.getPermanentResident());
        user.setBank(dto.getBank());
        user.setBankAccountOwner(dto.getBankAccountOwner());
        user.setBankAccountNumber(dto.getBankAccountNumber());
        user.setAdmissionDate(dto.getAdmissionDate());
        user.setRole("student");

        // Tạo Student
        Student student = new Student();
        student.setUser(user);
        student.setEducationLevel(dto.getEducationLevel());
        student.setAdmissionDate(dto.getAdmissionDate());
        student.setTypeOfTraining(dto.getTypeOfTraining());
        student.setSpecialization(specialization);
        student.setClassEntity(classEntity);

        return studentRepository.save(student);
    }


    private Long generateUserId() {
        return userRepository.findTopByOrderByUserIdDesc()
                .map(u -> u.getUserId() + 1)
                .orElse(20240000L);
    }

    @Override
    public Page<StudentResponseDTO> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable)
                .map(StudentResponseDTO::new);
    }

    @Override
    public StudentResponseDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));
        return new StudentResponseDTO(student);
    }

    @Override
    public Student updateStudent(Long id, StudentRequestDTO dto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));

        User user = student.getUser();
        user.setUserName(dto.getUserName());
        user.setGender(dto.getGender());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setAddress(dto.getAddress());
        user.setEthnicGroup(dto.getEthnicGroup());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setReligion(dto.getReligion());
        user.setIdNumber(dto.getIdNumber());
        user.setEmail(dto.getEmail());
        user.setStatus(dto.getStatus());
        user.setPlaceOfBirth(dto.getPlaceOfBirth());
        user.setPermanentResident(dto.getPermanentResident());
        user.setBank(dto.getBank());
        user.setBankAccountOwner(dto.getBankAccountOwner());
        user.setBankAccountNumber(dto.getBankAccountNumber());
        user.setAdmissionDate(dto.getAdmissionDate());

        student.setEducationLevel(dto.getEducationLevel());
        student.setAdmissionDate(dto.getAdmissionDate());
        student.setTypeOfTraining(dto.getTypeOfTraining());

        Specialization specialization = specializationRepository.findById(dto.getSpecializationId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyên ngành"));
        student.setSpecialization(specialization);

        if (dto.getClassId() != null) {
            Class classEntity = classRepository.findById(dto.getClassId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp"));
            student.setClassEntity(classEntity);
        }

        userRepository.save(user);
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
        userRepository.deleteById(id);
    }

    @Override
    public List<StudentResponseDTO> importFromExcel(MultipartFile file) throws Exception {
        List<StudentResponseDTO> result = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                StudentRequestDTO dto = new StudentRequestDTO();
                dto.setUserName(getCell(row, 0));
                dto.setGender(getCell(row, 1));
                dto.setPhoneNumber(getCell(row, 2));
                dto.setDateOfBirth(LocalDate.parse(getCell(row, 3)));
                dto.setAdmissionDate(LocalDate.parse(getCell(row, 4)));
                dto.setEducationLevel(getCell(row, 5));
                dto.setTypeOfTraining(getCell(row, 6));
                dto.setSpecializationId((long) Double.parseDouble(getCell(row, 7)));
                dto.setClassId((long) Double.parseDouble(getCell(row, 8)));
                Student student = createStudent(dto);
                result.add(new StudentResponseDTO(student));
            }
        }
        return result;
    }

    private String getCell(Row row, int i) {
        Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        return cell.toString().trim();
    }

    @Override
    public List<StudentResponseDTO> searchByName(String name) {
        return studentRepository.findAll().stream()
                .filter(student -> student.getUser().getUserName().toLowerCase().contains(name.toLowerCase()))
                .map(StudentResponseDTO::new)
                .toList();
    }

    @Override
    public Page<StudentResponseDTO> getStudentsWithSearch(String keyword, Pageable pageable) {
        // Nếu từ khoá null hoặc rỗng thì lấy tất cả sinh viên phân trang
        if (keyword == null || keyword.trim().isEmpty()) {
            return studentRepository.findAll(pageable)
                    .map(StudentResponseDTO::new);
        }

        // Nếu có từ khoá thì tìm kiếm theo keyword (userId hoặc userName)
        return studentRepository
                .searchByKeyword(keyword.trim(), pageable)
                .map(StudentResponseDTO::new);
    }


    @Override
    public List<StudentResponseDTO> filterByClass(Long classId) {
        return studentRepository.findAll().stream()
                .filter(student -> student.getClassEntity() != null &&
                        student.getClassEntity().getClassId().equals(classId))
                .map(StudentResponseDTO::new)
                .toList();
    }

    private Long generateCustomStudentUserId(Long majorId, Long classId, Integer schoolYear) {
        String mm = String.format("%02d", Integer.parseInt(majorId.toString().substring(0, 2)));
        String cc = String.format("%02d", Integer.parseInt(classId.toString().substring(classId.toString().length() - 2)));
        String yy = String.format("%02d", schoolYear % 100);

        String prefix = mm + cc + yy; // ví dụ: "101225"

        List<User> existing = userRepository.findUserIdLikeCustom(prefix);

        int nextSeq = 1;
        if (!existing.isEmpty()) {
            // Lấy số lớn nhất và +1
            existing.sort(Comparator.comparing(User::getUserId).reversed());
            String lastUserIdStr = String.valueOf(existing.get(0).getUserId());
            String lastSeqStr = lastUserIdStr.substring(6); // lấy 2 số cuối
            nextSeq = Integer.parseInt(lastSeqStr) + 1;
        }

        String userIdStr = prefix + String.format("%02d", nextSeq); // full 8 số
        return Long.parseLong(userIdStr);
    }

    @Override
    public ByteArrayInputStream exportToExcelByClass(Long classId) {
        List<Student> students = studentRepository.findByClassEntity_ClassId(classId);

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("DanhSachSinhVien");

            // Tạo dòng tiêu đề
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Mã sinh viên");
            header.createCell(1).setCellValue("Họ tên");
            header.createCell(2).setCellValue("Giới tính");
            header.createCell(3).setCellValue("Ngày sinh");
            header.createCell(4).setCellValue("Mã lớp");

            int rowNum = 1;
            for (Student s : students) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(s.getUser().getUserId());
                row.createCell(1).setCellValue(s.getUser().getUserName());
                row.createCell(2).setCellValue(s.getUser().getGender());
                row.createCell(3).setCellValue(s.getUser().getDateOfBirth().toString());
                row.createCell(4).setCellValue(s.getClassEntity().getClassId());
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi export danh sách sinh viên", e);
        }
    }

}