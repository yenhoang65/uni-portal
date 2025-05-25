package io.spring.uni_portal.Job;

import io.spring.uni_portal.service.ClassRegistrationService.IClassRegistrationService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class FinalizeRegistrationJob implements Job {

    @Autowired
    private IClassRegistrationService registrationService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap data = context.getMergedJobDataMap();
        Long periodId = data.getLong("periodId");

        try {
            registrationService.finalizeRegistrationPeriod(periodId);
            System.out.println("Đã finalize đợt đăng ký ID = " + periodId);
        } catch (Exception e) {
            throw new JobExecutionException("Lỗi khi finalize đợt đăng ký ID = " + periodId, e);
        }
    }
}

