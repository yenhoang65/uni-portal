package io.spring.uni_portal.repository;

import io.spring.uni_portal.model.TeachingAssignment;
import io.spring.uni_portal.model.TermClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeachingAssignmentRepository extends JpaRepository<TeachingAssignment, Long> {
    TeachingAssignment findByTermClass(TermClass termClass);
    // Lấy ra những lớp đã có giảng viên
    List<TeachingAssignment> findByLecturerIsNotNull();

}
