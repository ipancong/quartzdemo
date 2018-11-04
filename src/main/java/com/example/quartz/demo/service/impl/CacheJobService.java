package com.example.quartz.demo.service.impl;

import com.example.quartz.demo.entity.dao.JobEntityRepository;
import com.example.quartz.demo.entity.JobEntity;
import com.example.quartz.demo.jobs.execution.CacheJobExecution;
import com.example.quartz.demo.service.JobService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CacheJobService implements JobService {
  @Autowired private JobEntityRepository repository;

  @Override
  public JobEntity findJobById(Integer id) {
    return repository.findById(id).get();
  }

  @Override
  public List<JobEntity> findAllJobs() {
    return repository.findAll();
  }

  @Override
  public JobDataMap getJobDataMap(JobEntity jobEntity) {
    JobDataMap map = new JobDataMap();
    map.put("name", jobEntity.getJobName());
    map.put("jobGroup", jobEntity.getJobGroup());
    map.put("cronExpression", jobEntity.getCron());
    map.put("JobDescription", jobEntity.getDescription());
    map.put("jobStatus", jobEntity.getJobStatus());
    return map;
  }

  @Override
  public JobDetail geJobDetail(JobKey jobKey, String description, JobDataMap map) {
    return JobBuilder.newJob(CacheJobExecution.class)
        .withIdentity(jobKey)
        .withDescription(description)
        .setJobData(map)
        .storeDurably()
        .build();
  }

  @Override
  public Trigger getTrigger(JobEntity jobEntity) {
    return TriggerBuilder.newTrigger()
        .withIdentity(jobEntity.getJobName(), jobEntity.getJobGroup())
        .withSchedule(CronScheduleBuilder.cronSchedule(jobEntity.getCron()))
        .build();
  }

  @Override
  public JobKey getJobKey(JobEntity jobEntity) {
    return JobKey.jobKey(jobEntity.getJobName(), jobEntity.getJobGroup());
  }
}
