package io.spring.uni_portal.repository;

import io.spring.uni_portal.model.TeachingAssignment;
import io.spring.uni_portal.model.TermClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeachingAssignmentRepository extends JpaRepository<TeachingAssignment, Long> {
    TeachingAssignment findByTermClass(TermClass termClass);
    // Lấy ra những lớp đã có giảng viên
    List<TeachingAssignment> findByLecturerIsNotNull();

    List<TeachingAssignment> findByLecturer_UserId(Long userId);

    TeachingAssignment findByTermClassAndAssignmentType(TermClass termClass, TeachingAssignment.AssignmentType assignmentType);

//    Page<TeachingAssignment> findAllBySearchValue(String searchValue, Pageable pageable);

//    Page<TeachingAssignment> findByLecturerUserUserIdAndSearchValue(Long userId, String searchValue, Pageable pageable);
@Query("SELECT t FROM TeachingAssignment t " +
        "JOIN t.termClass termClass " +
        "JOIN t.subject subject " +
        "WHERE (termClass.classname LIKE %:searchValue% OR subject.subjectName LIKE %:searchValue%)")
Page<TeachingAssignment> findAllBySearchValue(@Param("searchValue") String searchValue, Pageable pageable);

    @Query("SELECT t FROM TeachingAssignment t " +
            "WHERE t.lecturer.user.userId = :userId " +
            "AND (t.subject.subjectName LIKE %:searchValue% OR t.termClass.classname LIKE %:searchValue%)")
    Page<TeachingAssignment> findByLecturerUserUserIdAndSearchValue(
            @Param("userId") Long userId,
            @Param("searchValue") String searchValue,
            Pageable pageable);

    Optional<TeachingAssignment> findByTermClass_TermclassId(Long termclassId);
}
