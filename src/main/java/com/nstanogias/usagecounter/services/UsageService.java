package com.nstanogias.usagecounter.services;

import com.nstanogias.usagecounter.models.License;
import com.nstanogias.usagecounter.repository.LicenseRepository;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class UsageService {
    @Autowired
    private LicenseRepository licenseRepository;

    static final Logger LOGGER = Logger.getLogger(UsageService.class.getName());

    @Scheduled(fixedRateString = "${interval}")
    @SchedulerLock(name = "computeUsageTask")
    public void computeUsage() throws InterruptedException {
        List<License> allLicenses = licenseRepository.findAll();
        allLicenses.forEach(license -> sendMessageToBillingService(license));

        Thread.sleep(5000); //this is added to simulate latency from API
    }

    private void sendMessageToBillingService(License license) {
        LOGGER.info(license.toString());
    }
}
