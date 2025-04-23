package io.spring.uni_portal.repository;

import io.spring.uni_portal.model.StudentRegistrationPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRegistrationPeriodRepository extends JpaRepository<StudentRegistrationPeriod, Long> {
}
