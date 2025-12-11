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


    @Query("""
    SELECT t FROM TeachingAssignment t
    JOIN t.termClass termClass
    JOIN t.subject subject
    WHERE (:searchValue IS NULL OR termClass.classname LIKE %:searchValue% OR subject.subjectName LIKE %:searchValue%)
      AND (:semester IS NULL OR termClass.semester = :semester)
      AND (:schoolyear IS NULL OR termClass.schoolyears = :schoolyear)
""")
    Page<TeachingAssignment> findAllWithFilters(
            @Param("searchValue") String searchValue,
            @Param("semester") String semester,
            @Param("schoolyear") String schoolyear,
            Pageable pageable
    );

    @Query("""
    SELECT t FROM TeachingAssignment t
    JOIN t.termClass termClass
    JOIN t.subject subject
    WHERE t.lecturer.user.userId = :userId
      AND (:searchValue IS NULL OR subject.subjectName LIKE %:searchValue% OR termClass.classname LIKE %:searchValue%)
      AND (:semester IS NULL OR termClass.semester = :semester)
      AND (:schoolyear IS NULL OR termClass.schoolyears = :schoolyear)
""")
    Page<TeachingAssignment> findByLecturerUserUserIdWithFilters(
            @Param("userId") Long userId,
            @Param("searchValue") String searchValue,
            @Param("semester") String semester,
            @Param("schoolyear") String schoolyear,
            Pageable pageable
    );

}
