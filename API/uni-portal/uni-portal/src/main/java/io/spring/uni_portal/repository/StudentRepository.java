package io.spring.uni_portal.repository;

import io.spring.uni_portal.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByClassEntity_ClassId(Long classId);
    Optional<Student> findByUserId(Long userId);
    @Query("""
    SELECT s FROM Student s
    WHERE CAST(s.user.userId AS string) LIKE %:keyword%
       OR LOWER(s.user.userName) LIKE LOWER(CONCAT('%', :keyword, '%'))
""")
    Page<Student> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
