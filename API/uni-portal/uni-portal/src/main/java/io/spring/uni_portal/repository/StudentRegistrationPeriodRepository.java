package io.spring.uni_portal.repository;

import io.spring.uni_portal.model.StudentRegistrationPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface StudentRegistrationPeriodRepository extends JpaRepository<StudentRegistrationPeriod, Long> {

    @Query("SELECT s FROM StudentRegistrationPeriod s WHERE :now BETWEEN s.startDate AND s.endDate AND s.status = 'active'")
    Optional<StudentRegistrationPeriod> findActivePeriod(@Param("now") LocalDateTime now);
}
