package com.sensormonitor.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReadingRequest {

    @JsonProperty("reading_id")
    private String readingId;

    @JsonProperty("sensor_id")
    private String sensorId;

    private double temperature;

    private double humidity;

    public ReadingRequest() {
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
}