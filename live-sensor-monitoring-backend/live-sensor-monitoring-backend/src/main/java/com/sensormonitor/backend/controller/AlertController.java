package com.sensormonitor.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sensormonitor.backend.dto.AlertResponse;
import com.sensormonitor.backend.entity.Alert;
import com.sensormonitor.backend.service.AlertService;

@RestController
@RequestMapping("/alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(
            AlertService alertService) {

        this.alertService = alertService;
    }

    @GetMapping
    public ResponseEntity<List<AlertResponse>> getAlerts() {

        List<Alert> alerts =
                alertService.getAllAlerts();

        List<AlertResponse> response =
                alerts.stream()
                        .map(AlertResponse::new)
                        .toList();

        return ResponseEntity.ok(response);
    }
}