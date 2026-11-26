package com.sensormonitor.simulator;

import java.util.Random;
import java.util.UUID;

public class SensorService {

    private static final double MIN_TEMPERATURE = 20.0;
    private static final double MAX_TEMPERATURE = 40.0;

    private static final double MIN_HUMIDITY = 30.0;
    private static final double MAX_HUMIDITY = 80.0;

    private final Random random = new Random();

    public SensorReading generateReading(String sensorId) {

        String readingId = UUID.randomUUID().toString();

        double temperature = generateRandomValue(
                MIN_TEMPERATURE,
                MAX_TEMPERATURE
        );

        double humidity = generateRandomValue(
                MIN_HUMIDITY,
                MAX_HUMIDITY
        );

        return new SensorReading(
                readingId,
                sensorId,
                temperature,
                humidity
        );
    }

    private double generateRandomValue(double min, double max) {

        double value = min + (max - min) * random.nextDouble();

        return Math.round(value * 10.0) / 10.0;
    }
}