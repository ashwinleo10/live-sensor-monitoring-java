package com.sensormonitor.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sensormonitor.backend.entity.SensorReading;
import com.sensormonitor.backend.repository.SensorReadingRepository;

@Component
public class SensorMonitoringScheduler {

    private static final long SENSOR_OFFLINE_THRESHOLD_SECONDS = 10;

    private static final List<String> SENSOR_IDS =
            List.of("S1", "S2");

    private final SensorReadingRepository sensorReadingRepository;
    private final AlertService alertService;

    public SensorMonitoringScheduler(
            SensorReadingRepository sensorReadingRepository,
            AlertService alertService) {

        this.sensorReadingRepository = sensorReadingRepository;
        this.alertService = alertService;
    }

    @Scheduled(fixedRate = 2000)
    public void checkSensorConnectivity() {

        for (String sensorId : SENSOR_IDS) {

            SensorReading latestReading =
                    sensorReadingRepository
                            .findTopBySensorIdOrderByTimestampDesc(
                                    sensorId
                            )
                            .orElse(null);

            if (latestReading == null) {

                alertService.checkSensorDisconnected(
                        sensorId
                );

                continue;
            }

            LocalDateTime offlineTime =
                    LocalDateTime.now()
                            .minusSeconds(
                                    SENSOR_OFFLINE_THRESHOLD_SECONDS
                            );

            boolean sensorOffline =
                    latestReading.getTimestamp()
                            .isBefore(offlineTime);

            if (sensorOffline) {

                alertService.checkSensorDisconnected(
                        sensorId
                );
            }
        }
    }
}