package com.factorrh.hrmanagement.controller;

import com.factorrh.hrmanagement.entity.Schedule;
import com.factorrh.hrmanagement.model.dto.IDRequest;
import com.factorrh.hrmanagement.repository.ControllerRepository;
import com.factorrh.hrmanagement.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/controller")
public class ControllerController {

    private final ScheduleService scheduleService;
    private final ControllerRepository controllerRepository;

    public ControllerController (ScheduleService scheduleService, ControllerRepository controllerRepository) {
        this.scheduleService = scheduleService;
        this.controllerRepository = controllerRepository;
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<Schedule>> getSchedules(@RequestBody IDRequest request) {
        if (controllerRepository.findById(request.Id()).isPresent()) {
            return ResponseEntity.ok().body(scheduleService.getAllSchedules());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/schedule/{scheduleId}")
    public ResponseEntity<Void> updateSchedule(@PathVariable UUID scheduleId, @RequestBody IDRequest request) {
        if (controllerRepository.findById(request.Id()).isPresent()) {
            scheduleService.updateSchedule(scheduleId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}