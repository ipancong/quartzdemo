package com.example.quartz.demo.jobs.cache.impl;

import com.example.quartz.demo.jobs.cache.CacheJobBase;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("cache_group.cache_job1")
public class CacheJobFirst implements CacheJobBase {
  @Override
  public void cache() {
    System.out.println(" 呜啦啦啦啦啦啦，这个是 job1 执行----->>>>>" + new Date());
  }
}
