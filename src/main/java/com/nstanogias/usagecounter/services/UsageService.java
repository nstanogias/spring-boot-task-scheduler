package com.nstanogias.usagecounter.services;

import com.nstanogias.usagecounter.models.License;
import com.nstanogias.usagecounter.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
        List<License> allLicenses = licenseRepository.findAllByLockedUntilAfter(LocalDateTime.now());
        allLicenses.forEach(license -> {
            try {
                processLicense(license.getKey());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void processLicense(Long key) throws Exception {
        LOGGER.info("here");
        License license = licenseRepository.getById(key);
        if (license.getLockedUntil() == null || license.getLockedUntil().isBefore(LocalDateTime.now())) {
            try {
                license.setLockedUntil(LocalDateTime.now().plus(30, ChronoUnit.SECONDS));
                licenseRepository.save(license);

                long usage = analyticsService.getUsageCount(key);
                long delta = usage - license.getCurrentUsage();
                license.setCurrentUsage(usage);
                sendMessageToBillingService(license, delta);

                license.setLockedUntil(null);
                licenseRepository.save(license);
            } catch (Exception e) {
                LOGGER.info(e.getMessage());
            }
        }
    }


    private void sendMessageToBillingService(License license, long delta) {
        LOGGER.info("license: " + license.toString() + ", delta: " + delta);
    }
}
