package com.example.quartz.demo.jobs.cache.impl;

import com.example.quartz.demo.jobs.cache.CacheJobBase;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("cache_group.cache_job2")
public class CacheJobSecond implements CacheJobBase {

  @Override
  public void cache() {
    System.out.println(" 呜啦啦啦啦啦啦，这个是 job2 执行----->>>>>" + new Date());
  }
}
