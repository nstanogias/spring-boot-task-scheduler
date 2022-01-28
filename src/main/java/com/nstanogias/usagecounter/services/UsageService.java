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
    @Autowired
    private AnalyticsService analyticsService;

    static final Logger LOGGER = Logger.getLogger(UsageService.class.getName());

    @Scheduled(fixedRateString = "${interval}")
    public void computeUsage() throws InterruptedException {
        List<License> allLicenses = licenseRepository.findAllByLocked(false);
        allLicenses.forEach(license -> processLicense(license.getKey()));

        Thread.sleep(5000); //this is added to simulate latency from API
    }

    public void processLicense(Long key) {
        License license = licenseRepository.getById(key);
        license.locked = true;
        licenseRepository.save(license);

        long usage = analyticsService.getUsageCount(key);
        sendMessageToBillingService(license, usage);

        license.locked = false;
        licenseRepository.save(license);
    }


    private void sendMessageToBillingService(License license, long usage) {
        LOGGER.info("license: " + license.toString() + ", usage: " + usage);
    }
}
