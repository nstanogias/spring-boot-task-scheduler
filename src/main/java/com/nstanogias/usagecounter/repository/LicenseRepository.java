package com.nstanogias.usagecounter.repository;

import com.nstanogias.usagecounter.models.License;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseRepository extends JpaRepository<License, Long> {

  List<License> findAllByLocked(boolean b);
}
