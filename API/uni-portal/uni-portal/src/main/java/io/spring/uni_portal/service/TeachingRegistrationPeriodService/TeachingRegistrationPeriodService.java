package io.spring.uni_portal.service.TeachingRegistrationPeriodService;

import io.spring.uni_portal.SendEmail.IEmailService;
import io.spring.uni_portal.dto.TeachingRegistrationPeriod.TeachingRegistrationPeriodDTO;
import io.spring.uni_portal.model.TeachingRegistrationPeriod;
import io.spring.uni_portal.repository.TeachingRegistrationPeriodRepository;
import io.spring.uni_portal.service.UserService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeachingRegistrationPeriodService implements ITeachingRegistrationPeriodService {

    @Autowired
    private TeachingRegistrationPeriodRepository repo;
    @Autowired
    private IUserService userService;

    @Autowired
    private IEmailService emailService;

    @Override
    public List<TeachingRegistrationPeriod> getAll() {
        List<TeachingRegistrationPeriod> list = repo.findAll();

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

//    @Override
//    public TeachingRegistrationPeriod create(TeachingRegistrationPeriodDTO dto) {
//        TeachingRegistrationPeriod p = new TeachingRegistrationPeriod();
//        p.setSchoolYear(dto.getSchoolYear());
//        p.setSemester(dto.getSemester());
//        p.setStartDate(dto.getStartDate());
//        p.setEndDate(dto.getEndDate());
//        p.setStatus(dto.getStatus());
//        return repo.save(p);
//    }

    @Override
    public TeachingRegistrationPeriod create(TeachingRegistrationPeriodDTO dto) {
        TeachingRegistrationPeriod p = new TeachingRegistrationPeriod();
        p.setSchoolYear(dto.getSchoolYear());
        p.setSemester(dto.getSemester());
        p.setStartDate(dto.getStartDate());
        p.setEndDate(dto.getEndDate());
        p.setStatus(dto.getStatus());

        TeachingRegistrationPeriod saved = repo.save(p);

        // Gửi email thông báo
        List<String> lecturerEmails = userService.getAllLecturerEmails();
        String subject = "Thông báo mở đăng ký lịch giảng dạy";
        String content = String.format("""
        Kính gửi giảng viên,

        Một đợt đăng ký lịch giảng dạy mới đã được mở.

        Năm học: %d - Học kỳ: %d
        Thời gian đăng ký: từ %s đến %s

        Trân trọng,
        Hệ thống UniPortal
        """, dto.getSchoolYear(), dto.getSemester(), dto.getStartDate(), dto.getEndDate());

        emailService.sendEmail(lecturerEmails, subject, content);

        return saved;
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
