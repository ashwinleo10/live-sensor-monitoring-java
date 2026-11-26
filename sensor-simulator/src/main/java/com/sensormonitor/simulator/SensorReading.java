package com.sensormonitor.simulator;

import java.util.Locale;

public class SensorReading {

    private final String readingId;
    private final String sensorId;
    private final double temperature;
    private final double humidity;

    public SensorReading(
            String readingId,
            String sensorId,
            double temperature,
            double humidity) {

        this.readingId = readingId;
        this.sensorId = sensorId;
        this.temperature = temperature;
        this.humidity = humidity;
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

    public String toJson() {

        return String.format(
                Locale.US,
                """
                {
                    "reading_id": "%s",
                    "sensor_id": "%s",
                    "temperature": %.1f,
                    "humidity": %.1f
                }
                """,
                readingId,
                sensorId,
                temperature,
                humidity
        );
    }

    @Override
    public String toString() {
        return "SensorReading{" +
                "readingId='" + readingId + '\'' +
                ", sensorId='" + sensorId + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                '}';
    }
}