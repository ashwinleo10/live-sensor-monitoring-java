package com.sensormonitor.simulator;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SensorSimulatorApplication {

    private static final long INTERVAL_SECONDS = 2;

    private static final int DISCONNECT_AFTER_CYCLES = 8;
    private static final int DISCONNECT_DURATION_CYCLES = 10;

    public static void main(String[] args) {

        SensorService sensorService = new SensorService();
        SensorApiClient sensorApiClient = new SensorApiClient();

        ScheduledExecutorService scheduler =
                Executors.newScheduledThreadPool(1);

        final int[] cycleCounter = {0};

        Runnable generateReadings = () -> {

            cycleCounter[0]++;

            System.out.println(
                    "Cycle: " + cycleCounter[0]
            );

            boolean sensorS2Disconnected =
                    cycleCounter[0] > DISCONNECT_AFTER_CYCLES
                    && cycleCounter[0]
                    <= DISCONNECT_AFTER_CYCLES
                    + DISCONNECT_DURATION_CYCLES;

            SensorReading s1Reading =
                    sensorService.generateReading("S1");

            System.out.println(s1Reading);
            sensorApiClient.sendReading(s1Reading);

            if (sensorS2Disconnected) {

                System.out.println(
                        "S2 is temporarily disconnected."
                );

            } else {

                SensorReading s2Reading =
                        sensorService.generateReading("S2");

                System.out.println(s2Reading);
                sensorApiClient.sendReading(s2Reading);
            }

            System.out.println("----------------------------------");
        };

        scheduler.scheduleAtFixedRate(
                generateReadings,
                0,
                INTERVAL_SECONDS,
                TimeUnit.SECONDS
        );
    }
}