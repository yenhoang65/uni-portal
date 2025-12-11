package io.spring.uni_portal.repository;

import io.spring.uni_portal.model.TeachingSlide;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeachingSlideRepository extends JpaRepository<TeachingSlide, Long> {
    Optional<TeachingSlide> findBySession_SessionId(Long sessionId);

}
