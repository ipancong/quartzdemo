package com.example.quartz.demo.entity.dao;

import com.example.quartz.demo.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobEntityRepository extends JpaRepository<JobEntity, Integer> {}
