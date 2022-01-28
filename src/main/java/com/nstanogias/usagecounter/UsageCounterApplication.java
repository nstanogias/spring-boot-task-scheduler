package com.nstanogias.usagecounter;

import com.nstanogias.usagecounter.models.License;
import com.nstanogias.usagecounter.repository.LicenseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class UsageCounterApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsageCounterApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(LicenseRepository licenseRepo) {
		return args -> {
			licenseRepo.save(new License());
			licenseRepo.save(new License());
			licenseRepo.save(new License());
		};
	}
}
