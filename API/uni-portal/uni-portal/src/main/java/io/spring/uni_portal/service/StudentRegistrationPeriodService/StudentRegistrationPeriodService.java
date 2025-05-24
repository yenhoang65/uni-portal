package io.spring.uni_portal.service.StudentRegistrationPeriodService;


import io.spring.uni_portal.Job.FinalizeRegistrationJob;
import io.spring.uni_portal.dto.StudentRegistrationPeriod.StudentRegistrationPeriodDTO;
import io.spring.uni_portal.model.StudentRegistrationPeriod;
import io.spring.uni_portal.repository.StudentRegistrationPeriodRepository;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentRegistrationPeriodService implements IStudentRegistrationPeriodService {

    @Autowired
    private StudentRegistrationPeriodRepository repo;

    @Autowired
    private Scheduler quartzScheduler;

    @Override
    public List<StudentRegistrationPeriod> getAll() {
        List<StudentRegistrationPeriod> list = repo.findAll();

        LocalDateTime now = LocalDateTime.now();

        List<StudentRegistrationPeriod> expiredList = list.stream()
                .filter(p -> "active".equalsIgnoreCase(p.getStatus()) && now.isAfter(p.getEndDate()))
                .toList();

        expiredList.forEach(p -> p.setStatus("inactive"));

        if (!expiredList.isEmpty()) {
            repo.saveAll(expiredList);
        }

        return list;
    }


//    @Override
//    public StudentRegistrationPeriod create(StudentRegistrationPeriodDTO dto) {
//        StudentRegistrationPeriod p = new StudentRegistrationPeriod();
//        p.setSchoolYear(dto.getSchoolYear());
//        p.setSemester(dto.getSemester());
//        p.setStartDate(dto.getStartDate());
//        p.setEndDate(dto.getEndDate());
//        p.setStatus(dto.getStatus());
//        return repo.save(p);
//    }

    @Override
    public StudentRegistrationPeriod create(StudentRegistrationPeriodDTO dto) {
        StudentRegistrationPeriod p = new StudentRegistrationPeriod();
        p.setSchoolYear(dto.getSchoolYear());
        p.setSemester(dto.getSemester());
        p.setStartDate(dto.getStartDate());
        p.setEndDate(dto.getEndDate());
        p.setStatus("active");

        StudentRegistrationPeriod saved = repo.save(p);

        scheduleFinalizeJob(saved.getId(), saved.getEndDate());

        return saved;
    }



    @Override
    public StudentRegistrationPeriod update(Long id, StudentRegistrationPeriodDTO dto) {
        StudentRegistrationPeriod p = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
        p.setSchoolYear(dto.getSchoolYear());
        p.setSemester(dto.getSemester());
        p.setStartDate(dto.getStartDate());
        p.setEndDate(dto.getEndDate());
        p.setStatus(dto.getStatus());

        StudentRegistrationPeriod saved = repo.save(p);

        scheduleFinalizeJob(saved.getId(), saved.getEndDate());

        return saved;
//        return repo.save(p);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }


    private void scheduleFinalizeJob(Long periodId, LocalDateTime endDate) {
        try {
            JobKey jobKey = new JobKey("finalize-registration-" + periodId, "registration");
            TriggerKey triggerKey = new TriggerKey("trigger-registration-" + periodId, "registration");

            // XÓA JOB/TRIGGER CŨ TRƯỚC KHI ĐẶT LẠI
            if (quartzScheduler.checkExists(jobKey)) {
                quartzScheduler.deleteJob(jobKey);
            }

            JobDetail jobDetail = JobBuilder.newJob(FinalizeRegistrationJob.class)
                    .withIdentity(jobKey)
                    .usingJobData("periodId", periodId)
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    .startAt(java.sql.Timestamp.valueOf(endDate))
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withMisfireHandlingInstructionFireNow())
                    .build();

            quartzScheduler.scheduleJob(jobDetail, trigger);
            System.out.println("Đặt lại lịch finalize ID = " + periodId + " tại " + endDate);

        } catch (SchedulerException e) {
            throw new RuntimeException("Không thể đặt lại lịch Quartz", e);
        }
    }


}
