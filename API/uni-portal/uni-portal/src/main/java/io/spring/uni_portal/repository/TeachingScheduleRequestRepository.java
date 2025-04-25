package io.spring.uni_portal.repository;

import io.spring.uni_portal.model.TeachingAssignment;
import io.spring.uni_portal.model.TeachingScheduleRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeachingScheduleRequestRepository extends JpaRepository<TeachingScheduleRequest, Long> {

}
