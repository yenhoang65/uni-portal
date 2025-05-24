package io.spring.uni_portal.repository;

import io.spring.uni_portal.model.GradeEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GradeEventRepository extends JpaRepository<GradeEvent, Long> {
    boolean existsByClassStudent_ClassStudentIdAndGradeType_Code(Long classStudentId, String code);

    @Query("SELECT ge, tc " +
            "FROM GradeEvent ge " +
            "JOIN ge.classStudent cs " +
            "JOIN cs.teachingScheduleRequest tsr " +
            "JOIN tsr.assignment ta " +
            "JOIN ta.termClass tc")
    List<Object[]> findGradeEventsWithTermClass();

    // Existing method (assumed to be already defined based on your code)

    // Add the new method with a custom query
    @Query("SELECT CASE WHEN COUNT(ge) > 0 THEN true ELSE false END " +
            "FROM GradeEvent ge " +
            "WHERE ge.classStudent.classStudentId = :classStudentId " +
            "AND ge.gradeType.code = :code " +
            "AND ge.gradeEventId != :gradeEventId")
    boolean existsByClassStudent_ClassStudentIdAndGradeType_CodeAndGradeEventIdNot(
            @Param("classStudentId") Long classStudentId,
            @Param("code") String code,
            @Param("gradeEventId") Long gradeEventId);

    Optional<GradeEvent> findById(Long id);
    List<GradeEvent> findByClassStudent_ClassStudentId(Long classStudentId);
}
