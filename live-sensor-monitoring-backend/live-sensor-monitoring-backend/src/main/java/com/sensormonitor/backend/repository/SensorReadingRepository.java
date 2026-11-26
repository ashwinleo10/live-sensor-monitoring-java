package com.sensormonitor.backend.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sensormonitor.backend.entity.SensorReading;

public interface SensorReadingRepository
        extends JpaRepository<SensorReading, Long> {

    boolean existsByReadingId(String readingId);

    Optional<SensorReading> findByReadingId(String readingId);

    @Query("""
            SELECT r
            FROM SensorReading r
            WHERE r.timestamp = (
                SELECT MAX(r2.timestamp)
                FROM SensorReading r2
                WHERE r2.sensorId = r.sensorId
            )
            """)
    List<SensorReading> findLatestReadingsForEachSensor();

    List<SensorReading> findBySensorId(String sensorId);

    List<SensorReading> findByTimestampBetween(
            LocalDateTime startDate,
            LocalDateTime endDate
    );

    List<SensorReading> findBySensorIdAndTimestampBetween(
            String sensorId,
            LocalDateTime startDate,
            LocalDateTime endDate
    );

    Optional<SensorReading> findTopBySensorIdOrderByTimestampDesc(
            String sensorId
    );
}