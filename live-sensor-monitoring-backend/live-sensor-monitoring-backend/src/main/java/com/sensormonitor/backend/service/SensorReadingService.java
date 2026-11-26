package com.sensormonitor.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sensormonitor.backend.entity.SensorReading;
import com.sensormonitor.backend.repository.SensorReadingRepository;

@Service
public class SensorReadingService {

    private static final long SENSOR_OFFLINE_THRESHOLD_SECONDS = 10;

    private final SensorReadingRepository sensorReadingRepository;

    public SensorReadingService(
            SensorReadingRepository sensorReadingRepository) {

        this.sensorReadingRepository = sensorReadingRepository;
    }

    public List<SensorReading> getLatestReadings() {

        return sensorReadingRepository
                .findLatestReadingsForEachSensor();
    }

    public List<SensorReading> getHistory(
            String sensorId,
            LocalDateTime startDate,
            LocalDateTime endDate) {

        if (sensorId != null && startDate != null && endDate != null) {

            return sensorReadingRepository
                    .findBySensorIdAndTimestampBetween(
                            sensorId,
                            startDate,
                            endDate
                    );
        }

        if (sensorId != null) {

            return sensorReadingRepository
                    .findBySensorId(sensorId);
        }

        if (startDate != null && endDate != null) {

            return sensorReadingRepository
                    .findByTimestampBetween(
                            startDate,
                            endDate
                    );
        }

        return sensorReadingRepository.findAll();
    }

    public boolean isSensorOnline(String sensorId) {

        Optional<SensorReading> latestReading =
                sensorReadingRepository
                        .findTopBySensorIdOrderByTimestampDesc(
                                sensorId
                        );

        if (latestReading.isEmpty()) {
            return false;
        }

        LocalDateTime lastReadingTime =
                latestReading.get().getTimestamp();

        LocalDateTime offlineTime =
                LocalDateTime.now()
                        .minusSeconds(
                                SENSOR_OFFLINE_THRESHOLD_SECONDS
                        );

        return lastReadingTime.isAfter(offlineTime);
    }

    public SensorReading getLatestReadingForSensor(
            String sensorId) {

        return sensorReadingRepository
                .findTopBySensorIdOrderByTimestampDesc(
                        sensorId
                )
                .orElse(null);
    }
}