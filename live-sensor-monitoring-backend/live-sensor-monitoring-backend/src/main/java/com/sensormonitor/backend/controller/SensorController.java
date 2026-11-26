package com.sensormonitor.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sensormonitor.backend.dto.SensorStatusResponse;
import com.sensormonitor.backend.entity.SensorReading;
import com.sensormonitor.backend.service.SensorReadingService;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private static final List<String> SENSOR_IDS =
            List.of("S1", "S2");

    private final SensorReadingService sensorReadingService;

    public SensorController(
            SensorReadingService sensorReadingService) {

        this.sensorReadingService = sensorReadingService;
    }

    @GetMapping
    public ResponseEntity<List<SensorStatusResponse>> getSensors() {

        List<SensorStatusResponse> sensors =
                SENSOR_IDS.stream()
                        .map(this::createSensorStatus)
                        .toList();

        return ResponseEntity.ok(sensors);
    }

    private SensorStatusResponse createSensorStatus(
            String sensorId) {

        SensorReading latestReading =
                sensorReadingService
                        .getLatestReadingForSensor(sensorId);

        if (latestReading == null) {

            return new SensorStatusResponse(
                    sensorId,
                    "OFFLINE",
                    null
            );
        }

        boolean online =
                sensorReadingService
                        .isSensorOnline(sensorId);

        return new SensorStatusResponse(
                sensorId,
                online ? "ONLINE" : "OFFLINE",
                latestReading.getTimestamp()
        );
    }
}