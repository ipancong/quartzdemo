package com.example.quartz.demo.jobs.execution;

import com.example.quartz.demo.jobs.cache.CacheJobBase;
import com.example.quartz.demo.utils.ApplicationContextHelper;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

@DisallowConcurrentExecution
@Component
@Slf4j
public class CacheJobExecution implements Job {
  /**
   * JobDataMap，它是JobDetail的一个属性。
   * JobDataMap是Map接口的一个实现，并且它有一些便利的方法来储存和检索基本类型数据,
   * Trigger中也可以设置JobDataMap属性，这是为了在多个Trigger中使用相同的Job。
   * JobExecutionContext将会合并JobDetail与Trigger的JobDataMap，如果其中属性名相同，后者将覆盖前者,
   * 可以使用JobExecutionContext.getMergedJobDataMap()方法来获取合并后的JobDataMap。
   * @param jobExecutionContext JobExecutionContext中封装有Quartz运行所需的参数
   * @throws JobExecutionException execute()方法只允许抛出JobExecutionException异常
   */
  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    JobDataMap map = jobExecutionContext.getMergedJobDataMap();
     // 根据jobkey，也就是jobgroup,jobname这种方式找到对应的类运行，我们可以把一个spring容器管理的bean的名字改为这种形式。
    log.info("Running JobEntity jobName : {} ", map.getString("name"));
    String groupName = map.getString("jobGroup");
    String jobName = map.getString("name");
    CacheJobBase execution = (CacheJobBase) ApplicationContextHelper.getBean(groupName+"."+jobName);
    execution.cache();
  }
}
