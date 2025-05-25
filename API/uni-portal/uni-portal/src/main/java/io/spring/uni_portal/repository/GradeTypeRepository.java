package io.spring.uni_portal.repository;

import io.spring.uni_portal.model.GradeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeTypeRepository extends JpaRepository<GradeType, Long> {}