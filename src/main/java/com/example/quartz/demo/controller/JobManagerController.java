package com.example.quartz.demo.controller;

import com.example.quartz.demo.entity.JobEntity;
import com.example.quartz.demo.service.impl.CacheJobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Set;

@RestController
@Slf4j
public class JobManagerController {
  private SchedulerFactoryBean factory;
  private CacheJobService jobService;

  @Autowired
  public JobManagerController(
      SchedulerFactoryBean schedulerFactoryBean, CacheJobService cacheJobService) {
    this.factory = schedulerFactoryBean;
    this.jobService = cacheJobService;
  }

  /** 初始化，启动所有状态正常的jobs */
  @PostConstruct
  public void init() {
    try {
      reBootAllJobs();
    } catch (SchedulerException e) {
    }
  }

  /**
   * 根据jobid重启某个job
   *
   * @param id
   * @return
   * @throws SchedulerException
   */
  @RequestMapping("/jobs/{id}")
  public void refresh(@PathVariable Integer id) throws SchedulerException {
    JobEntity entity = jobService.findJobById(id);
    if (entity == null) {
      log.info("Job {} does not exist!", id);
      throw new SchedulerException();
    }
    TriggerKey triggerKey = new TriggerKey(entity.getJobName(), entity.getJobGroup());
    JobKey jobKey = jobService.getJobKey(entity);
    Scheduler scheduler = factory.getScheduler();
    try {
      scheduler.unscheduleJob(triggerKey);
      scheduler.deleteJob(jobKey);
      JobDataMap map = jobService.getJobDataMap(entity);
      JobDetail jobDetail = jobService.geJobDetail(jobKey, entity.getDescription(), map);
      if (entity.getJobStatus() == 1) {
        scheduler.scheduleJob(jobDetail, jobService.getTrigger(entity));
        log.info("Refresh JobEntity : " + entity.getJobName() + " success !");
      }
    } catch (SchedulerException e) {
      log.error("Refresh Job{} failed!", id);
    }
  }

  /**
   * 重启所有的job
   *
   * @return
   */
  @RequestMapping("/jobs")
  public void refreshAll() {
    try {
      reBootAllJobs();
    } catch (SchedulerException e) {
      log.error("refreshAll failed");
    }
  }

  /** 重新启动所有的job */
  private void reBootAllJobs() throws SchedulerException {
    Scheduler scheduler = factory.getScheduler();
    Set<JobKey> set = scheduler.getJobKeys(GroupMatcher.anyGroup());
    set.forEach(
        jobKey -> {
          try {
            scheduler.deleteJob(jobKey);
          } catch (SchedulerException e) {
            log.error("");
          }
        });
    jobService
        .findAllJobs()
        .forEach(
            job -> {
              JobDataMap map = jobService.getJobDataMap(job);
              JobKey jobKey = jobService.getJobKey(job);
              JobDetail jobDetail = jobService.geJobDetail(jobKey, job.getDescription(), map);
              // 当job状态为1时，启动job
              if (job.getJobStatus() == 1) {
                try {
                  scheduler.scheduleJob(jobDetail, jobService.getTrigger(job));
                  log.info(
                      "Job register jobName : {} , jobGroup : {} , cron : {}",
                      job.getJobName(),
                      job.getJobGroup(),
                      job.getCron());
                } catch (SchedulerException e) {
                  log.error(
                      "Job register failed : {} , jobGroup : {}",
                      job.getJobName(),
                      job.getJobGroup(),
                      job.getCron());
                }
              }
            });
  }
}
