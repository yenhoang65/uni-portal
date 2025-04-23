package io.spring.uni_portal.repository;

import io.spring.uni_portal.model.TeachingAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeachingAssignmentRepository extends JpaRepository<TeachingAssignment, Long> {
    List<TeachingAssignment> findByLecturerUserIdAndSchoolYearAndSemester(Long userId, Long schoolYear, Long semester);
}
