//package io.spring.uni_portal.config;
//
//import io.spring.uni_portal.service.ClassRegistrationService.IClassRegistrationService;
//import org.quartz.Job;
//import org.quartz.JobExecutionContext;
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class FinalizeRegistrationJob implements Job {
//
//    @Autowired
//    private IClassRegistrationService classRegistrationServiceImpl;
//
//    @Override
//    public void execute(JobExecutionContext context) {
//        Long periodId = context.getJobDetail().getJobDataMap().getLong("periodId");
//        classRegistrationServiceImpl.finalizeRegistrationPeriod(periodId);
//    }
//}
