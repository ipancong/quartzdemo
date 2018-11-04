package com.example.quartz.demo.service;

import com.example.quartz.demo.entity.JobEntity;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Trigger;

import java.util.List;

public interface JobService {

  /**
   * 根据id获取job
   *
   * @param id
   * @return
   */
  JobEntity findJobById(Integer id);

  /**
   * 查询出所有的job
   *
   * @return
   */
  List<JobEntity> findAllJobs();

  /**
   * 获取job参数，map类型
   *
   * @param jobEntity
   * @return
   */
  JobDataMap getJobDataMap(JobEntity jobEntity);

  /**
   * 根据job的基本信息，组装出jobDetail
   * JobDetail就是Job的一个具体实例
   * @param jobKey
   * @param description
   * @param map
   * @return
   */
  JobDetail geJobDetail(JobKey jobKey, String description, JobDataMap map);

  /**
   * 根据job基本信息，获取Trigger
   *
   * @param jobEntity
   * @return
   */
  Trigger getTrigger(JobEntity jobEntity);

  /**
   * 获取jobKey，jobKey可以唯一标识一个job
   * @param jobEntity
   * @return
   */
  JobKey getJobKey(JobEntity jobEntity);
}
