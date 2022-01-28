package com.nstanogias.usagecounter.services;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsService {
  public long getUsageCount(Long key) throws InterruptedException {
    Thread.sleep(5000); //this is added to simulate latency from API
    return LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
  }
}
