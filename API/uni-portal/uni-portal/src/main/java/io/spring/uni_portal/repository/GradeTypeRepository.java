package io.spring.uni_portal.repository;

import io.spring.uni_portal.model.ExamSchedule;
import io.spring.uni_portal.model.GradeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GradeTypeRepository extends JpaRepository<GradeType, Long> {
}