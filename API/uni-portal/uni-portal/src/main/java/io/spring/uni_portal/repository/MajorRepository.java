package io.spring.uni_portal.repository;

import io.spring.uni_portal.model.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MajorRepository extends JpaRepository<Major, Long> {
    @Query("SELECT COALESCE(MAX(m.majorId), 999) FROM Major m")
    Long findMaxMajorId();
}
