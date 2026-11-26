package com.sensormonitor.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sensormonitor.backend.entity.Alert;

public interface AlertRepository
        extends JpaRepository<Alert, Long> {

    List<Alert> findAllByOrderByTimestampDesc();

    Optional<Alert> findTopBySensorIdAndTypeAndActiveTrueOrderByTimestampDesc(
            String sensorId,
            String type
    );
}