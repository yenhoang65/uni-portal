package io.spring.uni_portal.repository;

import io.spring.uni_portal.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    @Query("SELECT COALESCE(MAX(f.facultyId), 999) FROM Faculty f")
    Long findMaxFacultyId();
}