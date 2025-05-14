package io.spring.uni_portal.repository;

import io.spring.uni_portal.model.TeachingRegistrationPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TeachingRegistrationPeriodRepository extends JpaRepository<TeachingRegistrationPeriod, Long> {
    @Query("SELECT t FROM TeachingRegistrationPeriod t WHERE t.startDate <= :currentDate AND t.endDate >= :currentDate AND t.status = 'active'")
    Optional<TeachingRegistrationPeriod> findActivePeriod(LocalDateTime currentDate);
}