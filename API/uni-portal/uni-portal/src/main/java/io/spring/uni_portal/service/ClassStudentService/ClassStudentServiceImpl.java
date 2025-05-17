package io.spring.uni_portal.service.ClassStudentService;

import io.spring.uni_portal.dto.ClassStudent.ClassStudentDTO;
import io.spring.uni_portal.dto.ClassStudent.ClassStudentResponseDTO;
import io.spring.uni_portal.dto.ClassStudent.OpenedClassFullDTO;
import io.spring.uni_portal.model.ClassStudent;
import io.spring.uni_portal.model.User;
import io.spring.uni_portal.repository.ClassStudentRepository;
import io.spring.uni_portal.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassStudentServiceImpl implements IClassStudentService{
    @Autowired
    private ClassStudentRepository classStudentRepository;

    @Override
    public Response<Page<ClassStudentDTO>> getClassStudentsWithSearch(String searchValue, Pageable pageable) {
        // Lấy user đang đăng nhập từ SecurityContextHolder
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        // Kiểm tra nếu người dùng có role là admin
        boolean isAdmin = currentUser.getRole().contains("admin");

        Page<ClassStudent> classStudentPage;

        if (isAdmin) {
            // Nếu là admin, trả về tất cả bản ghi với phân trang và tìm kiếm
            classStudentPage = classStudentRepository.findAllBySearchValue(searchValue, pageable);
        } else {
            // Nếu không phải admin, chỉ trả về bản ghi của student hiện tại với phân trang và tìm kiếm
            Long userId = currentUser.getUserId();
            classStudentPage = classStudentRepository.findClassStudentsForStudent(userId, searchValue, pageable);
        }

        // Chuyển đổi từ Page<ClassStudent> thành Page<ClassStudentDTO>
        Page<ClassStudentDTO> responsePage = classStudentPage.map(this::convertToDTO);

        return Response.success("Danh sách ClassStudent với phân trang", responsePage);
    }



    // Phương thức chuyển đổi từ entity sang DTO
    private ClassStudentDTO convertToDTO(ClassStudent classStudent) {
        ClassStudentDTO dto = new ClassStudentDTO();
        dto.setClassStudentId(classStudent.getClassStudentId());
        dto.setStatus(classStudent.getStatus());
        dto.setScheduleId(classStudent.getTeachingScheduleRequest() != null
                ? classStudent.getTeachingScheduleRequest().getScheduleId()
                : null);
        dto.setCreatedAt(classStudent.getCreatedAt());
        dto.setEndDate(classStudent.getEndDate());
        dto.setMaterials(classStudent.getMaterials());
        // Giả định có cột student_id và class_id trong bảng class_student
        dto.setStudentId(null);
        dto.setClassId(null);
        return dto;
    }

    @Override
    public List<OpenedClassFullDTO> getOpenedClassesBySubject(Long subjectId) {
        return classStudentRepository.findFullOpenedClassesBySubjectId(subjectId)
                .stream()
                .map(OpenedClassFullDTO::new)
                .collect(Collectors.toList());
    }
//
//    @Override
//    public Page<ClassStudentResponseDTO> getRegisteredClasses(Long userId, String status, String searchValue, Pageable pageable) {
//        Page<ClassStudent> page = classStudentRepository.findByFilters(userId, status, searchValue, pageable);
//        return page.map(ClassStudentResponseDTO::new);
//    }
}
