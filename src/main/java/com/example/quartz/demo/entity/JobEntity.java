package com.example.quartz.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * job的实体类
 */
@Entity
@Table(name = "t_jobs")
@Data
public class JobEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  /**
   * 任务名称
   */
  private String jobName;
  /**
   * 任务组名称
   */
  private String jobGroup;
  /**
   * 执行的cron字符串  http://cron.qqe2.com/
   */
  private String cron;

  /**
   * job的描述信息
   */
  private String description;

  /**
   * 执行参数
   */
  private String execParams;

  /**
   * job的优先级
   */
  private int priority;

  /**
   * 下一个job的id
   */
  private int nextJob;


  /**
   * job的状态 0,1 当状态为1时正常执行
   */
  private int jobStatus;


  @Override
  public String toString() {
    return "JobEntity{" +
            "id=" + id +
            ", jobName='" + jobName + '\'' +
            ", jobGroup='" + jobGroup + '\'' +
            ", cron='" + cron + '\'' +
            ", description='" + description + '\'' +
            ", execParams='" + execParams + '\'' +
            ", priority=" + priority +
            ", nextJob=" + nextJob +
            ", jobStatus='" + jobStatus + '\'' +
            '}';
  }
}
