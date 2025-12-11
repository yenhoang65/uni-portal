package io.spring.uni_portal.service.ClassSubjectStudentService;

import io.spring.uni_portal.dto.ClassStudent.ClassStudentRequestDTO;
import io.spring.uni_portal.dto.ClassSubjectStudent.ClassSubjectStudentRequestDTO;
import io.spring.uni_portal.model.User;
import io.spring.uni_portal.repository.ClassSubjectStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassSubjectStudentService implements IClassSubjectStudentService {

    @Autowired
    private ClassSubjectStudentRepository repository;

    @Override
    public List<ClassSubjectStudentRequestDTO> getSuccessfulRegisteredClasses(String semester, String schoolYear) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            throw new RuntimeException("Người dùng chưa xác thực hoặc token không hợp lệ.");
        }

        User currentUser = (User) authentication.getPrincipal();
        Long studentId = currentUser.getUserId();

        if (semester != null && schoolYear != null) {
            return repository.findSuccessClassesByStudentAndTerm(studentId, semester, schoolYear);
        } else {
            return repository.findSuccessClassesByStudent(studentId);
        }
    }
}
