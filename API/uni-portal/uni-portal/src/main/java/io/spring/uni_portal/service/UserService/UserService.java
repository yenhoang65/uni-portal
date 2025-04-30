package io.spring.uni_portal.service.UserService;

import io.spring.uni_portal.dto.User.UpdatePasswordDTO;
import io.spring.uni_portal.dto.User.UserProfileDTO;
import io.spring.uni_portal.dto.User.UserProfileUpdateDTO;
import io.spring.uni_portal.model.Lecturer;
import io.spring.uni_portal.model.Student;
import io.spring.uni_portal.model.User;
import io.spring.uni_portal.repository.LecturerRepository;
import io.spring.uni_portal.repository.StudentRepository;
import io.spring.uni_portal.repository.UserRepository;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Response<UserProfileDTO> getUserProfile(HttpServletRequest request) {
        try {
            Long userId = jwtService.getUserIdFromToken(request);

            if (userId == null) {
                return Response.failure("Unauthorized");
            }

            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

            // Tạo DTO cho profile
            UserProfileDTO userProfileDTO = new UserProfileDTO(
                    user.getUserId(), user.getUserName(), user.getRole(), user.getEmail(), user.getPhoneNumber(),
                    user.getAddress(), user.getReligion(), user.getDateOfBirth(), user.getAdmissionDate(),
                    user.getEthnicGroup(), user.getIdNumber(), user.getPlaceOfBirth(), user.getPermanentResident(),
                    user.getBank(), user.getBankAccountOwner(), user.getBankAccountNumber(), user.getStatus(),
                    null, null, null, null, null // Phần thêm vào cho sinh viên và giảng viên
            );

            // Lấy thêm thông tin từ Student hoặc Lecturer
            if ("student".equals(user.getRole())) {
                Student student = studentRepository.findById(userId).orElseThrow(() -> new RuntimeException("Student details not found"));
                userProfileDTO.setEducationLevel(student.getEducationLevel());
            } else if ("lecturer".equals(user.getRole())) {
                Lecturer lecturer = lecturerRepository.findById(userId).orElseThrow(() -> new RuntimeException("Lecturer details not found"));
                userProfileDTO.setAcademicDegree(lecturer.getAcademicDegree());
                userProfileDTO.setPosition(lecturer.getPosition());
                userProfileDTO.setMajorName(lecturer.getMajor().getMajorName());
                userProfileDTO.setFacultyName(lecturer.getMajor().getFaculty().getFacultyName());
            }

            return Response.success("User profile fetched successfully", userProfileDTO);
        } catch (Exception e) {
            return Response.failure("Failed to fetch user profile: " + e.getMessage());
        }
    }

    @Override
    public Response<UserProfileUpdateDTO> updateUserProfile(HttpServletRequest request, UserProfileUpdateDTO userProfileUpdateDTO) {
        try {
            // Lấy userId từ token
            Long userId = jwtService.getUserIdFromToken(request);

            if (userId == null) {
                return Response.failure("Unauthorized");
            }

            // Lấy người dùng từ cơ sở dữ liệu
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

            // Cập nhật thông tin người dùng trong bảng User
            if (userProfileUpdateDTO.getUserName() != null) user.setUserName(userProfileUpdateDTO.getUserName());
            if (userProfileUpdateDTO.getEmail() != null) user.setEmail(userProfileUpdateDTO.getEmail());
            if (userProfileUpdateDTO.getPhoneNumber() != null) user.setPhoneNumber(userProfileUpdateDTO.getPhoneNumber());
            if (userProfileUpdateDTO.getAddress() != null) user.setAddress(userProfileUpdateDTO.getAddress());
            if (userProfileUpdateDTO.getReligion() != null) user.setReligion(userProfileUpdateDTO.getReligion());
            if (userProfileUpdateDTO.getDateOfBirth() != null) user.setDateOfBirth(userProfileUpdateDTO.getDateOfBirth());
            if (userProfileUpdateDTO.getEthnicGroup() != null) user.setEthnicGroup(userProfileUpdateDTO.getEthnicGroup());
            if (userProfileUpdateDTO.getIdNumber() != null) user.setIdNumber(userProfileUpdateDTO.getIdNumber());
            if (userProfileUpdateDTO.getPlaceOfBirth() != null) user.setPlaceOfBirth(userProfileUpdateDTO.getPlaceOfBirth());
            if (userProfileUpdateDTO.getPermanentResident() != null) user.setPermanentResident(userProfileUpdateDTO.getPermanentResident());
            if (userProfileUpdateDTO.getBank() != null) user.setBank(userProfileUpdateDTO.getBank());
            if (userProfileUpdateDTO.getBankAccountOwner() != null) user.setBankAccountOwner(userProfileUpdateDTO.getBankAccountOwner());
            if (userProfileUpdateDTO.getBankAccountNumber() != null) user.setBankAccountNumber(userProfileUpdateDTO.getBankAccountNumber());
            if (userProfileUpdateDTO.getStatus() != null) user.setStatus(userProfileUpdateDTO.getStatus());

            userRepository.save(user);

            // Cập nhật thông tin Student nếu người dùng là sinh viên
            if ("student".equals(user.getRole())) {
                Student student = studentRepository.findById(userId).orElseThrow(() -> new RuntimeException("Student details not found"));
                if (userProfileUpdateDTO.getEducationLevel() != null) student.setEducationLevel(userProfileUpdateDTO.getEducationLevel());
                studentRepository.save(student);
            }

            // Cập nhật thông tin Lecturer nếu người dùng là giảng viên
            if ("lecturer".equals(user.getRole())) {
                Lecturer lecturer = lecturerRepository.findById(userId).orElseThrow(() -> new RuntimeException("Lecturer details not found"));
                if (userProfileUpdateDTO.getAcademicDegree() != null) lecturer.setAcademicDegree(userProfileUpdateDTO.getAcademicDegree());
                if (userProfileUpdateDTO.getPosition() != null) lecturer.setPosition(userProfileUpdateDTO.getPosition());
                // Cập nhật thông tin ngành học và khoa nếu có
                if (userProfileUpdateDTO.getMajorName() != null) lecturer.getMajor().setMajorName(userProfileUpdateDTO.getMajorName());
                if (userProfileUpdateDTO.getFacultyName() != null) lecturer.getMajor().getFaculty().setFacultyName(userProfileUpdateDTO.getFacultyName());
                lecturerRepository.save(lecturer);
            }

            // Trả về thông tin đã cập nhật
            return Response.success("User profile updated successfully", userProfileUpdateDTO);
        } catch (Exception e) {
            return Response.failure("Failed to update user profile: " + e.getMessage());
        }
    }

    @Override
    public Response<String> updatePassword(HttpServletRequest request, UpdatePasswordDTO updatePasswordDTO) {
        try {
            Long userId = jwtService.getUserIdFromToken(request);

            if (userId == null) {
                return Response.failure("Unauthorized");
            }

            // Lấy người dùng từ cơ sở dữ liệu
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

            // Kiểm tra mật khẩu hiện tại
            if (!passwordEncoder.matches(updatePasswordDTO.getPassword(), user.getPassword())) {
                return Response.failure("Current password is incorrect");
            }

            // Cập nhật mật khẩu mới
            user.setPassword(passwordEncoder.encode(updatePasswordDTO.getNewPassword()));

            // Lưu lại mật khẩu mới vào cơ sở dữ liệu
            userRepository.save(user);

            return Response.success("Password updated successfully", null);
        } catch (Exception e) {
            return Response.failure("Failed to update password: " + e.getMessage());
        }
    }
}
