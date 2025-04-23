package io.spring.uni_portal.repository;

import io.spring.uni_portal.model.TeachingRegistrationPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeachingRegistrationPeriodRepository extends JpaRepository<TeachingRegistrationPeriod, Long> {
}