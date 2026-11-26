package com.sensormonitor.backend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sensor_readings")
public class SensorReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reading_id", nullable = false, unique = true)
    private String readingId;

    @Column(name = "sensor_id", nullable = false)
    private String sensorId;

    @Column(nullable = false)
    private double temperature;

    @Column(nullable = false)
    private double humidity;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public SensorReading() {
    }

    public SensorReading(
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

    public Long getId() {
        return id;
    }

    public String getReadingId() {
        return readingId;
    }

    public void setReadingId(String readingId) {
        this.readingId = readingId;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}