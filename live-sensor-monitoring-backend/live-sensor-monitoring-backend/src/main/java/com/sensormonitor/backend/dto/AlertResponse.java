package com.sensormonitor.backend.dto;

import java.time.LocalDateTime;

import com.sensormonitor.backend.entity.Alert;

public class AlertResponse {

    private String sensorId;
    private String type;
    private String message;
    private LocalDateTime timestamp;
    private boolean active;

    public AlertResponse(Alert alert) {

        this.sensorId = alert.getSensorId();
        this.type = alert.getType();
        this.message = alert.getMessage();
        this.timestamp = alert.getTimestamp();
        this.active = alert.isActive();
    }

    public String getSensorId() {
        return sensorId;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isActive() {
        return active;
    }
}