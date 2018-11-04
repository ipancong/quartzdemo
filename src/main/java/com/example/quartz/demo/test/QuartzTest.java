package com.example.quartz.demo.test;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;


@Slf4j
public class QuartzTest {
  public static final String JOB_NAME = "DEFAULT_JOBNAME";
  public static final String JOB_GROUP_NAME = "GROUP_NAME";

  public static final String TRIGGER_NAME = "DEFAULT_TRIGGER_NAME";
  public static final String TRIGGER_GROUP_NAME = "TRIGGER_GROUP_NAME";

  public static void main(String[] args) {

    Scheduler scheduler = null;
    try {
      // 获取调度器
      scheduler = StdSchedulerFactory.getDefaultScheduler();
      // 开启调度器
      scheduler.start();
    } catch (SchedulerException e) {
      e.printStackTrace();
    }

    // 定义一个job
    JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withIdentity(JOB_NAME, JOB_GROUP_NAME).build();
    // 定义一个trigger
    Trigger trigger =
        TriggerBuilder.newTrigger()
            .withIdentity(TRIGGER_NAME, TRIGGER_GROUP_NAME)
            .startNow()
            .withSchedule(simpleSchedule().withIntervalInSeconds(10).repeatForever())
            .build();

    // 在scheduler上面注册job和trigger
    try {
      scheduler.scheduleJob(jobDetail,trigger);
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
  }
}
