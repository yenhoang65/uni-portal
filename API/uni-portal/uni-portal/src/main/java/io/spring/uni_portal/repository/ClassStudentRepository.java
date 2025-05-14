package io.spring.uni_portal.repository;

import io.spring.uni_portal.model.ClassStudent;
import io.spring.uni_portal.model.TeachingScheduleRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassStudentRepository extends JpaRepository<ClassStudent, Long> {

    Optional<ClassStudent> findByTeachingScheduleRequest(TeachingScheduleRequest teachingScheduleRequest);
}
