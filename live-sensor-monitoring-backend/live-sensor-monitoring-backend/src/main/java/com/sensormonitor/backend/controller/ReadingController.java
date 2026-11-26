package com.sensormonitor.backend.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sensormonitor.backend.dto.ReadingRequest;
import com.sensormonitor.backend.dto.ReadingResponse;
import com.sensormonitor.backend.entity.SensorReading;
import com.sensormonitor.backend.service.ReadingService;
import com.sensormonitor.backend.service.SensorReadingService;

@RestController
@RequestMapping("/readings")
public class ReadingController {

    private final ReadingService readingService;
    private final SensorReadingService sensorReadingService;

    public ReadingController(
            ReadingService readingService,
            SensorReadingService sensorReadingService) {

        this.readingService = readingService;
        this.sensorReadingService = sensorReadingService;
    }

    @PostMapping
    public ResponseEntity<ReadingResponse> createReading(
            @RequestBody ReadingRequest request) {

        ReadingResponse response =
                readingService.saveReading(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<ReadingResponse>> getLatestReadings() {

        List<SensorReading> latestReadings =
                sensorReadingService.getLatestReadings();

        List<ReadingResponse> response =
                latestReadings.stream()
                        .map(ReadingResponse::new)
                        .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    public ResponseEntity<List<ReadingResponse>> getHistory(

            @RequestParam(required = false)
            String sensorId,

            @RequestParam(required = false)
            String startDate,

            @RequestParam(required = false)
            String endDate) {

        LocalDateTime startDateTime =
                startDate != null
                        ? LocalDateTime.parse(startDate)
                        : null;

        LocalDateTime endDateTime =
                endDate != null
                        ? LocalDateTime.parse(endDate)
                        : null;

        List<SensorReading> history =
                sensorReadingService.getHistory(
                        sensorId,
                        startDateTime,
                        endDateTime
                );

        List<ReadingResponse> response =
                history.stream()
                        .map(ReadingResponse::new)
                        .toList();

        return ResponseEntity.ok(response);
    }
}