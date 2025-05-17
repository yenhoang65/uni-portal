package io.spring.uni_portal.repository;

import io.spring.uni_portal.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findBySubjectNameContainingIgnoreCase(String keyword);
}
