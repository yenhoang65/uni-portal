//package io.spring.uni_portal.service.ClassSubjectStudentService;
//
//import io.spring.uni_portal.dto.ClassStudent.ClassStudentResponseDTO;
//import io.spring.uni_portal.model.ClassSubjectStudent;
//import io.spring.uni_portal.repository.ClassSubjectStudentRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class ClassSubjectStudentService implements IClassSubjectStudentService {
//
//    @Autowired
//    private ClassSubjectStudentRepository repository;
//
//    @Override
//    public Page<ClassStudentResponseDTO> getRegisteredClassesByStatuses(Long userId, List<String> statuses, Pageable pageable) {
//        Page<ClassSubjectStudent> page = repository.findByStudentUserIdAndStatusIn(userId, statuses, pageable);
//        return page.map(css -> new ClassStudentResponseDTO(css));
//    }
//}
//
