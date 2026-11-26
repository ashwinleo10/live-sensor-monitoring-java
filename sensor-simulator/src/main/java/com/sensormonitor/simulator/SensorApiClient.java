package com.sensormonitor.simulator;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SensorApiClient {

    private static final String BACKEND_URL =
            "http://localhost:8080/readings";

    private final HttpClient httpClient;

    public SensorApiClient() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public void sendReading(SensorReading reading) {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BACKEND_URL))
                    .header("Content-Type", "application/json")
                    .POST(
                            HttpRequest.BodyPublishers.ofString(
                                    reading.toJson()
                            )
                    )
                    .build();

            HttpResponse<String> response =
                    httpClient.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            System.out.println(
                    "Sent " + reading.getSensorId()
                            + " | HTTP " + response.statusCode()
            );

        } catch (Exception exception) {

            System.out.println(
                    "Backend unavailable. Could not send reading from "
                            + reading.getSensorId()
            );
        }
    }
}