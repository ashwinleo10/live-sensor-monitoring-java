package com.sensormonitor.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sensormonitor.backend.entity.Alert;
import com.sensormonitor.backend.repository.AlertRepository;

@Service
public class AlertService {

    public static final String HIGH_TEMPERATURE =
            "HIGH_TEMPERATURE";

    public static final String HIGH_HUMIDITY =
            "HIGH_HUMIDITY";

    public static final String SENSOR_DISCONNECTED =
            "SENSOR_DISCONNECTED";

    private final AlertRepository alertRepository;

    public AlertService(
            AlertRepository alertRepository) {

        this.alertRepository = alertRepository;
    }

    public void checkReading(
            String sensorId,
            double temperature,
            double humidity) {

        if (temperature > 35.0) {

            activateAlert(
                    sensorId,
                    HIGH_TEMPERATURE,
                    "High Temperature"
            );

        } else {

            deactivateAlert(
                    sensorId,
                    HIGH_TEMPERATURE
            );
        }

        if (humidity > 75.0) {

            activateAlert(
                    sensorId,
                    HIGH_HUMIDITY,
                    "High Humidity"
            );

        } else {

            deactivateAlert(
                    sensorId,
                    HIGH_HUMIDITY
            );
        }

        deactivateAlert(
                sensorId,
                SENSOR_DISCONNECTED
        );
    }

    public void checkSensorDisconnected(
            String sensorId) {

        activateAlert(
                sensorId,
                SENSOR_DISCONNECTED,
                "Sensor Disconnected"
        );
    }

    private void activateAlert(
            String sensorId,
            String type,
            String message) {

        boolean alreadyActive =
                alertRepository
                        .findTopBySensorIdAndTypeAndActiveTrueOrderByTimestampDesc(
                                sensorId,
                                type
                        )
                        .isPresent();

        if (alreadyActive) {
            return;
        }

        Alert alert =
                new Alert(
                        sensorId,
                        type,
                        message,
                        LocalDateTime.now(),
                        true
                );

        alertRepository.save(alert);
    }

    private void deactivateAlert(
            String sensorId,
            String type) {

        alertRepository
                .findTopBySensorIdAndTypeAndActiveTrueOrderByTimestampDesc(
                        sensorId,
                        type
                )
                .ifPresent(alert -> {

                    alert.setActive(false);

                    alertRepository.save(alert);
                });
    }

    public List<Alert> getAllAlerts() {

        return alertRepository
                .findAllByOrderByTimestampDesc();
    }
}