package com.sensormonitor.backend.dto;

import java.time.LocalDateTime;

import com.sensormonitor.backend.entity.SensorReading;

public class ReadingResponse {

    private String readingId;
    private String sensorId;
    private double temperature;
    private double humidity;
    private LocalDateTime timestamp;

    public ReadingResponse(
            String readingId,
            String sensorId,
            double temperature,
            double humidity,
            LocalDateTime timestamp) {

        this.readingId = readingId;
        this.sensorId = sensorId;
        this.temperature = temperature;
        this.humidity = humidity;
        this.timestamp = timestamp;
    }

    public ReadingResponse(SensorReading reading) {

        this(
                reading.getReadingId(),
                reading.getSensorId(),
                reading.getTemperature(),
                reading.getHumidity(),
                reading.getTimestamp()
        );
    }

    public String getReadingId() {
        return readingId;
    }

    public String getSensorId() {
        return sensorId;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}