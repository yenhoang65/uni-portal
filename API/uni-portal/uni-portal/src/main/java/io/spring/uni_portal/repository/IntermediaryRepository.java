package io.spring.uni_portal.repository;

import io.spring.uni_portal.model.Intermediary;
import io.spring.uni_portal.model.IntermediaryId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface IntermediaryRepository extends JpaRepository<Intermediary, IntermediaryId> {
    List<Intermediary> findByTrainingProgram_TrainingProgramId(Long trainingProgramId);
    List<Intermediary> findByTrainingProgram_TrainingProgramNameContainingIgnoreCase(String trainingProgramName);
    @Transactional
    @Modifying
    void deleteByTrainingProgram_TrainingProgramId(Long trainingProgramId);
    List<Intermediary> findByTrainingProgram_TrainingProgramIdAndSchoolYear(Long trainingProgramId, String schoolYear);
}