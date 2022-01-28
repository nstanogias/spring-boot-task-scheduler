package com.nstanogias.usagecounter.repository;

import com.nstanogias.usagecounter.models.License;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseRepository extends JpaRepository<License, Long> {

  @Query("SELECT l from License l where l.lockedUntil >= ?1 or l.lockedUntil is null ")
  List<License> findAllByLockedUntilAfter(LocalDateTime now);
}
