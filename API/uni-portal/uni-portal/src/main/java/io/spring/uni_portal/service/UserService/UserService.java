package io.spring.uni_portal.service.UserService;

import io.spring.uni_portal.dto.User.UserProfileDTO;
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

    @Override
    public Response<UserProfileDTO> getUserProfile(HttpServletRequest request) {
        try {
            Long userId = jwtService.getUserIdFromToken(request);

            if (userId == null) {
                return Response.failure("Unauthorized");
            }

            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

            // Tạo DTO cho profile
            UserProfileDTO userProfileDTO = new UserProfileDTO(
                    user.getUserId(), user.getUserName(), user.getRole(), user.getEmail(), user.getPhoneNumber(),
                    user.getAddress(), user.getDateOfBirth(), user.getEthnicGroup(), user.getIdNumber(),
                    user.getPlaceOfBirth(), user.getPermanentResident(), user.getBank(), user.getBankAccountOwner(),
                    user.getBankAccountNumber(), user.getStatus(), null, null, null
            );

            // Lấy thêm thông tin từ Student hoặc Lecturer
            if ("student".equals(user.getRole())) {
                Student student = studentRepository.findById(userId).orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin chi tiết về sinh viên"));
                userProfileDTO.setEducationLevel(student.getEducationLevel());
            } else if ("lecturer".equals(user.getRole())) {
                Lecturer lecturer = lecturerRepository.findById(userId).orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin chi tiết về giảng viên"));
                userProfileDTO.setAcademicDegree(lecturer.getAcademicDegree());
                userProfileDTO.setPosition(lecturer.getPosition());
            }

            return Response.success("Hồ sơ người dùng đã được lấy lại thành công", userProfileDTO);
        } catch (Exception e) {
            return Response.failure("Không thể lấy lại hồ sơ người dùng: " + e.getMessage());
        }
    }
}
