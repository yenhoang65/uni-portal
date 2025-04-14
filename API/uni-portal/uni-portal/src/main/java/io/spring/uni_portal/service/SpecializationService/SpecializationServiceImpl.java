package io.spring.uni_portal.service.SpecializationService;

import io.spring.uni_portal.dto.Specialization.SpecializationDTO;
import io.spring.uni_portal.dto.Specialization.SpecializationResponse;
import io.spring.uni_portal.model.Major;
import io.spring.uni_portal.model.Specialization;
import io.spring.uni_portal.repository.MajorRepository;
import io.spring.uni_portal.repository.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecializationServiceImpl implements ISpecializationService {

    @Autowired
    private SpecializationRepository specializationRepository;

    @Autowired
    private MajorRepository majorRepository;

    private SpecializationResponse toResponse(Specialization entity) {
        return new SpecializationResponse(
                entity.getSpecializationId(),
                entity.getSpecializationName(),
                entity.getMajor().getMajorId(),
                entity.getMajor().getMajorName()
        );
    }

    private Specialization toEntity(SpecializationDTO dto) {
        Major major = majorRepository.findById(dto.getMajorId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy ngành với ID: " + dto.getMajorId()));

        Specialization specialization = new Specialization();
        specialization.setSpecializationId(dto.getSpecializationId());
        specialization.setSpecializationName(dto.getSpecializationName());
        specialization.setMajor(major);
        return specialization;
    }

    @Override
    public SpecializationResponse create(SpecializationDTO dto) {
        if (dto.getSpecializationId() == null) {
            throw new RuntimeException("specializationId không được để trống");
        }
        if (specializationRepository.existsById(dto.getSpecializationId())) {
            throw new RuntimeException("specializationId đã tồn tại");
        }
        return toResponse(specializationRepository.save(toEntity(dto)));
    }

    @Override
    public SpecializationResponse getById(Long id) {
        return specializationRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyên ngành"));
    }

    @Override
    public List<SpecializationResponse> getAll() {
        return specializationRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SpecializationResponse update(Long id, SpecializationDTO dto) {
        Specialization spec = specializationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyên ngành"));
        spec.setSpecializationName(dto.getSpecializationName());

        if (!spec.getMajor().getMajorId().equals(dto.getMajorId())) {
            Major major = majorRepository.findById(dto.getMajorId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy ngành"));
            spec.setMajor(major);
        }

        return toResponse(specializationRepository.save(spec));
    }

    @Override
    public void delete(Long id) {
        specializationRepository.deleteById(id);
    }
}
