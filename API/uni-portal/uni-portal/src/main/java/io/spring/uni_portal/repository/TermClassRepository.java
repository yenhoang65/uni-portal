package io.spring.uni_portal.repository;

import io.spring.uni_portal.model.TermClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TermClassRepository extends JpaRepository<TermClass, Long> {
    boolean existsById(Long id);

    boolean existsByClassname(String classname);

    List<TermClass> findByClassnameContaining(String classname);

    Optional<TermClass> findById(Long id);


}
