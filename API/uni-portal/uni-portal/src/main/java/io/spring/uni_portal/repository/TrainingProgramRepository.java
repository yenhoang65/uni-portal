package io.spring.uni_portal.repository;

import io.spring.uni_portal.model.TrainingProgram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, Long> {
    List<TrainingProgram> findByTrainingCodeContainingIgnoreCase(String trainingCode);
}
