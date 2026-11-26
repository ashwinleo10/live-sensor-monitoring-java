package com.sensormonitor.backend.dto;

import java.time.LocalDateTime;

public class SensorStatusResponse {

    private String sensorId;
    private String status;
    private LocalDateTime lastReadingTime;

    public SensorStatusResponse(
            String sensorId,
            String status,
            LocalDateTime lastReadingTime) {

        this.sensorId = sensorId;
        this.status = status;
        this.lastReadingTime = lastReadingTime;
    }

    public String getSensorId() {
        return sensorId;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getLastReadingTime() {
        return lastReadingTime;
    }
}