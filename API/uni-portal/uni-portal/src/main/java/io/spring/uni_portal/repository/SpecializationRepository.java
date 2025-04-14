package io.spring.uni_portal.repository;

import io.spring.uni_portal.model.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
}
