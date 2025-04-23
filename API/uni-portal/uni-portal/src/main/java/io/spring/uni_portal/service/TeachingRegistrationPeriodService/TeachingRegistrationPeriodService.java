package io.spring.uni_portal.service.TeachingRegistrationPeriodService;

import io.spring.uni_portal.dto.TeachingRegistrationPeriod.TeachingRegistrationPeriodDTO;
import io.spring.uni_portal.model.TeachingRegistrationPeriod;
import io.spring.uni_portal.repository.TeachingRegistrationPeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeachingRegistrationPeriodService implements ITeachingRegistrationPeriodService {

    @Autowired
    private TeachingRegistrationPeriodRepository repo;

    @Override
    public List<TeachingRegistrationPeriod> getAll() {
        List<TeachingRegistrationPeriod> list = repo.findAll();

        // Tự động cập nhật trạng thái nếu đã quá hạn
        LocalDateTime now = LocalDateTime.now();

        List<TeachingRegistrationPeriod> expiredList = list.stream()
                .filter(p -> "active".equalsIgnoreCase(p.getStatus()) && now.isAfter(p.getEndDate()))
                .toList();

        expiredList.forEach(p -> p.setStatus("inactive"));

        if (!expiredList.isEmpty()) {
            repo.saveAll(expiredList);
        }

        return list;
    }

    @Override
    public TeachingRegistrationPeriod create(TeachingRegistrationPeriodDTO dto) {
        TeachingRegistrationPeriod p = new TeachingRegistrationPeriod();
        p.setSchoolYear(dto.getSchoolYear());
        p.setSemester(dto.getSemester());
        p.setStartDate(dto.getStartDate());
        p.setEndDate(dto.getEndDate());
        p.setStatus(dto.getStatus());
        return repo.save(p);
    }

    @Override
    public TeachingRegistrationPeriod update(Long id, TeachingRegistrationPeriodDTO dto) {
        TeachingRegistrationPeriod p = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
        p.setSchoolYear(dto.getSchoolYear());
        p.setSemester(dto.getSemester());
        p.setStartDate(dto.getStartDate());
        p.setEndDate(dto.getEndDate());
        p.setStatus(dto.getStatus());
        return repo.save(p);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
