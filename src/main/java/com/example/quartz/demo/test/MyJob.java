package com.example.quartz.demo.test;


import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


import java.util.Date;

@Slf4j
public class MyJob implements Job {

    /**
     * 定义自己的job，需要做的事情卸载execute方法中
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("this is my job execute ->>" + new Date());
    }
}
