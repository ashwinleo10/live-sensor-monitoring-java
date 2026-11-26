package com.sensormonitor.backend.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.sensormonitor.backend.dto.ReadingRequest;
import com.sensormonitor.backend.dto.ReadingResponse;
import com.sensormonitor.backend.entity.SensorReading;
import com.sensormonitor.backend.repository.SensorReadingRepository;

@Service
public class ReadingService {

    private final SensorReadingRepository readingRepository;
    private final AlertService alertService;

    public ReadingService(
            SensorReadingRepository readingRepository,
            AlertService alertService) {

        this.readingRepository = readingRepository;
        this.alertService = alertService;
    }

    public ReadingResponse saveReading(
            ReadingRequest request) {

        validateReading(request);

        if (readingRepository.existsByReadingId(
                request.getReadingId())) {

            throw new IllegalArgumentException(
                    "Reading ID already exists"
            );
        }

        SensorReading reading =
                new SensorReading(
                        request.getReadingId(),
                        request.getSensorId(),
                        request.getTemperature(),
                        request.getHumidity(),
                        LocalDateTime.now()
                );

        SensorReading savedReading =
                readingRepository.save(reading);

        alertService.checkReading(
                savedReading.getSensorId(),
                savedReading.getTemperature(),
                savedReading.getHumidity()
        );

        return new ReadingResponse(savedReading);
    }

    private void validateReading(
            ReadingRequest request) {

        if (request.getReadingId() == null
                || request.getReadingId().isBlank()) {

            throw new IllegalArgumentException(
                    "Reading ID is required"
            );
        }

        if (request.getSensorId() == null
                || request.getSensorId().isBlank()) {

            throw new IllegalArgumentException(
                    "Sensor ID is required"
            );
        }

        if (request.getTemperature() < 20.0
                || request.getTemperature() > 40.0) {

            throw new IllegalArgumentException(
                    "Temperature must be between 20 and 40 degrees Celsius"
            );
        }

        if (request.getHumidity() < 30.0
                || request.getHumidity() > 80.0) {

            throw new IllegalArgumentException(
                    "Humidity must be between 30 and 80 percent"
            );
        }
    }
}