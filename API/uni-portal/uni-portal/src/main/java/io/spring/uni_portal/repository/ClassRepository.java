package io.spring.uni_portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import io.spring.uni_portal.model.Class;
public interface ClassRepository extends JpaRepository<Class, Long> {
}
