package io.spring.uni_portal.service.SubjectService;


import io.spring.uni_portal.dto.Subject.SubjectDTO;
import io.spring.uni_portal.dto.Subject.SubjectResponse;
import io.spring.uni_portal.model.Subject;
import io.spring.uni_portal.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements ISubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    private SubjectResponse toResponse(Subject subject) {
        return new SubjectResponse(
                subject.getSubjectId(),
                subject.getSubjectName(),
                subject.getLtCredits(),
                subject.getThCredits(),
                subject.getSubjectDescription(),
                subject.getSubjectType(),
                subject.getSubjectCoefficient()
        );
    }

    private Subject toEntity(SubjectDTO dto) {
        Subject subject = new Subject();
        subject.setSubjectId(dto.getSubjectId());
        subject.setSubjectName(dto.getSubjectName());
        subject.setLtCredits(dto.getLtCredits());
        subject.setThCredits(dto.getThCredits());
        subject.setSubjectDescription(dto.getSubjectDescription());
        subject.setSubjectType(dto.getSubjectType());
        subject.setSubjectCoefficient(dto.getSubjectCoefficient());
        return subject;
    }

    @Override
    public SubjectResponse create(SubjectDTO dto) {
        if (dto.getSubjectId() == null) {
            throw new RuntimeException("subjectId không được để trống");
        }
        if (subjectRepository.existsById(dto.getSubjectId())) {
            throw new RuntimeException("subjectId đã tồn tại");
        }

        return toResponse(subjectRepository.save(toEntity(dto)));
    }

    @Override
    public SubjectResponse getById(Long id) {
        return subjectRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy môn học"));
    }

    @Override
    public List<SubjectResponse> getAll() {
        return subjectRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SubjectResponse> searchByName(String keyword) {
        return subjectRepository.findBySubjectNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SubjectResponse update(Long id, SubjectDTO dto) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy môn học"));

        subject.setSubjectName(dto.getSubjectName());
        subject.setLtCredits(dto.getLtCredits());
        subject.setThCredits(dto.getThCredits());
        subject.setSubjectDescription(dto.getSubjectDescription());
        subject.setSubjectType(dto.getSubjectType());
        subject.setSubjectCoefficient(dto.getSubjectCoefficient());

        return toResponse(subjectRepository.save(subject));
    }

    @Override
    public void delete(Long id) {
        subjectRepository.deleteById(id);
    }
}
